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


    public AbstractArrayStorageTest(StorageType storageType) {
        switch (storageType) {
            case ARRAY_STORAGE:
                this.storage = new ArrayStorage();
                break;
            case SORTEDARRAY_STORAGE:
                this.storage = new SortedArrayStorage();
                break;
        }
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
        System.out.println("Size:" + storage.size());
        printAll();
    }

    @Test
    public void clear() {
        storage.clear();
        printAll();
    }

    @Test
    public void update() {
        Resume r = new Resume("uuid2");
        storage.update(r);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExisting() {
        Resume r = new Resume("uuid200052");
        storage.update(r);
    }

    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        for (Resume r : array) {
            System.out.println(r);
        }
    }

    @Test
    public void save() {
        storage.save(new Resume("Another one!"));
    }

    @Test(expected = StorageException.class)
    public void saveOverSize() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume("uuid" + String.valueOf(i)));
        }

        storage.save(new Resume("uuid_over!"));
    }

    @Test
    public void delete() {
        storage.delete("uuid1");
        storage.delete("uuid2");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteUnExisting() {
        storage.delete("UUID_200005");
        storage.delete("UUID_200006");
    }

    @Test
    public void get() {
        System.out.println(storage.get("uuid1"));
    }

    void printAll() {
        for (Resume r : storage.getAll()) {
            System.out.println(r);
        }
    }
}