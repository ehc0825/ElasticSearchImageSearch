package com.ehc.image_search.util;

import com.ehc.elastiknnSimilarityQuery.Similarity;
import com.ehc.elastiknnSimilarityQuery.query.KnnQueryBuilder;
import com.ehc.image_search.util.similarity.SimilarityEnum;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class QueryBuildTest {

    @Autowired
    ImageToVector imageToVector;

    @Autowired
    RestHighLevelClient client;



    @Test
    void String_query_test(){
        String imageUrl="https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/62/65/e6/3e/6265e63e0bf7d2738276.jpg";
        String[] vector=imageToVector.imageUrlToVector(imageUrl);
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        KnnQueryBuilder knnQueryBuilder=new KnnQueryBuilder("vector",Similarity.L2,vector);
        searchSourceBuilder.query(knnQueryBuilder);
        String query=searchSourceBuilder.toString();
        System.out.println(query);
        assertThat(query).isNotEmpty();
    }

    @Test
    void KnnQueryBuildOptionTest() throws IOException {
        String imageUrl="https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/62/65/e6/3e/6265e63e0bf7d2738276.jpg";
        String[] vectors=imageToVector.imageUrlToVector(imageUrl);
        SearchRequest searchRequest= new SearchRequest(SimilarityEnum.getIndexNameBySimilarity(SimilarityEnum.COSINE.toString()));
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        KnnQueryBuilder knnQueryBuilder=new KnnQueryBuilder("vector",Similarity.COSINE,vectors,100);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        searchSourceBuilder.query(knnQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse=client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit : hits)
        {
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("검색 소스");
        System.out.println(searchSourceBuilder);
        assertThat(searchSourceBuilder.toString()).isNotEmpty();
    }
    @Test
    void SimilarityFindTest(){
        boolean success=false;
       if(Similarity.find("exact").toString().equals("exact")) {
          success=true;
       }
       else {
           success=false;
       }
       assertThat(success).isTrue();
    }


}
