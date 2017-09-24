import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class XMLReader implements DocumentReader {

    private File contentFile;
    private DefaultCorpusXMLHandler xmlHandler;
    private SAXParser saxParser;
    private int docID;

    public XMLReader(DefaultCorpusXMLHandler xmlHandler)
            throws ParserConfigurationException, SAXException {
        this.xmlHandler = xmlHandler;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        this.saxParser = factory.newSAXParser();

    }

    @Override
    public void open(File file) {
        this.contentFile = file;
    }

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
}
