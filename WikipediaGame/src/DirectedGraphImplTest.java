import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DirectedGraphImplTest {

    DirectedGraphImpl g1, g2;
    Page i1, i2, i3, i4, i5;

    @Before
    public void init() {
        g1 = new DirectedGraphImpl();
        g2 = new DirectedGraphImpl();
        i1 = new Page("id1");
        i2 = new Page("id2");
        i3 = new Page("id1");
        i4 = new Page("id4");
        i5 = new Page("id5");
    }

    @Test
    public void testID() {
        assertEquals("id1", i1.getId());
        assertEquals("id2", i2.getId());
        assertEquals(i1.getId(), i3.getId());
        assertEquals(i1, i3);
        assertEquals(i1.hashCode(), i3.hashCode());
    }
    
    @Test
    public void testAll() {
        Map<String, Page> temp = new HashMap<String, Page>();
        temp.put("id1", i1);
        temp.put("id2", i2);
        g1 = DirectedGraphImpl.tempToGraph(temp);
        Set<Page> v = new HashSet<Page>();
        v.add(i2);
        v.add(i1);
        assertEquals(v ,g1.vertexSet());
        assertEquals(new HashSet<Page>() ,g1.outNeighbors(i1));
        i2.addNeighbor(i1);
        Set<Page> g = new HashSet<Page>();
        g.add(i1);
        assertEquals(g ,g1.outNeighbors(i2));
        Set<Page> h = new HashSet<Page>();
        h.add(i2);
        assertEquals(h ,g1.inNeighbors(i1));
    }
}
