package com.ehc.ElasticSearchImageSearch.dao;

import com.ehc.ElasticSearchImageSearch.util.similarity.SimilarityEnum;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class ImageSearchDao {

    @Autowired
    RestClient lowClient;

    @Autowired
    RestHighLevelClient client;


    public SearchResponse searchImage(SearchRequest searchRequest) throws IOException {
            return client.search(searchRequest, RequestOptions.DEFAULT);
    }
}
