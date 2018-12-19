import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class PageTest {

    Page i1, i2, i3;

    @Before
    public void init() {
        i1 = new Page("id1");
        i2 = new Page("id2");
        i3 = new Page("id1");
    }

    @Test
    public void testAll() {
        assertEquals("id1", i1.getId());
        assertEquals("id2", i2.getId());
        assertEquals(i1.getId(), i3.getId());
        assertEquals(i1, i3);
        assertEquals(i1.hashCode(), i3.hashCode());
        i1.setText("lmao");
        i1.addNeighbor(i2);
        Set<Page> neighbors = i1.getNeighbors();
        System.out.println(i1.text);
        System.out.println(neighbors + " " + i1.getText());
    }

}
