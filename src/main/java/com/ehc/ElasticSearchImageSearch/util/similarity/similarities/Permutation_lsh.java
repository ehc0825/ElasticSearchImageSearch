package com.ehc.ElasticSearchImageSearch.util.similarity.similarities;

import com.ehc.ElasticSearchImageSearch.util.similarity.Similarity;

public class Permutation_lsh extends Similarity {
    public Permutation_lsh(){
        this.similarity="permutation_lsh";
    }
    @Override
    public String queryForSimalarity(String from, String size, String vector) {
        return null;
    }
}
