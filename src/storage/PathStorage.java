package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class PathStorage extends AbstractStorage<Path> {
    Serialization serialization;

    private Path directory;

    protected PathStorage(String directory, Serialization serialization) {
        this.directory = Paths.get(directory);
        Objects.requireNonNull(directory, "directory must not be null!");
        Objects.requireNonNull(serialization, "serialization must not be null");
        this.serialization = serialization;
        if (!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(directory + "is not directory");
        }
    }

    @Override
    protected void makeDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("file delete error", null, e);
        }
    }

    @Override
    protected void makeInsert(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IO error", resume.getUuid(), e);
        }
        makeUpdate(resume, path);
    }

    @Override
    protected void makeUpdate(Resume resume, Path path) {
        try {
            serialization.writeResume(resume, new BufferedOutputStream(new FileOutputStream(path.toString())));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected Resume getResumeByKey(Path path) {
        try {
            return serialization.readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isResumeExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Resume[] getResumeArray() {

        try {
            return Files.list(directory).map(this::getResumeByKey).toArray(Resume[]::new);
        } catch (IOException e) {
            throw new StorageException("Error in getResumeArray", null, e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::makeDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return Math.toIntExact(Files.list(directory).count());
        } catch (IOException e) {
            throw new StorageException("Error in Files.list", null, e);
        }
    }
}
