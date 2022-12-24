package com.ehc.ElasticSearchImageSearch.util;

import com.ehc.elastiknnSimilarityQuery.KnnQueryBuilder;
import com.ehc.elastiknnSimilarityQuery.Similarity;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ImageToVectorTest {


    @Autowired
    ImageToVector imageToVector;

    @Autowired
    RestHighLevelClient client;

    @Autowired
    RestClient lowClient;

    @Test
    void 이미지_url_vector화_테스트()
    {
        String imageUrl="https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/62/65/e6/3e/6265e63e0bf7d2738276.jpg";
        String vector=imageToVector.imageUrlToVector(imageUrl);
        if(!vector.equals("")&&vector!=null)
        {
            System.out.println("image vector화 성공");
            System.out.println(vector);
        }
        else
        {
            System.out.println("image vector화 실패");
        }
    }

    @Test
    String 라이브러리_테스트()
    {
        String imageUrl="https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/62/65/e6/3e/6265e63e0bf7d2738276.jpg";
        String vector=imageToVector.imageUrlToVector(imageUrl);
        String vectorForQuery="";
        Pattern pattern = Pattern.compile("(\\[)(.*?)(\\])");
        Matcher matcher= pattern.matcher(vector);
        while (matcher.find()) {
            vectorForQuery=matcher.group(2);
            if(matcher.group(1) ==  null)
                break;
        }
        String[] vectors=vectorForQuery.split(",");
        String query= KnnQueryBuilder.buildStringKnnQuery(Similarity.COSINE,"vector",0,10,vectors);
        System.out.println(query);
        return query;

    }

    @Test
    void 서치빌드연동테스트() throws IOException {
        String imageUrl="https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/62/65/e6/3e/6265e63e0bf7d2738276.jpg";
        String vector=imageToVector.imageUrlToVector(imageUrl);
        String vectorForQuery="";
        Pattern pattern = Pattern.compile("(\\[)(.*?)(\\])");
        Matcher matcher= pattern.matcher(vector);
        while (matcher.find()) {
            vectorForQuery=matcher.group(2);
            if(matcher.group(1) ==  null)
                break;
        }
        String[] vectors=vectorForQuery.split(",");
        SearchRequest searchRequest= new SearchRequest("test-image-vector-angular");
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        KnnQueryBuilder knnQueryBuilder=new KnnQueryBuilder("vector",Similarity.COSINE,vectors);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        searchSourceBuilder.query(knnQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse= client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits=searchResponse.getHits();
        List<Map<String,Object>> results=new ArrayList<>();
        for(SearchHit hit:searchHits)
        {
            Map<String,Object> result= hit.getSourceAsMap();
            results.add(result);
        }
        System.out.println("검색결과");
        System.out.println("총"+searchHits.getTotalHits()+"개");
        System.out.println(results.size()+"가 검색되었음");
        for (Map<String,Object> result:results)
        {
            System.out.println(result);
        }

    }



}