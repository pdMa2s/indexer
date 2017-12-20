package src.java.searchengine.searchenginebuilders;

import src.java.index.InvertedIndex;
import src.java.normalizer.Normalizer;
import src.java.query.ColumnResultWriter;
import src.java.query.DocumentIndex;
import src.java.query.QueryIndex;
import src.java.query.QueryLoader;
import src.java.query.relevancefeedback.*;
import src.java.corpus.tokenizer.Tokenizer;
import src.java.searchengine.queryprocessors.NormalizedProcessor;
import src.java.searchengine.queryprocessors.QueryProcessor;
import src.java.searchengine.SearchEngine;

/**
 * A extension of {@link SearchEngineBuilder} that builds a {@link SearchEngine} object that uses implicit
 */
public class ImplicitFeedBackEngineBuilder extends SearchEngineBuilder {
    /**
     * {@inheritDoc}
     */
    public ImplicitFeedBackEngineBuilder(InvertedIndex invertedIndex, Tokenizer tokenizer,
                                         QueryIndex queryIndex, DocumentIndex documentIndex, double threshold) {
        super(invertedIndex, tokenizer, queryIndex, documentIndex, threshold);
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
     * Configures the searchEngine's {@link QueryProcessor} with a {@link NormalizedProcessor}
     */
    @Override
    public void buildQueryProcessor() {
        searchEngine.setQueryProcessor(new NormalizedProcessor(queryIndex));
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
        searchEngine.setQueryIndex(queryIndex);
    }
    /**
     * Configures the searchEngine's with a {@link Normalizer}
     */
    @Override
    public void buildNormalizer() {
        searchEngine.setNormalizer(new Normalizer());
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
        searchEngine.setDocumentIndex(documentIndex);
    }

    @Override
    public void buildQueryUpdater() {
        searchEngine.setUpdater(new ImplicitFeedBack(queryIndex, documentIndex));
    }
}
