package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

    protected abstract Integer getSearchKey(String uuid);


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
        storage[(Integer) key] = resume;
    }

    protected void makeInsert(Resume resume, Object key) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(resume, (Integer) key);
            size++;
        }
    }

    public void makeDelete(Object key) {
        fillDeletedElement((Integer) key);
        storage[size--] = null;
    }

    @Override
    public Resume getResumeByKey(Object key) {
        return storage[(Integer) key];
    }

    @Override
    protected Resume[] getResumeArray() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected boolean isResumeExist(Object key) {
        return (Integer) key >= 0;
    }
}
