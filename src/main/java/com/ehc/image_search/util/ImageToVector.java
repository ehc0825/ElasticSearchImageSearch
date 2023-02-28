package com.ehc.image_search.util;

import com.ehc.image_search.dto.ResponseVector;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class ImageToVector {


    @Value("${imageToVectorAPI.urlToVec}")
    private String urlToVecApiUrl;

    public String[] imageUrlToVector(String imageUrl){
        Map<String,Object> jsonMap=new HashMap<>();
        jsonMap.put("image_url",imageUrl);
        ResponseEntity<ResponseVector> response=vectorApiResponse(urlToVecApiUrl,jsonMap);
        return Objects.requireNonNull(response.getBody()).getVector();
    }


    private ResponseEntity<ResponseVector> vectorApiResponse(String apiUrl, Map<String,Object> jsonMap){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));
        HttpEntity<Map<String, Object>> entity=new HttpEntity<>(jsonMap,headers);
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate.exchange(apiUrl, HttpMethod.POST,entity, ResponseVector.class);
    }

}
