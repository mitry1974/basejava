package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map storage = new HashMap<String, Resume>();

    @Override
    protected void makeDelete(int index) {

    }

    @Override
    protected void makeInsert(Resume resume, int index) {

    }

    @Override
    protected void makeUpdate(Resume resume, int index) {

    }

    @Override
    protected Resume makeSearch(int index) {
        return null;
    }

    @Override
    protected int getIndex(String uuid) {
        return 0;
    }

    @Override
    protected int getIndexToInsert(String uuid) {
        return 0;
    }

    @Override
    public void clear() {

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
