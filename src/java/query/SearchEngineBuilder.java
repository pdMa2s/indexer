package src.java.query;

import src.java.index.Index;
import src.java.tokenizer.Tokenizer;

/**
 * This abstract class serves as a starting point for the an implementation of the builder pattern with the
 * purpose of creating an instance of a {@link SearchEngine}.
 *
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 10-16-2017
 */
public abstract class SearchEngineBuilder {
    protected SearchEngine searchEngine;
    protected Index index;
    protected Tokenizer tokenizer;

    public SearchEngineBuilder(Index index, Tokenizer tokenizer) {
        this.index = index;
        this.tokenizer = tokenizer;
    }

    public void createSearchEngine(){
        searchEngine = new SearchEngine();
    }

    public SearchEngine getSearchEngine() {
        return searchEngine;
    }

    public SearchEngine constructSearEngine(){
        createSearchEngine();
        buildIndex();
        buildQueryProcessor();
        buildQueryReader();
        buildQueryResultWriter();
        buildQueryTokenizer();
        return searchEngine;
    }
    public abstract void buildIndex();

    public abstract void buildQueryReader();

    public abstract void buildQueryProcessor();

    public abstract void buildQueryResultWriter();

    public abstract void buildQueryTokenizer();
}
