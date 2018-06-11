package storage;

import model.Resume;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void makeDelete(Object key) {
        storage.remove((String) key);
    }

    @Override
    protected void makeInsert(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void makeUpdate(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume makeSearch(Object key) {
        return storage.get((String) key);
    }

    @Override
    protected Object getIndex(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
/*        Collection<Resume> collection = storage.values();

        Resume[] array = collection.toArray(new Resume[0]);
        System.out.println(array.length);
*/
        return (Resume[]) storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

}
