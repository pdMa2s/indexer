package src.java;

import src.java.evaluation.Evaluator;
import src.java.index.CSVIndexReader;
import src.java.index.InvertedIndex;
import src.java.index.IndexReader;
import src.java.normalizer.Normalizer;
import src.java.query.QueryIndex;
import src.java.searchengine.SearchEngine;
import src.java.searchengine.SearchEngineBuilder;
import src.java.searchengine.NormalizedSearchEngineBuilder;
import java.io.File;

public class RankingMain {
    public static void main(String[] args){
        checkParameterLength(args);
        File indexFile = new File(args[0]);
        File queryFile = new File(args[1]);
        File relevanceFile = new File(args[2]);

        String rankingResultsFile = "NORMALIZED_RANKING";

        long startTime = System.currentTimeMillis();
        InvertedIndex invertedIndex = new InvertedIndex();
        QueryIndex queryIndex;
        Normalizer nm = new Normalizer();
        //-----------------------------------------------------
        Evaluator evaluator = new Evaluator(relevanceFile);
        evaluator.parseRelevanceFile();
        //-----------------------------------------------------
        IndexReader idr = new CSVIndexReader();
        idr.parseInvertedIndex(indexFile,invertedIndex);
        int corpusSize = idr.getCorpusSize();
        queryIndex = new QueryIndex(corpusSize);

        SearchEngineBuilder searchEngineBuilder = new NormalizedSearchEngineBuilder(invertedIndex,
                idr.getTokenizer(), queryIndex,nm);
        SearchEngine searchEngine = searchEngineBuilder.constructSearEngine();

        searchEngine.processQueries(queryFile, invertedIndex);
        searchEngine.saveResults(rankingResultsFile);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Querying time: "+elapsedTime+"ms");
    }

    private static void checkParameterLength(String[] args) {
        if (args.length != 3) {
            printUSAGE();
        }
    }

    private static void printUSAGE(){
        System.err.println("USAGE: \n"+
                "java -cp ../../libstemmer_java/java/libstemmer.jar: src.java.rankingMain <corpusDirectory> <indexFile>(Optional)\n"+
                "<indexFile> - The file where the index is built \n"+
                "<QueryFile> - The File with the queries to be evaluated \n"+
                "<RelevanceFile> - The file with the relevance for each query and doc\n");
        System.exit(1);
    }
}
