package src.java;

import org.tartarus.snowball.ext.englishStemmer;
import src.java.index.CSVIndexReader;
import src.java.index.Index;
import src.java.index.IndexReader;
import src.java.query.DisjuntiveQueryProcessor;
import src.java.query.Query;
import src.java.query.QueryLoader;
import src.java.query.QueryProcessor;
import src.java.tokenizer.ComplexTokenizer;
import src.java.tokenizer.SimpleTokenizer;
import src.java.tokenizer.Tokenizer;

import java.io.File;
import java.util.List;

public class Searcher {
    public static void main(String[] args){
        File contentFile = new File(args[0]);
        File queryFile = new File(args[1]);
        Tokenizer tokenizer = parseTokenizerType(args[2]);


        Index index;
        IndexReader idr = new CSVIndexReader();


        index = idr.parseToIndex(contentFile);
        QueryLoader br = new QueryLoader(tokenizer);
        List<Query> queries = br.loadQueries(queryFile);
        //System.out.println(queries);
        QueryProcessor qp = new DisjuntiveQueryProcessor(index);
        qp.frequencyOfQueryWordsInDocument(queries);
        qp.saveQueryResultsToFile("results", queries);    }

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
