package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List storage;

    {
        storage = new ArrayList<Resume>();
    }

    public ListStorage() {
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        int index = storage.indexOf(resume);
        if (index != -1)
            storage.set(index, resume);
    }

    @Override
    public void save(Resume resume) {
        if (storage.indexOf(resume) == -1) {
            storage.add(resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return (Resume[]) storage.toArray();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
