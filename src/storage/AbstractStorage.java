package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void makeDelete(SK key);

    protected abstract void makeInsert(Resume resume, SK key);

    protected abstract void makeUpdate(Resume resume, SK key);

    protected abstract Resume getResumeByKey(SK key);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isResumeExist(SK key);

    protected abstract Resume[] getResumeArray();

    @Override
    public void update(Resume resume) {
//        LOG.info("update" + resume);
        makeUpdate(resume, getKeyCheckExist(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
//        LOG.info("save" + resume);
        makeInsert(resume, getKeyCheckNotExist(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
//        LOG.info("get" + uuid);
        return getResumeByKey(getKeyCheckExist(uuid));
    }

    @Override
    public void delete(String uuid) {
//        LOG.info("delete" + uuid);
        makeDelete(getKeyCheckExist(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
//        LOG.info("getAllSorted");
        Resume[] array = getResumeArray();
        Arrays.sort(array);
        return Arrays.asList(array);
    }

    private SK getKeyCheckExist(String uuid) {
        SK key = getSearchKey(uuid);
        if (!isResumeExist(key)) {
            LOG.warning("Resume " + uuid + " not exist!");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK getKeyCheckNotExist(String uuid) {
        SK key = getSearchKey(uuid);
        if (isResumeExist(key)) {
//            LOG.warning("Resume " + uuid + " already exists!");
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}
