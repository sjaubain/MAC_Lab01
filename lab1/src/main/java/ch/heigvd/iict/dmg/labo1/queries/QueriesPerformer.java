package ch.heigvd.iict.dmg.labo1.queries;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.misc.HighFreqTerms;
import org.apache.lucene.misc.TermStats;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QueriesPerformer {
	
	private Analyzer		analyzer		= null;
	private IndexReader 	indexReader 	= null;
	private IndexSearcher 	indexSearcher 	= null;

	public QueriesPerformer(Analyzer analyzer, Similarity similarity) {
		this.analyzer = analyzer;
		Path path = FileSystems.getDefault().getPath("index");
		Directory dir;
		try {
			dir = FSDirectory.open(path);
			this.indexReader = DirectoryReader.open(dir);
			this.indexSearcher = new IndexSearcher(indexReader);
			if(similarity != null)
				this.indexSearcher.setSimilarity(similarity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	
	public void query(String q) {

        System.out.println("\nSearching for [" + q +"]");
	    int numTermsToDisplay;

        try {
            // Search only in field summary
            QueryParser queryParser = new QueryParser("summary", analyzer);
            Query query = queryParser.parse(q);
            ScoreDoc[] hits = indexSearcher.search(query, indexReader.numDocs()).scoreDocs;

            int length = hits.length;
            numTermsToDisplay = length > 10 ? 10 : length;
            System.out.println("Total documents found : " + length);
            for(int i = 0; i < numTermsToDisplay; ++i) {

                Document document = indexSearcher.doc(hits[i].doc);
                System.out.println(document.get("id") + ": " + document.get("title") + " (" + hits[i].score + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	 
	public void close() {
		if(this.indexReader != null)
			try { this.indexReader.close(); } catch(IOException e) { /* BEST EFFORT */ }
	}
	
}
