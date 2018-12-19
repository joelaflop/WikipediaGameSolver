import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import edu.upenn.cis121.project.data.AbstractIdentifiable;
import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.engine.WikipediaGame;

/**
 * Implementation of a solver for the Wikipedia game. It finds the shortest path
 * between two Wikipedia pages. In this case, it also solves the game when there
 * is a twist: when it is beneficial to go through less "important" Wikipedia
 * pages. This twist is implemented by altering the edge weights on the graph.
 * The constructor for the class takes in the unweighted, directed graph you
 * created from the Wikidump.
 * <p>
 * You are free to implement this class however you like. We will be testing
 * your code through the {@link WikipediaGameFactoryImpl} class.
 */
class WikipediaGameImpl<V extends AbstractIdentifiable> implements WikipediaGame<V> {

    DoubleWeightedDirectedGraphImpl<Integer> dg;
    Map<Integer, V> toVal;
    Map<V, Integer> toInt;
    
    public WikipediaGameImpl(DirectedGraph<V> g) {
        dg = new DoubleWeightedDirectedGraphImpl<Integer>(g.order());
        toVal = new HashMap<Integer, V>(g.order());
        toInt = new HashMap<V, Integer>(g.order());
        int i = 0;
        for(V curr : g.vertexSet()) {
            toInt.put(curr, i); 
            toVal.put(i, curr);
            i++;
        }
        for(V curr : g.vertexSet()) {
            for(V neighbor : g.neighbors(curr)) {
                dg.addEdge(toInt.get(curr), toInt.get(neighbor), 1.0);
            }
        }
        
    }

    /**
     * Solves the Wikipedia Game.
     *
     * @param e
     *            how the edges should be weighted. DEGREE means that each edge has
     *            weight equal to the in-degree of its target node and UNWEIGHTED
     *            means all edges have weight equal to 1.
     * @param src
     *            the starting page
     * @param tgt
     *            the ending page
     * @throws IllegalArgumentException
     *             if any of the parameters are invalid
     * @return - the shortest path (considering the edge weights) from src to tgt -
     *         an empty Iterable if there is no path - an Iterable consisting of
     *         just src if src.equals(tgt)
     */
    @Override 
    public Iterable<V> solveWikiGame(EdgeWeightType e, V src, V tgt) {
        ShortestPathImpl<Integer> s = new ShortestPathImpl<Integer>();
        if(e.equals(EdgeWeightType.DEGREE)) { 
            for(Integer curr : dg.vertexSet()) {
                for(Integer neighbor : dg.neighbors(curr)) {
                    dg.setEdge(curr, neighbor, (double) dg.inDegree(curr));
                }
            }
            
        } else if(e.equals(EdgeWeightType.UNWEIGHTED)) { 
            //edges are already weighted to 1
        } else {
            throw new IllegalArgumentException();
        }
        Iterable<Integer> ints = s.getShortestPath(dg, toInt.get(src), toInt.get(tgt));
        List<V> vals = new ArrayList<V>();
        for(Integer i : ints) {
            vals.add(toVal.get(i));
        }
        return vals;
    }
}