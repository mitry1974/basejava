package storage;

import model.Resume;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListStorageTest extends AbstractArrayStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Test
    public void saveOverSize() {

    }
}