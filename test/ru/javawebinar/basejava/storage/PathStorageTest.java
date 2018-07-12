package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.FileSerialization;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getName(), new FileSerialization()));
    }
}
