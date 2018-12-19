import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import edu.upenn.cis121.project.graph.DoubleWeightedDirectedGraph;
import edu.upenn.cis121.project.graph.ForeignVertexException;

public class DoubleWeightedDirectedGraphImpl<V extends Comparable<V>> implements DoubleWeightedDirectedGraph<Integer> {
    ArrayList<Map<Integer, Double>> g;
    
    DoubleWeightedDirectedGraphImpl(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        g = new ArrayList<Map<Integer, Double>>(n); 
        for (int i = 0; i < 2*n; i++) {
            Map<Integer, Double> curr = new HashMap<Integer, Double>();
            g.add(curr); 
        }
    }
    
    int getSize() {
        return g.size()/2;
    }
    
    public boolean hasEdge(Integer source, Integer target) {
        if(source == null || target == null) {
            throw new IllegalArgumentException();
        }
        if (source < 0 || source >= getSize() || target < 0 || target >= getSize()) {
            throw new ForeignVertexException();
        }
        return g.get(2*source).keySet().contains(target); 

    }
    
    @Override
    public Optional<Double> getWeight(Integer source, Integer target) {
        if(source == null || target == null) {
            throw new IllegalArgumentException();
        }
        if (source < 0 || source >= getSize() || target < 0 || target >= getSize()) {
            throw new ForeignVertexException();
        }
        if (!hasEdge(source, target)) {
            return Optional.empty();
        }
        return Optional.of(g.get(2*source).get(target));
    }
    
    public boolean addEdge(Integer source, Integer target, Double weight) {
        if(source == null || target == null) {
            throw new IllegalArgumentException();
        }
        if (source < 0 || source >= getSize() || target < 0 || target >= getSize()) {
            throw new ForeignVertexException();
        }
        if (hasEdge(source, target)) {
            return false; 
        } else {
            g.get(2*source).put(target, weight);
            g.get(2*target + 1).put(source, weight);
            return true;
        }
    }
    
    public void setEdge(Integer source, Integer target, Double weight) {
        if(source == null || target == null) {
            throw new IllegalArgumentException();
        }
        if (source < 0 || source >= getSize() || target < 0 || target >= getSize()) {
            throw new ForeignVertexException();
        }
        if (hasEdge(source, target)) {
            g.get(2*source).put(target, weight);
            g.get(2*target + 1).put(source, weight);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Set<Integer> neighbors(Integer vertex) {
        return outNeighbors(vertex);
    }

    @Override
    public Set<Integer> inNeighbors(Integer vertex) {
        Set<Integer> neighbors = new HashSet<Integer>();
        for(Integer i : g.get(2*vertex +1 ).keySet()) {
            neighbors.add(i);
        }
        return Collections.unmodifiableSet(neighbors);
    }

    @Override
    public Set<Integer> outNeighbors(Integer vertex) {
        Set<Integer> neighbors = new HashSet<Integer>();
        for(Integer i : g.get(2*vertex).keySet()) {
            neighbors.add(i);
        }
        return Collections.unmodifiableSet(neighbors);
    }

    @Override
    public Set<Integer> vertexSet() {
        Set<Integer> vSet = new HashSet<Integer>();
        for(int i = 0; i < getSize(); i++) {
            vSet.add(i);
        }
        return Collections.unmodifiableSet(vSet);
    }
}
