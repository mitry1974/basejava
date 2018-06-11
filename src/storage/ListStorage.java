package storage;

import exception.NotExistStorageException;
import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void makeDelete(Object key) {
        int index = (Integer) key;
        if (index < 0) {
            throw new NotExistStorageException(String.valueOf(index));
        }

        storage.remove(index);
    }

    @Override
    protected void makeInsert(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void makeUpdate(Resume resume, Object key) {
        int index = (Integer) key;
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        storage.set(index, resume);
    }

    @Override
    protected Resume makeSearch(Object key) {
        int index = (Integer) key;
        if (index < 0) {
            throw new NotExistStorageException(String.valueOf(index));
        }
        return storage.get(index);
    }

    @Override
    protected Object getIndex(String uuid) {
        return (Integer) storage.indexOf(new Resume(uuid));
    }

    @Override
    protected Object getIndexToInsert(String uuid) {
        return (Integer) storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
