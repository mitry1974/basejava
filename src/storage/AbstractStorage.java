package storage;

import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        makeUpdate(resume, getIndex(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        makeInsert(resume);
    }

    @Override
    public Resume get(String uuid) {
        return makeSearch(getIndex(uuid));
    }

    @Override
    public void delete(String uuid) {
        makeDelete(getIndex(uuid));
    }

    protected abstract void makeDelete(Object key);

    protected abstract void makeInsert(Resume resume);

    protected abstract void makeUpdate(Resume resume, Object key);

    protected abstract Resume makeSearch(Object key);

//    protected abstract Object getIndex(String uuid);
//    protected abstract Object getIndexToInsert(String uuid);
}
