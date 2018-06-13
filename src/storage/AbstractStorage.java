package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected abstract void makeDelete(Object key);

    protected abstract void makeInsert(Resume resume, Object key);

    protected abstract void makeUpdate(Resume resume, Object key);

    protected abstract Resume getResumeByKey(Object key);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isResumeExist(Object key);

    protected abstract Resume[] getResumeArray();

    @Override
    public void update(Resume resume) {
        makeUpdate(resume, getKeyCheckExist(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        makeInsert(resume, getKeyCheckNotExist(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return getResumeByKey(getKeyCheckExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        makeDelete(getKeyCheckExist(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        Resume[] array = getResumeArray();
        Arrays.sort(array);
        return Arrays.asList(array);
    }

    private Object getKeyCheckExist(String uuid) {
        Object key = getSearchKey(uuid);
        if (!isResumeExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object getKeyCheckNotExist(String uuid) {
        Object key = getSearchKey(uuid);
        if (isResumeExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}
