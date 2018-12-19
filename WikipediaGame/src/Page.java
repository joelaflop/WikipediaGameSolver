import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.upenn.cis121.project.data.AbstractIdentifiable;

public class Page extends AbstractIdentifiable {
    String text;
    Set<Page> outNeighbors;
    
    protected Page(String id) {
        super(id);
        this.text = new String();
        outNeighbors = new HashSet<Page>();
    }
    
    void setText(String t) {
        this.text = t;
    }
    
    String getText() {
        return text;
    }

    void addNeighbor(Page p) {
        outNeighbors.add(p);
    }

    Set<Page> getNeighbors() {
        return Collections.unmodifiableSet(outNeighbors);
    }
}
