package org.dms.document.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dms.document.commun.FileMethode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

@Configuration
@Slf4j
public class AppConfig {
    @Value("${spring.minio.access-key}")
    private String minioAccessKey;

    @Value("${spring.minio.secret-key}")
    private String minioSecretKey;

    @Value("${spring.minio.url}")
    private String minioUrl;

    @Bean
    public FileMethode fileMethode() {
        return new FileMethode();
    }
    @Bean
    public MinioClient minioClient(){
        log.info("Start Call AppConfig.minioClient with url {}, accessKey {}, secretKey {}", minioUrl, minioAccessKey, minioSecretKey);
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioAccessKey, minioSecretKey)
                .build();
    }
}
