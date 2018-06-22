package storage;

import model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage {

    Serialization serialization;

    public FileStorage(File directory, Serialization serialization) {
        super(directory);
        this.serialization = serialization;
    }

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }

    @Override
    public Resume readResume(InputStream is) throws IOException {
        return serialization.readResume(is);
    }

    @Override
    public void writeResume(Resume resume, OutputStream os) throws IOException {
        serialization.writeResume(resume, os);
    }
}