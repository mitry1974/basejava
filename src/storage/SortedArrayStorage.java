package storage;

import model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Resume:delete, uuid - " + resume.getUuid() + " not found!");
            return;
        }
        storage[index] = resume;
    }

    @Override
    public void save(Resume resume) {
        if (size >= STORAGE_LIMIT - 1) {
            System.out.println("Resume:save, can't insert resume. Maximum amount is reached!");
            return;
        }

        int indexToInsert = Arrays.binarySearch(storage, 0, size, resume);
        if (indexToInsert >= 0) {
            System.out.println("Resume:save, uuid - always in the database!");
            return;
        }

        indexToInsert = Math.abs(indexToInsert + 1);
        System.arraycopy(storage, indexToInsert, storage, indexToInsert + 1, size - indexToInsert);
        storage[indexToInsert] = resume;
        size++;
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume:delete, uuid - " + uuid + " not found!");
            return;
        }

        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}