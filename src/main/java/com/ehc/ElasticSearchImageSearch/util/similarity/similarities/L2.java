package com.ehc.ElasticSearchImageSearch.util.similarity.similarities;

import com.ehc.ElasticSearchImageSearch.util.similarity.ImageSearchQuery;
import com.ehc.ElasticSearchImageSearch.util.similarity.Similarity;

public class L2 extends Similarity {
    public L2()
    {
        this.similarityName="l2";
    }
    @Override
    public String queryForSimilarity(String from, String size, String vector) {
        return ImageSearchQuery.basedFrontQuery(from,size)
                +ImageSearchQuery.getVectorForQuery(vector)
                +ImageSearchQuery.basedTailQueryForL2();
    }
}
