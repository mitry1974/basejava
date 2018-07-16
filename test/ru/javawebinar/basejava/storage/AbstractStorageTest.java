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
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    private static final String UUID1 = UUID.randomUUID().toString();
    private static final String UUID2 = UUID.randomUUID().toString();
    private static final String UUID3 = UUID.randomUUID().toString();
    private static final String UUID4 = UUID.randomUUID().toString();

    private static final Resume R1 = new Resume(UUID1, "Девелопер 1");
    private static final Resume R2 = new Resume(UUID2, "Девелопер 2");
    private static final Resume R3 = new Resume(UUID3, "Девелопер 3");
    private static final Resume R4 = new Resume(UUID4, "Девелопер 4");


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
        Resume newResume = new Resume(UUID2, "New Name");
        newResume.addContact(ContactType.EMAIL, "newmail@google.com");
        newResume.addContact(ContactType.SKYPE, "newSkype");
        newResume.addContact(ContactType.LINK, "new link.ru");

        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID2));
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
        storage.delete(UUID1);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteUnExisting() {
        storage.delete(UUID4);
    }

    @Test
    public void get() {
        assertEquals(R1, storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getUnExisting() {
        storage.get(UUID4);
    }

}
