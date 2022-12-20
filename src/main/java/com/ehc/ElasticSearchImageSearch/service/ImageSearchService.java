package com.ehc.ElasticSearchImageSearch.service;

import com.ehc.ElasticSearchImageSearch.util.ImageToVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageSearchService {

    @Autowired
    ImageToVector imageToVector;

    public void putImageService(){

    }
    public void searchImageService(String imageUrl){
        String vector=imageToVector.imageUrlToVector(imageUrl);//url의 이미지를 백터화 한다.

    }
}
