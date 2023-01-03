package com.ehc.image_search.util.similarity.similarities;

import com.ehc.image_search.util.similarity.ImageSearchQuery;
import com.ehc.image_search.util.similarity.Similarity;

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
