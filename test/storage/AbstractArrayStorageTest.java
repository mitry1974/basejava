package storage;

import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest {
    private Storage storage = null;
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_1 = "uuid1";


    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void size() {
        Assert.assertEquals(storage.size(), 3);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(storage.size(), 0);
    }

    @Test
    public void update() {
        Resume r = new Resume("uuid2");
        storage.update(r);
        Assert.assertEquals(storage.get("uuid2"), r);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExisting() {
        Resume r = new Resume("uuid200052");
        storage.update(r);
    }

    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        Assert.assertEquals(array.length, storage.size());
    }

    @Test
    public void save() {
        Resume resume = new Resume("Another one!");
        storage.save(resume);
        Resume anotherResume = storage.get(resume.getUuid());
        Assert.assertTrue(anotherResume == resume);
    }

    @Test(expected = StorageException.class)
    public void saveOverSize() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume("uuid" + String.valueOf(i)));
        }

        storage.save(new Resume("uuid_over!"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid1");
        Assert.assertTrue(storage.get("uuid1") == null);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteUnExisting() {
        storage.delete("UUID_200005");
        storage.delete("UUID_200006");
    }

    @Test
    public void get() {
        Assert.assertTrue(storage.get("uuid1") != null);
    }
}