import indexer.*;

/**
 * @author Pedro Matos - 73941
 */
public class Main {
    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        checkParameterLength(args);
        String FILETOSAVEINDEX = chooseFileToSaveIndex(args);
        String dirName = args[0];
        Indexer indexer;
        Index index;
        IndexerBuilder builder = parseTokenizerType( args[1],dirName );
        indexer = builder.constructIndexer();
        index = indexer.createIndex();

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("Indexing time: "+elapsedTime+"ms");
        //System.out.println(index);
        System.out.println("Vocabulary size: " +index.vocabularySize());
        System.out.println("Ten First terms in document 1 with alphabetical order: " + index.getTop10TermsOccurrences(1));
        System.out.println("Ten terms with the higher doc frequency: " + index.getTopFreqTerms());

        index.saveIndexToFile(FILETOSAVEINDEX);


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
            case "st":
                return new SimpleTokenizerIndexerBuilder(dirName);
            case "ct":
                return new ComplexTokenizerIndexerBuilder(dirName);
            case "cts":
                return new CTStemmingIndexerBuilder(dirName);
            default:
                System.err.println("ERROR: Unknown Tokenizer type");
                printUSAGE();

        }
        return null;

    }
    private static void printUSAGE(){
        System.err.println("USAGE: java Main <corpusDirectory> <tokenizerType> <indexFile>(Optional)\n"+
                            "<corpusDirectory> - The directory where the corpus is located\n"+
                            "<tokenizerType> - The of tokenizer you want to use\n"+
                            "tokenizer types:\n"+
                            "st - Simple tokenizer.tokenizer\n"+
                            "ct - Complex tokenizer.tokenizer\n"+
                            "cts - Complex tokenizer.tokenizer with Stemming\n"+
                            "<indexFile> - The optional parameter lets you pick the name of the file where the index will be saved to\n");
        System.exit(1);
    }
}
