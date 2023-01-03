package com.ehc.imageSearch.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JasyptConfigTest {
    @Autowired
    JasyptConfig jasyptConfig;

    @Test
    void encrypt_Test()
    {
        String encrypt_Password=jasyptConfig.jasyptStringEncryptor().encrypt("password");
        System.out.println("encrypt_Password =>"+encrypt_Password);
    }
}