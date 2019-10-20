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

algorithm (frequency : 1008)
comput (frequency : 392)
program (frequency : 307)
system (frequency : 280)
gener (frequency : 169)
method (frequency : 156)
languag (frequency : 144)
function (frequency : 142)
us (frequency : 123)
data (frequency : 110)

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

            for(int i = 0; i < numTerms; ++i) {
                topTerms += byHigherDocFreqTerms[i].termtext.utf8ToString();
                topTerms += " (frequency : " + byHigherDocFreqTerms[i].totalTermFreq + ")\n";
            }
            System.out.println("Top ranking terms for field ["  + field +"] are: \n" + topTerms);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
```
