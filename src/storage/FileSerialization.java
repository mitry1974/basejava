package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;

public class FileSerialization{

        public static Resume readResume(InputStream is) throws IOException {
            try (ObjectInputStream ois = new ObjectInputStream(is)) {
                return (Resume) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new StorageException("Error read resume", null, e);
            }
        }

        public static void writeResume(Resume resume, OutputStream os) throws IOException {
            try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
                oos.writeObject(resume);
            }

        }

}
