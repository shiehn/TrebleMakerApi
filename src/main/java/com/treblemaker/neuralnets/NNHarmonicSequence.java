package com.treblemaker.neuralnets;

import org.deeplearning4j.models.word2vec.Word2Vec;

public class NNHarmonicSequence {

    public void test() {
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(5)
                .iterations(1)
                .layerSize(100)
                .seed(42)
                .windowSize(5)
                .build();


    }

}
