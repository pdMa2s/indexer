package src.java;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.helper.HelpScreenException;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import src.java.evaluation.Evaluator;
import src.java.index.CSVIndexReader;
import src.java.index.InvertedIndex;
import src.java.index.IndexReader;
import src.java.query.QueryIndex;
import src.java.searchengine.SearchEngine;
import src.java.searchengine.SearchEngineBuilder;
import src.java.searchengine.NormalizedSearchEngineBuilder;
import java.io.File;

import static src.java.constants.Constants.*;

public class RankingMain {
    public static void main(String[] args){
        Namespace parsedArgs = parseParameters(args);
        File indexFile = new File(parsedArgs.getString("indexFile"));
        File queryFile = new File(parsedArgs.getString("queryFile"));

        String rankingResultsFile = "NORMALIZED_RANKING";

        long startTime = System.currentTimeMillis();
        InvertedIndex invertedIndex = new InvertedIndex();
        QueryIndex queryIndex;
        //Evaluator evaluator = new Evaluator(relevanceFile);
        //evaluator.parseRelevanceFile();
        IndexReader idr = new CSVIndexReader();
        idr.parseInvertedIndex(indexFile,invertedIndex);
        int corpusSize = idr.getCorpusSize();
        queryIndex = new QueryIndex(corpusSize);

        SearchEngineBuilder searchEngineBuilder = new NormalizedSearchEngineBuilder(invertedIndex,
                idr.getTokenizer(), queryIndex);
        SearchEngine searchEngine = searchEngineBuilder.constructSearEngine();

        searchEngine.processQueries(queryFile, invertedIndex);
        searchEngine.saveResults(rankingResultsFile);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Querying time: "+elapsedTime+"ms");
    }

    private static Namespace parseParameters(String[] args){
        ArgumentParser parser = ArgumentParsers.newFor("RankingMain").build()
                .defaultHelp(true)
                .description("Loads and index and answers queries, the results are stored in a file");
        parser.addArgument("-s", "--scoring")
                .choices(DOCFREQUENCY, NORMALIZED).setDefault(NORMALIZED)
                .help("Specify the scoring system");
        parser.addArgument("queryFile")
                .help("The path to the file that contains the queries");
        parser.addArgument("indexFile").nargs("?").setDefault(INDEXDEAFAULTFILENAME)
                .help("(Optional) The path to the file were the index is contained");
        parser.addArgument("resultFile").nargs("?").setDefault("queryResults")
                .help("(Optional) The name of the file that will store the results");
        parser.addArgument("relevanceFile").nargs("?")
                .help("(Optional) The path to the file that contains the relevance scores");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        }
        catch (HelpScreenException e){
            System.exit(0);
        }
        catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        return ns;
    }


    private static void printUSAGE(){
        System.err.println("USAGE: \n"+
                "java -cp ../../libstemmer_java/java/libstemmer.jar: src.java.rankingMain <corpusDirectory> <indexFile>(Optional)\n"+
                "<indexFile> - The file where the index is built \n"+
                "<QueryFile> - The File with the queries to be evaluated \n"+
                "<RelevanceFile> - The file with the relevance for each query and doc\n");
        System.exit(1);
    }

}
