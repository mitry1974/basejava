package storage;

import exception.StorageException;
import model.Resume;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        for (int i = 0; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume("uuid" + i, "Unknow man " + i));
        }
    }
}