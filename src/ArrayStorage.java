import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int MAX_RECORDS = 10000;
    private Resume[] storage = new Resume[MAX_RECORDS];
    private int count = 0;

    void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    void save(Resume resume) {
        if (find(resume.uuid) == -1) {
            if (count < MAX_RECORDS) {
                storage[count++] = resume;
            } else {
                System.out.println("Resume:save Максимальное количество резюме в базе !");
            }
        } else {
            System.out.println("Resume:save Резюме уже находится в базе, update !");
        }
    }

    Resume get(String uuid) {
        int i = find(uuid);
        if (i == -1) {
            System.out.println("Resume:get " + uuid + " не найдено в базе !");
            return null;
        }

        return storage[i];
    }

    void delete(String uuid) {
        int index = find(uuid);
        if (index >= 0) {
            --count;
            storage[index] = storage[count];
            storage[count] = null;
        } else {
            System.out.println("Resume:delete " + uuid + " не найдено в базе !");
        }
    }

    public void update(Resume resume) {
        int index = find(resume.uuid);
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Resume:update " + resume.uuid + " не найдено в базе !");
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
