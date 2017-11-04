package src.java;

import src.java.index.CSVIndexReader;
import src.java.index.DocumentIndex;
import src.java.index.InvertedIndex;
import src.java.index.IndexReader;
import src.java.normalizer.Normalizer;
import src.java.query.SearchEngine;
import src.java.query.SearchEngineBuilder;
import src.java.query.Tf_idf_SearchEngineBuilder;

import java.io.File;

public class tf_idf_rankingMain {
    public static void main(String[] args){
        checkParameterLength(args);
        File indexFile = new File(args[0]);
        File queryFile = new File(args[1]);

        String rankingResultsFile = "NORMALIZED_RANKING";

        long startTime = System.currentTimeMillis();
        InvertedIndex invertedIndex = new InvertedIndex();
        DocumentIndex documentIndex = new DocumentIndex();
        Normalizer nm = new Normalizer(documentIndex);
        IndexReader idr = new CSVIndexReader();
        idr.parseDocumentAndInvertedIndexes(indexFile,invertedIndex,  documentIndex);
        nm.normalize();
        SearchEngineBuilder searchEngineBuilder = new Tf_idf_SearchEngineBuilder(invertedIndex, idr.getTokenizer());
        SearchEngine searchEngine = searchEngineBuilder.constructSearEngine();

        searchEngine.processQueries(queryFile);
        nm.normalizeResults();
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
                "<indexFile> - The optional parameter lets you pick the name of the file where the index will be saved to\n");
        System.exit(1);
    }
}
