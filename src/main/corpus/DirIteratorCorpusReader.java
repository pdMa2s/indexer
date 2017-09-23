import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

public class DirIteratorCorpusReader extends CorpusReader {
    private String dirName;
    private DocumentReader reader;
    private DefaultCorpusXMLHandler xmlHandler;
    private SAXParser saxParser;

    public DirIteratorCorpusReader(String dirName){
        initializeReader();
        this.dirName = dirName;
    }

    public DirIteratorCorpusReader(){
        initializeReader();
    }

    @Override
    public String processDocument() {
        return null;
    }

    private void initializeReader(){
        xmlHandler = new XMLDocumentHandler();
        try {
            reader = new XMLReader(xmlHandler);
          
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("ERROR Configurating SAX parser");
            System.exit(1);
        }
    }
}

