package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void makeDelete(int index) {
        storage.remove(index);
    }

    @Override
    protected void makeInsert(Resume resume, int index) {
        storage.add(index, resume);
    }

    @Override
    protected void makeUpdate(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected Resume makeSearch(int index) {
        return storage.get(index);
    }

    @Override
    protected int getIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected int getIndexToInsert(String uuid) {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
