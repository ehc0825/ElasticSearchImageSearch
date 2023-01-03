package com.ehc.image_search.util.similarity.similarities;

import com.ehc.image_search.util.similarity.Similarity;

public class L2 extends Similarity {
    public L2()
    {
        this.similarityName="l2";
        this.indexName="test-image-vector-l2";
    }

}
