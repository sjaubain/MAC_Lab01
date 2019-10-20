package ch.heigvd.iict.dmg.labo1.similarities;

import org.apache.lucene.search.similarities.ClassicSimilarity;

public class MySimilarity extends ClassicSimilarity {

    @Override
    public float tf(float freq) {
        return (float)(1 + Math.log10(freq));
    }

    @Override
    public float idf(long docFreq, long numDocs) {
        return (float)(1 + Math.log10(numDocs / (1 + docFreq)));
    }

    @Override
    public float lengthNorm(int numTerms) {
        return 1;
    }
}
