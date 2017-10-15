package src.java;

import org.tartarus.snowball.ext.englishStemmer;
import src.java.IndexReader.IndexReader;
import src.java.IndexReader.SimpleIndexReader;
import src.java.indexer.*;
import src.java.queryProcessingUnits.BooleanQueryProcessor;
import src.java.queryProcessingUnits.QueryProcessor;
import src.java.tokenizer.ComplexTokenizer;
import src.java.tokenizer.SimpleTokenizer;
import src.java.tokenizer.Tokenizer;

import java.io.File;

public class Searcher {
    public static void main(String[] args){
        File ContentFile = new File(args[0]);
        File QueryFile = new File(args[1]);
        Tokenizer tokenizer = parseTokenizerType(args[2]);

        Index index = new Index();
        QueryProcessor br = new BooleanQueryProcessor(tokenizer);
        IndexReader idr = new SimpleIndexReader(index);

        idr.open(ContentFile);
        idr.parseAndIndex();

        br.open(QueryFile);
        br.processQueries();
    }

    private static Tokenizer parseTokenizerType(String arg){
        switch (arg){
            case "st":
                return new SimpleTokenizer();
            case "ct":
                return new ComplexTokenizer();
            case "cts":
                return new ComplexTokenizer(new englishStemmer());
            default:
                System.err.println("ERROR: Unknown Tokenizer type");
        }
        return null;
    }
}
