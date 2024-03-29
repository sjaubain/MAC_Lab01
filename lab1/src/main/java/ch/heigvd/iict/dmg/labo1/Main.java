package ch.heigvd.iict.dmg.labo1;

import ch.heigvd.iict.dmg.labo1.indexer.CACMIndexer;
import ch.heigvd.iict.dmg.labo1.parsers.CACMParser;
import ch.heigvd.iict.dmg.labo1.queries.QueriesPerformer;
import ch.heigvd.iict.dmg.labo1.similarities.MySimilarity;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.shingle.ShingleAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.Similarity;

import java.io.IOException;
import java.nio.file.FileSystems;


public class Main {

	public static void main(String[] args) {

		// 1.1. create an analyzer
		Analyzer analyser = null;
		try {
			analyser = getAnalyzer();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Similarity similarity = new MySimilarity();
		
		CACMIndexer indexer = new CACMIndexer(analyser, similarity);
		indexer.openIndex();
		CACMParser parser = new CACMParser("documents/cacm.txt", indexer);
		long begin = System.currentTimeMillis();
		parser.startParsing();
		System.out.println("indexation time: " + (System.currentTimeMillis() - begin) + "ms");
		indexer.finalizeIndex();
		
		QueriesPerformer queriesPerformer = new QueriesPerformer(analyser, similarity);

		// Section "Reading Index"
		readingIndex(queriesPerformer);

		// Section "Searching"
		searching(queriesPerformer);

		queriesPerformer.close();
		
	}

	private static void readingIndex(QueriesPerformer queriesPerformer) {
		queriesPerformer.printTopRankingTerms("summary", 10);
		queriesPerformer.printTopRankingTerms("title", 10);
	}

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
        queriesPerformer.query( "\"Information Retrieval\"~5");

		// test searching for document ids (should not return them)
		queriesPerformer.query( "254");

	}

	private static Analyzer getAnalyzer() throws IOException {
	    // TODO student... For the part "Indexing and Searching CACM collection
		// - Indexing" use, as indicated in the instructions,
		// the StandardAnalyzer class.
		//
		// For the next part "Using different Analyzers" modify this method
		// and return the appropriate Analyzers asked.

		return new StopAnalyzer(FileSystems.getDefault().getPath("common_words.txt"));
	}

}
