package storage;

import storage.serializer.FileSerialization;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new FileSerialization()));
    }
}
