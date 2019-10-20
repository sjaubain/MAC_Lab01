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

**2. Publications containing both "Information" and "Retrieval"**

**3. Publications containing at least the term "Retrieval" and, possibly "Information" but not "Database"**

**4. Publications containing a term starting with "Info"**

**5. Publications containing the term "Information" close to "Retrieval" (max distance 5)**
