import static org.junit.Assert.*;

import org.junit.Test;

public class BasicTfIdfSearchEngineFactoryTest {

    BasicTfIdfSearchEngineFactory b;
    
    @Test
    public void test() {
       b = new BasicTfIdfSearchEngineFactory();
       BasicTfIdfSearchEngine bb = b.newSearchEngine();
       assertTrue(b != null);
       assertTrue(bb != null);
    }

}
