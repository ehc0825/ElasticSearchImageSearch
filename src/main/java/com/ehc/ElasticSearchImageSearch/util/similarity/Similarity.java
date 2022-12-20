package com.ehc.ElasticSearchImageSearch.util.similarity;

public abstract class Similarity {
    public String similarityName;
    public abstract String queryForSimilarity(String from, String size, String vector);
}
