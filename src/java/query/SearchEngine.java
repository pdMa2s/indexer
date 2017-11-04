package src.java.query;

import src.java.index.DocumentIndex;
import src.java.index.InvertedIndex;
import src.java.normalizer.Normalizer;
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


    public List<Query> processQueries(File queryFile){
        queries = queryReader.loadQueries(queryFile, tokenizer);
        queryIndex.addQueries(queries);
        queryIndex.applyTFAndIDFtoQueries(invertedIndex);
        normalizer.normalize(documentIndex);
        normalizer.normalize(queryIndex);
        queryProcessor.processQueries( queries);
        return queries;
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

    public void setQueryIndex(QueryIndex queryIndex) {
        this.queryIndex = queryIndex;
    }

    public void setDocumentIndex(DocumentIndex documentIndex) {
        this.documentIndex = documentIndex;
    }

    public void setNormalizer(Normalizer normalizer) {
        this.normalizer = normalizer;
    }
}
