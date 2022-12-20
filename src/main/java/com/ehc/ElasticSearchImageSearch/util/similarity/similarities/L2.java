package com.ehc.ElasticSearchImageSearch.util.similarity.similarities;

import com.ehc.ElasticSearchImageSearch.util.similarity.Similarity;

public class L2 extends Similarity {
    public L2()
    {
        this.similarity="l2";
    }
    @Override
    public String queryForSimalarity(String from, String size, String vector) {
        return null;
    }
}
