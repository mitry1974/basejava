package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void makeDelete(Object index) {

    }

    @Override
    protected void makeInsert(Resume resume, Object index) {

    }

    @Override
    protected void makeUpdate(Resume resume, Object index) {

    }

    @Override
    protected Resume makeSearch(Object index) {
        return null;
    }

    @Override
    protected Object getIndex(String uuid) {
        return 0;
    }

    @Override
    protected Object getIndexToInsert(String uuid) {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return (Resume[])storage.values().toArray();
    }

    @Override
    public int size() {
        return storage.size();
    }

}
