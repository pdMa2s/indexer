# Indexer

## University of Aveiro
**Departamento de Electrónica, Telecomunicações e Informática**  
**MEI**  

This work was developed for the course of *Information Retrieval*.

It consists of two programs:
1. One that creates an *inverted index* based on a corpus of documents using a specific scoring method.
2. Another that queries the *inverted index* and extracts the results based on the same scoring method.

After the results are calculated, measurements of the engine's performance data are performed.

To use this project, it can be freely downloaded from the link below and imported into an IDE that supports Maven integration:
[Bitbucket Repository](https://bitbucket.org/PedroMatos/indexer)

---

## Program #1: Index Construction

This program analyzes a document corpus, extracts relevant information, converts its content into tokens, and indexes them based on a scoring method.
If the user wants to use a query expansion method, an additional file will be written to disk containing the full corpus content for subsequent thesaurus creation.

### Arguments

| Tags                         | Choices for Tags                               | Default Value   |
|------------------------------|-----------------------------------------------|----------------|
| `-s, --scoring`              | Choices: `norm`, `freq`                        | `norm`         |
| `-t, --tokenizer`            | Choices: `simple`, `complex`, `complexStemming` | `complexStemming` |
| `<corpusDirectory>`          | File Name                                     | --             |
| `<indexFile>`                | File Name                                     | `index.csv`    |
| `-di, --documentIndexFile`   | File Name                                     | `docIndexFile` |

### Argument Descriptions
- **Scoring (`-s, --scoring`)** - The scoring method used in inverted index construction.
- **Tokenizer (`-t, --tokenizer`)** - The type of tokenizer to use.
- **Corpus Directory (`<corpusDirectory>`)** - The path to the directory of the corpus.
- **Index File (`<indexFile>`)** - The file name where the index will be written.
- **Document Index File (`-di, --documentIndexFile`)** - The file name where the document index will be written.

For more information about running the code, execute the program with the `-h` argument to display additional details.

---

## Program #2: Query Processing

This program takes four input files:
1. The constructed *inverted index*.
2. The constructed *document index*.
3. The file containing *queries* to be processed.
4. The file containing *golden standard relevance scores*.

The inverted index is rebuilt in memory, and a ranking method is executed to find the most relevant documents for each query. At the end, both the results file and performance measurement file are written to disk.
If a *relevance feedback* or *query expansion* method is used, the results file will be adjusted accordingly.

### Arguments

| Tags                           | Choices for Tags      | Default Value  |
|--------------------------------|----------------------|---------------|
| `<queryFile>`                  | File Name           | --            |
| `-f, --frequencyOfQueryWords`  | --                  | --            |
| `-w, --wordsInDoc`             | --                  | --            |
| `-idx, --indexFile`            | File Name           | `index.csv`   |
| `-r, --resultFile`             | File Name           | `queryResults` |
| `-th, --threshold`             | --                  | `-1`          |
| `-cem, --calculateEfficiencyMetrics` | --           | --            |
| `-rvf, --relevanceFile`        | File Name           | --            |
| `-rvs, --relevanceScore`       | 1, 2, 3, 4          | `4`           |
| `-di, --documentIndexFile`     | File Name           | `docIndexFile` |
| `-exf, --explicitFeedBack`     | --                  | --            |
| `-imf, --implicitFeedBack`     | --                  | --            |
| `-qex, --queryExpansion`       | --                  | --            |

### Argument Descriptions
- **Query File (`<queryFile>`)** - The file containing queries to be processed.
- **Frequency of Query Words (`-f, --frequencyOfQueryWords`)** - Computes the frequency of query words in each document.
- **Words in Document (`-w, --wordsInDoc`)** - Computes how many query words appear in a document.
- **Index File (`-idx, --indexFile`)** - Path to the file containing the inverted index.
- **Result File (`-r, --resultFile`)** - The name of the file storing the results.
- **Threshold (`-th, --threshold`)** - The minimum value for results.
- **Calculate Efficiency Metrics (`-cem, --calculateEfficiencyMetrics`)** - Flag to compute system efficiency metrics.
- **Relevance File (`-rvf, --relevanceFile`)** - Path to the file with relevance scores.
- **Relevance Score (`-rvs, --relevanceScore`)** - Minimum relevance score for efficiency metrics (default: 4).
- **Document Index File (`-di, --documentIndexFile`)** - Path to the file containing the document index.
- **Explicit Feedback (`-exf, --explicitFeedBack`)** - Flag to update query results based on explicit feedback.
- **Implicit Feedback (`-imf, --implicitFeedBack`)** - Flag to update query results based on implicit feedback.
- **Query Expansion (`-qex, --queryExpansion`)** - Flag to update query results based on query expansion.

For more information about running the code, execute the program with the `-h` argument to display additional details.

---

## Additional Documentation
For more information, consult the documentation in the `doc` folder or the project's report in the `report` folder.
