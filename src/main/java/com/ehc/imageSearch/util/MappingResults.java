package com.ehc.imageSearch.util;

import com.ehc.imageSearch.dto.ImageSearchResults;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MappingResults {



    public ImageSearchResults mappingImageSearchResults(SearchResponse searchResponse) {
        ImageSearchResults imageSearchResults=new ImageSearchResults();
        List<Map<String,Object>> results=new ArrayList<>();
        for(SearchHit hit:searchResponse.getHits())
        {
            results.add(hit.getSourceAsMap());
        }
        int totalCount= (int) searchResponse.getHits().getTotalHits().value;
        imageSearchResults.setResults(results);
        imageSearchResults.setTotal(totalCount);
        return imageSearchResults;
    }
}
