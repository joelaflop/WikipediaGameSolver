import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;

public class RecordInvertedIndexImplTest {

    RecordInvertedIndexImpl<Integer> r0;
    
    @Before
    public void init() {
        r0 = new RecordInvertedIndexImpl<Integer>();
    }
    
    @Test
    public void test0() {
        r0.addRecord("First", 1);
        r0.addRecord("First", 2);
        r0.addRecord("First", 15);
        r0.addRecord("First", 16);
        assertEquals(1, r0.size());
        System.out.println(r0.getRecords("First"));
        r0.deleteRecords("First");
        assertEquals(0, r0.count("First"));
        System.out.println(r0.getRecords("First"));
        assertEquals(new HashSet<String>(), r0.keySet());
    }
    @Test
    public void test1() {
        r0.addRecord("First", 1);
        r0.addRecord("First", 2);
        r0.addRecord("First", 15);
        r0.addRecord("First", 16);
        assertEquals(4, r0.count("First"));
        r0.deleteRecords("First");
        assertEquals(0, r0.size());
        assertEquals(new HashSet<String>(), r0.keySet());
    }
    @Test
    public void test2() {
        r0.addRecord("First", 1);
        r0.addRecord("First", 2);
        r0.addRecord("First", 15);
        r0.addRecord("First", 16);
        assertEquals(0, r0.count("Firt"));
        r0.deleteRecords("First");
        assertFalse(r0.containsKey("First"));
        assertEquals(new HashSet<String>(), r0.keySet());
    }
    @Test
    public void test3() {
        r0.addRecord("First", 1);
        r0.addRecord("First", 2);
        r0.addRecord("First", 15);
        r0.addRecord("First", 16);
        assertTrue(r0.containsKey("First"));
        r0.deleteRecords("First");
        assertFalse(r0.containsKey(null));
        assertEquals(new HashSet<String>(), r0.keySet());
    }
    @Test
    public void test4() {
        r0.addRecord("First", 1);
        r0.addRecord("First", 2);
        r0.addRecord("First", 15);
        r0.addRecord("First", 16);
        r0.deleteRecords("First");
        assertNull(r0.getRecords("g"));
        assertNull(r0.deleteRecords("First"));
        assertEquals(new HashSet<String>(), r0.keySet());
    }

}
