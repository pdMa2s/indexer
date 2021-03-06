package src.java.searchengine.searchenginebuilders;

import src.java.index.InvertedIndex;
import src.java.query.ColumnResultWriter;
import src.java.query.QueryLoader;
import src.java.corpus.tokenizer.Tokenizer;
import src.java.searchengine.queryprocessors.QueryProcessor;
import src.java.searchengine.SearchEngine;
import src.java.searchengine.queryprocessors.WordsInDocumentProcessor;

/**
 * An implementation of {@link SearchEngineBuilder} creates a {@link SearchEngine} with a {@link WordsInDocumentProcessor}.
 */
public class WordsInDocBuilder extends SearchEngineBuilder {

    /**
     *{@inheritDoc}
     */
    public WordsInDocBuilder(InvertedIndex invertedIndex, Tokenizer tokenizer, double threshold) {
        super(invertedIndex, tokenizer, null, null,threshold);
    }

    @Override
    public void buildInvertedIndex() {
        searchEngine.setInvertedIndex(invertedIndex);
    }

    /**
     * Configures the searchEngine's {@link src.java.query.QueryReader} with a {@link QueryLoader}
     */
    @Override
    public void buildQueryReader() {
        searchEngine.setQueryReader(new QueryLoader());
    }

    /**
     * Configures the searchEngine's {@link QueryProcessor} with a {@link WordsInDocumentProcessor}
     */
    @Override
    public void buildQueryProcessor() {
        searchEngine.setQueryProcessor(new WordsInDocumentProcessor());
    }

    /**
     * Configures the searchEngine's {@link src.java.query.QueryResultWriter} with a {@link ColumnResultWriter}
     */
    @Override
    public void buildQueryResultWriter() {
        searchEngine.setQueryResultWriter(new ColumnResultWriter());
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public void buildQueryTokenizer() {
        searchEngine.setTokenizer(tokenizer);
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public void buildQueryIndex() {
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public void buildNormalizer() {
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public void buildThreshold() {
        searchEngine.setThreshold(threshold);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void buildDocumentIndex() { }

    /**
     *{@inheritDoc}
     */
    @Override
    public void buildQueryUpdater() {

    }
}
