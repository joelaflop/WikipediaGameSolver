import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.upenn.cis121.project.graph.DirectedGraph;

public class DirectedGraphImpl implements DirectedGraph<Page> {

    Set<Page> g;

    DirectedGraphImpl() {
        g = new HashSet<Page>();
    }

    private void addVertex(Page vertex) {
        if (!g.contains(vertex)) {
            g.add(vertex);
        }
    }

    static DirectedGraphImpl tempToGraph(Map<String, Page> temp) {
        DirectedGraphImpl graph = new DirectedGraphImpl();
        Iterator<String> iter = temp.keySet().iterator();
        while (iter.hasNext()) {
            String curr = iter.next();
            Page v = temp.get(curr);
                graph.addVertex(v);
        }
        return graph;
    }

    @Override
    public Set<Page> vertexSet() {
        return Collections.unmodifiableSet(g);
    }

    @Override
    public Set<Page> inNeighbors(Page vertex) {
        Iterator<Page> iter = g.iterator();
        Set<Page> inNeighbors = new HashSet<Page>();
        while (iter.hasNext()) {
            Page p = iter.next();
            if (p.getNeighbors().contains(vertex)) {
                inNeighbors.add(p);
            }
        }
        return Collections.unmodifiableSet(inNeighbors);
    }

    @Override
    public Set<Page> outNeighbors(Page vertex) {
        return Collections.unmodifiableSet(vertex.getNeighbors());
    }

}
