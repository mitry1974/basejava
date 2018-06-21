package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {
    public ObjectStreamStorage(File directory) {
        super(directory);
    }

    public Resume readResume(InputStream is) throws IOException {
        return FileSerialization.readResume(is);
    }

    public void writeResume(Resume resume, OutputStream os) throws IOException {
        FileSerialization.writeResume(resume, os);
    }
}
