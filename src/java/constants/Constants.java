package src.java.constants;


/**
 * This class contains the constant variables that certain fixed used throughout the project.
 * This facilitates any alterations of this values, since they will be automatically updated in
 * every place where they are being used.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public class Constants {
    /**
     * The tag that represents an {@link src.java.indexer.Indexer} with a {@link src.java.tokenizer.ComplexTokenizer}
     * without stemming.
     */
    public static final String COMPLEXTOKENIZER = "complex";

    /**
     * The tag that represents an {@link src.java.indexer.Indexer} with a {@link src.java.tokenizer.ComplexTokenizer}
     * with stemming.
     */
    public static final String COMPLEXTOKENIZERSTEMMING = "complexStemming";

    /**
     * The tag that represents an {@link src.java.indexer.Indexer} with a {@link src.java.tokenizer.SimpleTokenizer}.
     */
    public static final String SIMPLETOKENIZER = "simple";
    /**
     * Identifies the ranking system that will be used, in this case Boolean Retrieval
     */
    public static final String DOCFREQUENCY = "freq";
    /**
     * Identifies the ranking system that will be used, in this case Ranked Retrieval
     */
    public static final String NORMALIZED = "norm";
    /**
     * The default name of the index that is written on disk
     */
    public static final String INDEXDEAFAULTFILENAME = "index.csv";
    /**
     * The default value of the threshold that limits the results of the queries
     */
    public static final double THRESHOLDDEFAULTVALUE = -1;

    public static final String CORPUSLOCATION = "cranfield";

    public static final String DOCUMENTINDEXFILE = "docIndexFile";

    public static final String RELEVANCESCOREFILE = "cranfield.query.relevance.txt";
}
