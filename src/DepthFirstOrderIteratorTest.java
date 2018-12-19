import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DepthFirstOrderIteratorTest {

    DoubleWeightedDirectedGraphImpl<Integer> g, g1, g2, g3, g4, g5, g99;
    DepthFirstOrderIterator<Integer> iter;

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

        g3 = new DoubleWeightedDirectedGraphImpl<Integer>(15);

        g3.addEdge(0, 1, 1.0);
        g3.addEdge(1, 2, 1.0);

        g3.addEdge(1,3,1.0);

        g3.addEdge(1, 5, 1.0);
        g3.addEdge(3, 4, 1.0);
        g3.addEdge(4, 14, 1.0);
        g3.addEdge(5, 0, 1.0);
        // g3.addEdge(14,4,1.0);
        g3.addEdge(14, 3, 1.0);

        g3.addEdge(6, 7, 1.0);
        g3.addEdge(7, 8, 1.0);
        g3.addEdge(8, 12, 1.0);
        g3.addEdge(12, 13, 1.0);
        g3.addEdge(7, 11, 1.0);
        g3.addEdge(11, 9, 1.0);
        g3.addEdge(9, 10, 1.0);
        g3.addEdge(10, 6, 1.0);
        
        g4 = new DoubleWeightedDirectedGraphImpl<Integer>(18);

        g4.addEdge(0,1,1.0);
        g4.addEdge(1,2,1.0);
        
        
        g4.addEdge(1,3,1.0);
        
        
        g4.addEdge(1,5,1.0);
        g4.addEdge(3,4,1.0);
        g4.addEdge(4,14,1.0);
        g4.addEdge(5,0,1.0);
        //g3.addEdge(14,4,1.0);
        g4.addEdge(14,3,1.0);
       
        g4.addEdge(6,7,1.0);
        g4.addEdge(7,8,1.0);
        g4.addEdge(8,12,1.0);
        g4.addEdge(12,13,1.0);
        g4.addEdge(7,11,1.0);
        g4.addEdge(11,9,1.0);
        g4.addEdge(9,10,1.0);
        g4.addEdge(10,6,1.0);
        
        g4.addEdge(2,15,1.0);
        g4.addEdge(15,16,1.0);
        g4.addEdge(16,17,1.0);
        g4.addEdge(17,2,1.0);

        
        g5 = new DoubleWeightedDirectedGraphImpl<Integer>(18);

        g5.addEdge(1,0,1.0);
        g5.addEdge(2,1,1.0);
        
        
        g5.addEdge(3,1,1.0);
        
        
        g5.addEdge(5,1,1.0);
        g5.addEdge(4,3,1.0);
        g5.addEdge(14,4,1.0);
        g5.addEdge(0,5,1.0);
        //g3.addEdge(14,4,1.0);
        g5.addEdge(3,14,1.0);
       
        g5.addEdge(7,6,1.0);
        g5.addEdge(8,7,1.0);
        g5.addEdge(12,8,1.0);
        g5.addEdge(13,12,1.0);
        g5.addEdge(11,7,1.0);
        g5.addEdge(9,11,1.0);
        g5.addEdge(10,9,1.0);
        g5.addEdge(6,10,1.0);
        
        g5.addEdge(15,2,1.0);
        g5.addEdge(16,15,1.0);
        g5.addEdge(17,16,1.0);
        g5.addEdge(2,17,1.0);
        
        g99 = new DoubleWeightedDirectedGraphImpl<Integer>(4);
        
        g99.addEdge(1, 2, 1.0);
        g99.addEdge(2, 3, 1.0);
        g99.addEdge(3, 1, 1.0);
    } 

    @Test
    public void test0() {
        iter = new DepthFirstOrderIterator<Integer>(g1);
        while (iter.hasNext()) {
            while (iter.hasNextInComponent()) {
                System.out.println(iter.nextInComponent() + "  in component");
            }
            System.out.println("END OF COMPONENT::::::::::::::::::::::::::::::::");
            if (iter.hasNext()) {
                System.out.println(iter.next() + "  normal next:");
            }
        }
        System.out.println(iter.finishedVerts);
        System.out.println("-----------------------------------------------------");
    }

    @Test
    public void test1() {
        iter = new DepthFirstOrderIterator<Integer>(g);
        while (iter.hasNext()) {
            while (iter.hasNextInComponent()) {
                System.out.println(iter.nextInComponent() + "  in component");
            }
            System.out.println("END OF COMPONENT::::::::::::::::::::::::::::::::");
            if (iter.hasNext()) {
                System.out.println(iter.next() + "  normal next:");
            }
        }
        System.out.println(iter.finishedVerts);
        System.out.println("-----------------------------------------------------");
    }

    @Test
    public void test2() {
        iter = new DepthFirstOrderIterator<Integer>(g2);
        while (iter.hasNext()) {

            while (iter.hasNextInComponent()) {
                System.out.println(iter.nextInComponent() + "  in component");
            }
            System.out.println("END OF COMPONENT::::::::::::::::::::::::::::::::");
            if (iter.hasNext()) {
                System.out.println(iter.next() + "  normal next:");
            }
        }
        System.out.println(iter.finishedVerts);
        System.out.println("-----------------------------------------------------");
    }

    @Test
    public void test3a() {
        iter = new DepthFirstOrderIterator<Integer>(g2);
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println(iter.finishedVerts);
        System.out.println("-----------------------------------------------------");
    }
    
    @Test
    public void test4() {
        iter = new DepthFirstOrderIterator<Integer>(g3);
        while (iter.hasNext()) {

            while (iter.hasNextInComponent()) {
                System.out.println(iter.nextInComponent() + "  in component");
            }
            System.out.println("END OF COMPONENT::::::::::::::::::::::::::::::::");
            if (iter.hasNext()) {
                System.out.println(iter.next() + "  normal next:");
            }
        }
        System.out.println(iter.finishedVerts);
        System.out.println("-----------------------------------------------------");
    }
 
    @Test
    public void test5() {
        
        iter = new DepthFirstOrderIterator<Integer>(g);
        while (iter.hasNext()) {

            while (iter.hasNextInComponent()) {
                System.out.println(iter.nextInComponent() + "  in component");
            }
            System.out.println("END OF COMPONENT::::::::::::::::::::::::::::::::");
            if (iter.hasNext()) {
                Integer curr = iter.next();
                System.out.println(curr + "  normal next");
            } 
        }
        System.out.println(iter.finishedVerts);
        System.out.println("-----------------------------------------------------");
    }
    
    @Test
    public void test6() {
        
        iter = new DepthFirstOrderIterator<Integer>(g99);
        
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(0);
        //Collections.reverse(list);
        
        iter.sortedVerts = list;
        System.out.println(iter.sortedVerts);
        
        
        while (iter.hasNext()) {

            while (iter.hasNextInComponent()) {
                System.out.println(iter.nextInComponent() + "  in component");
            }
            System.out.println("END OF COMPONENT::::::::::::::::::::::::::::::::");
            if (iter.hasNext()) {
                Integer curr = iter.next();
                System.out.println(curr + "  normal next");
            } 
        }
        System.out.println(iter.finishedVerts);
        System.out.println("99-----------------------------------------------------");
    }
    
}
