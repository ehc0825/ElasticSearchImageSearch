package com.ehc.image_search.util;


import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ImageToVectorTest {


    @Autowired
    ImageToVector imageToVector;

    @Autowired
    RestHighLevelClient client;

    @Autowired
    RestClient lowClient;

    @Test
    void image_url_to_vector_test()
    {
        boolean success=false;
        String imageUrl="https://dimg.donga.com/ugc/CDB/SHINDONGA/Article/62/65/e6/3e/6265e63e0bf7d2738276.jpg";
        String[] vectors=imageToVector.imageUrlToVector(imageUrl);
       for(String vector:vectors)
       {
           System.out.println(vector);
       }
        if(vectors.length>0)
        {
            success=true;
        }
        assertThat(success).isTrue();
    }





}