package src.java;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.helper.HelpScreenException;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import src.java.index.CSVIndexWriter;
import src.java.index.InvertedIndex;
import src.java.index.IndexWriter;
import src.java.indexer.*;
import src.java.query.DocumentIndex;

import java.io.File;

import static src.java.constants.Constants.*;

public class IndexerMain {

    public static void main(String[] args){

        Namespace parsedArgs = parseParameters(args);
        String indexFile = parsedArgs.getString("indexFile");
        String dirName = parsedArgs.getString("corpusDirectory");
        String docIndexFile = parsedArgs.getString("documentIndexFile");
        Indexer indexer;
        InvertedIndex index;
        DocumentIndex docIndex = new DocumentIndex();
        IndexWriter writer = new CSVIndexWriter();
        IndexerBuilder builder;
        String scoringSystem = parsedArgs.getString("scoring");
        if(!scoringSystem.equals(NORMALIZED))
             builder = parseTokenizerType(parsedArgs.getString("tokenizer"), dirName);
        else
            builder = new NormalizedBuilder(dirName);

        long startTime = System.currentTimeMillis();
        indexer = builder.constructIndexer();
        indexer.setDocumentIndex(docIndex);
        index = indexer.createIndex();
        writer.saveIndexToFile(indexFile, index, builder.getTokenizerType(), indexer.getCorpusSize(),
                scoringSystem);
        if(scoringSystem.equals(NORMALIZED))
            writer.saveDocumentIndexToFile(docIndexFile, docIndex);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("Indexing time: "+elapsedTime+"ms");
        //System.out.println(invertedIndex);
        System.out.println("Vocabulary size: " +index.vocabularySize());

    }


    private static Namespace parseParameters(String[] args){
        ArgumentParser parser = ArgumentParsers.newFor("IndexerMain").build()
                .defaultHelp(true)
                .description("Creates an inverted index on disk mapping terms, to their respective document and frequency on that document.");
        parser.addArgument("-s", "--scoring")
                .choices(DOCFREQUENCY, NORMALIZED).setDefault(NORMALIZED)
                .help("Specify the scoring system");
        parser.addArgument("-t", "--tokenizer")
                .choices(SIMPLETOKENIZER, COMPLEXTOKENIZER, COMPLEXTOKENIZERSTEMMING).setDefault("complexStemming")
                .help("Specify the tokenizer type");
        parser.addArgument("corpusDirectory")
                .help("The path to the directory of the corpus");
        parser.addArgument("indexFile").nargs("?").setDefault(INDEXDEAFAULTFILENAME)
                .help("(Optional)The name of the file where the index will be written in to");
        parser.addArgument("documentIndexFile").nargs("?").setDefault(DOCUMENTINDEXFILE)
                .help("(Optional)The name of the file where the index will be written in to");
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
    private static IndexerBuilder parseTokenizerType(String arg, String dirName){
        switch (arg){
            case SIMPLETOKENIZER:
                return new SimpleTokenizerIndexerBuilder(dirName);
            case COMPLEXTOKENIZER:
                return new ComplexTokenizerIndexerBuilder(dirName);
            case COMPLEXTOKENIZERSTEMMING:
                return new CTStemmingIndexerBuilder(dirName);
            default:
                System.err.println("ERROR: Unknown Tokenizer type");

        }
        return null;
    }

    
}
