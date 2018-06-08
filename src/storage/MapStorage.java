package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map storage;

    {
        storage = new HashMap<String, Resume>();
    }

    public MapStorage() {
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        if (storage.containsKey(resume.getUuid())) {
            storage.put(resume.getUuid(), resume);
        }
    }

    @Override
    public void save(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume get(String uuid) {
        if (storage.containsKey(uuid)) {
            return (Resume) storage.get(uuid);
        }

        return null;
    }

    @Override
    public void delete(String uuid) {
        if(storage.containsKey(uuid)){
            storage.remove(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return (Resume []) storage.values().toArray();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
