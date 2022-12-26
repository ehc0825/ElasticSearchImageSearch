package com.ehc.ElasticSearchImageSearch.controller;

import com.ehc.ElasticSearchImageSearch.dto.RequestParameter;
import com.ehc.ElasticSearchImageSearch.dao.ImageSearchResults;
import com.ehc.ElasticSearchImageSearch.service.ImageSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ImageSearchRestController {

    @Autowired
    ImageSearchService imageSearchService;

    @PutMapping("/putImageFolder")
    public void putImageFolder(){

    }

    @PostMapping("/searchImage")
    public ImageSearchResults searchImage(@RequestBody RequestParameter requestParameter){
       ImageSearchResults imageSearchResults=new ImageSearchResults();
        try {
            imageSearchResults=imageSearchService.searchImage(requestParameter);
        } catch (JsonProcessingException e) {
            imageSearchResults.setResults(new ArrayList<>());
            imageSearchResults.setTotal(0);
        }
        return imageSearchResults;
    }


}
