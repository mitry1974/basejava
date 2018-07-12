package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    StreamSerializer serialization;
    private File directory;

    protected FileStorage(File directory, StreamSerializer serialization) {
        Objects.requireNonNull(directory, "directory must not be null!");
        Objects.requireNonNull(serialization, "serialization must not be null");
        this.serialization = serialization;
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
            serialization.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getResumeByKey(File file) {
        try {
            return serialization.doRead(new BufferedInputStream(new FileInputStream(file)));
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
            resumes[i] = getResumeByKey(array[i]);
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
}
