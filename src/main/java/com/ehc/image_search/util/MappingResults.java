package com.ehc.image_search.util;

import com.ehc.image_search.dto.ImageSearchResults;
import lombok.experimental.UtilityClass;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@UtilityClass
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
