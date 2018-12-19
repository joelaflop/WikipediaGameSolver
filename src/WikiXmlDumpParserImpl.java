import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import edu.upenn.cis121.project.WikiXmlDumpParser;
import edu.upenn.cis121.project.util.mediawiki.MediaWikiParseException;
import edu.upenn.cis121.project.util.mediawiki.MediaWikiText;
import edu.upenn.cis121.project.util.mediawiki.MediaWikiUtils;

public class WikiXmlDumpParserImpl implements WikiXmlDumpParser {

    @Override
    public DirectedGraphImpl parseXmlDump(File file) throws IOException, XMLStreamException {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        Map<String, Page> temp = new HashMap<String, Page>();
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
            XMLEventReader r = xmlif.createXMLEventReader(br);
            int titlecnt = 0, formatcnt = 0, textcnt = 0;
            while (r.hasNext()) {
                XMLEvent event = r.nextEvent();
                if (isStartPage(event)) {
                    String format = null, title = null, text = null;
                    while (r.hasNext() && !isEndPage(event)) { 
                        event = r.nextEvent();
                        if (isStartTitle(event)) {
                            title = r.getElementText();
                            titlecnt++;
                        } else if (isStartFormat(event)) {
                            format = r.getElementText();
                            formatcnt++;
                        } else if (isStartText(event)) {
                            text = r.getElementText();
                            textcnt++;
                        }
                    }
                    if(titlecnt > 1 || formatcnt > 1 || textcnt > 1) {
                        throw new XMLStreamException();
                    }
                    if (title != null) {
                        Page currP;
                        if (!temp.containsKey(title)) {
                            currP = new Page(title);
                            temp.put(title, currP);
                        } else {
                            currP = temp.get(title);
                        }
                        if (text != null && format != null) {
                            if (format.equals("text/x-wiki")) {
                                try {
                                    MediaWikiText mwt = MediaWikiUtils.parseWikiText(text);
                                    text = mwt.getText();
                                    currP.setText(text);
                                    List<String> outNeighbors = mwt.getOutlinks();
                                    Iterator<String> neighborsIter = outNeighbors.iterator();
                                    while (neighborsIter.hasNext()) {
                                        String neighborTitle = neighborsIter.next();
                                        if (temp.containsKey(neighborTitle)) {
                                            currP.addNeighbor(temp.get(neighborTitle));
                                        } else {
                                            Page neighborP = new Page(neighborTitle);
                                            temp.put(neighborTitle, neighborP);
                                            currP.addNeighbor(neighborP);
                                        }
                                    }
                                } catch (MediaWikiParseException e) {
                                }
                            } else if (format.equals("text/plain")) {
                                currP.setText(text);
                            }
                        }
                    }
                }
                titlecnt = 0;
                formatcnt = 0; 
                textcnt = 0;
            }
        } catch (XMLStreamException e) {
        }
        DirectedGraphImpl g = DirectedGraphImpl.tempToGraph(temp);
        return g;
    }

    boolean isStartPage(XMLEvent event) {
        if (event.isStartElement()) {
            return event.asStartElement().getName().getLocalPart().equals("page");
        } else {
            return false;
        }
    }

    boolean isEndPage(XMLEvent event) {
        if (event.isEndElement()) {
            return event.asEndElement().getName().getLocalPart().equals("page");
        } else {
            return false;
        }
    }

    boolean isStartTitle(XMLEvent event) {
        if (event.isStartElement()) {
            return event.asStartElement().getName().getLocalPart().equals("title");
        } else {
            return false;
        }
    }

    boolean isStartFormat(XMLEvent event) {
        if (event.isStartElement()) {
            return event.asStartElement().getName().getLocalPart().equals("format");
        } else {
            return false;
        }
    }

    boolean isStartText(XMLEvent event) {
        if (event.isStartElement()) {
            return event.asStartElement().getName().getLocalPart().equals("text");
        } else {
            return false;
        }
    }

}
