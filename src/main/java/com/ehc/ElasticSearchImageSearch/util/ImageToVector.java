package com.ehc.ElasticSearchImageSearch.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageToVector {


    @Value("${imageToVectorAPI.urlToVec}")
    private String urlToVecApiUrl;

    public String imageUrlToVector(String ImageUrl){
        Map<String,Object> jsonMap=new HashMap<>();
        jsonMap.put("image_url",ImageUrl);
        ResponseEntity<String> response=vectorApiResponse(urlToVecApiUrl,jsonMap);
        String vector= response.getBody();
        return vector;
    }


    private ResponseEntity<String> vectorApiResponse(String apiUrl,Map<String,Object> jsonMap){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        HttpEntity entity=new HttpEntity<>(jsonMap,headers);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=restTemplate.exchange(apiUrl, HttpMethod.POST,entity,String.class);
        return responseEntity;
    }

}
