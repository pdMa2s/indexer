# indexer
University of Aveiro

Departamento de Electrónica, Telecomunicações e Informática

MEI

This work was developed for the course of Information Retrieval.

It consists of two programs, one that creates an invertedIndex based on a corpus of documents and in a specific scoring method, and another that queries the invertedIndex and extracts the results based on the same scoring method that was defined during the invertedIndex construction. After the results are calculated measurements of the engine's performance data are performed.

So that this project can be used, it can be freely downloaded in the link below and needs to be imported in an IDE that supports maven integration:
https://bitbucket.org/PedroMatos/indexer


-----------------------------------------------------------------------------------------Program #1:-------------------------------------------------------------------------------------------------------

It's purpose is to analise a document corpus, extract relevant information, convert its content into tokens and invertedIndex them based on a scoring method. 
If the user wants to use a query expansion method, an additional file will be written into disk containing the full corpus content for the subsequent thesaurus creation.

Arguments: 	Tags	              |               Choices for Tags		        |        Value by Default
	   ---------------------------------------------------------------------------------------------------------
	   <-s, --scoring>            | Choices: "norm", "freq"                         | "norm";
	   <-t, --tokenizer>          | Choices: "simple", "complex", "complexStemming" | "complexStemming";
	   <corpusDirectory>          | File Name				        | --
	   <indexFile>       	      | File Name				        | "index.csv";	
	   <-di, --documentIndexFile> | File Name				        | "docIndexFile";
	

<Scoring> - The scoring in which the invertedIndex construction will be based.
<tokenizer> - The type of tokenizer to be used for the tokenization process.
<corpusDirectory> - The path to the directory of the corpus.
<indexFile> - The name of the file where the index will be written into.
<documentIndexFile> - The name of the file where the document index will be written into.

For More information about running the code, run the program with the argument "-h" and additional arguments information will be displayed.


-----------------------------------------------------------------------------------------Program #2:-------------------------------------------------------------------------------------------------------

Given four files, one with the constructed invertedIndex in disk, one with the constructed documentIndex in disk, one with the queries to be processed and one with the golden standards of relevance, the invertedIndex will be rebuild in memory and then for each query in the third file there will be executed a ranking method in order to indicate the documents with highest ranking for each of the queries in the given file. In the end both the file containing the results of the specific ranking method and the file with the measures taken from the engine's performance will be written to disk. 
In contrast with the last developed assignment, the results file will be written considering if a relevance feedback or a query expansion method was used.

	
Arguments: 	     Tags				|  Choices for Tags  |		Value by Default
	   ---------------------------------------------------------------------------------
           <queryFile> 					| File Name	     | --
	   <-f,--frequencyOfQueryWords> 		| --		     | --
	   <-w,--wordsInDoc>				| --	 	     | --
	   <-idx,--indexFile> 				| File Name	     | "index.csv"
	   <-r,--resultFile> 				| File Name	     | "queryResults"
           <-th, --threshold> 				| --		     | -1
	   <-cem, --calculateEfficiencyMetrics> 	| --	 	     | --
	   <-rvf,--relevanceFile> 			| File Name	     | --
	   <-rvs,--relevanceScore>			| 1, 2, 3, 4   	     | 4
 	   <-di,--documentIndexFile>			| File Name  	     | "docIndexFile"
	   <-exf,--explicitFeedBack>			| --   	    	     | --
	   <-imf,--implicitFeedBack>			| --  	    	     | --
	   <-qex,--queryExpansion>			| --  	             | --


<queryFile> - The path to the file where the queries to be processed are contained.
<frequencyOfQueryWords> - Calculates the frequency of the query words in each document.
<wordsInDoc> - Calculates how many query words are in the document.
<indexFile> - The path to the file where the index is contained.
<resultFile> - The name of the file that will store the results.
<threshold> - The minimum value of the results.
<calculateEfficiencyMetrics> - Flag to calculate the system efficiency metrics.
<relevanceFile> - The path to the file that contains the relevance scores.
<relevanceScore> - The minimum relevance score to be considered when calculating the efficiency metrics.
<documentIndexFile> - The path to the file were the document index is contained.
<explicitFeedBack> - Flag to update query results based on a explicit feedback.
<implicitFeedBack> - Flag to update query results based on implicit feedback.
<queryExpansion> - Flag to update query results based on query expansion.

For More information about running the code, run the program with the argument "-h" and additional arguments information will be displayed.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
For more information you can consult the documentation on folder doc, or the project's report on folder report.
