package src.java.searchengine;

import src.java.index.InvertedIndex;
import src.java.query.ColumnResultWriter;
import src.java.query.QueryLoader;
import src.java.tokenizer.Tokenizer;

/**
 * An implementation of {@link SearchEngineBuilder} creates a {@link SearchEngine} with a {@link FrequencyOfQueryWordsProcessor}.
 */
public class FreqQueryWordsBuilder extends SearchEngineBuilder {

    /**
     *{@inheritDoc}
     */
    public FreqQueryWordsBuilder(InvertedIndex invertedIndex, Tokenizer tokenizer, double threshold) {
        super(invertedIndex, tokenizer, null, null,threshold);
    }

    /**
     *{@inheritDoc}
     */
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
     * Configures the searchEngine's {@link QueryProcessor} with a {@link FrequencyOfQueryWordsProcessor}
     */
    @Override
    public void buildQueryProcessor() {
        searchEngine.setQueryProcessor(new FrequencyOfQueryWordsProcessor());
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

    @Override
    public void buildDocumentIndex() {

    }

    @Override
    public void buildRelevanceQueryUpdater() {

    }
}
