package src.java.indexer.indexerbuilders;

import src.java.corpus.CorpusReader;
import src.java.corpus.DirIteratorCorpusReader;
import src.java.corpus.documentreader.XMLDocumentHandler;
import src.java.corpus.documentreader.XMLReader;
import src.java.corpus.tokenizer.ComplexTokenizer;
import src.java.corpus.tokenizer.Tokenizer;
import org.tartarus.snowball.ext.englishStemmer;
import org.xml.sax.SAXException;
import src.java.indexer.Indexer;
import src.java.indexer.IndexerBuilder;

import javax.xml.parsers.ParserConfigurationException;

import static src.java.constants.Constants.COMPLEXTOKENIZERSTEMMING;

/**
 * A builder that constructs {@link Indexer} with a {@link ComplexTokenizer} that uses an English Stemmer,
 * the {@link CorpusReader} used here is a {@link DirIteratorCorpusReader}.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 *
 */
public class CTStemmingIndexerBuilder extends IndexerBuilder {

    /**
     * {@inheritDoc}
     */
    public CTStemmingIndexerBuilder(String dirName) {
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
     * Configures a {@link Tokenizer} of a type {@link ComplexTokenizer} with Stemming.
     * Uses an English Stemmer of the type {@link org.tartarus.snowball.SnowballStemmer}.
     */
    @Override
    public void buildTokenizer() {
        indexer.setTokenizer(new ComplexTokenizer(new englishStemmer()));
    }

    @Override
    public void buildNormalizer() {
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String getTokenizerType() {
        return COMPLEXTOKENIZERSTEMMING;
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
