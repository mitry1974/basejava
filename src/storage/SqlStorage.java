package storage;

import exception.NotExistStorageException;
import model.ContactType;
import model.Resume;
import sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
                deleteContacts(conn, resume.getUuid());
                saveContacts(conn, resume.getUuid(), resume.getContacts());
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
            saveContacts(conn, r.getUuid(), r.getContacts());
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
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "   LEFT JOIN contact c " +
                        "     ON r.uuid = c.resume_uuid " +
                        "       WHERE r.uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();

                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(rs.getString("uuid"), rs.getString("full_Name"));
                    do {
                        String type = rs.getString("type");
                        String value = rs.getString("value");
                        if (type != null && value != null) {
                            r.addContact(ContactType.valueOf(type), value);
                        }
                    } while (rs.next());

                    return r;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                        " SELECT * FROM resume r" +
                        "   LEFT JOIN contact c" +
                        "     ON r.uuid = c.resume_uuid " +
                        "       ORDER BY full_name, uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    ArrayList<Resume> list = new ArrayList<>();
                    String uuid = "";
                    Resume r = null;
                    while (rs.next()) {
                        if(!rs.getString("uuid").equals(uuid)){
                            uuid = rs.getString("uuid");
                            r = new Resume(uuid, rs.getString("full_Name"));
                            list.add(r);
                        }
                        r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
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

    private void deleteContacts(Connection conn, String uuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void saveContacts(Connection conn, String uuid, Map<ContactType, String> contacts) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            ps.setString(1, uuid);
            for (Map.Entry<ContactType, String> e : contacts.entrySet()) {
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    private Map<ContactType, String> loadContacts(String uuid) {
        Map<ContactType, String> map = new HashMap<>();
        return sqlHelper.execute("SELECT * FROM contact WHERE resume_uuid=?", se -> {
            se.setString(1, uuid);
            ResultSet rs = se.executeQuery();
            while (rs.next()) {
                map.put(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
            return map;
        });
    }
}
