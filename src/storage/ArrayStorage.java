package storage;

import model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void makeDelete(int index){
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    public void makeSave(Resume resume){
        storage[size] = resume;
        size++;
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