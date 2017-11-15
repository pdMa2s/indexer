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
    public static final String DOCFREQUENCY = "freq";
    public static final String NORMALIZED = "norm";
    public static final String INDEXDEAFAULTFILENAME = "index.csv";
}
