package storage;

import serializer.FileSerialization;
import serializer.XmlStreamSerializer;
import storage.AbstractStorageTest;
import storage.PathStorage;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }
}
