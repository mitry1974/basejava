package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public void makeDelete(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    public void makeSave(Resume resume, int index) {
        index = Math.abs(index + 1);
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}