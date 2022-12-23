package com.ehc.ElasticSearchImageSearch.util;

import com.ehc.elastiknnSimilarityQuery.QueryBuilder;
import com.ehc.elastiknnSimilarityQuery.Similarity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Test
    void 라이브러리_테스트()
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
        String query= QueryBuilder.buildKnnQuery(Similarity.COSINE,"vector",0,10,vectors);
        System.out.println(query);

    }



}