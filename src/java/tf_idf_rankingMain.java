package src.java;

import src.java.index.CSVIndexReader;
import src.java.index.Index;
import src.java.index.IndexReader;
import src.java.index.Normalizer;

import java.io.File;

public class tf_idf_rankingMain {
    public static void main(String[] args){
        checkParameterLength(args);
        File indexFile = new File(args[0]);
        File queryFile = new File(args[1]);

        long startTime = System.currentTimeMillis();
        Index index;
        Normalizer nm = new Normalizer();
        IndexReader idr = new CSVIndexReader();
        index = idr.parseToIndexWithNormalization(indexFile, nm);
        nm.normalize();

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
