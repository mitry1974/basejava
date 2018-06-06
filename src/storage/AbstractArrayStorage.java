package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Resume:delete, uuid - " + resume.getUuid() + " not found!");
            return;
        }
        storage[index] = resume;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume:delete, uuid - " + uuid + " not found!");
            return;
        }
        makeDelete(index);
    }

    public void save(Resume resume) {
        if (size >= STORAGE_LIMIT - 1) {
            System.out.println("Resume:save, can't insert resume. Maximum amount is reached!");
            return;
        }

        if(getIndex(resume.getUuid()) >= 0){
            System.out.println("Resume:save, " +resume.getUuid() +" can't insert resume. Resume already in the database!");
            return;
        }

        makeSave(resume);
    }

    protected abstract int getIndex(String uuid);
    protected abstract void makeDelete(int index);
    protected abstract void makeSave(Resume resume);
}
