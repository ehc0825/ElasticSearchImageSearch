package com.ehc.imageSearch.util.similarity.similarities;

import com.ehc.imageSearch.util.similarity.ImageSearchQuery;
import com.ehc.imageSearch.util.similarity.Similarity;

public class Permutation_lsh extends Similarity {
    public Permutation_lsh(){
        this.similarityName="permutation_lsh";
        this.indexName="test-image-vector-permutation_lsh";
    }
    @Override
    public String queryForSimilarity(int from, int size, String vector) {
        return ImageSearchQuery.basedFrontQuery(from,size)
                +ImageSearchQuery.getVectorForQuery(vector)
                +ImageSearchQuery.basedTailQueryForPermutation_lsh();
    }
}
