package indexer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class XMLDocumentHandler extends DefaultCorpusXMLHandler {

    private boolean titleElement = false;
    private boolean bodyElement = false;
    private boolean docElement = false;

    private String title;
    private String body;
    private int docNr;

    private StringBuilder text;

    static final String TITLETAG = "TITLE";
    static final String BODYTAG = "TEXT";
    static final String IDTAG = "DOCNO";

    public XMLDocumentHandler(){
        super();
        text = new StringBuilder();
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public int getID() {return docNr; }

    @Override
    public void startElement(
            String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if(qName.equalsIgnoreCase(TITLETAG))
            titleElement = true;
        else if(qName.equalsIgnoreCase(BODYTAG))
            bodyElement = true;
        else if(qName.equalsIgnoreCase(IDTAG))
            docElement = true;
    }


        @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if(titleElement) {
            title = new String(ch, start, length);
            text.append(title);
            titleElement = false;
        }
        else if(bodyElement){
            body = new String(ch, start, length)  ;
            text.append(body);
        }
        else if(docElement){
            Double id =  Double.parseDouble(new String(ch, start, length));
            docNr = id.intValue();
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase(BODYTAG))
            bodyElement = false;
        else if(qName.equalsIgnoreCase(TITLETAG))
            titleElement = false;
        else if(qName.equalsIgnoreCase(IDTAG))
            docElement = false;
    }

    @Override
    public String getText() {
        String clear = text.toString();
        text = new StringBuilder();
        return clear;
    }
}