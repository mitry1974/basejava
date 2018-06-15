package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void makeDelete(Object key) {
        storage.remove(key);
    }

    @Override
    protected void makeInsert(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void makeUpdate(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResumeByKey(Object key) {
        return storage.get(key);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isResumeExist(Object key) {
        return key != null;
    }

    @Override
    protected Resume[] getResumeArray() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

}