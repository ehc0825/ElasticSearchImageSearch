package com.ehc.imageSearch.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {


    @Value("${elasticsearch.protocol}")
    private String elastic_protocol;

    @Value("${elasticsearch.host}")
    private String elastic_host;

    @Value("${elasticsearch.port}")
    private int elastic_port;

    @Value("${elasticsearch.id}")
    private String elastic_id;

    @Value("${elasticsearch.password}")
    private String elastic_password;

    @Bean(name = "client",destroyMethod = "close")
    public RestHighLevelClient highLevelClient(){
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(elastic_host, elastic_port, elastic_protocol));

        restClientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                return requestConfigBuilder.setConnectTimeout(30*1000).setConnectTimeout(10*60*1000);
            }
        });
        if(elastic_id!=null && elastic_password!=null)
        {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(elastic_id,elastic_password));
            restClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }
            });
        }
        return new RestHighLevelClient(restClientBuilder);
    }

    @Bean(name = "lowClient", destroyMethod = "close")
    public RestClient lowLevenClient(){
       return highLevelClient().getLowLevelClient();
    }


}
