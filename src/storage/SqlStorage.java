package storage;

import exception.NotExistStorageException;
import model.Resume;
import sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.runSql("DELETE FROM resume", PreparedStatement::executeUpdate);
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.runSql("UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
        });

/*
        deleteContacts(resume);
        saveContacts(resume);
*/
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.runSql("INSERT INTO resume (uuid, full_name) VALUES(?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.executeUpdate();
        });

        //saveContacts(resume);
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.runSql("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        final Resume[] resume = {null};
        sqlHelper.runSql("SELECT * FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                resume[0] = new Resume(uuid, rs.getString("full_Name"));
                //resume[0].setContacts(readContacts(resume[0]));

            } else {
                throw new NotExistStorageException(uuid);
            }
        });


        return resume[0];
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> list = new ArrayList<>();

        sqlHelper.runSql("SELECT * FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_Name"));
                list.add(resume);
                //resume.setContacts(readContacts(resume));
            }
        });

        return list;
    }

    @Override
    public int size() {
        final int[] size = new int[1];
        sqlHelper.runSql("SELECT COUNT (*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                size[0] = rs.getInt(1);
            }
        });

        return size[0];
    }

/*

    private Map<ContactType, String> readContacts(Resume r) {
        Map<ContactType, String> map = new HashMap<>();
        sqlHelper.runSql("SELECT * FROM contact WHERE resume_uuid=?",
                ps -> ps.setString(1, r.getUuid()),
                rs -> map.put(ContactType.valueOf(rs.getString("type")), rs.getString("value")));
        return map;
    }

    ;

    private void deleteContacts(Resume r) {
        sqlHelper.runSqlNoResults(
                "DELETE FROM contact WHERE resume_uuid=?",
                ps -> ps.setString(1, r.getUuid()));
    }

    private void saveContacts(Resume r) {
        Map<ContactType, String> contacts = r.getContacts();
        for (Map.Entry<ContactType, String> e : contacts.entrySet()) {
            sqlHelper.runSqlNoResults("INSERT INTO contact (resume_uuid, type, value) VALUES(?,?,?)", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
            });
        }

    }
*/
}
