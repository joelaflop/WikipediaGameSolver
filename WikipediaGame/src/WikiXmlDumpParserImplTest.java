import static org.junit.Assert.*;

import java.io.File;
//import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis121.project.WikiXmlDumpParser;

public class WikiXmlDumpParserImplTest {

    File t0, t1, to, tf;
    WikiXmlDumpParser w0, w1, w2;

    @Before
    public void init() { 
        w0 = new WikiXmlDumpParserImpl();
        w1 = new WikiXmlDumpParserImpl();
        w2 = new WikiXmlDumpParserImpl();
        t0 = new File("testOne.xml");
        t1 = new File("smallerTest.xml");
        to = new File("newTest.xml");
        //tf = new File("simplewiki-20180220-pages-meta-current.xml");
    }
    @Test
    public void dumb() {
        System.out.println("tf:"+tf);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullTest() {
        try {
            w0.parseXmlDump(null);
        } catch (XMLStreamException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }
    @Test
    public void testOwn() {
        try {
            DirectedGraphImpl g1 = (DirectedGraphImpl) w0.parseXmlDump(to);
            Set<Page> vertices = g1.vertexSet();
            System.out.println("OUT: ");
            for(Page p : vertices) {
                System.out.print(p.getId() +":  ");
                for(Page pn : g1.outNeighbors(p)) {
                    System.out.print(pn.getId()+", ");
                }
                System.out.println();
                
            }
            System.out.println("IN: ");
            for(Page p : vertices) {
                System.out.print(p.getId() +":  ");
                for(Page pn : g1.inNeighbors(p)) {
                    System.out.print(pn.getId()+", ");
                }
                System.out.println();
                if(p.getId().equals("6")) {
                    assertEquals(8, g1.outDegree(p));
                    assertEquals(0, g1.inDegree(p));
                }
                if(p.getId().equals("4")) {
                    assertEquals(1, g1.outDegree(p));
                    assertEquals(3, g1.inDegree(p));
                }
            }
        } catch (XMLStreamException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }
    @Test
    public void testOne() {
        try {
            w0.parseXmlDump(t0);
        } catch (XMLStreamException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }
    @Test
    public void smallerTest() {
        try {
            DirectedGraphImpl g1 = (DirectedGraphImpl) w0.parseXmlDump(t1);
            Set<Page> vertices = g1.vertexSet();
            for(Page p : vertices) {
                if(p.getId().equals("Iran")) {
                    System.out.println(g1.inNeighbors(p));
                    System.out.println(g1.outNeighbors(p));
                    System.out.println();
                }
                if(p.getId().equals("April")) {
                    System.out.println(p);
                    Set<Page> outs = g1.outNeighbors(p);
                    for(Page pp: outs) {
                        System.out.print(pp.getId() + "  ");
                    }
                }
            }
            
        } catch (XMLStreamException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }

}
