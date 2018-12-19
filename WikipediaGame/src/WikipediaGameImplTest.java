import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis121.project.WikiXmlDumpParser;
import edu.upenn.cis121.project.engine.WikipediaGame.EdgeWeightType;

public class WikipediaGameImplTest {
    DoubleWeightedDirectedGraphImpl<Integer> g, g1, g2, g3, g4, g5;
    WikipediaGameImpl<Page> wg0;
    File t0, t1, to, tf;
    WikiXmlDumpParser w0, w1, w2;

    @Before
    public void init() { 
        w0 = new WikiXmlDumpParserImpl();
        w1 = new WikiXmlDumpParserImpl();
        w2 = new WikiXmlDumpParserImpl();
        t1 = new File("smallerTest.xml");
        to = new File("newTest.xml");
        //tf = new File("simplewiki-20180220-pages-meta-current.xml");
        
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
            wg0 = new WikipediaGameImpl<Page>(g1);

            Iterable<Page> sPath = wg0.solveWikiGame(EdgeWeightType.UNWEIGHTED, new Page("3"), new Page("5"));
            
            for(Page p: sPath) {
                System.out.print(p.getId() + ", ");
            }
            System.out.println();
        } catch (XMLStreamException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void test1() {
        DirectedGraphImpl g1 = null;
        try {
            g1 = (DirectedGraphImpl) w0.parseXmlDump(to);
        } catch (IOException | XMLStreamException e) {
        }
        wg0 = new WikipediaGameImpl<Page>(g1);
        Iterable<Page> sPath1 = wg0.solveWikiGame(EdgeWeightType.DEGREE, new Page("3"), new Page("5"));
        for(Page p: sPath1) {
            System.out.print(p.getId() + ", ");
        }
    }

}
