package indexer;

import corpus.CorpusReader;
import corpus.DirIteratorCorpusReader;
import documentReader.XMLDocumentHandler;
import documentReader.XMLReader;
import tokenizer.SimpleTokenizer;
import tokenizer.Tokenizer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * An implementation of IndexerBuilder. Constructs an Indexer with a {@link SimpleTokenizer} and with
 *  a {@link DirIteratorCorpusReader}
 * @author Pedro Matos
 * @author David Ferreira
 * @since 09-27-2017
 */
public class SimpleTokenizerIndexerBuilder extends IndexerBuilder {
    /**
     *
     *{@inheritDoc}
     */
    public SimpleTokenizerIndexerBuilder(String dirName) {
        super(dirName);
    }
    /**
     * Configures a {@link CorpusReader} of a type {@link DirIteratorCorpusReader} with a {@link XMLReader}.
     */
    @Override
    public void buildCorpusReader() {
        indexer.setCorpusReader(new DirIteratorCorpusReader(directoryName,initializeReader()));
    }

    /**
     * Configures a {@link Tokenizer} of a type {@link SimpleTokenizer}.
     */
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
