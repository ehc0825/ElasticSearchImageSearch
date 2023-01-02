package com.ehc.ElasticSearchImageSearch.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Value("${jasypt.encryptor.algorithm}")
    private String algorithm;
    @Value("${jasypt.encryptor.pool-size}")
    private int poolSize;
    @Value("${jasypt.encryptor.string-output-type}")
    private String stringOutputType;
    @Value("${jasypt.encryptor.key-obtention-iterations}")
    private int keyObtentionIterations;


    @Bean
    public StringEncryptor jasyptStringEncryptor(){
        PooledPBEStringEncryptor encryptor= new PooledPBEStringEncryptor();
        encryptor.setPoolSize(poolSize);
        encryptor.setAlgorithm(algorithm);
        encryptor.setStringOutputType(stringOutputType);
        encryptor.setPassword(getJasyptEncryptorPassword());
        encryptor.setKeyObtentionIterations(keyObtentionIterations);
        return encryptor;
    }

    private String getJasyptEncryptorPassword() {
        try {
            ClassPathResource resource = new ClassPathResource("jasypt-encryptor-password.txt");
            InputStream inputStream=resource.getInputStream();
            Reader reader = new InputStreamReader(inputStream);
            StringBuilder result = new StringBuilder();
            for (int data = reader.read(); data != -1; data = reader.read()) {
                result.append((char)data);
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException("Not found Jasypt password file.");
        }
    }
}
