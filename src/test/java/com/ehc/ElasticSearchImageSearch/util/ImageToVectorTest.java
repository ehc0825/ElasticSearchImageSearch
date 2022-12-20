package com.ehc.ElasticSearchImageSearch.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ImageToVectorTest {


    @Autowired
    ImageToVector imageToVector;

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



}