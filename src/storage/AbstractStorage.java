package storage;

import exception.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        Object key = getIndex(uuid);
        if (key == null) {
            throw new NotExistStorageException(uuid);
        }
        makeUpdate(resume, getIndex(uuid));
    }

    @Override
    public void save(Resume resume) {
        makeInsert(resume);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getIndex(uuid);
        if (key == null) {
            throw new NotExistStorageException(uuid);
        }
        return makeSearch(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = getIndex(uuid);
        if (key == null) {
            throw new NotExistStorageException(uuid);
        }
        makeDelete(key);
    }

    protected abstract void makeDelete(Object key);

    protected abstract void makeInsert(Resume resume);

    protected abstract void makeUpdate(Resume resume, Object key);

    protected abstract Resume makeSearch(Object key);

    protected abstract Object getIndex(String uuid);
}
