package storage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class ArrayStorageTest extends AbstractArrayStorageTest {
    public ArrayStorageTest() {
        super(StorageType.ARRAY_STORAGE);
    }
}