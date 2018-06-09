package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        makeUpdate(resume, index);
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }

        index = getIndexToInsert(resume.getUuid());

        makeInsert(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }

        return makeSearch(index);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        makeDelete(index);
    }

    protected abstract void makeDelete(int index);

    protected abstract void makeInsert(Resume resume, int index);

    protected abstract void makeUpdate(Resume resume, int index);

    protected abstract Resume makeSearch(int index);

    protected abstract int getIndex(String uuid);

    protected abstract int getIndexToInsert(String uuid);
}
