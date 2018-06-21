package storage;

import model.Resume;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorageWrapperTest extends ArrayStorageTest {
    public FileStorageWrapperTest() {
        super(new FileStorageWrapper(new ObjectStreamPathStorage(STORAGE_DIR.getName())));
    }
}
