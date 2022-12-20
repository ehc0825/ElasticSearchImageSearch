package com.ehc.ElasticSearchImageSearch.util.similarity.similarities;

import com.ehc.ElasticSearchImageSearch.util.similarity.Similarity;

public class Cosine extends Similarity {
    public Cosine(){
    this.similarity="angular";
    }
    @Override
    public String queryForSimalarity(String from, String size, String vector) {
        return null;
    }
}
