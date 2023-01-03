package com.ehc.image_search.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class JasyptConfigTest {
    @Autowired
    JasyptConfig jasyptConfig;

    @Test
    void encrypt_Test(){
        String str="password";
        String encrypt_Password=jasyptConfig.jasyptStringEncryptor().encrypt(str);
        System.out.println("encrypt_Password =>"+encrypt_Password);
        assertThat(encrypt_Password).isNotEqualTo(str);
    }
}