package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void makeDelete(String key) {
        storage.remove(key);
    }

    @Override
    protected void makeInsert(Resume resume, String key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void makeUpdate(Resume resume, String key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResumeByKey(String key) {
        return storage.get(key);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isResumeExist(String key) {
        return storage.containsKey(key);
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

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

}
