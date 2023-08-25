package io.web.bi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * EsConfig.
 * 打印日志必须
 *
 * @date 2023-06-08
 */
@Configuration
public class EsConfig {

    @Bean(destroyMethod = "close")
    public org.elasticsearch.client.RestHighLevelClient restClient() {

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        org.elasticsearch.client.RestHighLevelClient client = RestClients.create(clientConfiguration).rest();
        return client;
    }

}
