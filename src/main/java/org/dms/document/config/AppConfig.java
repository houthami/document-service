package org.dms.document.config;

import lombok.RequiredArgsConstructor;
import org.dms.document.commun.FileMethode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

@Configuration
public class AppConfig {
    @Bean
    public FileMethode fileMethode() {
        return new FileMethode();
    }
}
