package src.java;

import src.java.index.CSVIndexReader;
import src.java.index.Index;
import src.java.index.IndexReader;
import src.java.query.DisjuntiveQueryProcessor;
import src.java.query.Query;
import src.java.query.QueryLoader;
import src.java.query.QueryProcessor;
import java.io.File;
import java.util.List;


public class Searcher {
    public static void main(String[] args){
        checkParameterLength(args);
        File indexFile = new File(args[0]);
        File queryFile = new File(args[1]);
        String resultFileName = chooseFileToSaveIndex(args);
        Index index;
        IndexReader idr = new CSVIndexReader();


        index = idr.parseToIndex(indexFile);
        QueryLoader br = new QueryLoader(idr.getTokenizer());
        List<Query> queries = br.loadQueries(queryFile);
        QueryProcessor qp = new DisjuntiveQueryProcessor(index);
        parseOperation(args[2], qp, queries);
        qp.saveQueryResultsToFile(resultFileName, queries);
    }

    private static void parseOperation(String arg, QueryProcessor processor, List<Query> queries) {
        int argNumber = Integer.parseInt(arg);
        switch (argNumber) {
            case 1:
                processor.queryWordsInDocument(queries);
                break;
            case 2:
                processor.frequencyOfQueryWordsInDocument(queries);
                break;
            default:
                System.err.println("ERROR Unknown operation");
                printUSAGE();
        }
    }
    private static String chooseFileToSaveIndex(String[] args){
        if(args.length == 4)
            return args[3];
        return "results";
    }
    private static void printUSAGE(){
        System.err.println("USAGE: java Searcher <indexFile> <queryFile> <operation> <resultFile> (Optional)\n"+
                            "<indexFile> - The path to the file of the index on disk\n"+
                            "<queryFile> - The path to the file of the queries\n"+
                            "<operation> - The id of the operation you want to execute\n"+
                            "Operation Types:\n"+
                            "1 - Number of words in the query that appear in the document\n"+
                            "2 - Total frequency of query words in the document\n"+
                            "<resultFile> - This argument is optional, the name of the file where you to save the results\n"+
                            "By default the results will be saved to the file \"results\".");

        System.exit(1);
    }
    private static void checkParameterLength(String[] args){
        if(args.length < 3 || args.length > 4){
            printUSAGE();
        }

    }


}
