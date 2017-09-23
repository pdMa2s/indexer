import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class DirIteratorCorpusReader extends CorpusReader {
    private String dirName;
    private DocumentReader reader;
    private DefaultCorpusXMLHandler xmlHandler;
    private SAXParser saxParser;

    public DirIteratorCorpusReader(String dirName){
        initializeParser();
        this.dirName = dirName;
    }

    public DirIteratorCorpusReader(){
        initializeParser();
    }

    @Override
    public String processDocument() {
        return null;
    }

    private void initializeParser(){
        xmlHandler = new XMLDocumentHandler();
        try {
            reader = new XMLReader(xmlHandler);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();

        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("ERROR Configurating SAX parser");
            System.exit(1);
        }
    }
}

