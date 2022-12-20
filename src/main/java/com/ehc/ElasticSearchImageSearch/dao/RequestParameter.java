package com.ehc.ElasticSearchImageSearch.dao;

import lombok.Data;

@Data
public class RequestParameter {
    int from;
    int size;
    String similarity;
    String imageUrl;
}
