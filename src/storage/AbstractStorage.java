package storage;

import exception.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        makeUpdate(resume, getKeyCheckNull(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        makeInsert(resume);
    }

    @Override
    public Resume get(String uuid) {
        return getResumeByKey(getKeyCheckNull(uuid));
    }

    @Override
    public void delete(String uuid) {
        makeDelete(getKeyCheckNull(uuid));
    }

    private Object getKeyCheckNull(String uuid){
        Object key = getKey(uuid);
        if (key == null) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }
    protected abstract void makeDelete(Object key);

    protected abstract void makeInsert(Resume resume);

    protected abstract void makeUpdate(Resume resume, Object key);

    protected abstract Resume getResumeByKey(Object key);

    protected abstract Object getKey(String uuid);
}
