package ru.javawebinar.basejava;

import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("..\\config\\resumes.properties");
    private static final Config INSTANCE = new Config();
    private File storageDir;

    public static Config get() {
        return INSTANCE;
    }

    public final File getStorageDir() {
        return storageDir;
    }

    public final Storage getStorage() {
        return storage;
    }

    public final Storage storage;

    private Config() {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("resumes.properties")) {

            Properties props = new Properties();
            props.load(is);

            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));

        } catch (IOException e) {
            throw new IllegalStateException("invalid config file name" + PROPS.getAbsolutePath());
        }
    }
}
