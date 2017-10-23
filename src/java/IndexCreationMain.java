package src.java;
import src.java.index.CSVIndexWriter;
import src.java.index.Index;
import src.java.index.IndexWriter;
import src.java.indexer.*;

import static src.java.constants.Constants.COMPLEXTOKENIZER;
import static src.java.constants.Constants.COMPLEXTOKENIZERSTEMMING;
import static src.java.constants.Constants.SIMPLETOKENIZER;

/**
 * This serves as the main class to create an index based on a corpus of documents.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 */
public class IndexCreationMain {
    /**
     * This main method creates an index, saves it on disk, measures the time that took to do it and prints
     * some results.
     * Execute the program with no arguments to see the usage which contains more information.
     * @param args [corpusDirectory] [tokenizerType] [indexFile]
     */
    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        checkParameterLength(args);
        String FILETOSAVEINDEX = chooseFileToSaveIndex(args);
        String dirName = args[0];
        Indexer indexer;
        Index index;
        IndexWriter writer = new CSVIndexWriter();
        IndexerBuilder builder = parseTokenizerType( args[1],dirName);
        indexer = builder.constructIndexer();
        index = indexer.createIndex();

        writer.saveIndexToFile(FILETOSAVEINDEX, index, builder.getTokenizerType());

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("Indexing time: "+elapsedTime+"ms");
        //System.out.println(index);
        System.out.println("Vocabulary size: " +index.vocabularySize());
        System.out.println("Ten First terms in document 1 with alphabetical order: " + index.getTop10TermsOccurrences(1));
        System.out.println("Ten terms with the higher doc frequency: " + index.getTopFreqTerms());



    }

    private static void checkParameterLength(String[] args){
        if(args.length < 2 || args.length > 3){
            printUSAGE();
        }

    }
    private static String chooseFileToSaveIndex(String[] args){
        if(args.length == 3)
            return args[2];
        return "index.csv";
    }

    private static IndexerBuilder parseTokenizerType(String arg, String dirName){
        switch (arg){
            case SIMPLETOKENIZER:
                return new SimpleTokenizerIndexerBuilder(dirName);
            case COMPLEXTOKENIZER:
                return new ComplexTokenizerIndexerBuilder(dirName);
            case COMPLEXTOKENIZERSTEMMING:
                return new CTStemmingIndexerBuilder(dirName);
            default:
                System.err.println("ERROR: Unknown Tokenizer type");
                printUSAGE();

        }
        return null;
    }
    private static void printUSAGE(){
        System.err.println("USAGE: \n"+
                            "java -cp ../../libstemmer_java/java/libstemmer.jar: src.java.IndexCreationMain <corpusDirectory> <tokenizerType> <indexFile>(Optional)\n"+
                            "<corpusDirectory> - The directory where the corpus is located\n"+
                            "<tokenizerType> - The of tokenizer you want to use\n"+
                            "tokenizer types:\n"+
                            SIMPLETOKENIZER +" - Simple tokenizer.tokenizer\n"+
                            COMPLEXTOKENIZER+" - Complex tokenizer.tokenizer\n"+
                            COMPLEXTOKENIZERSTEMMING+ " - Complex tokenizer.tokenizer with Stemming\n"+
                            "<indexFile> - The optional parameter lets you pick the name of the file where the index will be saved to\n");
        System.exit(1);
    }
}
