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
    protected void makeDelete(Object key) {
        storage.remove((int) (Integer) key);
    }

    @Override
    protected void makeInsert(Resume resume, Object key) {
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
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid))
                return i;
        }

        return null;
    }

    @Override
    protected boolean isResumeExist(Object key){
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
