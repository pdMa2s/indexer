package src.java.query;

import src.java.index.Index;

import java.io.File;
import java.util.List;

public class SearchEngine {

    private Index index;
    private QueryReader queryReader;
    private QueryProcessor queryProcessor;
    private QueryResultWriter queryResultWriter;
    private List<Query> queries;

    public List<Query> searchQueryWordsInDocument(File queryFile){
        queries = queryReader.loadQueries(queryFile);
        queryProcessor.queryWordsInDocument(index, queries);
        return queries;
    }

    public List<Query> searchFrequencyOfQueryWordsInDocument(File queryFile){
        queries = queryReader.loadQueries(queryFile);
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
}
