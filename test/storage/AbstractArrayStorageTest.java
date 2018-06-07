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
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

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
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        System.out.println("Testing size----------------------------");
        System.out.println("Size:" + storage.size());
        printAll();
        System.out.println("Testing size - finished ----------------");
    }

    @Test
    public void clear() {
        System.out.println("Testing clear----------------------------");
        storage.clear();
        printAll();
        System.out.println("Testing clear- finished -----------------");
    }

    @Test
    public void update() {
        Resume r = new Resume("uuid2");
        storage.update(r);
    }

    @Test
    public void updateNotExisting() {
        try {
            Resume r = new Resume("uuid200052");
            storage.update(r);
        } catch (NotExistStorageException e) {
            System.out.println("try to update not existing resume" + e);
        }
    }

    @Test
    public void getAll() {
        System.out.println("Testing getAll - printing all items--------------------------");
        Resume[] array = storage.getAll();
        for (Resume r : array) {
            System.out.println(r);
        }

        System.out.println("Testing getAll - finish--------------------------------------");
    }

    @Test
    public void save() {
        storage.save(new Resume("Another one!"));
    }

    @Test
    public void saveOverSize() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume("uuid" + String.valueOf(i)));
        }

        try {
            storage.save(new Resume("uuid_over!"));

        } catch (StorageException e) {
            StringBuilder sb = new StringBuilder();

            System.out.println(String.format("Try to save Resume over limit, exception - %s", e));
        }
    }


    @Test
    public void delete() {
        storage.delete("uuid1");
        storage.delete("uuid2");
    }

    @Test
    public void deleteUnExisting() {
        try {
            storage.delete("UUID_200005");
            storage.delete("UUID_200006");
        } catch (NotExistStorageException e) {
            System.out.println("Deleteting not existing uuid - " + e);
        }
    }

    @Test
    public void get() {
        System.out.println(storage.get("uuid1"));
    }

    void printAll() {
        System.out.println("\nPrint all items------------------------------");
        for (Resume r : storage.getAll()) {
            System.out.println(r);
        }
        System.out.println("\nPrint all items-finished --------------------");
    }
}