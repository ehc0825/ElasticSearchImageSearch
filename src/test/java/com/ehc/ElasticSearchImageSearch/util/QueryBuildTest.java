package com.ehc.ElasticSearchImageSearch.util;

import com.ehc.elastiknnSimilarityQuery.KnnQueryBuilder;
import com.ehc.elastiknnSimilarityQuery.Similarity;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class QueryBuildTest {

    @Autowired
    ImageToVector imageToVector;

    @Autowired
    RestHighLevelClient client;

    @Test
    void String_쿼리_테스트(){
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
        String query= KnnQueryBuilder.buildStringKnnQuery(Similarity.PERMUTATION_LSH,"vector",0,10,vectors);
        System.out.println(query);
    }

    @Test
    void KnnQueryBuildOption테스트()
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
        SearchRequest searchRequest= new SearchRequest("test-image-vector-angular");
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        KnnQueryBuilder knnQueryBuilder=new KnnQueryBuilder("vector",Similarity.PERMUTATION_LSH,vectors,100);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        searchSourceBuilder.query(knnQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        System.out.println("검색 소스");
        System.out.println(searchSourceBuilder);
    }
}
