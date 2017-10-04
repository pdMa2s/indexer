public class Main {
    public static void main(String[] args){
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

        long startTime = System.currentTimeMillis();
        checkParameterLength(args);
        String dirName = args[0];
        Indexer indexer;
        Index index;
        IndexerBuilder builder = parseTokenizerType( args[1],dirName );
        indexer = builder.constructIndexer();
        index = indexer.createIndex();

        //System.out.println(index);
        System.out.println("Vocabulary size: " +index.vocabularySize());
        System.out.println("Ten First terms in document 1 with alphabetical order: " + index.getTop10TermsOccurrences(1));
        System.out.println("Ten terms with the higher doc frequency: " + index.getTopFreqTerms());

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("Run time: "+elapsedTime+"ms");

        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;

        System.out.println("Memory: "+actualMemUsed);
    }

    private static void checkParameterLength(String[] args){
        if(args.length != 2){
            printUSAGE();
        }

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
                printUSAGE();


        }
        return null;

    }
    private static void printUSAGE(){
        System.err.println("USAGE: java Main <corpusDirectory> <tokenizerType>\n"+
                            "Tokenizer types:\n"+
                            "st - Simple Tokenizer\n"+
                            "ct - Complex Tokenizer\n"+
                            "cts - Complex Tokenizer with Stemming");
        System.exit(1);
    }
}
