import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis121.project.WikiXmlDumpParser;
import edu.upenn.cis121.project.engine.WikipediaGame;
import edu.upenn.cis121.project.engine.WikipediaGame.EdgeWeightType;

public class WikipediaGameFactoryImplTest {
    DoubleWeightedDirectedGraphImpl<Integer> g, g1, g2, g3, g4, g5;
    WikipediaGameFactoryImpl wgf0;
    WikipediaGame<Page> wg0;
    File t0, t1, to, tf;
    WikiXmlDumpParser w0, w1, w2;

    @Before
    public void init() { 
        w0 = new WikiXmlDumpParserImpl();
        w1 = new WikiXmlDumpParserImpl();
        w2 = new WikiXmlDumpParserImpl();
        t0 = new File("testOne.xml");
        t1 = new File("smallerTest.xml");
        to = new File("ownTest.xml");
        //tf = new File("simplewiki-20180220-pages-meta-current.xml");
        
    }
    @Test
    public void testOwn() {
        try {
            DirectedGraphImpl g1 = (DirectedGraphImpl) w0.parseXmlDump(to);
            wgf0 = new WikipediaGameFactoryImpl();
            wg0 = wgf0.create(g1);

            Iterable<Page> sPath = wg0.solveWikiGame(EdgeWeightType.UNWEIGHTED, new Page("3"), new Page("5"));
            for(Page p: sPath) {
                System.out.print(p.getId() + ", ");
            }
            System.out.println();
            Iterable<Page> sPath1 = wg0.solveWikiGame(EdgeWeightType.DEGREE, new Page("3"), new Page("3"));
            for(Page p: sPath1) {
                System.out.print(p.getId() + ", ");
            }
        } catch (XMLStreamException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }
}
