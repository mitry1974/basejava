package storage;

import model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamPathStorage extends AbstractPathStorage {

    public ObjectStreamPathStorage(String directory) {
        super(directory);
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        return FileSerialization.readResume(is);
    }

    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        FileSerialization.writeResume(resume, os);
    }
}
