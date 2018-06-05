import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage    = new Resume[10000];
    int      count      = 0;

    void clear() {
        Arrays.fill(storage, null);
        count = 0;
    }

    void save(Resume r) {
        storage[count++] = r;
    }

    Resume get(String uuid) {
        Resume result = null;
        int i = find(uuid);
        if(i >= 0)
            result = storage[i];

        return result;
    }

    void delete(String uuid) {
        int index = find(uuid);
        if(index >= 0) {
            for (int i = index + 1; i < count; i++)
                storage[i - 1] = storage[i];
            storage[--count] = null;
        }
    }

    int find(String uuid) {
        int result = -1;
        for (int i = 0; i < count; i++) {
            if (storage[i].uuid.equals(uuid)) {
                result = i;
                break;
            }
        }

        return result;
    }
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return storage;
    }

    int size() {
        return count;
    }
}
