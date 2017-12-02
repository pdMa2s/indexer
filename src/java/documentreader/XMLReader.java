package src.java.documentreader;

import org.xml.sax.SAXException;
import src.java.query.Query;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

/**
 * An implementation of a {@link DocumentReader} with the purpose of reading files in XML format.
 * This class uses a {@link SAXParser} for file parsing.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 * @see <a href="https://en.wikipedia.org/wiki/XML">XML</a>
 */
public class XMLReader implements DocumentReader {

    private File contentFile;
    private DefaultCorpusXMLHandler xmlHandler;
    private SAXParser saxParser;
    private String corpusLocation;

    /**
     * Constructs a XMLReader object and initializes a {@link SAXParser} using a {@link DefaultCorpusXMLHandler}.
     * @param xmlHandler An handler for dealing with the relevant document tags.
     * @throws ParserConfigurationException An exception that can occur while instantiating the parser.
     * @throws SAXException An exception that can occur while instantiating the parser.
     */
    public XMLReader(DefaultCorpusXMLHandler xmlHandler)
            throws ParserConfigurationException, SAXException {
        this.xmlHandler = xmlHandler;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        this.saxParser = factory.newSAXParser();

    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void open(File file) {
        this.contentFile = file;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String parse() {

        try {
            saxParser.parse(contentFile, xmlHandler);
        } catch (SAXException | IOException e) {
            System.err.println("ERROR while parsing the xml file!");
            System.exit(1);
        }
        return xmlHandler.getText();
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public int getDocumentID() {
        return xmlHandler.getID();
    }

    public void setCorpusLocation(String corpusLocation){
        this.corpusLocation = corpusLocation;
    }

    public String openAndParse(int id, Query query) {
        this.contentFile = new File(corpusLocation+"/"+"cranfield"+id);
        try {
            saxParser.parse(contentFile, xmlHandler);
        } catch (SAXException | IOException e) {
            System.err.println("ERROR while parsing the xml file!");
            System.exit(1);
        }
       return xmlHandler.getText();
    }
}
