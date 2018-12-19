import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import edu.upenn.cis121.project.graph.Graph;

public class DepthFirstOrderIterator<V extends Comparable<V>> implements Iterator<V> {

    // TODO: You will need to add public constructor(s) of your own.
    // You may also add package-private helper methods as you see fit.

    // Graph<V> g;

    Stack<V> stack;
    List<V> sortedVerts;

    int count;
    // Graph<V> g;

    Map<V, Boolean> visited;
    Map<V, PriorityQueue<V>> neighbors;

    boolean first;
    V potentialNext;

    List<V> finishedVerts;

    public DepthFirstOrderIterator(Graph<V> graph) {
        Set<V> vertsSet = graph.vertexSet();
        sortedVerts = new ArrayList<V>(vertsSet);
        Collections.sort(sortedVerts);
        neighbors = new HashMap<V, PriorityQueue<V>>();

        visited = new HashMap<V, Boolean>();
        for (int i = 0; i < sortedVerts.size(); i++) {
            visited.put(sortedVerts.get(i), false);
            neighbors.put(sortedVerts.get(i),
                    new PriorityQueue<V>(graph.neighbors(sortedVerts.get(i))));
        }

        stack = new Stack<V>();

        visited.put(sortedVerts.get(0), true);
        stack.push(sortedVerts.get(0));

        count = 0;

        finishedVerts = new LinkedList<V>();
        first = true;
    }


    public void setUpAgain(Graph<V> graph) {
        if (finishedVerts.size() < sortedVerts.size()) {
            throw new IllegalArgumentException("Not ready yet! finishedCnt:" + finishedVerts.size()
                    + " sortedcnt: " + sortedVerts.size());
        }

        Collections.reverse(finishedVerts);
        sortedVerts = finishedVerts;
        finishedVerts = new LinkedList<V>();
        neighbors = new HashMap<V, PriorityQueue<V>>();
        visited = new HashMap<V, Boolean>();

        for (int i = 0; i < sortedVerts.size(); i++) {
            visited.put(sortedVerts.get(i), false);
            neighbors.put(sortedVerts.get(i),
                    new PriorityQueue<V>(graph.neighbors(sortedVerts.get(i))));
        }

        stack = new Stack<V>();

        visited.put(sortedVerts.get(0), true);
        stack.push(sortedVerts.get(0));

        count = 0;
        potentialNext = null;

        first = true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method considers elements across components as part of the iteration.
     */

    @Override
    public boolean hasNext() {
        return hasNextInComponent() || count < sortedVerts.size() - 1;
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the current component has no more elements, then a subsequent call to this
     * method will return a vertex from an unvisited component.
     *
     * @throws NoSuchElementException
     *             {@inheritDoc}
     */
    @Override
    public V next() {
        if (hasNextInComponent()) {
            return nextInComponent();
        } else if (hasNext()) {
            for (int i = 0; i < sortedVerts.size(); i++) {
                if (!visited.get(sortedVerts.get(i))) {
                    potentialNext = sortedVerts.get(i);
                    visited.put(sortedVerts.get(i), true);
                    while (!neighbors.get(potentialNext).isEmpty()) {
                        V neighbor = neighbors.get(potentialNext).poll();
                        if (!visited.get(neighbor)) {
                            stack.push(potentialNext);
                            stack.push(neighbor);
                            visited.put(neighbor, true);
                            count += 2;
                            V temp = potentialNext;
                            potentialNext = neighbor;
                            return temp;
                        }
                    }
                    finishedVerts.add(potentialNext);
                    count++;
                    return potentialNext;
                }
            }
            throw new NoSuchElementException("Made it to end of sorted Verts");
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Returns {@code true} if the iteration has more elements for the current
     * component.
     *
     * @return {@code true} if the iteration has more elements for the current
     *         component
     */
    public boolean hasNextInComponent() {
        while (potentialNext == null && !stack.isEmpty()) {
            potentialNext = temp();
        }
        return potentialNext != null && !stack.isEmpty();
    }

    /**
     * Returns the next element in the iteration of the current component. Note:
     * This advances the iterator as well.
     * <p>
     * Note that this will not advance past a component. Calling next() will allow
     * you to enter a new component.
     *
     * @return the next element in the iteration for the current component.
     * @throws NoSuchElementException
     *             if the iteration has no more elements for the current component
     */
    public V nextInComponent() {
        if (hasNextInComponent()) {
            V temp = potentialNext;
            potentialNext = temp();
            return temp;
        } else {
            throw new NoSuchElementException();
        }
    }

    public V temp() {
        V curr = stack.peek();
        if (first) {
            first = false;
            return curr;
        }
        while (!neighbors.get(curr).isEmpty()) {
            V neighbor = neighbors.get(curr).poll();
            if (!visited.get(neighbor)) {
                visited.put(neighbor, true);
                stack.push(neighbor);
                count++;
                return neighbor;
            }

        }
        V c = stack.pop();
        finishedVerts.add(c);
        return null;
    }

}
