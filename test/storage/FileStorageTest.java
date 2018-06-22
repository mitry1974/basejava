package storage;

public class FileStorageTest extends ArrayStorageTest {
    public FileStorageTest() {
        super(new FileStorageWrapper(new FileStorage(STORAGE_DIR, new FileSerialization())));
    }
}
