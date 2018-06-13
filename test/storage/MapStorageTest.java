package storage;

import model.Resume;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }
}