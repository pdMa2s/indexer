import org.xml.sax.helpers.DefaultHandler;

import java.io.File;

public class XMLReader implements DocumentReader {

    private File contentFile;
    private DefaultHandler xmlHandler;

    public XMLReader(DefaultHandler xmlHandler, File contentFile){
        this.contentFile = contentFile;
        this.xmlHandler = xmlHandler;
    }

    public void open(File file) {
        this.contentFile = file;
    }

    public String parse() {
        return null;
    }
}
