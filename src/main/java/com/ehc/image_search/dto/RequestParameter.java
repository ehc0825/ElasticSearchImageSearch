package com.ehc.image_search.dto;

import lombok.Data;

@Data
public class RequestParameter {
    int from;
    int size;
    String similarity;
    String imageUrl;
}
