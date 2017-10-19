package src.java.query;

import src.java.index.Index;
import src.java.tokenizer.Tokenizer;

public class DisjuntiveSearchEngineBuilder extends SearchEngineBuilder{

    public DisjuntiveSearchEngineBuilder(Index index, Tokenizer tokenizer) {
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
