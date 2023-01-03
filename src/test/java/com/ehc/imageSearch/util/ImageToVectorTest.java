package com.ehc.imageSearch.util;


import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        String[] vectors=imageToVector.imageUrlToVector(imageUrl);
       for(String vector:vectors)
       {
           System.out.println(vector);
       }

    }





}