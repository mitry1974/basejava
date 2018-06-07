package storage;

import model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    public void makeDelete(int index) {
        storage[index] = storage[size - 1];
    }

    public void makeSave(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}