import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CorpusXMLDocumentHandler extends DefaultHandler {

    private boolean titleElement = false;
    private boolean bodyElement = false;

    private String title;
    private String body;
    private StringBuilder allTheText;

    static final String TITLETAG = "TITLE";
    static final String BODYTAG = "TEXT";

    public CorpusXMLDocumentHandler(){
        super();
        allTheText = new StringBuilder();
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public String getAllTheText() {
        return allTheText.toString();
    }

    @Override
    public void startElement(
            String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if(qName.equalsIgnoreCase(TITLETAG))
            titleElement = true;
        else if(qName.equalsIgnoreCase(BODYTAG))
            bodyElement = true;

    }


        @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if(titleElement) {
            title = new String(ch, start, length);
            allTheText.append(title);
        }
        else if(bodyElement){
            body = new String(ch, start, length);
            allTheText.append(body);
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase(BODYTAG)){
            bodyElement = false;
        }
        else if(qName.equalsIgnoreCase(TITLETAG))
            titleElement = false;

    }
}
