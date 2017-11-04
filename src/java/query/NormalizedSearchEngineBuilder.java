package src.java.query;

import src.java.index.DocumentIndex;
import src.java.index.InvertedIndex;
import src.java.normalizer.Normalizer;
import src.java.tokenizer.Tokenizer;

public class NormalizedSearchEngineBuilder extends SearchEngineBuilder {

    public NormalizedSearchEngineBuilder(InvertedIndex index, Tokenizer tokenizer,
                                         QueryIndex queryIndex, DocumentIndex documentIndex,
                                         Normalizer normalizer) {
        super(index, tokenizer, queryIndex, documentIndex, normalizer);
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
        searchEngine.setQueryProcessor(new NormalizedProcessor(documentIndex, queryIndex));
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
        searchEngine.setQueryIndex(queryIndex);
    }

    @Override
    public void buildDocumentIndex() {
        searchEngine.setDocumentIndex(documentIndex);
    }

    @Override
    public void buildNormalizer() {
        searchEngine.setNormalizer(normalizer);
    }
}
