import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis121.project.engine.SearchResult;

public class BasicTfIdfSearchEngineTest {

    BasicTfIdfSearchEngine b;
    File fo;
    
    @Before
    public void init() {
        b = new BasicTfIdfSearchEngine();
        fo = new File("newTest.xml");
    }
    
    @Test
    public void test1() {
        try {
            b.loadXmlDump(fo);
        } catch (IOException | XMLStreamException e) {
            fail();
        }

        for(SearchResult s : b.search("orang", 10)) {
            System.out.println(s.getTitle() + " " +s.getOverallValue());
        }
        System.out.println();

        for(SearchResult s : b.search("jeFF", 3)) {
            System.out.println(s.getTitle() + " " +s.getOverallValue());
        }
        System.out.println();
        for(SearchResult s : b.search("jeFF everyone I orange", 10)) {
            System.out.println(s.getTitle() + " " +s.getOverallValue() + " URL:"+s.getURL() + " MAP:"+s.getSubValueMap() + " "+s.getQueryContexts());
        }
    }

}
