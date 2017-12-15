package src.java.searchengine;

import src.java.index.InvertedIndex;
import src.java.normalizer.Normalizer;
import src.java.query.ColumnResultWriter;
import src.java.query.DocumentIndex;
import src.java.query.QueryIndex;
import src.java.query.QueryLoader;
import src.java.query.relevancefeedback.GoldStandardFeedBack;
import src.java.query.relevancefeedback.RelevanceIndex;
import src.java.corpus.tokenizer.Tokenizer;

public class ExplicitFeedBackEngineBuilder extends SearchEngineBuilder{
    private RelevanceIndex relevanceIndex;
    /**
     *{@inheritDoc}
     */
    public ExplicitFeedBackEngineBuilder(InvertedIndex invertedIndex, Tokenizer tokenizer, QueryIndex queryIndex,
                                         DocumentIndex documentIndex, double threshold, RelevanceIndex relevanceIndex) {
        super(invertedIndex, tokenizer, queryIndex, documentIndex,threshold);
        this.relevanceIndex = relevanceIndex;
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
        searchEngine.setUpdater(new GoldStandardFeedBack(relevanceIndex));
    }
}
