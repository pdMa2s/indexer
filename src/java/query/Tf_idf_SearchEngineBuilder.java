package src.java.query;

import src.java.index.InvertedIndex;
import src.java.tokenizer.Tokenizer;

public class Tf_idf_SearchEngineBuilder extends SearchEngineBuilder {

    public Tf_idf_SearchEngineBuilder(InvertedIndex index, Tokenizer tokenizer) {
        super(index, tokenizer);
    }


    @Override
    public void buildIndex() {
        searchEngine.setIndex(index);
    }

    @Override
    public void buildQueryReader() {
        searchEngine.setQueryReader(new QueryLoader());
    }

    @Override
    public void buildQueryProcessor() {
        searchEngine.setQueryProcessor(new DisjuntiveQueryProcessor());
    }

    @Override
    public void buildQueryResultWriter() {
        searchEngine.setQueryResultWriter(new ColumnResultWriter());
    }

    @Override
    public void buildQueryTokenizer() {
        searchEngine.setTokenizer(tokenizer);
    }
}
