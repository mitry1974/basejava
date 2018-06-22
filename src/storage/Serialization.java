package storage;

import model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serialization {
    Resume readResume(InputStream is) throws IOException;

    void writeResume(Resume resume, OutputStream os) throws IOException ;
}
