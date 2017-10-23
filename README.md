University of Aveiro
Departamento de Electrónica, Telecomunicações e Informática
MEI

This work was developed for the course of Information Retrieval.

It consists of two programs, one that creates an index based on a corpus of documents, and another that queries that index and extracts  results.

Program #1:
It's porpuse is  to analise a document corpus, extract relevant information,
convert its conten into tokens and index them.

Program #2:
Given two files, one with the constructed index in disk and one with the queries to be processed, the index will be rebuild in memory and then for each query in the second file there will be executed a ranking method in order to indicate the documents with highest ranking for each of the queries in the given file. In the end, two files will be written to disk which correspond to the processed results for both of the implemented and used ranking methods.

For more information you can consult the documentation on folder doc, or the
project's report on folder report.


USAGE:
$ javac -d target/classes -classpath "libstemmer_java/java/libstemmer.jar" src/java/*.java src/java/*/*.java
$ cd target/classes/
$ java -cp ../../libstemmer_java/java/libstemmer.jar: src.java.<mainClass> <arguments>
    <mainClass>: 
        - IndexCreationMain -> Program n#1
        - SearcherMain -> Program n#2
        
After you run the desired program a print with instructions on how to run the programs will apppear, check the arguments that you need to pass
to the programs to properly run them.

EXAMPLE:
$ java -cp ../../libstemmer_java/java/libstemmer.jar: src.java.SearcherMain ../../index.csv ../../cranfield.queries.txt
