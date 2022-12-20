package com.ehc.ElasticSearchImageSearch.dao;

import com.ehc.ElasticSearchImageSearch.util.similarity.SimilarityEnum;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class ImageSearchDao {

    @Autowired
    RestClient lowClient;

    public String imageSearch(String query,String similarityName){
        String results="";
        String indexName= SimilarityEnum.getIndexNameBySimilarity(similarityName);
        Response response=searchResponse(query,indexName);
        if(response!=null)
        {
            try {
                results = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private Response searchResponse(String query,String indexName)
    {
        Request request=new Request("GET","/"+indexName+"/_search");
        request.setJsonEntity(query);
        Response response = null;
        try {
            response=lowClient.performRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
