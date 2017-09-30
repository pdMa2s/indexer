import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class DirIteratorCorpusReader implements CorpusReader {
    private File[] dirFiles;
    private DocumentReader reader;
    private int fileIndex;

    public DirIteratorCorpusReader(String dirName){
        initializeReader();
        dirFiles = new File(dirName).listFiles();
        fileIndex = 0;

    }

    @Override
    public boolean hasDocument(){
        return !(fileIndex == dirFiles.length);
    }

    @Override
    public int getDocumentID() {
        return reader.getDocumentID();
    }

    @Override
    public String processDocument() {
        if(!hasDocument())
            return null;
        reader.open(dirFiles[fileIndex]);
        fileIndex++;
        return reader.parse();
    }

    private void initializeReader(){
        try {
            reader = new XMLReader(new XMLDocumentHandler());
          
        } catch (ParserConfigurationException | SAXException e) {
            System.err.println("ERROR Configuring SAX parser");
            System.exit(1);
        }
    }
}

