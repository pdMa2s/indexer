import java.io.File;

public class XMLReader implements DocumentReader {
    private File contentFile;
    public XMLReader(File contentFile){
        this.contentFile = contentFile;
    }

    public void open(File file) {

    }

    public String parse() {
        return null;
    }
}
