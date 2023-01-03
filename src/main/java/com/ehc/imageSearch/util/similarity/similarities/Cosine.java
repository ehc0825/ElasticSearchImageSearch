package com.ehc.imageSearch.util.similarity.similarities;

import com.ehc.imageSearch.util.similarity.ImageSearchQuery;
import com.ehc.imageSearch.util.similarity.Similarity;

public class Cosine extends Similarity {
    public Cosine(){
    this.similarityName="cosine";
    this.indexName="test-image-vector-angular";
    }
    @Override
    public String queryForSimilarity(int from, int size, String vector) {
        return ImageSearchQuery.basedFrontQuery(from,size)
                +ImageSearchQuery.getVectorForQuery(vector)
                +ImageSearchQuery.basedTailQueryForCosine();
    }
}
