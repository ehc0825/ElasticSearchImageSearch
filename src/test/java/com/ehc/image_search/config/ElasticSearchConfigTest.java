package com.ehc.image_search.config;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ElasticSearchConfigTest {

    @Autowired
    RestHighLevelClient client;

    @Autowired
    RestClient lowClient;

    @Test
    void highLevelClient_connection_test(){
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
        assertThat(success).isTrue();
    }

    @Test
    void lowLevelClient_connection_test() {
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
        assertThat(success).isTrue();
    }

}