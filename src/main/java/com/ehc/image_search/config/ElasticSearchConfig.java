package com.ehc.image_search.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {


    @Value("${elasticsearch.protocol}")
    private String elasticProtocol;

    @Value("${elasticsearch.host}")
    private String elasticHost;

    @Value("${elasticsearch.port}")
    private int elasticPort;

    @Value("${elasticsearch.id}")
    private String elasticId;

    @Value("${elasticsearch.password}")
    private String elasticPassword;

    @Bean(name = "client",destroyMethod = "close")
    public RestHighLevelClient highLevelClient(){
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(elasticHost, elasticPort, elasticProtocol));

        restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(30*1000).setConnectTimeout(10*60*1000));
        if(elasticId!=null && elasticPassword!=null)
        {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(elasticId,elasticPassword));
            restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        }
        return new RestHighLevelClient(restClientBuilder);
    }

    @Bean(name = "lowClient", destroyMethod = "close")
    public RestClient lowLevenClient(){
       return highLevelClient().getLowLevelClient();
    }


}
