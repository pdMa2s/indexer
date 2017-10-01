import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class SimpleTokenizerIndexerBuilder extends IndexerBuilder {

    public SimpleTokenizerIndexerBuilder(String dirName) {
        super(dirName);
    }

    @Override
    public void buildCorpusReader() {
        indexer.setCorpusReader(new DirIteratorCorpusReader(directoryName,initializeReader()));
    }

    @Override
    public void buildTokenizer() {
        indexer.setTokenizer(new SimpleTokenizer());
    }

    private XMLReader initializeReader(){
        try {
            return new XMLReader(new XMLDocumentHandler());

        } catch (ParserConfigurationException | SAXException e) {
            System.err.println("ERROR Configuring SAX parser");
            System.exit(1);
        }
        return null;
    }
}
