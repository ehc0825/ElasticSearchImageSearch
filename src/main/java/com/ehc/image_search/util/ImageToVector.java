package com.ehc.image_search.util;

import com.ehc.image_search.dto.ResponseVector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ImageToVector {


    @Value("${imageToVectorAPI.urlToVec}")
    private String urlToVecApiUrl;

    public String[] imageUrlToVector(String ImageUrl){
        Map<String,Object> jsonMap=new HashMap<>();
        jsonMap.put("image_url",ImageUrl);
        ResponseEntity<ResponseVector> response=vectorApiResponse(urlToVecApiUrl,jsonMap);
        return Objects.requireNonNull(response.getBody()).vector;
    }


    private ResponseEntity<ResponseVector> vectorApiResponse(String apiUrl, Map<String,Object> jsonMap){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        HttpEntity entity=new HttpEntity<>(jsonMap,headers);
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate.exchange(apiUrl, HttpMethod.POST,entity, ResponseVector.class);
    }

}
