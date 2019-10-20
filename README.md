# MAC_Lab01
Jobin Simon - Caduff Max

## 1 Introduction

The goal of this lab is to discover the Lucene platform and to learn its functionalities by using its Java API.
Lucene is a library for indexing and searching text files, written in Java and available as open source under the
Apache License. It is not a standalone application; it is designed to be integrated easily into applications that
have to search text in local files or on the Internet. It attempts to balance efficiency, flexibility, and conceptual
simplicity at the API level.

## 2 Familiarizing with Lucene

### 2.1 Understanding the Lucene API

### 2.2 Using Luke to explore the index

## 3 Indexing and Searching the CACM collection

### 3.1 indexing

### 3.2 Using different analyzers

### 3.3 Reading Index

1. Thacher Jr., H. C with 38 publications

2. Top 10 terms in the field title with their frequencies :

|terms with frequency         |
|-----------------------------|
|algorithm (frequency : 1008) |
|comput (frequency : 392)     |
|program (frequency : 307)    |
|system (frequency : 280)     |
|gener (frequency : 169)      |
|method (frequency : 156)     |
|languag (frequency : 144)    |
|function (frequency : 142)   |
|us (frequency : 123)         |
|data (frequency : 110)       |

Here is the completed code for this step:

```java
public void printTopRankingTerms(String field, int numTerms) {

    // This methods print the top ranking term for a field.
    // See "Reading Index".
    // Use EnglishAnalyzer to remove useless words
    try {

        TermStats[] byHigherDocFreqTerms;
        String topTerms = "";

        byHigherDocFreqTerms = HighFreqTerms.getHighFreqTerms(indexReader, numTerms, field,
                new HighFreqTerms.DocFreqComparator());

        for (int i = 0; i < numTerms; ++i) {
            topTerms += byHigherDocFreqTerms[i].termtext.utf8ToString();
            topTerms += " (frequency : " + byHigherDocFreqTerms[i].totalTermFreq + ")\n";
        }
        System.out.println("Top ranking terms for field [" + field + "] are: \n" + topTerms);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
```
### 3.4 searching

Here is the code to perform queries, display number of results and print 10 top scores :

```java
public void query(String q) {

    System.out.println("\nSearching for [" + q + "]");
    int numTermsToDisplay;

    try {
        // Search only in field summary
        QueryParser queryParser = new QueryParser("summary", analyzer);
        Query query = queryParser.parse(q);
        ScoreDoc[] hits = indexSearcher.search(query, indexReader.numDocs()).scoreDocs;

        int length = hits.length;
        numTermsToDisplay = length > 10 ? 10 : length;
        System.out.println("Total documents found : " + length);
        for (int i = 0; i < numTermsToDisplay; ++i) {

            Document document = indexSearcher.doc(hits[i].doc);
            System.out.println(document.get("id") + ": " + document.get("title") + " (" + hits[i].score + ")");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

Here are the results of following queries, with lucene score :

**1. Publications containing the term "Information Retrieval"**
```text
Searching for [Information Retrieval]
Total documents found : 188
1457: Data Manipulation and Programming Problemsin Automatic Information Retrieval (1.7697731)
891: Everyman's Information Retrieval System (1.6287744)
3134: The Use of Normal Multiplication Tablesfor Information Storage and Retrieval (1.5548403)
1935: Randomized Binary Search Technique (1.5292588)
2307: Dynamic Document Processing (1.4392829)
1699: Experimental Evaluation of InformationRetrieval Through a Teletypewriter (1.436444)
1032: Theoretical Considerations in Information Retrieval Systems (1.4117906)
2519: On the Problem of Communicating Complex Information (1.3600239)
1681: Easy English,a Language for InformationRetrieval Through a Remote Typewriter Console (1.3113353)
2990: Effective Information Retrieval Using Term Accuracy (1.2403991)
```

**2. Publications containing both "Information" and "Retrieval"**
```text
Searching for [Information AND Retrieval]
Total documents found : 23
1457: Data Manipulation and Programming Problemsin Automatic Information Retrieval (1.7697731)
891: Everyman's Information Retrieval System (1.6287744)
3134: The Use of Normal Multiplication Tablesfor Information Storage and Retrieval (1.5548403)
1935: Randomized Binary Search Technique (1.5292588)
2307: Dynamic Document Processing (1.4392829)
1699: Experimental Evaluation of InformationRetrieval Through a Teletypewriter (1.436444)
1032: Theoretical Considerations in Information Retrieval Systems (1.4117906)
2519: On the Problem of Communicating Complex Information (1.3600239)
1681: Easy English,a Language for InformationRetrieval Through a Remote Typewriter Console (1.3113353)
2990: Effective Information Retrieval Using Term Accuracy (1.2403991)
```

**3. Publications containing at least the term "Retrieval" and, possibly "Information" but not "Database"**
```text
Searching for [+Retrieval Information NOT Database]
Total documents found : 54
1457: Data Manipulation and Programming Problemsin Automatic Information Retrieval (1.7697731)
891: Everyman's Information Retrieval System (1.6287744)
3134: The Use of Normal Multiplication Tablesfor Information Storage and Retrieval (1.5548403)
1935: Randomized Binary Search Technique (1.5292588)
2307: Dynamic Document Processing (1.4392829)
1699: Experimental Evaluation of InformationRetrieval Through a Teletypewriter (1.436444)
1032: Theoretical Considerations in Information Retrieval Systems (1.4117906)
2519: On the Problem of Communicating Complex Information (1.3600239)
1681: Easy English,a Language for InformationRetrieval Through a Remote Typewriter Console (1.3113353)
2990: Effective Information Retrieval Using Term Accuracy (1.2403991)
```

**4. Publications containing a term starting with "Info"**
```text
Searching for [Info*]
Total documents found : 193
222: Coding Isomorphisms (1.0)
272: A Storage Allocation Scheme for ALGOL 60 (1.0)
396: Automation of Program  Debugging (1.0)
397: A Card Format for Reference Files in Information Processing (1.0)
409: CL-1, An Environment for a Compiler (1.0)
440: Record Linkage (1.0)
483: On the Nonexistence of a Phrase Structure Grammar for ALGOL 60 (1.0)
616: An Information Algebra - Phase I Report-LanguageStructure Group of the CODASYL Development Committee (1.0)
644: A String Language for Symbol Manipulation Based on ALGOL 60 (1.0)
655: COMIT as an IR Language (1.0)
```

**5. Publications containing the term "Information" close to "Retrieval" (max distance 5)**
```text
Searching for [Information Retrieval~5]
Total documents found : 191
1457: Data Manipulation and Programming Problemsin Automatic Information Retrieval (1.4836584)
3134: The Use of Normal Multiplication Tablesfor Information Storage and Retrieval (1.3640971)
891: Everyman's Information Retrieval System (1.3426597)
1935: Randomized Binary Search Technique (1.2820275)
2832: Faster Retrieval from Context Trees (Corrigendum) (1.2517626)
2307: Dynamic Document Processing (1.2272837)
1746: Protection in an Information Processing Utility (1.2121993)
1699: Experimental Evaluation of InformationRetrieval Through a Teletypewriter (1.1841145)
1032: Theoretical Considerations in Information Retrieval Systems (1.18355)
2519: On the Problem of Communicating Complex Information (1.1736662)
```

Here is the code to add int the Main class :

```java
private static void searching(QueriesPerformer queriesPerformer) {

    // source : https://lucene.apache.org/core/2_9_4/queryparsersyntax.html
    // Example
    queriesPerformer.query("compiler program");

    // Containing the term <Information Retrieval>;
    queriesPerformer.query("Information Retrieval");

    // Containing both <Information> and <Retrieval>;
    queriesPerformer.query("Information AND Retrieval");

    // Containing at least the term <Retrieval> and, possibly <Information> but not <Database>
    queriesPerformer.query("+Retrieval Information NOT Database");

    // Containing a term starting with <Info>
    queriesPerformer.query("Info*");

    // Containing the term <Information> close to <Retrieval> (max distance 5)
    queriesPerformer.query("Information Retrieval~5");
}
```
### 3.5 Tuning the Lucene score

Here is the code of the custom similaritiy class :

```java
public class MySimilarity extends ClassicSimilarity {

    @Override
    public float tf(float freq) {
        return (float) (1 + Math.log10(freq));
    }

    @Override
    public float idf(long docFreq, long numDocs) {
        return (float) (1 + Math.log10(numDocs / (1 + docFreq)));
    }

    @Override
    public float lengthNorm(int numTerms) {
        return 1;
    }
}
```

and the results of the query "computer programm" with the classic similarity :
```text
Total documents found : 578
3189: An Algebraic Compiler for the FORTRAN Assembly Program (1.4853004)
1215: Some Techniques Used in the ALCOR ILLINOIS 7090 (1.40438)
1183: A Note on the Use of a Digital Computerfor Doing Tedious Algebra and Programming (1.3361712)
1459: Requirements for Real-Time Languages (1.3162413)
718: An Experiment in Automatic Verification of Programs (1.3136772)
1122: A Note on Some Compiling Algorithms (1.3136772)
1465: Program Translation Viewed as a General Data Processing Problem (1.2863079)
2652: Reduction of Compilation Costs Through Language Contraction (1.2732332)
1988: A Formalism for Translator Interactions (1.2580339)
46: Multiprogramming STRETCH: Feasibility Considerations (1.2391106)
```

and with the custom similarity class :
```text
Total documents found : 578
2923: High-Level Data Flow Analysis (5.424762)
2534: Design and Implementation of a Diagnostic Compiler for PL/I (5.3897924)
637: A NELIAC-Generated 7090-1401 Compiler (5.1789074)
1647: WATFOR-The University of Waterloo FORTRAN IV Compiler (5.1361294)
2652: Reduction of Compilation Costs Through Language Contraction (4.7872586)
1135: A General Business-Oriented Language Based on Decision Expressions* (4.523839)
1237: Conversion of Decision Tables To Computer Programs (4.523839)
1459: Requirements for Real-Time Languages (4.523839)
2944: Shifting Garbage Collection Overhead to Compile Time (4.523839)
1465: Program Translation Viewed as a General Data Processing Problem (4.5153804)
```
