package storage;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new FileStorageWrapper(new PathStorage(STORAGE_DIR.getName(), new FileSerialization())));
    }
}
