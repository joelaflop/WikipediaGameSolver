import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ConnectedComponentsImplTest {

    DoubleWeightedDirectedGraphImpl<Integer> g, g1, g2, g3, g4, g5;
    DepthFirstOrderIterator<Integer> iter;
    ConnectedComponentsImpl<Integer> cc, cc1, cc2;

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

        g1 = new DoubleWeightedDirectedGraphImpl<Integer>(20);
        g1.addEdge(0, 1, 2.0);
        g1.addEdge(1, 2, 2.0);
        g1.addEdge(2, 3, 2.0);
        g1.addEdge(3, 4, 2.0);

        g1.addEdge(0, 5, 2.0);
        g1.addEdge(0, 7, 2.0);

        g1.addEdge(5, 6, 2.0);

        g1.addEdge(10, 11, 2.0);
        g1.addEdge(11, 12, 2.0);
        g1.addEdge(12, 13, 2.0);
        g1.addEdge(10, 12, 2.0);

        g2 = new DoubleWeightedDirectedGraphImpl<Integer>(20);

        g2.addEdge(1, 18, 2.0);
        g2.addEdge(18, 17, 2.0);
        g2.addEdge(17, 19, 2.0);
        g2.addEdge(19, 14, 2.0);
        g2.addEdge(14, 18, 2.0);
        g2.addEdge(14, 17, 2.0);

        g2.addEdge(2, 3, 2.0);
        g2.addEdge(3, 8, 2.0);
        g2.addEdge(8, 7, 2.0);
        g2.addEdge(7, 6, 2.0);
        g2.addEdge(6, 17, 2.0);

        g2.addEdge(4, 5, 2.0);
        g2.addEdge(4, 9, 2.0);
        g2.addEdge(4, 11, 2.0);
        g2.addEdge(5, 10, 2.0);
        g2.addEdge(5, 12, 2.0);
        g2.addEdge(10, 13, 2.0);
        g2.addEdge(10, 16, 2.0);
        g2.addEdge(9, 15, 2.0);
        
        g3 = new DoubleWeightedDirectedGraphImpl<Integer>(20);
        
        g3.addEdge(0,1,1.0);
        g3.addEdge(1,2,1.0);
        
        
        g3.addEdge(1,3,1.0);
        
        
        g3.addEdge(1,5,1.0);
        g3.addEdge(3,4,1.0);
        g3.addEdge(4,14,1.0);
        g3.addEdge(5,0,1.0);
        //g3.addEdge(14,4,1.0);
        g3.addEdge(14,3,1.0);
        
        g3.addEdge(6,7,1.0);
        g3.addEdge(7,8,1.0);
        g3.addEdge(8,12,1.0);
        g3.addEdge(12,13,1.0);
        g3.addEdge(7,11,1.0);
        g3.addEdge(11,9,1.0);
        g3.addEdge(9,10,1.0);
        g3.addEdge(10,6,1.0);
        
        g3.addEdge(2,15,1.0);
        g3.addEdge(15,16,1.0);
        g3.addEdge(16,17,1.0);
        g3.addEdge(17,2,1.0);
        
        g3.addEdge(13, 18, 1.0);
        g3.addEdge(18, 13, 1.0);
        
        g3.addEdge(8, 19, 1.0);
        g3.addEdge(19, 8, 1.0);
        

        cc = new ConnectedComponentsImpl<Integer>();
        
        g4 = new DoubleWeightedDirectedGraphImpl<Integer>(21);
        
        g4.addEdge(0, 4, 1.0);
        g4.addEdge(4, 1, 1.0);
        g4.addEdge(1, 0, 1.0);
        
        g4.addEdge(0, 2, 1.0);
        g4.addEdge(2, 3, 1.0);
        g4.addEdge(5, 2, 1.0);
        g4.addEdge(3, 5, 1.0);
        g4.addEdge(5, 17, 1.0);
        g4.addEdge(17, 18, 1.0);
        g4.addEdge(18, 19, 1.0);
        g4.addEdge(19, 3, 1.0);
        
        g4.addEdge(4, 7, 1.0);
        
        g4.addEdge(6, 0, 1.0);
        
        g4.addEdge(8, 1, 1.0);
        
        g4.addEdge(10, 8, 1.0);
        g4.addEdge(10, 6, 1.0);
        
        g4.addEdge(11, 10, 1.0);
        g4.addEdge(11, 12, 1.0);
        g4.addEdge(12, 13, 1.0);
        g4.addEdge(13, 11, 1.0);
        g4.addEdge(12, 14, 1.0);
        g4.addEdge(14, 15, 1.0);
        g4.addEdge(15, 16, 1.0);
        g4.addEdge(16, 12, 1.0);
        
        g4.addEdge(20, 9, 1.0);
        
        g5 = new DoubleWeightedDirectedGraphImpl<Integer>(4);
        
        g5.addEdge(1, 2, 1.0);
        g5.addEdge(2, 3, 1.0);
        g5.addEdge(3, 1, 1.0);
    }

    @Test
    public void test() {
        Set<Set<Integer>> setOfSets = cc.weaklyConnectedComponents(g);
        for (Set<Integer> verts : setOfSets) {
            System.out.println(verts);
        }
        System.out.println("0-------------------------------------------------------");
    }

    @Test
    public void test1() {
        Set<Set<Integer>> setOfSets = cc.weaklyConnectedComponents(g1);
        for (Set<Integer> verts : setOfSets) {
            System.out.println(verts);
        }
        System.out.println("1-------------------------------------------------------");
    }

    @Test
    public void test2() {
        Set<Set<Integer>> setOfSets = cc.weaklyConnectedComponents(g2);
        for (Set<Integer> verts : setOfSets) {
            System.out.println(verts);
        }
        System.out.println("2-------------------------------------------------------");
    }
    
    @Test
    public void test3() {
        Set<Set<Integer>> setOfSets = cc.stronglyConnectedComponents(g);
        for (Set<Integer> verts : setOfSets) {
            System.out.println(verts);
        }
        System.out.println("3-------------------------------------------------------");
    }

    @Test
    public void test4() {
        Set<Set<Integer>> setOfSets = cc.stronglyConnectedComponents(g1);
        for (Set<Integer> verts : setOfSets) {
            System.out.println(verts);
        }
        System.out.println("4-------------------------------------------------------");
    }

    @Test
    public void test5() {
        Set<Set<Integer>> setOfSets = cc.stronglyConnectedComponents(g2);
        for (Set<Integer> verts : setOfSets) {
            System.out.println(verts);
        }
        System.out.println("5-------------------------------------------------------");
    }
    
    @Test
    public void test6() {
        Set<Set<Integer>> setOfSets = cc.stronglyConnectedComponents(g3);
        for (Set<Integer> verts : setOfSets) {
            System.out.println(verts);
        }
        System.out.println("6-------------------------------------------------------");
    }
    
    @Test
    public void test7() {
        Set<Set<Integer>> setOfSets = cc.stronglyConnectedComponents(g4);
        for (Set<Integer> verts : setOfSets) {
            System.out.println(verts);
        }
        System.out.println("7-------------------------------------------------------");
    }
    
    @Test
    public void test8() {
        Set<Set<Integer>> setOfSets = cc.stronglyConnectedComponents(g5);
        for (Set<Integer> verts : setOfSets) {
            System.out.println(verts);
        }
        System.out.println("7-------------------------------------------------------");
    }

}
