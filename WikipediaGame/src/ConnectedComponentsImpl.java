import edu.upenn.cis121.project.ConnectedComponents;
import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.graph.SimpleGraph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Provides access to canonical connected components algorithms for a graph.
 *
 * @param <V> the type of vertices of the graph. The vertices must implement {@link Comparable}, in
 * order to provide a deterministic ordering for the iterative depth-first order iterator.
 *
 * For testing purposes, this class must provide a public default no-argument constructor. This
 * means that you do not need to write any constructor at all; if you do write other constructors,
 * be sure you have one that is public and takes no arguments.
 *
 * @author davix
 */
public class ConnectedComponentsImpl<V extends Comparable<V>>  
        implements ConnectedComponents<V> {

    /**
     * Returns an {@link Iterator} over the vertices of a specified graph in depth-first order. The
     * vertices will be visited according to their {@linkplain Comparable natural ordering}.
     *
     * @param graph the graph
     * @return a lazy iterator over vertices of the graph, in depth-first order
     */
    @Override
    public DepthFirstOrderIterator<V> iterativeDepthFirstOrderIterator(SimpleGraph<V> graph) {
        return new DepthFirstOrderIterator<V>(graph);
    }
    

    /**
     * Computes the weakly connected components of the specified graph.
     *
     * @param graph the graph
     * @return an immutable set of sets of vertices that comprise the weakly connected components
     * of the specified graph
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<Set<V>> weaklyConnectedComponents(SimpleGraph<V> graph) {
        DoubleWeightedDirectedGraphImpl<Integer> newGraph =  new DoubleWeightedDirectedGraphImpl<Integer>(graph.order());
        Map<Integer, V> toVal = new HashMap<Integer, V>();
        Map<V, Integer> toInt = new HashMap<V, Integer>();
        int i = 0;
        for(V curr : graph.vertexSet()) {
            toInt.put(curr, i); 
            toVal.put(i, curr);
            i++;
        }
        for(V curr : graph.vertexSet()) {
            for(V neighbor : graph.neighbors(curr)) {
                newGraph.addEdge(toInt.get(curr), toInt.get(neighbor), 1.0);
                newGraph.addEdge(toInt.get(neighbor), toInt.get(curr), 1.0);
            }
        }
        Set<Set<V>> SetOfSets = new HashSet<Set<V>>();
        Set<V> currComponent = new HashSet<V>();
        DepthFirstOrderIterator<V> iter = iterativeDepthFirstOrderIterator((SimpleGraph<V>) newGraph);
        while(iter.hasNext()) {
            while(iter.hasNextInComponent()) {
                currComponent.add(toVal.get(iter.nextInComponent()));
            }
            SetOfSets.add(currComponent);
            if(iter.hasNext()) {
                currComponent = new HashSet<V>();
                currComponent.add(toVal.get(iter.next()));
            }
        }
        return Collections.unmodifiableSet(SetOfSets);
    }

    /**
     * Computes the strongly connected components of the specified graph.
     *
     * @param graph the graph
     * @return an immutable set of sets of vertices that comprise the strongly connected components
     * of the specified graph
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<Set<V>> stronglyConnectedComponents(DirectedGraph<V> graph) {
        DoubleWeightedDirectedGraphImpl<Integer> actualGraph =  new DoubleWeightedDirectedGraphImpl<Integer>(graph.order());
        DoubleWeightedDirectedGraphImpl<Integer> invertedGraph =  new DoubleWeightedDirectedGraphImpl<Integer>(graph.order());
        Map<Integer, V> toVal = new HashMap<Integer, V>();
        Map<V, Integer> toInt = new HashMap<V, Integer>();
        int i = 0;
        for(V curr : graph.vertexSet()) {
            toInt.put(curr, i); 
            toVal.put(i, curr);
            i++;
        }
        for(V curr : graph.vertexSet()) {
            for(V neighbor : graph.neighbors(curr)) {
                actualGraph.addEdge(toInt.get(curr), toInt.get(neighbor), 1.0);
                invertedGraph.addEdge(toInt.get(neighbor), toInt.get(curr), 1.0);
            }
        }

        DepthFirstOrderIterator<V> iter = iterativeDepthFirstOrderIterator((SimpleGraph<V>) actualGraph);
        while(iter.hasNext()) { 
            while(iter.hasNextInComponent()) {
                iter.nextInComponent();
            }
            if(iter.hasNext()) {
                iter.next();
            }
            while(iter.hasNextInComponent()) {
                iter.nextInComponent();
            }
        }
        
        iter.setUpAgain((SimpleGraph<V>) invertedGraph);
        
        Set<Set<V>> setOfSets = new HashSet<Set<V>>();
        Set<V> currComponent = new HashSet<V>();
        while(iter.hasNext()) { 
            while(iter.hasNextInComponent()) {
                currComponent.add(toVal.get(iter.nextInComponent()));
            }
            setOfSets.add(currComponent); 
            if(iter.hasNext()) {
                currComponent = new HashSet<V>();
                currComponent.add(toVal.get(iter.next()));
            }
        }
        setOfSets.add(currComponent);
        
        return Collections.unmodifiableSet(setOfSets);
    }
}
