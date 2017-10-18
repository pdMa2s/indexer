package src.java.query;

import src.java.index.Index;
import src.java.tokenizer.Tokenizer;

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
        return searchEngine;
    }
    public abstract void buildIndex();

    public abstract void buildQueryReader();

    public abstract void buildQueryProcessor();

    public abstract void buildQueryResultWriter();
}
