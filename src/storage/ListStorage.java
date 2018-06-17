package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void makeDelete(Integer key) {
        storage.remove((int) key);
    }

    @Override
    protected void makeInsert(Resume resume, Integer key) {
        storage.add(resume);
    }

    @Override
    protected void makeUpdate(Resume resume, Integer key) {
        storage.set( key, resume);
    }

    @Override
    protected Resume getResumeByKey(Integer key) {
        return storage.get( key);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid))
                return i;
        }

        return null;
    }

    @Override
    protected boolean isResumeExist(Integer key) {
        return key != null;
    }

    @Override
    protected Resume[] getResumeArray() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
