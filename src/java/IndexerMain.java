package src.java;

import src.java.index.CSVIndexWriter;
import src.java.index.InvertedIndex;
import src.java.index.IndexWriter;
import src.java.indexer.CTStemmingIndexerBuilder;
import src.java.indexer.Indexer;
import src.java.indexer.IndexerBuilder;

public class IndexerMain {

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        checkParameterLength(args);
        String FILETOSAVEINDEX = chooseFileToSaveIndex(args);
        String dirName = args[0];
        Indexer indexer;
        InvertedIndex index;
        IndexWriter writer = new CSVIndexWriter();
        IndexerBuilder builder = new CTStemmingIndexerBuilder(dirName);
        indexer = builder.constructIndexer();
        index = indexer.createIndex();
        index.applyTF();
        writer.saveIndexToFile(FILETOSAVEINDEX, index, builder.getTokenizerType(), indexer.getCorpusSize());

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("Indexing time: "+elapsedTime+"ms");
        //System.out.println(invertedIndex);
        System.out.println("Vocabulary size: " +index.vocabularySize());
        System.out.println("Ten First terms in document 1 with alphabetical order: " + index.getTop10TermsOccurrences(1));
        System.out.println("Ten terms with the higher doc frequency: " + index.getTopFreqTerms());

    }


    private static void checkParameterLength(String[] args){
        if(args.length < 1 || args.length > 2){
            printUSAGE();
        }

    }
    private static String chooseFileToSaveIndex(String[] args){
        if(args.length == 2)
            return args[1];
        return "index.csv";
    }

    private static void printUSAGE(){
        System.err.println("USAGE: \n"+
                "java -cp ../../libstemmer_java/java/libstemmer.jar: src.java.IndexerMain <corpusDirectory> <indexFile>(Optional)\n"+
                "<corpusDirectory> - The directory where the corpus is located\n"+
                "<indexFile> - The optional parameter lets you pick the name of the file where the invertedIndex will be saved to\n");
        System.exit(1);
    }
}
