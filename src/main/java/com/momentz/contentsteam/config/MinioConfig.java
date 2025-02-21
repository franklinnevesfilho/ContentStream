package com.momentz.contentsteam.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class MinioConfig {

    @Value("${cloud.minio.endpoint}")
    private String endpoint;

    @Value("${cloud.minio.access-key}")
    private String accessKey;

    @Value("${cloud.minio.secret-key}")
    private String secretKey;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

    }
}
