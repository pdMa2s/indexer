package src.java;

import src.java.index.CSVIndexReader;
import src.java.index.Index;
import src.java.index.IndexReader;
import src.java.query.*;

import java.io.File;

public class SearcherMain {
    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        checkParameterLength(args);
        File indexFile = new File(args[0]);
        File queryFile = new File(args[1]);

        String freqWordsFileName = "resultsFrequencyInDoc";
        String queryWordsInDocFileName = "resultsQueryWordsInDoc";

        Index index;
        IndexReader idr = new CSVIndexReader();
        index = idr.parseToIndex(indexFile);
        SearchEngineBuilder searchEngineBuilder = new DisjuntiveSearchEngineBuilder(index,idr.getTokenizer());
        SearchEngine searchEngine = searchEngineBuilder.constructSearEngine();

        searchEngine.searchFrequencyOfQueryWordsInDocument(queryFile);
        searchEngine.saveResults(freqWordsFileName);

        searchEngine.searchQueryWordsInDocument(queryFile);
        searchEngine.saveResults(queryWordsInDocFileName);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Query time: "+elapsedTime+"ms");
    }


    private static void printUSAGE(){
        System.err.println("USAGE: java SearcherMain <indexFile> <queryFile> <operation>\n"+
                            "<indexFile> - The path to the file of the index on disk\n"+
                            "<queryFile> - The path to the file of the queries\n");


        System.exit(1);
    }
    private static void checkParameterLength(String[] args){
        if(args.length != 2){
            printUSAGE();
        }

    }


}
