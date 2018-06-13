package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void makeDelete(Object key) {
        storage.remove((int) (Integer) key);
    }

    @Override
    protected void makeInsert(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void makeUpdate(Resume resume, Object key) {
        storage.set((Integer) key, resume);
    }

    @Override
    protected Resume getResumeByKey(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected Object getKey(String uuid) {
        Integer res = storage.indexOf(new Resume(uuid));

        return (res >= 0) ? res : null;
    }

    @Override
    protected Resume[] getResumeArray() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public List<Resume> getAllSorted() {
        Resume[] array = storage.toArray(new Resume[0]);
        Arrays.sort(array);
        return Arrays.asList(array);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
