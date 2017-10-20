package src.java.query;

import src.java.index.Index;
import src.java.tokenizer.Tokenizer;

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
    protected Index index;
    protected Tokenizer tokenizer;

    /**
     * A super constructor for all the class that will derive this class.
     * @param index An {@link Index} object to be provided to the searchEngine.
     * @param tokenizer A {@link Tokenizer} to be provided to the {@link QueryReader} object.
     */
    public SearchEngineBuilder(Index index, Tokenizer tokenizer) {
        this.index = index;
        this.tokenizer = tokenizer;
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
        buildIndex();
        buildQueryProcessor();
        buildQueryReader();
        buildQueryResultWriter();
        buildQueryTokenizer();
        return searchEngine;
    }

    /**
     * The purpose of this function is to configure a {@link Index} for the {@link SearchEngine} instance
     * that is being built.
     */
    public abstract void buildIndex();

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
}
