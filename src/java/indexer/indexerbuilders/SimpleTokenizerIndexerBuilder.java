package src.java.indexer.indexerbuilders;

import src.java.corpus.CorpusReader;
import src.java.corpus.DirIteratorCorpusReader;
import src.java.corpus.documentreader.XMLDocumentHandler;
import src.java.corpus.documentreader.XMLReader;
import src.java.corpus.tokenizer.SimpleTokenizer;
import src.java.corpus.tokenizer.Tokenizer;
import org.xml.sax.SAXException;
import src.java.indexer.IndexerBuilder;

import javax.xml.parsers.ParserConfigurationException;
import static src.java.constants.Constants.SIMPLETOKENIZER;

/**
 * An implementation of IndexerBuilder. Constructs an Indexer with a {@link SimpleTokenizer} and with
 *  a {@link DirIteratorCorpusReader}
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 */
public class SimpleTokenizerIndexerBuilder extends IndexerBuilder {
    /**
     *
     *{@inheritDoc}
     */
    public SimpleTokenizerIndexerBuilder(String dirName){
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

    @Override
    public void buildNormalizer() { }

    /**
     *{@inheritDoc}
     */
    @Override
    public String getTokenizerType() {
        return SIMPLETOKENIZER;
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

