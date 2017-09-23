import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Tester {
    public static void main(String[] args){
        try {
            File inputFile = new File("cranfield/cranfield0001");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            CorpusXMLDocumentHandler userhandler = new CorpusXMLDocumentHandler();
            saxParser.parse(inputFile, userhandler);

            System.out.println("text: "+ userhandler.getText().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
