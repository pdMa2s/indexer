University of Aveiro
Departamento de Electrónica, Telecomunicações e Informática
MEI

This work was developed for course of Information Retrieval.
It's porpuse is  to analise a document corpus, extract relevant information,
convert its conten into tokens and index them.
For more information you can consult the documentation on folder

USAGE:
$ mvn exec:java -Dexec.mainClass=Main -Dexec.args="<arguments>"

Argument: <corpusDirectory> <tokenizerType> <indexFile>(Optional)
                    <corpusDirectory> - The directory where the corpus is located
                    <tokenizerType> - The of tokenizer you want to use
                    tokenizer types:
                    st - Simple tokenizer.tokenizer
                    ct - Complex tokenizer.tokenizer
                    cts - Complex tokenizer.tokenizer with Stemming
                    <indexFile> - The optional parameter lets you pick the name of the file where the index will be saved to);

Example:
$ mvn exec:java -Dexec.mainClass=Main -Dexec.args="cranfield st"
