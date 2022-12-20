package com.ehc.ElasticSearchImageSearch.service;

import com.ehc.ElasticSearchImageSearch.dao.ImageSearchDao;
import com.ehc.ElasticSearchImageSearch.dto.ImageSearchResults;
import com.ehc.ElasticSearchImageSearch.util.ImageToVector;
import com.ehc.ElasticSearchImageSearch.util.MappingResults;
import com.ehc.ElasticSearchImageSearch.util.similarity.SimilarityEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageSearchService {

    @Autowired
    ImageToVector imageToVector;
    @Autowired
    ImageSearchDao imageSearchDao;
    @Autowired
    MappingResults mappingResults;

    public void putImageService(){

    }
    public ImageSearchResults searchImageService(String imageUrl,String similarityName,int from,int size) throws JsonProcessingException {
        String vector=imageToVector.imageUrlToVector(imageUrl);//url의 이미지를 백터화 한다.
        String query= SimilarityEnum.getImageQueryBySimilarity(similarityName,from,size,vector);//이미지 쿼리를 얻어온다.
        String results=imageSearchDao.imageSearch(query,similarityName);//이미지 검색 수행
        ImageSearchResults imageSearchResults=mappingResults.mappingImageSearchResults(results);//json 형태의 String 으로 넘어온 결과를 결과 값에 맞게 맵핑한다.
        return imageSearchResults;
    }
}
