package com.ehc.ElasticSearchImageSearch.util.similarity.similarities;

import com.ehc.ElasticSearchImageSearch.util.similarity.ImageSearchQuery;
import com.ehc.ElasticSearchImageSearch.util.similarity.Similarity;

public class Cosine extends Similarity {
    public Cosine(){
    this.similarityName="cosine";
    }
    @Override
    public String queryForSimilarity(int from, int size, String vector) {
        return ImageSearchQuery.basedFrontQuery(from,size)
                +ImageSearchQuery.getVectorForQuery(vector)
                +ImageSearchQuery.basedTailQueryForCosine();
    }
}
