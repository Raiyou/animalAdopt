package com.lovepet.animal;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnimalApplicationTests {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    void contextLoads() {
        // 使用 jasypt 進行加密，加密金鑰置於 application.properties
        String url = encryptor.encrypt("jdbc:mysql://localhost:3306/animal?serverTimezone=Asia/Taipei&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull");
        String name = encryptor.encrypt("root");
        String password = encryptor.encrypt("root");
        // 輸出加密結果
        System.out.println("spring.datasource.url=ENC(" + url + ")");
        System.out.println("spring.datasource.username=ENC(" + name + ")");
        System.out.println("spring.datasource.password=ENC(" + password + ")");
        Assertions.assertTrue(url.length() > 0);
        Assertions.assertTrue(name.length() > 0);
        Assertions.assertTrue(password.length() > 0);
    }

}
