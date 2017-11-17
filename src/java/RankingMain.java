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
import src.java.query.Query;
import src.java.query.QueryIndex;
import src.java.searchengine.*;
import src.java.tokenizer.Tokenizer;

import java.io.File;
import java.util.List;

import static src.java.constants.Constants.*;

public class RankingMain {
    public static void main(String[] args){
        Namespace parsedArgs = parseParameters(args);
        File indexFile = new File(parsedArgs.getString("indexFile"));
        File queryFile = new File(parsedArgs.getString("queryFile"));

        String rankingResultsFile = parsedArgs.getString("resultFile");

        double threshold = Double.parseDouble(parsedArgs.getString("threshold"));
        InvertedIndex invertedIndex = new InvertedIndex();
        QueryIndex queryIndex;


        long startTime = System.currentTimeMillis();

        IndexReader idr = new CSVIndexReader();
        idr.parseInvertedIndex(indexFile,invertedIndex);
        SearchEngineBuilder searchEngineBuilder;
        String scoringSystem = idr.getScoringSystem();

        if(scoringSystem.equals(NORMALIZED)){
            int corpusSize = idr.getCorpusSize();
            queryIndex = new QueryIndex(corpusSize);
             searchEngineBuilder = new NormalizedSearchEngineBuilder(invertedIndex,
                    idr.getTokenizer(), queryIndex, threshold);
        }
        else{
            searchEngineBuilder = getBuilder(parsedArgs, invertedIndex, idr.getTokenizer(), threshold);
        }

        SearchEngine searchEngine = searchEngineBuilder.constructSearEngine();

        List<Query> queries= searchEngine.processQueries(queryFile, invertedIndex);
        searchEngine.saveResults(rankingResultsFile);
<<<<<<< HEAD
        evaluator.calculateSystemMeasures(queries, 3);
=======

        Evaluator evaluator = checkEvaluatorParameter(parsedArgs, queries);
        if(evaluator != null)
            evaluator.calculateSystemMeasures(queries);

>>>>>>> d54cea3aaa28254e28c4878e0abdfbf77411de64

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
        parser.addArgument("-th","--threshold").setDefault(THRESHOLDDEFAULTVALUE)
                .help("(Optional) The minimum value of the results");
        parser.addArgument("-rvf","--relevanceFile")
                .help("(Optional) The path to the file that contains the relevance scores");
        parser.addArgument("-rvs","--relevanceScore")
                .choices(1, 2,3,4).setDefault(4)
                .help("(Optional) The minimum relevance score to be considered when calculating the efficiency metrics");


        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
            System.out.println(ns);
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


    private static SearchEngineBuilder getBuilder(Namespace ns, InvertedIndex idx, Tokenizer tokenizer, double threshold){
        if(ns.getBoolean("frequencyOfQueryWords"))
            return new FreqQueryWordsBuilder(idx, tokenizer, threshold);
        else
            return new WordsInDocBuilder(idx, tokenizer, threshold);
    }

    private static Evaluator checkEvaluatorParameter(Namespace ns, List<Query> queries){
        String relevanceFile  = ns.getString("relevanceFile");
        if(relevanceFile != null)
            return new Evaluator(relevanceFile, queries);
        return null;
    }

}
