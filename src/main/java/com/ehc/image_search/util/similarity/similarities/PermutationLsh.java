package com.ehc.image_search.util.similarity.similarities;

import com.ehc.image_search.util.similarity.Similarity;

public class PermutationLsh extends Similarity {
    public PermutationLsh(){
        this.similarityName="permutation_lsh";
        this.indexName="test-image-vector-permutation_lsh";
    }

}
