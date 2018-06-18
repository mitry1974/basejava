package storage;

import model.Resume;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileStorage extends AbstractStorage<String> {
    private String storagePath;
    private final List<String> storage = new ArrayList<>();

    public AbstractFileStorage(String path) {
        if (path.charAt(path.length() - 1) != '\\')
            path += '\\';

        this.storagePath = path;
    }

    @Override
    protected void makeDelete(String key) {
        new File(key).delete();
    }

    @Override
    protected void makeInsert(Resume resume, String key) {
        writeResume(resume, storagePath + key);
        storage.add(key);
    }

    @Override
    protected void makeUpdate(Resume resume, String key) {
        writeResume(resume, storagePath + key);
    }

    @Override
    protected Resume getResumeByKey(String key) {
        return readResume(key);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isResumeExist(String key) {
        return new File(storagePath + key).exists();
    }

    @Override
    protected Resume[] getResumeArray() {
        Resume[] array = new Resume[storage.size()];
        for (int i = 0; i < storage.size(); i++) {
            array[i] = readResume(storagePath + storage.get(i));
        }
        return array;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    public abstract Resume readResume(String filePath);

    public abstract void writeResume(Resume resume, String filePath);
}
