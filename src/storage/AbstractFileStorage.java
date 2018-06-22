package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
        if (!file.delete()) {
            throw new IllegalArgumentException(file.getAbsolutePath() + "can't delete");
        }
    }

    @Override
    protected void makeInsert(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        makeUpdate(resume, file);
    }

    @Override
    protected void makeUpdate(Resume resume, File file) {
        try {
            writeResume(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getResumeByKey(File file) {
        try {
            return readResume(new BufferedInputStream(new FileInputStream(file)));
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
        if (array == null) {
            throw new StorageException(directory.getAbsolutePath(), "list files must not be null!");
        }

        Resume[] resumes = new Resume[array.length];
        for (int i = 0; i < array.length; i++) {
            try {
                resumes[i] = readResume(new BufferedInputStream(new FileInputStream(array[i])));
            } catch (IOException e) {
                throw new StorageException("IO error", array[i].getName(), e);
            }
        }
        return resumes;
    }

    @Override
    public void clear() {
        File[] array = directory.listFiles();
        if (array == null) {
            throw new StorageException("IO error", "list files must not be null!");
        }

        for (File anArray : array) {
            makeDelete(anArray);
        }
    }

    @Override
    public int size() {
        String[] array = directory.list();
        if (array == null) {
            throw new StorageException(directory.getAbsolutePath(), "list files must not be null!");
        }
        return array.length;
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(getResumeByKey(file));
        }
        return list;
    }

    public abstract Resume readResume(InputStream is) throws IOException;

    public abstract void writeResume(Resume resume, OutputStream os) throws IOException;
}
