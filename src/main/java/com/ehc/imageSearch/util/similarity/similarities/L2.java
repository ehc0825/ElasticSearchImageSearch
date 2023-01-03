package com.ehc.imageSearch.util.similarity.similarities;

import com.ehc.imageSearch.util.similarity.ImageSearchQuery;
import com.ehc.imageSearch.util.similarity.Similarity;

public class L2 extends Similarity {
    public L2()
    {
        this.similarityName="l2";
        this.indexName="test-image-vector-l2";
    }
    @Override
    public String queryForSimilarity(int from, int size, String vector) {
        return ImageSearchQuery.basedFrontQuery(from,size)
                +ImageSearchQuery.getVectorForQuery(vector)
                +ImageSearchQuery.basedTailQueryForL2();
    }
}
