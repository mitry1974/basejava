package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String directory) {
        this.directory = Paths.get(directory);
        Objects.requireNonNull(directory, "directory must not be null!");
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
            writeResume(resume, new BufferedOutputStream(new FileOutputStream(path.toString())));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected Resume getResumeByKey(Path path) {
        try {
            return readResume(new BufferedInputStream(new FileInputStream(path.toString())));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isResumeExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Resume[] getResumeArray(){
        ArrayList<Resume> resumeList = new ArrayList<>();

        Consumer<? super Path> consumer = (Consumer<Path>) path -> {
            try {
                resumeList.add(readResume(new BufferedInputStream(new FileInputStream(path.toFile()))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        try {
            Files.list(directory).forEach(consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resumeList.toArray(new Resume[0]);
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

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(getResumeArray());
    }

    public abstract Resume readResume(InputStream is) throws IOException;

    public abstract void writeResume(Resume resume, OutputStream os) throws IOException;
}
