package src.java.query;

import src.java.index.InvertedIndex;
import src.java.tokenizer.Tokenizer;

/**
 * A derived class from {@link SearchEngineBuilder}, builds a {@link SearchEngine} with a {@link WordsInDocumentProcessor}.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 10-16-2017
 */
public class WordsInDocumentSearchEngineBuilder extends SearchEngineBuilder{

    /**
     * {@inheritDoc}
     */
    public WordsInDocumentSearchEngineBuilder(InvertedIndex index, Tokenizer tokenizer) {
        super(index, tokenizer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildIndex() {
        searchEngine.setInvertedIndex(index);
    }

    /**
     * Configures the searchEngine's {@link QueryReader} with a {@link QueryLoader}
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
