import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DoubleWeightedDirectedGraphImplTest {

    DoubleWeightedDirectedGraphImpl<Integer> g;
    
    @Before
    public void init() {
        g = new DoubleWeightedDirectedGraphImpl<Integer>(10);
        assertTrue(g.addEdge(0, 1, 1.0));
        assertTrue(g.addEdge(1, 3, 1.0));
        assertTrue(g.addEdge(2, 2, 1.0));
        assertTrue(g.addEdge(4, 7, 1.0));
        assertTrue(g.addEdge(5, 3, 1.0));
        assertTrue(g.addEdge(5, 6, 1.0));
        assertTrue(g.addEdge(7, 4, 1.0));
        assertTrue(g.addEdge(7, 6, 1.0));
        assertTrue(g.addEdge(7, 1, 1.0));
        assertTrue(g.addEdge(8, 1, 1.0));
        assertTrue(g.addEdge(8, 6, 1.0));
        assertTrue(g.addEdge(9, 1, 1.0));
        assertTrue(g.addEdge(9, 0, 1.0));
    }
    
    @Test
    public void dontOverwriteEdges() {
        assertFalse(g.addEdge(0, 1, 1000.0));
        assertFalse(g.addEdge(2, 2, 1000.0));
        assertFalse(g.addEdge(9, 1, 1000.0));
    }

    @Test
    public void testNeighbors() {
        for(int i = 0; i < g.getSize(); i++) {
            System.out.println("v: "+i+"  inNeighbors: "+ g.inNeighbors(i)+ "  outNeighbors: " + g.outNeighbors(i));
        }
    }
    
    @Test
    public void hasEdge() {
        assertTrue(g.hasEdge(1,3));
    }
}
