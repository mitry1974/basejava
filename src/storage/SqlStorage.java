package storage;

import exception.NotExistStorageException;
import model.ContactType;
import model.Resume;
import sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }

            deleteContacts(resume.getUuid());
            saveContacts(resume.getUuid(), resume.getContacts());
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES(?,?)", ps-> {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            return null;
            });
        saveContacts(resume.getUuid(), resume.getContacts());
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
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ArrayList<Resume> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resume r = new Resume(rs.getString("uuid"), rs.getString("full_Name"));
                r.setContacts(loadContacts(r.getUuid()));
                list.add(r);
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

    private void deleteContacts(String uuid){
        sqlHelper.execute("DELETE FROM contact WHERE resume_uuid=?", se->{
            se.setString(1, uuid);
            se.execute();
            return null;
        });
    }

    private void saveContacts(String uuid, Map<ContactType, String> contacts){
        sqlHelper.transactionalExecute(st->{
            try(PreparedStatement ps = st.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")){
                ps.setString(1, uuid);
                for(Map.Entry<ContactType, String> e: contacts.entrySet()){
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }

                ps.executeBatch();
            }
            return null;
        });
    }

    private Map<ContactType, String> loadContacts(String uuid){
        Map<ContactType, String> map = new HashMap<>();
        return sqlHelper.execute("SELECT * FROM contact WHERE resume_uuid=?", se->{
            se.setString(1, uuid);
            ResultSet rs = se.executeQuery();
            while (rs.next()){
                map.put(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
            return map;
        });
    }
}
