package src.java.indexer;

import org.tartarus.snowball.ext.englishStemmer;
import org.xml.sax.SAXException;
import src.java.corpus.CorpusReader;
import src.java.corpus.DirIteratorCorpusReader;
import src.java.corpus.documentreader.XMLDocumentHandler;
import src.java.corpus.documentreader.XMLReader;
import src.java.normalizer.Normalizer;
import src.java.tokenizer.ComplexTokenizer;
import src.java.tokenizer.Tokenizer;

import javax.xml.parsers.ParserConfigurationException;

import static src.java.constants.Constants.COMPLEXTOKENIZERSTEMMING;

public class NormalizedBuilder extends IndexerBuilder {
    /**
     * A super constructor for all the class that will derive this class.
     *
     * @param dirName The directory name where the document corpus is located
     */
    public NormalizedBuilder(String dirName) {
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
        indexer.setNormalizer(new Normalizer());
    }

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
