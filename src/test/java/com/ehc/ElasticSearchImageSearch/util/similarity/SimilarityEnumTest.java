package com.ehc.ElasticSearchImageSearch.util.similarity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SimilarityEnumTest {

    @Test
    void SimilarityMap_getTest()
    {
        boolean success = false;
        Map<String,Similarity> similarityMap=SimilarityEnum.getSimilarityMap();
        for(Map.Entry<String, Similarity> similarity: similarityMap.entrySet())
        {
            if(similarityMap.get(similarity.getKey()).similarityName.equals(similarity.getValue().similarityName)) {
                success=true;
            }
            else
            {
                success=false;
            }
        }
        assertThat(success).isTrue();
    }

    @Test
    void ImageSearchQueryGetCosineTest()
    {
        boolean success;
        String query=SimilarityEnum.getImageQueryBySimilarity("cosine",0,10,"{\"vector\":[0.9217332601547241,1.523964524269104,1.6131728887557983,1.0742337703704834]}");
        if(query.contains("angular"))
        {
            success=true;
        }else
        {
           success=false;
        }
        assertThat(success).isTrue();
    }

    @Test
    void ImageSearchQueryGetL2Test()
    {
        boolean success;
        String query=SimilarityEnum.getImageQueryBySimilarity("l2",0,10,"{\"vector\":[0.9217332601547241,1.523964524269104,1.6131728887557983,1.0742337703704834]}");
        if(query.contains("l2"))
        {
            success=true;
        }else
        {
            success=false;
        }
        assertThat(success).isTrue();
    }

    @Test
    void ImageSearchQueryGetPermutation_lshTest()
    {
        boolean success;
        String query=SimilarityEnum.getImageQueryBySimilarity("permutation_lsh",0,10,"{\"vector\":[0.9217332601547241,1.523964524269104,1.6131728887557983,1.0742337703704834]}");
        if(query.contains("permutation_lsh"))
        {
            success=true;
        }else
        {
            success=false;
        }
        assertThat(success).isTrue();
    }
}