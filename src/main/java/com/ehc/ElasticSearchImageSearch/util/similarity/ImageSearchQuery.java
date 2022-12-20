package com.ehc.ElasticSearchImageSearch.util.similarity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageSearchQuery {
    //모든 similarity 이미지검색 쿼리의 베이스 쿼리(앞)
    public static String basedFrontQuery(String from, String size){
        String query = "{\n" +
                "\"from\":"+from+","+
                "\"size\":"+size+","+
                "  \"query\": {\n" +
                "    \"elastiknn_nearest_neighbors\": {\n" +
                "      \"field\": " +"\"vector\","+
                "\"vec\":{\n" +
                "          \"values\": [\n";
        return query;
    }

    //cosine similarity 이미지검색 쿼리의 마지막 부분
    public static String basedTailQueryForCosine(){
        String query= "          ]\n" +
                "      },\n" +
                "\"model\":\"lsh\","+
                "\"similarity\":\"angular\","+
                "\"candidates\":50"+
                "    }\n" +
                "  }\n" +
                "}";
        return query;
    }
    //L2 similarity 이미지 검색 쿼리의 마지막 부분
    public static String basedTailQueryForL2(){
        String query= "          ]\n" +
                "      },\n" +
                "\"model\":\"lsh\","+
                "\"similarity\":\"l2\","+
                "\"candidates\":50,"+
                "\"probes\":2"+
                "    }\n" +
                "  }\n" +
                "}";
        return query;
    }
    //permutation_lsh similarity 이미지 검색 쿼리의 마지막 부분
    public static String basedTailQueryForPermutation_lsh(){
        String query= "          ]\n" +
                "      },\n" +
                "\"model\":\"permutation_lsh\","+
                "\"similarity\":\"angular\","+
                "\"candidates\":50"+
                "    }\n" +
                "  }\n" +
                "}";
        return query;
    }


    //String vector값을 query에 맞게 변화 시키기 위함
    public static String getVectorForQuery(String vector){
        String vectorForQuery="";
        Pattern pattern = Pattern.compile("(\\[)(.*?)(\\])");
        Matcher matcher= pattern.matcher(vector);
        while (matcher.find()) {
            vectorForQuery=matcher.group(2);
            if(matcher.group(1) ==  null)
                break;
        }
        return vectorForQuery;
    }
}
