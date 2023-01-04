package com.ehc.image_search.service;

import com.ehc.image_search.dao.ImageSearchDao;
import com.ehc.image_search.dto.RequestParameter;
import com.ehc.image_search.dto.ImageSearchResults;
import com.ehc.image_search.util.ImageToVector;
import com.ehc.image_search.util.MappingResults;
import com.ehc.image_search.util.similarity.SimilarityEnum;
import com.ehc.elastiknnSimilarityQuery.Similarity;
import com.ehc.elastiknnSimilarityQuery.query.KnnQueryBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageSearchService {

    @Autowired
    ImageToVector imageToVector;
    @Autowired
    ImageSearchDao imageSearchDao;
    @Autowired
    MappingResults mappingResults;

    public void putImageService(RequestParameter requestParameter) throws IOException {
        IndexRequest indexRequest=setIndexRequest(requestParameter);
        IndexResponse indexResponse=imageSearchDao.indexImageDoc(indexRequest);
        //TODO:230104할일 index insert testcase 추가 , index api 완료

    }

    public ImageSearchResults searchImage(RequestParameter requestParameter) throws IOException {
        SearchRequest searchRequest = setSearchRequest(requestParameter);
        SearchResponse searchResponse=imageSearchDao.searchImage(searchRequest);
        return  mappingResults.mappingImageSearchResults(searchResponse);//json 형태의 String 으로 넘어온 결과를 결과 값에 맞게 맵핑한다
    }

    private SearchRequest setSearchRequest(RequestParameter requestParameter) {
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.from(requestParameter.getFrom());
        searchSourceBuilder.size(requestParameter.getSize());
        KnnQueryBuilder knnQueryBuilder=new KnnQueryBuilder("vector",Similarity.find(requestParameter.getSimilarity()),imageToVector.imageUrlToVector(requestParameter.getImageUrl()));
        searchSourceBuilder.query(knnQueryBuilder);
        SearchRequest searchRequest=new SearchRequest(SimilarityEnum.getIndexNameBySimilarity(requestParameter.getSimilarity()));
        searchRequest.source(searchSourceBuilder);
        return searchRequest;
    }

    private IndexRequest setIndexRequest(RequestParameter requestParameter) {
        Map<String,Object> jsonMap= setImageDocumentMap(requestParameter);
        IndexRequest indexRequest = new IndexRequest(SimilarityEnum.getIndexNameBySimilarity(requestParameter.getSimilarity())).source(jsonMap);
        return indexRequest;
    }

    private Map<String, Object> setImageDocumentMap(RequestParameter requestParameter) {
        Map<String,Object> jsonMap=new HashMap<>();
        String[] vector=imageToVector.imageUrlToVector(requestParameter.getImageUrl());
        String imageSrc=requestParameter.getImageUrl();
        jsonMap.put("fileName",imageSrc);
        jsonMap.put("vector",vector);
        return jsonMap;
    }

}
