import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShortestPathImplTest {

    DoubleWeightedDirectedGraphImpl<Integer> g, g2, g3;
    ShortestPathImpl<Integer> s;
    
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
        
        g2 = new DoubleWeightedDirectedGraphImpl<Integer>(4);
        g2.addEdge(0, 1, 5.0);
        g2.addEdge(0, 2, 60.0);
        g2.addEdge(1, 3, 10.0);
        g2.addEdge(2, 3, 5.0);
        
        g3 = new DoubleWeightedDirectedGraphImpl<Integer>(7);
        g3.addEdge(1, 2, 1.0);
        g3.addEdge(1, 6, 14.0);
        g3.addEdge(1, 3, 9.0);
        g3.addEdge(3, 4, 11.0);
        g3.addEdge(2, 3, 10.0);
        g3.addEdge(3, 6, 2.0);
        g3.addEdge(6, 5, 9.0);
        g3.addEdge(2, 4, 15.0);
        g3.addEdge(4, 5, 6.0);
        
        s = new ShortestPathImpl<Integer>();
    }
    
    @Test
    public void testSimpleGraph() {
        System.out.println(s.getShortestPath(g, 9, 0));
        System.out.println(s.getShortestPath(g, 1, 3));
        System.out.println(s.getShortestPath(g, 4, 1));
        System.out.println(s.getShortestPath(g, 9, 3));
    }
    
    @Test
    public void pickTheBetterCycleRoute() {
        System.out.println(s.getShortestPath(g2, 0, 3));
    }
    
    @Test
    public void pickTheBetterCycleRouteOn3() {
        System.out.println("3:   "+s.getShortestPath(g3, 1, 5));
    }

}
