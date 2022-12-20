package com.ehc.ElasticSearchImageSearch.util.similarity;

import com.ehc.ElasticSearchImageSearch.util.similarity.similarities.Cosine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SimilarityEnumTest {



    @Test
    void SimilarityMap_getTest()
    {
        boolean success;
        Map<String,Similarity> similarityMap=SimilarityEnum.getSimilarityMap();
        if(similarityMap.size()==3)
        {
            if(similarityMap.get("cosine").similarityName.equals("cosine"))
            {
                success=true;
            }
            else
            {
                success=false;
            }
        }
        else
        {
            success=false;
        }
        if(success)
        {
            System.out.println("Map get 테스트 성공");
        }
        else
        {
            System.out.println("Map get 테스트 실패");
        }
    }

    @Test
    void ImageSearchQueryGetTest()
    {
        String query=SimilarityEnum.getImageQueryBySimilarity("cosine",0,10,"{\"vector\":[0.9217332601547241,1.523964524269104,1.6131728887557983,1.0742337703704834]}");
        if(query.contains("angular"))
        {
            System.out.println("이미지 쿼리 Get성공");
            System.out.println(query);
        }else
        {
            System.out.println("이미지 쿼리 Get실패");
        }
    }
}