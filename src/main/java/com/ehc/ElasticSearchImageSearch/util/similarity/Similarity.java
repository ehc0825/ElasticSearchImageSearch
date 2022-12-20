package com.ehc.ElasticSearchImageSearch.util.similarity;

public abstract class Similarity {
    public String similarity;
    public abstract String queryForSimalarity(String from, String size, String vector);
}
