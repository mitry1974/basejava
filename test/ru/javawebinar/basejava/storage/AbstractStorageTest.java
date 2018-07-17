package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.basejava.storage.TestData.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();

        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
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
        Resume newResume = new Resume(UUID_2, "New Name");
        newResume.addContact(ContactType.EMAIL, "newmail@google.com");
        newResume.addContact(ContactType.SKYPE, "newSkype");
        newResume.addContact(ContactType.LINK, "new link.ru");

        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateUnExisting() {
        storage.update(R4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(storage.size(), list.size());
        List<Resume> sortedList = Arrays.asList(R1, R2, R3);
        Collections.sort(sortedList);
        assertEquals(sortedList, list);
    }

    @Test
    public void save() {
        storage.save(R4);
        assertEquals(R4, storage.get(R4.getUuid()));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExisting() {
        storage.save(R1);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteUnExisting() {
        storage.delete(UUID_4);
    }

    @Test
    public void get() {
        assertEquals(R1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getUnExisting() {
        storage.get(UUID_4);
    }

}
