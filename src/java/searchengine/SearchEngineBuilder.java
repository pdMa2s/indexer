package src.java.searchengine;

import src.java.index.InvertedIndex;
import src.java.normalizer.Normalizer;
import src.java.query.DocumentIndex;
import src.java.query.QueryIndex;
import src.java.query.QueryReader;
import src.java.query.QueryResultWriter;
import src.java.tokenizer.Tokenizer;
import src.java.word2vec.QueryExpansionWord2Vec;

/**
 * This abstract class serves as a starting point for the an implementation of the builder pattern with the
 * purpose of creating an instance of a {@link SearchEngine}.
 * A {@link SearchEngine} object needs several components to work, all of them can be changed and replaced
 * to create a different kind of {@link SearchEngine}, as so, this object type of can have many representations,
 * so in order to separate it's representation from it's construction a builder pattern was applied.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 10-16-2017
 */
public abstract class SearchEngineBuilder {
    protected SearchEngine searchEngine;
    protected InvertedIndex invertedIndex;
    protected Tokenizer tokenizer;
    protected QueryIndex queryIndex;
    protected DocumentIndex documentIndex;
    protected double threshold;
    protected QueryExpansionWord2Vec w2v;

    /**
     * A super constructor for all the class that will derive this class.
     * @param invertedIndex An {@link InvertedIndex} object to be provided to the searchEngine.
     * @param tokenizer A {@link Tokenizer} to be provided to the {@link QueryReader} object.
     * @param queryIndex A {@link QueryIndex} with the queries to be processed.
     * @param threshold The minimum values of the result scores.
     */
    public SearchEngineBuilder(InvertedIndex invertedIndex, Tokenizer tokenizer,
                               QueryIndex queryIndex, DocumentIndex documentIndex, double threshold) {
        this.invertedIndex = invertedIndex;
        this.tokenizer = tokenizer;
        this.queryIndex = queryIndex;
        this.threshold = threshold;
        this.documentIndex = documentIndex;
    }

    public SearchEngineBuilder(InvertedIndex invertedIndex, Tokenizer tokenizer,
                               QueryIndex queryIndex, DocumentIndex documentIndex, double threshold, QueryExpansionWord2Vec w2v) {
        this.invertedIndex = invertedIndex;
        this.tokenizer = tokenizer;
        this.queryIndex = queryIndex;
        this.threshold = threshold;
        this.documentIndex = documentIndex;
        this.w2v = w2v;
        searchEngine.setW2vTrue();
    }

    /**
     * Creates an instance of a {@link SearchEngine} object.
     */
    public void createSearchEngine(){
        searchEngine = new SearchEngine();
    }

    /**
     *
     * @return An instance of a {@link SearchEngine} object.
     */
    public SearchEngine getSearchEngine() {
        return searchEngine;
    }

    /**
     * Configures an instance of {@link SearchEngine} with its different dependencies.
     * @return The configured {@link SearchEngine} object.
     */
    public SearchEngine constructSearEngine(){
        createSearchEngine();
        buildInvertedIndex();
        buildQueryIndex();
        buildDocumentIndex();
        buildNormalizer();
        buildQueryProcessor();
        buildQueryReader();
        buildQueryResultWriter();
        buildQueryTokenizer();
        buildThreshold();
        buildRelevanceQueryUpdater();
        return searchEngine;
    }

    /**
     * The purpose of this function is to configure a {@link InvertedIndex} for the {@link SearchEngine} instance
     * that is being built.
     */
    public abstract void buildInvertedIndex();

    /**
     * The purpose of this function is to configure a {@link QueryReader} for the {@link SearchEngine} instance
     * that is being built.
     */
    public abstract void buildQueryReader();

    /**
     * The purpose of this function is to configure a {@link QueryProcessor} for the {@link SearchEngine} instance
     * that is being built.
     */
    public abstract void buildQueryProcessor();

    /**
     * The purpose of this function is to configure a {@link QueryResultWriter} for the {@link SearchEngine} instance
     * that is being built.
     */
    public abstract void buildQueryResultWriter();

    /**
     * The purpose of this function is to configure a {@link Tokenizer} for the {@link SearchEngine} instance
     * that is being built.
     */
    public abstract void buildQueryTokenizer();

    /**
     * The purpose of this function is to configure a {@link QueryIndex} for the {@link SearchEngine} instance
     * that is being built.
     */
    public abstract void buildQueryIndex();

    /**
     * The purpose of this function is to configure a {@link Normalizer} for the {@link SearchEngine} instance
     * that is being built.
     */
    public abstract void buildNormalizer();

    /**
     * The purpose of this function is to configure a threshold for the results of the {@link SearchEngine} instance
     * that is being built.
     */
    public abstract void buildThreshold();

    public abstract void buildDocumentIndex();

    public abstract void buildRelevanceQueryUpdater();

}
