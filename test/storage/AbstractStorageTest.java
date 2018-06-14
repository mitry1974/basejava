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
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size() );
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(resume2_1);
        assertSame(resume2_1, storage.get(UUID2));
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(storage.size(), list.size());
        assertEquals(resume1, list.get(0));
        assertEquals(resume2, list.get(1));
        assertEquals(resume3, list.get(2));
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertSame(resume4, storage.get(resume4.getUuid()));
        assertEquals(4, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID1);
        assertEquals(2, storage.size());
        storage.get(UUID1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteUnExisting() {
        storage.delete("UUID_200005");
    }

    @Test
    public void get() {
        assertEquals(resume1, storage.get(UUID1));
    }
}
