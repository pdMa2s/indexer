import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import java.io.File;

public class DirIteratorCorpusReader extends CorpusReader {
    private String dirName;
    private DocumentReader reader;
    private DefaultCorpusXMLHandler xmlHandler;

    public DirIteratorCorpusReader(String dirName){
        initializeReader();
        this.dirName = dirName;
        startIterativeProcessing();
    }

    public DirIteratorCorpusReader(){
        initializeReader();
        startIterativeProcessing();
    }

    public void startIterativeProcessing(){
        File[] files = new File(dirName).listFiles();
        for (File file : files) {
            processDocument(file);
        }
    }

    @Override
    public String processDocument(File document) {
        reader.open(document);
        return reader.parse();
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

