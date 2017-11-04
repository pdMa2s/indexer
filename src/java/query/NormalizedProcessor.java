package src.java.query;

import src.java.index.InvertedIndex;
import src.java.normalizer.Normalizer;

import java.util.List;

public class NormalizedProcessor implements QueryProcessor{
    private Normalizer normalizer;

    public NormalizedProcessor(Normalizer normalizer) {
        this.normalizer = normalizer;
    }

    @Override
    public void processQueries(InvertedIndex index, List<Query> queries) {
        normalizedQueryWordsInDocument(index, queries, normalizer);
    }
    public void normalizedQueryWordsInDocument(InvertedIndex index, List<Query> queries, Normalizer nm){

        nm.normalizeQueryVector();
    }

}
