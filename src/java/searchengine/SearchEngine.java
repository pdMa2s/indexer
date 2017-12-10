package src.java.searchengine;

import src.java.index.InvertedIndex;
import src.java.normalizer.Normalizer;
import src.java.query.*;
import src.java.relevancefeedback.RelevanceQueryUpdater;
import src.java.tokenizer.Tokenizer;
import java.io.File;
import java.util.List;

/**
 * This class is responsible for loading the queries, process them and saving them into a file.
 * This class aggregates many other entities such as, a {@link QueryReader}, a {@link QueryProcessor} and
 * more, it can have several different representations depending on what entities are being used.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 10-16-2017
 */
public class SearchEngine {

    private InvertedIndex invertedIndex;
    private QueryIndex queryIndex;
    private DocumentIndex documentIndex;
    private QueryReader queryReader;
    private QueryProcessor queryProcessor;
    private QueryResultWriter queryResultWriter;
    private List<Query> queries;
    private Tokenizer tokenizer;
    private Normalizer normalizer;
    private double threshold;
    private RelevanceQueryUpdater updater;

    /**
     * This method has the same purpose the {@link QueryProcessor#processQueries(List, InvertedIndex, double)}
     * @param queryFile The file where the queries are contained
     * @param idx The {@link InvertedIndex} with the terms of the corpus
     * @return A {@link List} of {@link Query} objects that contains the results for them selves
     */
    public List<Query> processQueries(File queryFile, InvertedIndex idx){
        if(normalizer == null)
            return processFrequencyQueries(queryFile, idx);
        else
            return processNormalizedQueries(queryFile, idx);
    }

    private List<Query> processFrequencyQueries(File queryFile, InvertedIndex idx){
        queries = queryReader.loadQueries(queryFile, tokenizer);
        queryProcessor.processQueries(queries, idx, threshold);
        return queries;
    }
    private List<Query> processNormalizedQueries(File queryFile, InvertedIndex idx){
        queries = queryReader.loadQueries(queryFile, tokenizer);
        queryIndex.addQueries(queries);
        queryIndex.applyTFAndIDFtoQueries(invertedIndex);
        normalizer.normalize(queryIndex);
        queryProcessor.processQueries(queries, idx, threshold);
        if(updater != null){
            updater.updateQueries(queryIndex, documentIndex);
            queryProcessor.processQueries(queries, idx, threshold);

        }
        return queries;
    }

    public void setDocumentIndex(DocumentIndex documentIndex) {
        this.documentIndex = documentIndex;
    }

    public void setUpdater(RelevanceQueryUpdater updater) {
        this.updater = updater;
    }

    /**
     * This method has the same objective as {@link QueryResultWriter#saveQueryResultsToFile(String, List)}.
     * @param resultFileName The name of the file where the results will be stored.
     */
    public void saveResults(String resultFileName){
        queryResultWriter.saveQueryResultsToFile(resultFileName, queries);
    }

    /**
     *
     * @param invertedIndex The where the search will take place.
     */
    public void setInvertedIndex(InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    /**
     *
     * @param queryReader The {@link QueryReader} that is going to be used to read the queries from the a file.
     */
    public void setQueryReader(QueryReader queryReader) {
        this.queryReader = queryReader;
    }

    /**
     *
     * @param queryProcessor The {@link QueryProcessor} to be used to obtain results from the invertedIndex.
     */
    public void setQueryProcessor(QueryProcessor queryProcessor) {
        this.queryProcessor = queryProcessor;
    }

    /**
     *
     * @param queryResultWriter A {@link QueryResultWriter} to write the results of the queries to a file.
     */
    public void setQueryResultWriter(QueryResultWriter queryResultWriter) {
        this.queryResultWriter = queryResultWriter;
    }

    /**
     *
     * @param tokenizer The {@link Tokenizer} to process the queries when they are being read from the file.
     */
    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     *
     * @param queryIndex A {@link QueryIndex} with the queries to be processed
     */
    public void setQueryIndex(QueryIndex queryIndex) {
        this.queryIndex = queryIndex;
    }

    /**
     *
     * @param normalizer A {@link Normalizer} used to normalize the queries
     */
    public void setNormalizer(Normalizer normalizer) {
        this.normalizer = normalizer;
    }

    /**
     *
     * @param threshold The minimum values of the result scores
     */
    public void setThreshold(double threshold) {
        if(this.threshold < 0){
            System.err.println("Threshold cant be less than zero");
            System.exit(1);
        }
        this.threshold = threshold;
    }
}
