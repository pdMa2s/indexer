package src.java.query;

import src.java.index.Index;
import src.java.tokenizer.Tokenizer;
import java.io.File;
import java.util.List;

/**
 *
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 10-16-2017
 */
public class SearchEngine {

    private Index index;
    private QueryReader queryReader;
    private QueryProcessor queryProcessor;
    private QueryResultWriter queryResultWriter;
    private List<Query> queries;
    private Tokenizer tokenizer;

    public List<Query> searchQueryWordsInDocument(File queryFile){
        queries = queryReader.loadQueries(queryFile, tokenizer);
        queryProcessor.queryWordsInDocument(index, queries);
        return queries;
    }

    public List<Query> searchFrequencyOfQueryWordsInDocument(File queryFile){
        queries = queryReader.loadQueries(queryFile, tokenizer);
        queryProcessor.frequencyOfQueryWordsInDocument(index, queries);
        return queries;
    }


    public void saveResults(String resultFileName){
        queryResultWriter.saveQueryResultsToFile(resultFileName, queries);
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public void setQueryReader(QueryReader queryReader) {
        this.queryReader = queryReader;
    }

    public void setQueryProcessor(QueryProcessor queryProcessor) {
        this.queryProcessor = queryProcessor;
    }

    public void setQueryResultWriter(QueryResultWriter queryResultWriter) {
        this.queryResultWriter = queryResultWriter;
    }

    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }
}
