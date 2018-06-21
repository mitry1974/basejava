package storage;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR.getName()));
    }
}
