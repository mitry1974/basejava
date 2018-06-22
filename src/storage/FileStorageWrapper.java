package storage;

import model.Resume;

import java.util.List;

public class FileStorageWrapper implements Storage {
    private Storage storage;

    public FileStorageWrapper(Storage storage) {
        this.storage = storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        storage.update(resume);
    }

    @Override
    public void save(Resume resume) {
        storage.save(resume);
    }

    @Override
    public Resume get(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void delete(String uuid) {
        storage.delete(uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        return storage.getAllSorted();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
