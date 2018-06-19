package storage;

import exception.StorageException;
import model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null!");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }

        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "can't read or write");
        }

        this.directory = directory;
    }

    @Override
    protected void makeDelete(File file) {
        file.delete();
    }

    @Override
    protected void makeInsert(Resume resume, File file) {
        try {
            file.createNewFile();
            writeResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void makeUpdate(Resume resume, File file) {
        try {
            writeResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getResumeByKey(File file) {
        try {
            return readResume(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isResumeExist(File file) {
        return Objects.requireNonNull(file).exists();
    }

    @Override
    protected Resume[] getResumeArray() {
        File[] array = directory.listFiles();
        Objects.requireNonNull(array);
        Resume[] resumes = new Resume[array.length];
        for (int i = 0; i < array.length; i++) {
            try {
                resumes[i] = readResume(array[i]);
            } catch (IOException e) {
                throw new StorageException("IO error", array[i].getName(), e);
            }
        }
        return resumes;
    }

    @Override
    public void clear() {
        File[] array = directory.listFiles();
        Objects.requireNonNull(array);
        for (File anArray : array) {
            anArray.delete();
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.listFiles()).length;
    }

    public abstract Resume readResume(File file) throws IOException;

    public abstract void writeResume(Resume resume, File file) throws IOException;
}
