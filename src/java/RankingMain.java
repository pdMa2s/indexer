package src.java;

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

        String rankingResultsFile = "NORMALIZED_RANKING";

        long startTime = System.currentTimeMillis();
        InvertedIndex invertedIndex = new InvertedIndex();
        QueryIndex queryIndex;
        Normalizer nm = new Normalizer();
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
        if (args.length != 2) {
            printUSAGE();
        }
    }

    private static void printUSAGE(){
        System.err.println("USAGE: \n"+
                "java -cp ../../libstemmer_java/java/libstemmer.jar: src.java.rankingMain <corpusDirectory> <indexFile>(Optional)\n"+
                "<corpusDirectory> - The directory where the corpus is located\n"+
                "<indexFile> - The optional parameter lets you pick the name of the file where the invertedIndex will be saved to\n");
        System.exit(1);
    }
}
