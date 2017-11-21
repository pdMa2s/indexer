University of Aveiro
Departamento de Electrónica, Telecomunicações e Informática
MEI

This work was developed for the course of Information Retrieval.

It consists of two programs, one that creates an invertedIndex based on a corpus of documents and in a specific scoring method, and another that queries the invertedIndex and extracts the results based on the same scoring method that was defined during the invertedIndex construction. After the results are calculated measurements of the engine's performance data are performed.

Before the execution of the program its important to install maven and its dependencies, you can do so by:

											$mvn compile
											$mvn package
											$mvn install assembly:assembly


-----------------------------------------------------------------------------------------Program #1:-------------------------------------------------------------------------------------------------------

It's purpose is to analise a document corpus, extract relevant information, convert its content into tokens and invertedIndex them based on a scoring method.

Arguments: 	Tags	     |              Choices for Tags		       |        Value by Default
	   ---------------------------------------------------------------------------------------------------------
	   <-s, --scoring>   | Choices: "norm", "freq"                         | "norm";
	   <-t, --tokenizer> | Choices: "simple", "complex", "complexStemming" | "complexStemming";
	   <corpusDirectory> | File Name				       | --
	   <indexFile>       | File Name				       | "index.csv";	
	

<Scoring> - The scoring in which the invertedIndex construction will be based.
<tokenizer> - The type of tokenizer to be used for the tokenization process.
<corpusDirectory> - The path to the directory of the corpus.
<indexFile> - The name of the file where the index will be written into.

For More information about running the code, run the program with the argument "-h" and additional arguments information will be displayed.

Usage Example: $java -cp target/Indexer-1.0-SNAPSHOT-jar-with-dependencies.jar src.java.IndexerMain -h  (for checking the arguments)
	       $java -cp target/Indexer-1.0-SNAPSHOT-jar-with-dependencies.jar src.java.IndexerMain cranfield -s freq


-----------------------------------------------------------------------------------------Program #2:-------------------------------------------------------------------------------------------------------

Given three files, one with the constructed invertedIndex in disk, one with the queries to be processed, and one with the golden standards of relevance the invertedIndex will be rebuild in memory and then for each query in the second file there will be executed a ranking method in order to indicate the documents with highest ranking for each of the queries in the given file. In the end both the file containing the results of the specific ranking method and the file with the measures taken from the engine's performance will be written to disk.

	
Arguments: 	     Tags		|  Choices for Tags  |		Value by Default
	   ---------------------------------------------------------------------------------
	   <-f,--frequencyOfQueryWords> | --		     | --
	   <-w,--wordsInDoc>		| --	 	     | --
	   <queryFile> 			| File Name	     | --
	   <-i,--indexFile> 		| File Name	     | "index.csv"
	   <-r,--resultFile> 		| File Name	     | "queryResults"
           <-th, --threshold> 		| --		     | -1
	   <-rvf,--relevanceFile> 	| File Name	     | --
	   <-rvs,--relevanceScore>	| 1, 2, 3, 4   	     | 4


<frequencyOfQueryWords> - Calculates the frequency of the query words in each document.
<wordsInDoc> - Calculates how many query words are in the document.
<queryFile> - The path to the file that contains the queries.
<indexFile> - The path to the file were the index is contained.
<resultFile> - The name of the file that will store the results.
<threshold> - The minimum value of the results.
<relevanceFile> - The path to the file that contains the relevance scores.
<relevanceScore> - The minimum relevance score to be considered when calculating the efficiency metrics.

For More information about running the code, run the program with the argument "-h" and additional arguments information will be displayed.

Usage Example: $java -cp target/Indexer-1.0-SNAPSHOT-jar-with-dependencies.jar src.java.RankingMain -h (for checking the arguments)
	       $java -cp target/Indexer-1.0-SNAPSHOT-jar-with-dependencies.jar src.java.RankingMain cranfield.queries.txt -rvf cranfield.query.relevance.txt -w -th 4



----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
For more information you can consult the documentation on folder doc, or the project's report on folder report.
