package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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

    public void makeUpdate(Resume resume, Integer key) {
        storage[key] = resume;
    }

    protected void makeInsert(Resume resume, Integer key) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(resume, key);
            size++;
        }
    }

    public void makeDelete(Integer key) {
        fillDeletedElement(key);
        storage[size--] = null;
    }

    @Override
    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    public Resume getResumeByKey(Integer key) {
        return storage[key];
    }

    @Override
    protected Resume[] getResumeArray() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected boolean isResumeExist(Integer key) {
        return key >= 0;
    }
}
