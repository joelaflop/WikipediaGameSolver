import edu.upenn.cis121.project.WikiXmlDumpParser;
import edu.upenn.cis121.project.engine.QueryContext;
import edu.upenn.cis121.project.engine.SearchEngine;
import edu.upenn.cis121.project.engine.SearchResult;
import edu.upenn.cis121.project.graph.DirectedGraph;
import javax.xml.stream.XMLStreamException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author davix
 */
public class BasicTfIdfSearchEngine implements SearchEngine {

    /**
     * Loads a dataset from a particular XML file. Guaranteed to be called exactly
     * once before calling any other methods.
     *
     * @param file
     *            the file
     * @throws IOException
     *             if an I/O error occurs
     * @throws XMLStreamException
     *             if an error occurs during XML processing
     */

    RecordInvertedIndexImpl<String> words;
    Map<String, Integer> occurences;
    int documentCnt;

    @Override
    public void loadXmlDump(File file) throws IOException, XMLStreamException {
        WikiXmlDumpParser w = new WikiXmlDumpParserImpl();
        words = new RecordInvertedIndexImpl<String>();
        occurences = new HashMap<String, Integer>();

        @SuppressWarnings("unchecked")
        DirectedGraph<Page> g = (DirectedGraph<Page>) w.parseXmlDump(file);
        for (Page p : g.vertexSet()) {
            String[] text = p.getText().split("\\s+");
            for (int i = 0; i < text.length; i++) {
                text[i] = text[i].replaceAll("[^a-zA-Z]", "").toLowerCase().trim();
                words.addRecord(text[i], p.getId());
                if (occurences.containsKey(p.getId() + text[i])) {
                    occurences.put(p.getId() + text[i], occurences.get(p.getId() + text[i]) + 1);
                } else {
                    occurences.put(p.getId() + text[i], 1);
                }
            }
        }
        documentCnt = g.order();
    }

    /**
     * Retrieves search results for a query in order of descending relevance.
     *
     * @param text
     *            the query
     * @param maxResults
     *            the maximum number of results to return
     * @return search results for the specified query in order of descending
     *         relevance
     */
    @Override
    public Iterable<? extends SearchResult> search(String text, int maxNumResults) {
        
        String[] splitText = text.split("\\s+");
        Map<String, Double> docsInSearch = new HashMap<String, Double>();
        for (int i = 0; i < splitText.length; i++) {
            splitText[i] = splitText[i].replaceAll("[^a-zA-Z]", "").toLowerCase().trim();
            if (words.containsKey(splitText[i])) {
                Collection<String> documentsForTerm = words.getRecords(splitText[i]);
                for (String doc : documentsForTerm) {
                    double tf = (double) occurences.get(doc + splitText[i]);
                    double idf = Math.log((double) documentCnt / (double) (1 + documentsForTerm.size()));
                    if (docsInSearch.containsKey(doc)) {
                        docsInSearch.put(doc, tf * idf + docsInSearch.get(doc));
                    } else {
                        docsInSearch.put(doc, tf * idf);
                    }
                }
            }
        }
        
        Set<SearchResult> temp = new HashSet<SearchResult>();
        for (String doc : docsInSearch.keySet()) {
            temp.add(new SearchResultImpl(doc, docsInSearch.get(doc)));
        }
        PriorityQueue<SearchResult> listing = new PriorityQueue<SearchResult>(temp);
        LinkedList<SearchResult> results = new LinkedList<SearchResult>();
        while (results.size() < maxNumResults && !listing.isEmpty()) {
            results.addFirst(listing.poll());
        }

        return results;
    }

    public class SearchResultImpl implements SearchResult, Comparable<Object> {

        String title;
        double score;

        SearchResultImpl(String t, double s) {
            title = t;
            score = s;
        }

        @Override
        public double getOverallValue() {
            return score;
        }

        @Override
        public Collection<? extends QueryContext> getQueryContexts() {
            return null;
        }

        @Override
        public Map<String, Double> getSubValueMap() {
            Map<String, Double> temp = new HashMap<String, Double>();
            temp.put("tf-idf", score);
            return temp;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public URL getURL() {
            try {
                return new URL("https://simple.wikipedia.org/wiki/" + title);
            } catch (MalformedURLException e) {
                return null;
            }
        }

        @Override
        public int compareTo(Object o) {
                return Double.compare(score, ((SearchResult) o).getOverallValue());
        }

    }
}
