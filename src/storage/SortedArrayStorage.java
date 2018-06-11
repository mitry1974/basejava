package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void fillDeletedElement(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }

    @Override
    protected void insertElement(Resume r) {
        int index = getIndexToInsert(r.getUuid());
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    @Override
    protected Object getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        int res = Arrays.binarySearch(storage, 0, size, searchKey);
        return (res >= 0) ? (Integer) res : null;
    }

    private int getIndexToInsert(String uuid) {
        return -Arrays.binarySearch(storage, 0, size, new Resume(uuid)) - 1;
    }
}