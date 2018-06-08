package storage;

import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID1           = "uuid1";
    private static final String UUID2           = "uuid2";
    private static final String UUID3           = "uuid3";
    private static final String UUID4           = "uuid4";
    private static final String uuidNotExisting = "UUID_200005";
    private static final String uuidOversize    = "UUID_10000";

    private static final Resume resume1         = new Resume(UUID1);
    private static final Resume resume2         = new Resume(UUID2);
    private static final Resume resume2_1       = new Resume(UUID2);
    private static final Resume resume3         = new Resume(UUID3);
    private static final Resume resume4         = new Resume(UUID4);


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
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(resume2_1);
        assertSame(resume2_1, storage.get(UUID2) );
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExisting() {
        storage.update(resume4);
    }

    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        assertEquals(storage.size(), array.length );

        assertArrayEquals(new Resume[]{resume1, resume2, resume3}, array);
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertSame(resume4, storage.get(resume4.getUuid()));
    }

    @Test(expected = StorageException.class)
    public void saveOverSize() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i));
            }
        }catch (ArrayIndexOutOfBoundsException e){
            throw e;
        }

        storage.save(new Resume(uuidOversize));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID1);
        assertNull(storage.get(UUID1));
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteUnExisting() {
        storage.delete(uuidNotExisting);
    }

    @Test
    public void get() {
        assertEquals(resume1, storage.get(UUID1));
    }
}