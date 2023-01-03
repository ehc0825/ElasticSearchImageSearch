package com.ehc.image_search.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ImageSearchResults {
    private int total;
    private List<Map<String,Object>> results;
}
