package src.java;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.helper.HelpScreenException;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.MutuallyExclusiveGroup;
import net.sourceforge.argparse4j.inf.Namespace;
import src.java.evaluation.Evaluator;
import src.java.index.CSVIndexReader;
import src.java.index.InvertedIndex;
import src.java.index.IndexReader;
import src.java.query.QueryIndex;
import src.java.searchengine.*;
import src.java.tokenizer.Tokenizer;

import java.io.File;

import static src.java.constants.Constants.*;

public class RankingMain {
    public static void main(String[] args){
        Namespace parsedArgs = parseParameters(args);
        File indexFile = new File(parsedArgs.getString("indexFile"));
        File queryFile = new File(parsedArgs.getString("queryFile"));

        String rankingResultsFile = parsedArgs.getString("resultFile");

        InvertedIndex invertedIndex = new InvertedIndex();
        QueryIndex queryIndex;
        //Evaluator evaluator = new Evaluator(relevanceFile);
        //evaluator.parseRelevanceFile();

        long startTime = System.currentTimeMillis();

        IndexReader idr = new CSVIndexReader();
        idr.parseInvertedIndex(indexFile,invertedIndex);
        SearchEngineBuilder searchEngineBuilder;
        String scoringSystem = idr.getScoringSystem();
        checkOperationParameters(parsedArgs, scoringSystem);

        if(scoringSystem.equals(NORMALIZED)){
            int corpusSize = idr.getCorpusSize();
            queryIndex = new QueryIndex(corpusSize);
             searchEngineBuilder = new NormalizedSearchEngineBuilder(invertedIndex,
                    idr.getTokenizer(), queryIndex);
        }
        else{
            searchEngineBuilder = getBuilder(parsedArgs, invertedIndex, idr.getTokenizer());
        }

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
        MutuallyExclusiveGroup group = parser.addMutuallyExclusiveGroup();
        group.addArgument("-f","--frequencyOfQueryWords").action(Arguments.storeTrue())
                .help("Calculates the frequency of the query words in each document");
        group.addArgument("-w","--wordsInDoc").action(Arguments.storeTrue())
                .help("Calculates how many query words are in the document");
        parser.addArgument("queryFile")
                .help("The path to the file that contains the queries");
        parser.addArgument("-i","--indexFile").setDefault(INDEXDEAFAULTFILENAME)
                .help("(Optional) The path to the file were the index is contained");
        parser.addArgument("-r","--resultFile").setDefault("queryResults")
                .help("(Optional) The name of the file that will store the results");
        parser.addArgument("-v","--relevanceFile")
                .help("(Optional) The path to the file that contains the relevance scores");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        }
        catch (HelpScreenException e){
            System.exit(1);
        }
        catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        return ns;
    }

    private static void checkOperationParameters(Namespace ns, String scoringSystem){
        if(scoringSystem.equals(DOCFREQUENCY) && (!ns.getBoolean("frequencyOfQueryWords")
                && !ns.getBoolean("wordsInDoc") )){
            System.err.println("Missing Argument: -f or -w\n-h for more information");
            System.exit(1);
        }
    }

    private static SearchEngineBuilder getBuilder(Namespace ns, InvertedIndex idx, Tokenizer tokenizer){
        if(ns.getBoolean("frequencyOfQueryWords"))
            return new FreqQueryWordsBuilder(idx, tokenizer);
        else
            return new WordsInDocBuilder(idx, tokenizer);
    }

}
