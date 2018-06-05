import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int count = 0;

    void clear() {
        Arrays.fill(storage, 0, count - 1, null);
        count = 0;
    }

    void save(Resume r) {
        storage[count++] = r;
    }

    Resume get(String uuid) {
        if (find(uuid) >= 0) {
            return storage[i];
        }

        return null;
    }

    void delete(String uuid) {
        int index = find(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, count - index);
            storage[--count] = null;
        }
    }

    private int find(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    int size() {
        return count;
    }
}
