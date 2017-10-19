package src.java.indexer;

import src.java.corpus.CorpusReader;
import src.java.index.IndexWriter;
import src.java.tokenizer.Tokenizer;

/**
 * This abstract class serves as a starting point for the an implementation of the builder pattern with the
 * purpose of creating an instance of an Indexer.
 * An indexer class needs a {@link CorpusReader} and a {@link Tokenizer}, as such, this class can have many configurations depending on the
 * user's preferences, so, in order to hide some complexity a builder pattern is applied.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 * @see Indexer
 * @see CorpusReader
 * @see Tokenizer
 * @see ComplexTokenizerIndexerBuilder
 */
public abstract class IndexerBuilder {
    protected Indexer indexer;
    protected String directoryName;
    /**
     * A super constructor for all the class that will derive this class.
     * @param dirName The directory name where the document corpus is located
     */
    public IndexerBuilder(String dirName){
        this.directoryName = dirName;
    }
    public Indexer getIndexer() {
        return indexer;
    }

    /**
     * Creates an instance of a Indexer.
     * @see Indexer
     */
    public void createIndexer(){
        indexer = new Indexer();
    }

    /**
     * Creates a representation of an Indexer with certain configurations.
     * @return A representation of an Indexer
     * @see Indexer
     */
    public Indexer constructIndexer(){
        createIndexer();
        buildCorpusReader();
        buildTokenizer();
        return indexer;
    }

    /**
     * The purpose of this function is to configure a CorpusReader for this Indexer instance.
     * @see CorpusReader
     */
    public abstract void buildCorpusReader();

    /**
     * The purpose of this function is to configure a tokenizer for this Indexer instance.
     * @see Tokenizer
     */
    public abstract void buildTokenizer();

    /**
     *
     * @return The tag locate on {@link src.java.constants.Constants} that represents the type of {@link Tokenizer}
     * that is being used to create the {@link Indexer}.
     */
    public abstract String getTokenizerType();

}
