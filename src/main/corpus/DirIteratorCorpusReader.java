import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class DirIteratorCorpusReader extends CorpusReader {
    private String dirName;
    private DocumentReader reader;
    public DirIteratorCorpusReader(String dirName){
        initializeParser();
        this.dirName = dirName;
    }

    public DirIteratorCorpusReader(){
        initializeParser();
    }

    public String processDocument() {
      
    }

    private void initializeParser(){
        DefaultCorpusXMLHandler xmlHandler = new XMLDocumentHandler();
        try {
            reader = new XMLReader(xmlHandler);
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("ERROR Configurating SAX parser");
            System.exit(1);
        }
    }
}

