package storage;

import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void makeUpdate(Resume resume, Object key) {
        int index = (Integer) key;

        storage[index] = resume;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected void makeInsert(Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(resume);
            size++;
        }
    }

    public void makeDelete(Object key) {
        fillDeletedElement((Integer) key);
        storage[size--] = null;
    }

    public Resume makeSearch(Object key) {
        return storage[(Integer) key];
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r);

}
