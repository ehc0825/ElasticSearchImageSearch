package com.ehc.image_search.util.similarity.similarities;

import com.ehc.image_search.util.similarity.ImageSearchQuery;
import com.ehc.image_search.util.similarity.Similarity;

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
