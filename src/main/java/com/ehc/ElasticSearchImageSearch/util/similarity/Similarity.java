package com.ehc.ElasticSearchImageSearch.util.similarity;

public abstract class Similarity {
    public String similarityName;
    public abstract String queryForSimilarity(int from, int size, String vector);
}
