package indexer;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class ComplexTokenizerIndexerBuilder extends IndexerBuilder {

    public ComplexTokenizerIndexerBuilder(String dirName) {
        super(dirName);
    }

    @Override
    public void buildCorpusReader() {
        indexer.setCorpusReader(new DirIteratorCorpusReader(directoryName,initializeReader()));
    }

    @Override
    public void buildTokenizer() {
        indexer.setTokenizer(new ComplexTokenizer());
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
