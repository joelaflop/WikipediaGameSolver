import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BinaryMinHeapImplTest {

    BinaryMinHeapImpl<Integer, Integer> h1, h2, h3;

    @Before
    public void init() {
        h2 = new BinaryMinHeapImpl<Integer, Integer>();
        h3 = new BinaryMinHeapImpl<Integer, Integer>();
    }

    @Test
    public void initTest() {
        assertEquals(0, h2.size());
        assertEquals(0, h3.size());
    }

    @Test
    public void simpleTest() {
        h2.add(99, 1);
        System.out.println(h2.size);
        System.out.println(h2.extractMin());
    }

    @Test
    public void AddTest() {
        h2.add(30, 4);
        h2.add(31, 5);
        h2.add(32, 2);
        h2.add(33, 6);
        h2.add(34, 3);
        
        for (int i = 0; i < h2.size(); i++) {
            System.out.print(h2.heap.get(i + 1).getKey() + ", ");
        }
    }

    @Test
    public void SimpleExtractTest() {
        h2.add(30, 4);
        h2.add(31, 5);
        h2.add(32, 2);
        h2.add(33, 6);
        h2.add(34, 3);
        System.out.println(h2.extractMin());
        for (int i = 0; i < h2.size(); i++) {
            System.out.print(h2.heap.get(i + 1).getKey() + ", ");
        }
        System.out.println();
    }

    @Test
    public void BigExtractTest() {
        h2.add(31, 1);
        h2.add(33, 3);
        h2.add(30, 2);
        h2.add(34, 4);
        h2.add(32, 5);
        h2.add(35, 6);
        h2.add(37, 7);
        h2.add(38, 8);
        System.out.println();
        System.out.println();
        System.out.println();
        for (int i = 0; i < h2.size(); i++) {
            System.out.print(h2.heap.get(i + 1).getKey() + ", ");
        }
        System.out.println(h2.extractMin());
        for (int i = 0; i < h2.size(); i++) {
            System.out.print(h2.heap.get(i + 1).getKey() + ", ");
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    @Test
    public void DecreaseKeyTest1() {
        h2.add(31, 1);
        h2.add(33, 3);
        h2.add(30, 2);
        h2.add(34, 4);
        h2.add(32, 5);
        h2.add(35, 6);
        h2.add(37, 7);
        h2.add(38, 8);
        h2.decreaseKey(38, 0);
        for (int i = 0; i < h2.size(); i++) {
            System.out.print(h2.heap.get(i + 1).getKey() + ", ");
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    @Test
    public void DecreaseKeyTest2() {
        h2.add(31, 1);
        h2.add(33, 3);
        h2.add(30, 2);
        h2.add(34, 4);
        h2.add(32, 5);
        h2.add(35, 6);
        h2.add(37, 7);
        h2.add(38, 8);
        h2.peek();
        h2.values();
        h2.decreaseKey(31, 0);
        for (int i = 0; i < h2.size(); i++) {
            System.out.print(h2.heap.get(i + 1).getKey() + ", ");
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    @Test
    public void DecreaseKeyTest3() {
        h2.add(31, 1);
        h2.add(33, 3);
        h2.add(30, 2);
        h2.add(34, 4);
        h2.add(32, 5);
        h2.add(35, 6);
        h2.add(37, 7);
        h2.add(38, 8);
        h2.decreaseKey(32, 0);
        for (int i = 0; i < h2.size(); i++) {
            System.out.print(h2.heap.get(i + 1).getKey() + ", ");
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    @Test
    public void DecreaseKeyTest4() {
        h2.add(31, 1);
        h2.add(33, 3);
        h2.add(30, 2);
        h2.add(34, 4);
        h2.add(32, 5);
        h2.add(35, 6);
        h2.add(37, 7);
        h2.add(38, 8);
        h2.decreaseKey(35, 0);
        for (int i = 0; i < h2.size(); i++) {
            System.out.print(h2.heap.get(i + 1).getKey() + ", ");
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
