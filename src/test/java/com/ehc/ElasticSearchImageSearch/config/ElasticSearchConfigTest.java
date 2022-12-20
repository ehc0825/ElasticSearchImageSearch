package com.ehc.ElasticSearchImageSearch.config;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
class ElasticSearchConfigTest {

    @Autowired
    RestHighLevelClient client;

    @Autowired
    RestClient lowClient;

    @Test
    void highLevelClient연결_테스트(){
        SearchRequest searchRequest=new SearchRequest();
        boolean success=false;
        String reason="";
        try {
            client.search(searchRequest, RequestOptions.DEFAULT);
            success=true;
        } catch (IOException e) {
            e.printStackTrace();
            reason="연결 실패:"+e.getMessage();
        }
        finally {
            if (success) {
                System.out.println("연결성공");
            } else {
                System.out.println(reason);
            }
        }
    }

    @Test
    void lowLevelClient연결_테스트() {
        Request request=new Request("GET","/_search");
        boolean success=false;
        String reason="";
        try {
            lowClient.performRequest(request);
            success=true;
        } catch (IOException e) {
            e.printStackTrace();
            reason="연결 실패:"+e.getMessage();
        }
        finally {
            if(success)
            {
                System.out.println("연결성공");
            }
            else
            {
                System.out.println(reason);
            }
        }


    }

}