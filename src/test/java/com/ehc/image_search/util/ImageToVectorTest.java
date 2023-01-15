package com.ehc.image_search.util;


import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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




    @Test
    void testImageUrl() throws IOException {
        String[] result=imageUrlToVector("https://pelicana.co.kr/resources/images/menu/best_menu03_200623.jpg");
        for(String temp : result)
        {
            System.out.println("temp = " + temp);
        }
    }



    private static final String URL_TO_VEC_API_URL = "http://192.168.249.1:29888/urlImagevector";
    private static final String POST = "POST";
    private static final String CONTENT_TYPE = "application/json";


    public static String[] imageUrlToVector(String imageUrl) throws IOException {
        URL url = new URL(URL_TO_VEC_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String json="{\"image_url\" : \"" +imageUrl+"\"}";

        connection.setRequestMethod(POST);
        connection.setRequestProperty("Content-Type", CONTENT_TYPE);
        connection.setDoOutput(true);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection
                .getOutputStream()));

        bufferedWriter.write(json);
        bufferedWriter.flush();
        bufferedWriter.close();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null)  {
            stringBuffer.append(inputLine);
        }
        bufferedReader.close();

        String response = stringBuffer.toString();

        String[] vectors = parseStringToVectorArray(response);

        return vectors;
    }

    private static String[] parseStringToVectorArray(String response) {
        Pattern pattern = Pattern.compile("(\\[)(.*?)(\\])");
        Matcher matcher = pattern.matcher(response);
        String tempArray="";
        if(matcher.find()){
            tempArray =  matcher.group(2).trim();
        }
        return tempArray.split(",");
    }




}