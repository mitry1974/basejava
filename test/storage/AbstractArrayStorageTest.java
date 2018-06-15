package storage;

import exception.StorageException;
import model.Resume;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(""));
            }
        } catch (Exception e) {
            fail("saveOverflow test failed");
        }

        storage.save(new Resume("uuid10001", "Unknow man 10001"));
    }
}