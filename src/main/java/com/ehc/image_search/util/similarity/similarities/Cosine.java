package com.ehc.image_search.util.similarity.similarities;

import com.ehc.image_search.util.similarity.Similarity;

public class Cosine extends Similarity {
    public Cosine(){
    this.similarityName="cosine";
    this.indexName="test-image-vector-angular";
    }
}
