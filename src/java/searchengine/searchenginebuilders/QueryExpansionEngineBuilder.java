package src.java.searchengine.searchenginebuilders;

import src.java.index.InvertedIndex;
import src.java.normalizer.Normalizer;
import src.java.query.ColumnResultWriter;
import src.java.query.DocumentIndex;
import src.java.query.QueryIndex;
import src.java.query.QueryLoader;
import src.java.query.queryExpansion.Word2VecQueryExpansionUpdater;
import src.java.corpus.tokenizer.Tokenizer;
import src.java.query.queryExpansion.QueryExpansionWord2Vec;
import src.java.searchengine.searchenginebuilders.SearchEngineBuilder;
import src.java.searchengine.queryprocessors.NormalizedProcessor;
import src.java.searchengine.queryprocessors.QueryProcessor;

import java.io.IOException;

import static src.java.constants.Constants.FULLCONTENTFILE;
import static src.java.constants.Constants.WORD2VECMODEL;

public class QueryExpansionEngineBuilder extends SearchEngineBuilder {

    public QueryExpansionEngineBuilder(InvertedIndex invertedIndex, Tokenizer tokenizer,
                                       QueryIndex queryIndex, DocumentIndex documentIndex,
                                       double threshold){
        super(invertedIndex, tokenizer, queryIndex, documentIndex, threshold);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildDocumentIndex() {
        searchEngine.setDocumentIndex(documentIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildQueryUpdater() {
        QueryExpansionWord2Vec w2v = null;
        try {
            w2v = new QueryExpansionWord2Vec(FULLCONTENTFILE);
            w2v.persistModel(WORD2VECMODEL);
        } catch (IOException e) {
            printError(4, "ERROR full corpus content file not found");
        }
        searchEngine.setUpdater(new Word2VecQueryExpansionUpdater(w2v));
        searchEngine.expandQueries();
    }
    private void printError(int errorCode, String message){
        System.err.println(message);
        System.exit(errorCode);
    }
}
