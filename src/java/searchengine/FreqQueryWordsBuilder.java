package src.java.searchengine;

import src.java.index.InvertedIndex;
import src.java.query.ColumnResultWriter;
import src.java.query.QueryIndex;
import src.java.query.QueryLoader;
import src.java.tokenizer.Tokenizer;

public class FreqQueryWordsBuilder extends SearchEngineBuilder {
    
    public FreqQueryWordsBuilder(InvertedIndex invertedIndex, Tokenizer tokenizer, QueryIndex queryIndex) {
        super(invertedIndex, tokenizer, queryIndex);
    }

    @Override
    public void buildInvertedIndex() {
        searchEngine.setInvertedIndex(invertedIndex);
    }

    @Override
    public void buildQueryReader() {
        searchEngine.setQueryReader(new QueryLoader());
    }

    @Override
    public void buildQueryProcessor() {
        searchEngine.setQueryProcessor(new FrequencyOfQueryWordsProcessor());
    }

    @Override
    public void buildQueryResultWriter() {
        searchEngine.setQueryResultWriter(new ColumnResultWriter());
    }

    @Override
    public void buildQueryTokenizer() {
        searchEngine.setTokenizer(tokenizer);
    }

    @Override
    public void buildQueryIndex() {
    }

    @Override
    public void buildNormalizer() {
    }
}
