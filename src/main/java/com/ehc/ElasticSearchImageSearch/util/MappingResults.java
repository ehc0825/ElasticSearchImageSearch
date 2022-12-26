package com.ehc.ElasticSearchImageSearch.util;

import com.ehc.ElasticSearchImageSearch.dto.ImageSearchResults;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MappingResults {



    public ImageSearchResults mappingImageSearchResults(String results) throws JsonProcessingException {
        ImageSearchResults imageSearchResults=new ImageSearchResults();
        Map<Object,Object> searchResult=new ObjectMapper().readValue(results,Map.class);
        Map<String,Object> searchHits= (Map<String, Object>) searchResult.get("hits");
        Map<String,Object> totalCountMap= (Map<String, Object>) searchHits.get("total");
        int totalCount= (int) totalCountMap.get("value");
        List<Map<String,Object>> resultsHits= (List<Map<String, Object>>) searchHits.get("hits");
        imageSearchResults.setResults(resultsHits);
        imageSearchResults.setTotal(totalCount);
        return imageSearchResults;
    }
}
