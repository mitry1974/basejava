package storage;

import model.Resume;

import java.util.Arrays;
import java.util.List;


public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void fillDeletedElement(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void insertElement(Resume r) {
        storage[size] = r;
    }


    protected Integer getArrayKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}