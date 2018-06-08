package storage;

import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID1       = "uuid1";
    private static final String UUID2       = "uuid2";
    private static final String UUID3       = "uuid3";
    private static final String UUID4       = "uuid4";

    private static final Resume resume1     = new Resume(UUID1);
    private static final Resume resume2     = new Resume(UUID2);
    private static final Resume resume2_1   = new Resume(UUID2);
    private static final Resume resume3     = new Resume(UUID3);
    private static final Resume resume4     = new Resume(UUID4);


    public AbstractArrayStorageTest(Storage storage) {
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

    @Test(expected = NotExistStorageException.class)
    public void updateNotExisting() {
        storage.update(resume4);
    }

    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        assertEquals(array.length, storage.size());

        assertEquals(array[0],resume1);
        assertEquals(array[1],resume2);
        assertEquals(array[2],resume3);
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertSame(storage.get(resume4.getUuid()), resume4);
    }

    @Test(expected = StorageException.class)
    public void saveOverSize() {
        storage.clear();
        for (int i = 0; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume("uuid" + i));
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID1);
        assertNull(storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteUnExisting() {
        storage.delete("UUID_200005");
    }

    @Test
    public void get() {
        assertEquals(storage.get(UUID1),resume1);
    }
}