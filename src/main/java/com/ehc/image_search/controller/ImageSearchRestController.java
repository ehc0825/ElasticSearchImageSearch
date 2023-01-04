package com.ehc.image_search.controller;

import com.ehc.image_search.dto.RequestParameter;
import com.ehc.image_search.dto.ImageSearchResults;
import com.ehc.image_search.service.ImageSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class ImageSearchRestController {

    @Autowired
    ImageSearchService imageSearchService;

    @PutMapping("/image")
    public String putImage(@RequestBody RequestParameter requestParameter) throws IOException {
        imageSearchService.putImageService(requestParameter);
        return "success";
    }

    @PostMapping("/searchImage")
    public ImageSearchResults searchImage(@RequestBody RequestParameter requestParameter){
       ImageSearchResults imageSearchResults=new ImageSearchResults();
        try {
            imageSearchResults=imageSearchService.searchImage(requestParameter);
        } catch (IOException e) {
            imageSearchResults.setResults(new ArrayList<>());
            imageSearchResults.setTotal(0);
        }
        return imageSearchResults;
    }


}
