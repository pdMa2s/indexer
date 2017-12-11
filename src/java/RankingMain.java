package src.java;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.helper.HelpScreenException;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.MutuallyExclusiveGroup;
import net.sourceforge.argparse4j.inf.Namespace;
import src.java.evaluation.EfficiencyMetricsFileWriter;
import src.java.evaluation.MetricEvaluator;
import src.java.index.CSVIndexReader;
import src.java.index.IndexReader;
import src.java.index.InvertedIndex;
import src.java.query.DocumentIndex;
import src.java.query.Query;
import src.java.query.QueryIndex;
import src.java.relevancefeedback.RelevanceFileReader;
import src.java.relevancefeedback.RelevanceIndex;
import src.java.relevancefeedback.RevelevanceReader;
import src.java.searchengine.*;
import src.java.tokenizer.Tokenizer;
import src.java.word2vec.QueryExpansionWord2Vec;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static src.java.constants.Constants.*;

public class RankingMain {
    public static void main(String[] args){
        Namespace parsedArgs = parseParameters(args);
        File indexFile = new File(parsedArgs.getString("indexFile"));
        File queryFile = new File(parsedArgs.getString("queryFile"));
        File docIndexFile = new File(parsedArgs.getString("documentIndexFile"));
        QueryExpansionWord2Vec queryExpansionWord2Vec = null;

        String rankingResultsFile = parsedArgs.getString("resultFile");

        double threshold = Double.parseDouble(parsedArgs.getString("threshold"));
        String relevanceScoreFile = parsedArgs.getString("relevanceFile");
        InvertedIndex invertedIndex = new InvertedIndex();
        QueryIndex queryIndex;


        long startTime = System.currentTimeMillis();

        IndexReader idr = new CSVIndexReader();
        idr.parseInvertedIndex(indexFile,invertedIndex);
        SearchEngineBuilder searchEngineBuilder;
        String scoringSystem = idr.getScoringSystem();

        RelevanceFileReader relevanceFileReader = new RevelevanceReader();
        RelevanceIndex relevanceIndex = relevanceFileReader.parseRelevanceFile(relevanceScoreFile);

        if(scoringSystem.equals(NORMALIZED)){
            try {
                 queryExpansionWord2Vec = new QueryExpansionWord2Vec("fullCorpusContent.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            int corpusSize = idr.getCorpusSize();
            queryIndex = new QueryIndex(corpusSize);
            searchEngineBuilder = getFeedBackBuilder(parsedArgs, invertedIndex, queryIndex,
                     docIndexFile, idr, relevanceIndex,threshold, queryExpansionWord2Vec);
        }
        else{
            searchEngineBuilder = getCountBuilder(parsedArgs, invertedIndex, idr.getTokenizer(), threshold);
        }

        SearchEngine searchEngine = searchEngineBuilder.constructSearEngine();

        List<Query> queries= searchEngine.processQueries(queryFile, invertedIndex);
        searchEngine.saveResults(rankingResultsFile);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Querying time: "+elapsedTime+"ms");


        MetricEvaluator evaluator = checkEvaluatorParameter(parsedArgs, relevanceIndex,queries, relevanceScoreFile);
        if(evaluator != null){
            int relevanceScore = Integer.parseInt(parsedArgs.getString("relevanceScore"));
            evaluator.calculateSystemMeasures(relevanceScore);
        }
    }

    private static Namespace parseParameters(String[] args){
        ArgumentParser parser = ArgumentParsers.newFor("RankingMain").build()
                .defaultHelp(true)
                .description("Loads and index and answers queries, the results are stored in a file");
        MutuallyExclusiveGroup countGroup = parser.addMutuallyExclusiveGroup();
        countGroup.addArgument("-f","--frequencyOfQueryWords").action(Arguments.storeTrue())
                .help("Calculates the frequency of the query words in each document");
        countGroup.addArgument("-w","--wordsInDoc").action(Arguments.storeTrue())
                .help("Calculates how many query words are in the document (Option set by omission)");
        parser.addArgument("queryFile")
                .help("The path to the file that contains the queries");
        parser.addArgument("-i","--indexFile").setDefault(INDEXDEAFAULTFILENAME)
                .help("(Optional) The path to the file were the index is contained");
        parser.addArgument("-r","--resultFile").setDefault("queryResults")
                .help("(Optional) The name of the file that will store the results");
        parser.addArgument("-th","--threshold").setDefault(THRESHOLDDEFAULTVALUE)
                .help("(Optional) The minimum value of the results");
        parser.addArgument("-rvf","--relevanceFile").setDefault(RELEVANCESCOREFILE)
                .help("(Optional) The path to the file that contains the relevance feedback scores");
        parser.addArgument("-rvs","--relevanceScore")
                .choices("1", "2","3","4").setDefault("4")
                .help("(Optional) The minimum relevance score to be considered when calculating the efficiency metrics");
        parser.addArgument("-cem","--calculateEfficiencyMetrics")
                .action(Arguments.storeTrue())
                .help("Use this flag to calculate the system efficiency metrics");
        parser.addArgument("-di","--documentIndexFile").setDefault(DOCUMENTINDEXFILE)
                .help("(Optional)The name of the file where the document index will be written in to");
        parser.addArgument("-qe","--queryExpansion").action(Arguments.storeTrue())
                .help("(Optional)Use this flag to allow query expansion");

        MutuallyExclusiveGroup feedBackGroup = parser.addMutuallyExclusiveGroup();
        feedBackGroup.addArgument("-ex","--explicitFeedBack").action(Arguments.storeTrue())
                .help("Update query results based on a explicit feedback");
        feedBackGroup.addArgument("-im","--implicitFeedBack").action(Arguments.storeTrue())
                .help("Update query results based on implicit feedback");
        feedBackGroup.addArgument("-w2v","--word2VecFeedBack").action(Arguments.storeTrue())
                .help("Update query results based on word2Vec feedback");


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


    private static SearchEngineBuilder getCountBuilder(Namespace ns, InvertedIndex idx, Tokenizer tokenizer, double threshold){
        if(ns.getBoolean("frequencyOfQueryWords"))
            return new FreqQueryWordsBuilder(idx, tokenizer, threshold);
        else
            return new WordsInDocBuilder(idx, tokenizer, threshold);
    }

    private static SearchEngineBuilder getFeedBackBuilder(Namespace ns, InvertedIndex idx
                                                          , QueryIndex queryIndex, File documentIndexFile,
                                                          IndexReader indexReader, RelevanceIndex relevanceIndex,
                                                          double threshold, QueryExpansionWord2Vec w2v){

        if(ns.getBoolean("explicitFeedBack")){
            DocumentIndex docIndex = new DocumentIndex();
            indexReader.parseDocumentIndexFromFile(documentIndexFile, docIndex);
            return new ExplicitFeedBackEngineBuilder(idx, indexReader.getTokenizer(), queryIndex, docIndex, threshold,
                    relevanceIndex);
        }

        if(ns.getBoolean("implicitFeedBack")){
            DocumentIndex docIndex = new DocumentIndex();
            indexReader.parseDocumentIndexFromFile(documentIndexFile, docIndex);
            return new ImplicitFeedBackEngineBuilder(idx, indexReader.getTokenizer(), queryIndex, docIndex, threshold);
        }

        if(ns.getBoolean("word2VecFeedBack")){
            DocumentIndex docIndex = new DocumentIndex();
            indexReader.parseDocumentIndexFromFile(documentIndexFile, docIndex);
            return new word2VecFeedBackEngineBuilder(idx, indexReader.getTokenizer(), queryIndex, docIndex, threshold, w2v);
        }
        return new NormalizedSearchEngineBuilder(idx, indexReader.getTokenizer(), queryIndex, threshold);
    }

    private static MetricEvaluator checkEvaluatorParameter(Namespace ns, RelevanceIndex relevanceIndex,List<Query> queries, String relevanceFile){

        if(ns.getBoolean("calculateEfficiencyMetrics"))
            return new MetricEvaluator(relevanceFile, queries, relevanceIndex,new EfficiencyMetricsFileWriter("relevanceResults"));
        return null;
    }

}
