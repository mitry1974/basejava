package storage;

import storage.serializer.DataStreamSerializer;

public class DataStreamPathStorageTest extends AbstractStorageTest {
    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}
