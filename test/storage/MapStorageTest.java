package storage;

import model.Resume;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        assertEquals(array.length, storage.size());

        //   assertArrayEquals(array, new Resume[]{resume1,resume2,resume3});
    }
}