
//package edu.upenn.cis121.project.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import edu.upenn.cis121.project.data.BinaryMinHeap;
import edu.upenn.cis121.project.graph.DoubleWeightedDirectedGraph;
import edu.upenn.cis121.project.graph.ShortestPath;

/**
 * @param <V>
 *            {@inheritDoc}
 *
 * @author eyeung, 16sp
 */
public class ShortestPathImpl<V> implements ShortestPath<V> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<V> getShortestPath(DoubleWeightedDirectedGraph<V> G, V src, V tgt) {
        Set<V> vertices = G.vertexSet();
        List<V> dad = new ArrayList<V>(vertices.size());
        Map<V, Integer> ints = new HashMap<V, Integer>();
        double[] distance = new double[vertices.size()];
        Integer i = 0;
        for (V v : vertices) {
            ints.put(v, i);
            dad.add(v);
            distance[i] = Double.MAX_VALUE;
            i++;
        }
        distance[ints.get(src)] = 0.0;

        BinaryMinHeap<V, Double> queue = new BinaryMinHeapImpl<V, Double>();
        queue.add(src, 0.0);

        List<V> path = new ArrayList<V>();

        while (!queue.isEmpty()) {
            V mom = queue.extractMin();
            for (V neighbor : G.outNeighbors(mom)) {
                Optional<Double> w = G.getWeight(mom, neighbor);
                if (w.isPresent()) {
                    Double weight = w.get();
                    if (distance[ints.get(neighbor)] > distance[ints.get(mom)] + weight) {
                        distance[ints.get(neighbor)] = distance[ints.get(mom)] + weight;
                        dad.set(ints.get(neighbor), mom);
                        if(queue.containsValue(neighbor)) {
                            queue.decreaseKey(neighbor, distance[ints.get(neighbor)]);
                        } else {
                            queue.add(neighbor, distance[ints.get(neighbor)]);
                        }
                    }
                }
            }
            if (mom.equals(tgt)) {
                while (dad.get(ints.get(tgt)) != tgt) {
                    path.add(tgt);
                    tgt = dad.get(ints.get(tgt));
                }
                path.add(tgt);
            }
        }
        Collections.reverse(path);
        return path;
    }

}
