package storage;

import exception.NotExistStorageException;
import model.ContactType;
import model.Resume;
import sql.ConnectionFactory;
import util.SqlHelper;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.runSqlNoResults(connectionFactory, "DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        if (!SqlHelper.runSqlNoResults(connectionFactory, "UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
        })) {
            throw new NotExistStorageException(resume.getUuid());
        }


        deleteContacts(connectionFactory, resume);

        saveContacts(connectionFactory, resume);
    }

    @Override
    public void save(Resume resume) {
        SqlHelper.runSqlNoResults(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES(?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
        });

        saveContacts(connectionFactory, resume);
    }

    @Override
    public void delete(String uuid) {
        if (!SqlHelper.runSqlNoResults(connectionFactory, "DELETE FROM resume r WHERE r.uuid=?", ps -> ps.setString(1, uuid))) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {

        final String[] fullName = new String[1];
        SqlHelper.runSql(connectionFactory, "SELECT * FROM resume r WHERE r.uuid=?", ps -> ps.setString(1, uuid), rs -> {
            fullName[0] = rs.getString("full_Name");
        });

        if(fullName[0] == null) {
            throw new NotExistStorageException(uuid);
        }

        Resume resume = new Resume(uuid, fullName[0]);

        resume.setContacts(readContacts(connectionFactory, resume));

        return resume;
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> list = new ArrayList<>();

        SqlHelper.runSql(connectionFactory, "SELECT * FROM resume", rs -> {
            Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_Name"));
            list.add(resume);

            resume.setContacts(readContacts(connectionFactory, resume));
        });

        return list;
    }

    @Override
    public int size() {
        final int[] size = new int[1];
        SqlHelper.runSql(connectionFactory, "SELECT COUNT (*) FROM resume", rs -> {
            size[0] = rs.getInt(1);
        });

        return size[0];
    }

    private Map<ContactType, String> readContacts(ConnectionFactory cf, Resume r) {
        Map<ContactType, String> map = new HashMap<>();
        SqlHelper.runSql(cf,
                "SELECT * FROM contact WHERE resume_uuid=?",
                ps -> ps.setString(1, r.getUuid()),
                rs -> map.put(ContactType.valueOf(rs.getString("type")), rs.getString("value")));
        return map;
    }

    ;

    private void deleteContacts(ConnectionFactory cf, Resume r) {
        SqlHelper.runSqlNoResults(cf,
                "DELETE FROM contact WHERE resume_uuid=?",
                ps -> ps.setString(1, r.getUuid()));
    }

    private void saveContacts(ConnectionFactory cf, Resume r) {
        Map<ContactType, String> contacts = r.getContacts();
        for (Map.Entry<ContactType, String> e : contacts.entrySet()) {
            SqlHelper.runSqlNoResults(connectionFactory, "INSERT INTO contact (resume_uuid, type, value) VALUES(?,?,?)", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
            });
        }

    }
}
