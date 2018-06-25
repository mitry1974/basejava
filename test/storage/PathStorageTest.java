package storage;

import storage.serializer.FileSerialization;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getName(), new FileSerialization()));
    }
}
