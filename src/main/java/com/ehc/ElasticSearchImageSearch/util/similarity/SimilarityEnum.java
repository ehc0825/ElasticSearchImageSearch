package com.ehc.ElasticSearchImageSearch.util.similarity;

import com.ehc.ElasticSearchImageSearch.util.similarity.similarities.Cosine;
import com.ehc.ElasticSearchImageSearch.util.similarity.similarities.L2;
import com.ehc.ElasticSearchImageSearch.util.similarity.similarities.Permutation_lsh;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;



public enum SimilarityEnum {

    COSINE(new Cosine()),
    L2(new L2()),
    PERMUTATION_LSH(new Permutation_lsh());


    private Similarity similarity;
    SimilarityEnum( Similarity similarity)
    {
        this.similarity=similarity;
    }


    /**
     * Similarity이름과 Similarity 세트를 Map형태로 return
     */
    public static Map<String, Similarity> getSimilarityMap(){
        Map<String, Similarity> similarityMap= new HashMap<>();
        for(SimilarityEnum similarity: SimilarityEnum.values())
        {
            similarityMap.put(similarity.similarity.similarityName,similarity.similarity);
        }
        return similarityMap;
    }

    /**
     * 해당 similarityName에 맞는 ImageSearchQuery를 return
     */
    public static String getImageQueryBySimilarity(String similarityName,int from,int size, String vector)
    {
        Map<String, Similarity> similarityMap=getSimilarityMap();
        String query=similarityMap.get(similarityName).queryForSimilarity(from,size,vector);
        return query;
    }
}
