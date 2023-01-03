package com.ehc.imageSearch.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ImageSearchResults {
    private int total;
    private List<Map<String,Object>> results;
}
