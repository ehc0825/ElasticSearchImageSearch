package com.ehc.image_search.util.similarity;

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

}