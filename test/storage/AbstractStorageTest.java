package storage;

import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";
    private static final String UUID4 = "uuid4";

    private static final Resume resume1 = new Resume(UUID1);
    private static final Resume resume2 = new Resume(UUID2);
    private static final Resume resume2_1 = new Resume(UUID2);
    private static final Resume resume3 = new Resume(UUID3);
    private static final Resume resume4 = new Resume(UUID4);


    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() {
        assertEquals(storage.size(), 3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(storage.size(), 0);
    }

    @Test
    public void update() {
        storage.update(resume2_1);
        assertEquals(storage.get(UUID2), resume2_1);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(list.size(), storage.size());
        assertEquals(list.get(0), resume1);
        assertEquals(list.get(1), resume2);
        assertEquals(list.get(2), resume3);
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertSame(storage.get(resume4.getUuid()), resume4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID1);
        storage.get(UUID1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteUnExisting() {
        storage.delete("UUID_200005");
    }

    @Test
    public void get() {
        assertEquals(storage.get(UUID1), resume1);
    }
}
