package com.ehc.ElasticSearchImageSearch.service;

import com.ehc.ElasticSearchImageSearch.dao.ImageSearchDao;
import com.ehc.ElasticSearchImageSearch.dto.RequestParameter;
import com.ehc.ElasticSearchImageSearch.dto.ImageSearchResults;
import com.ehc.ElasticSearchImageSearch.util.ImageToVector;
import com.ehc.ElasticSearchImageSearch.util.MappingResults;
import com.ehc.ElasticSearchImageSearch.util.similarity.SimilarityEnum;
import com.ehc.elastiknnSimilarityQuery.Similarity;
import com.ehc.elastiknnSimilarityQuery.query.KnnQueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageSearchService {

    @Autowired
    ImageToVector imageToVector;
    @Autowired
    ImageSearchDao imageSearchDao;
    @Autowired
    MappingResults mappingResults;

    public void putImageService(RequestParameter requestParameter){
        String[] vector=imageToVector.imageUrlToVector(requestParameter.getImageUrl());
        String imageSrc=requestParameter.getImageUrl();
        //TODO:221231여기까지 함

    }
    public ImageSearchResults searchImage(RequestParameter requestParameter) throws IOException {
        SearchRequest searchRequest = getSearchRequest(requestParameter);
        SearchResponse searchResponse=imageSearchDao.searchImage(searchRequest);
        ImageSearchResults imageSearchResults=mappingResults.mappingImageSearchResults(searchResponse);//json 형태의 String 으로 넘어온 결과를 결과 값에 맞게 맵핑한다.
        return imageSearchResults;
    }

    private SearchRequest getSearchRequest(RequestParameter requestParameter) {
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.from(requestParameter.getFrom());
        searchSourceBuilder.size(requestParameter.getSize());
        KnnQueryBuilder knnQueryBuilder=new KnnQueryBuilder("vector",Similarity.find(requestParameter.getSimilarity()),imageToVector.imageUrlToVector(requestParameter.getImageUrl()));
        searchSourceBuilder.query(knnQueryBuilder);
        SearchRequest searchRequest=new SearchRequest(SimilarityEnum.getIndexNameBySimilarity(requestParameter.getSimilarity()));
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }
}
