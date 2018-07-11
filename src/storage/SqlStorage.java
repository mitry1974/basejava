package storage;

import exception.NotExistStorageException;
import model.*;
import sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::executeUpdate);
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                deleteContacts(conn, resume);
                deleteSections(conn, resume);

                insertContacts(conn, resume);
                insertSections(conn, resume);
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        Resume r = sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "  WHERE r.uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(rs.getString("uuid"), rs.getString("full_Name"));
                });

        sqlHelper.execute("SELECT * from contact c where c.resume_uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        addContact(rs, r);
                    }
                    return null;
                });

        sqlHelper.execute("SELECT * from section s WHERE s.resume_uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        addSection(rs, r);
                    }
                    return null;

                });
        return r;
    }

    @Override
    public List<Resume> getAllSorted() {

        return sqlHelper.execute("" +
                        " SELECT * FROM resume r" +
                        "   ORDER BY full_name, uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    ArrayList<Resume> list = new ArrayList<>();
                    Resume r = null;
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        r = new Resume(uuid, rs.getString("full_Name"));
                        list.add(r);
                        loadContacts(r);
                        loadSections(r);
                    }
                    return list;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT (*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void loadContacts(Resume r){
        sqlHelper.execute("select * from contact c where c.resume_uuid = ?", ps -> {
            ps.setString(1, r.getUuid());
            ResultSet crs = ps.executeQuery();
            while (crs.next()) {
                r.addContact(ContactType.valueOf(crs.getString("type")), crs.getString("value"));
            }
            return null;
        });
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            ps.setString(1, r.getUuid());
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String type = rs.getString("type");
        String value = rs.getString("value");
        if (type != null && value != null) {
            r.addContact(ContactType.valueOf(type), value);
        }
    }

    private void loadSections(Resume r){
        sqlHelper.execute("select * from section s where s.resume_uuid = ?", ps -> {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addSection(rs,r);
            }
            return null;
        });
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        SectionType st = SectionType.valueOf(rs.getString("type"));
        String value = rs.getString("value");
        Section section = null;
        switch (st) {
            case PERSONAL:
            case OBJECTIVE:
                section = new TextSection(value);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                section = new ListSection(value.split("\n"));
                break;
            case EXPERIENCE:
            case EDUCATION:
                break;
        }
        r.addSection(st, section);
    }

    private void deleteSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value ) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                String data = "";
                Section section = e.getValue();
                SectionType st = e.getKey();
                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        data = ((TextSection) section).getContent();
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = (ListSection) section;
                        StringBuilder sb = new StringBuilder();
                        for (String s : ls.getItems()) {
                            sb.append(s);
                            sb.append('\n');
                        }
                        data = sb.toString();
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        break;
                }
                ps.setString(1, r.getUuid());
                ps.setString(2, st.name());
                ps.setString(3, data);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}