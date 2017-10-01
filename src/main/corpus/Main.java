public class Main {
    public static void main(String[] args){
        //String dirName = args[0];
        Indexer indexer;
        Index index;
        IndexerBuilder builder = parseTokenizerTyoe( "");//args[1]);
        indexer = builder.constructIndexer();
        index = indexer.createIndex();

        //System.out.println(index);
        System.out.println("Vocabulary size: " +index.vocabularySize());
        System.out.println("Ten First terms in document 1 with alphabetical order: " + index.getTop10TermsOccurrences(1));
        System.out.println("Ten terms with the higher doc frequency: " + index.getTopFreqTerms());
    }

    static void checkParameterLength(String[] args){
        if(args.length != 3){
            System.err.println("USAGE: java Main <corpusDirectory> <tokenizerType>");
        }

    }

    static IndexerBuilder parseTokenizerTyoe(String arg){
        return new ComplexTokenizerIndexerBuilder("cranfield");
    }
}
