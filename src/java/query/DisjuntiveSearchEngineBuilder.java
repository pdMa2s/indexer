package src.java.query;

import src.java.index.Index;
import src.java.tokenizer.Tokenizer;

/**
 * A derived class from {@link SearchEngineBuilder}, builds a {@link SearchEngine} with a {@link DisjuntiveQueryProcessor}.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 10-16-2017
 */
public class DisjuntiveSearchEngineBuilder extends SearchEngineBuilder{

    /**
     * {@inheritDoc}
     */
    public DisjuntiveSearchEngineBuilder(Index index, Tokenizer tokenizer) {
        super(index, tokenizer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildIndex() {
        searchEngine.setIndex(index);
    }

    /**
     * Configures the searchEngine's {@link QueryReader} with a {@link QueryLoader}
     */
    @Override
    public void buildQueryReader() {
        searchEngine.setQueryReader(new QueryLoader());
    }


    /**
     * Configures the searchEngine's {@link QueryProcessor} with a {@link DisjuntiveQueryProcessor}
     */
    @Override
    public void buildQueryProcessor() {
        searchEngine.setQueryProcessor(new DisjuntiveQueryProcessor());
    }

    /**
     * Configures the searchEngine's {@link QueryResultWriter} with a {@link ColumnResultWriter}
     */
    @Override
    public void buildQueryResultWriter() {
        searchEngine.setQueryResultWriter(new ColumnResultWriter());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildQueryTokenizer() {
        searchEngine.setTokenizer(tokenizer);
    }
}
