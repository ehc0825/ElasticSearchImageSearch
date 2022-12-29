package com.ehc.ElasticSearchImageSearch.util;

import com.ehc.ElasticSearchImageSearch.util.similarity.ImageSearchQuery;
import com.ehc.elastiknnSimilarityQuery.Similarity;
import com.ehc.elastiknnSimilarityQuery.query.KnnQueryBuilder;
import org.elasticsearch.action.search.SearchRequest;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class QueryBuildTest {

    @Autowired
    ImageToVector imageToVector;

    @Autowired
    RestHighLevelClient client;



    @Test
    void String_쿼리_테스트(){
        String imageUrl="https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/62/65/e6/3e/6265e63e0bf7d2738276.jpg";
        String[] vector=imageToVector.imageUrlToVector(imageUrl);
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        KnnQueryBuilder knnQueryBuilder=new KnnQueryBuilder("vector",Similarity.L2,vector);
        searchSourceBuilder.query(knnQueryBuilder);
        String query=searchSourceBuilder.toString();
        System.out.println(query);
    }

    @Test
    void KnnQueryBuildOption테스트() throws IOException {
        String imageUrl="https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/62/65/e6/3e/6265e63e0bf7d2738276.jpg";
        String[] vectors=imageToVector.imageUrlToVector(imageUrl);
        SearchRequest searchRequest= new SearchRequest("test-image-vector-angular");
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        KnnQueryBuilder knnQueryBuilder=new KnnQueryBuilder("vector",Similarity.COSINE,vectors,100);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        searchSourceBuilder.query(knnQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse=client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        Map<String,Object> results= new HashMap<>();
        for(SearchHit hit : hits)
        {
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("검색 소스");
        System.out.println(searchSourceBuilder);

    }
    @Test
    void SimilarityFindTest(){
       if(Similarity.find("exact").toString().equals("exact")) {
           System.out.println("성공");
       }
       else {
           System.out.println("실패");
       }
    }

    @Test
    void strangeVectorTest()
    {
        boolean success=false;
        String strangeVector="[0.9217332601547241,1.523964524269104,1.6131728887557983,1.0742337703704834";
        strangeVector= ImageSearchQuery.getVectorForQuery(strangeVector);
        if(strangeVector.equals(""))
        {
            success=true;
        }
        strangeVector="[0.9217332601547241,1.523964524269104,1.6131728887557983,1.0742337703704834]";
        if(strangeVector.equals(""))
        {
            success=false;
        }
        assertThat(success).isTrue();
    }

}
