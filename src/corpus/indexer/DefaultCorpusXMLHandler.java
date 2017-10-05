package indexer;

import org.xml.sax.helpers.DefaultHandler;

public abstract class DefaultCorpusXMLHandler extends DefaultHandler {
    public abstract String getText();
    public abstract int getID();
}
