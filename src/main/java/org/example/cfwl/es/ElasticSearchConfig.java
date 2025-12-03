package org.example.cfwl.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/11/26$ 17:38$
 * @Params: $
 * @Return $
 */
@Configuration
public class ElasticSearchConfig {
    @Value("${elasticsearch.uris:http://localhost:9200}")
    private String uris;
    @Bean
    public ElasticsearchClient elasticsearchClient(){
        // 1. 构建自定义 ObjectMapper，加载 JavaTimeModule（支持 LocalDateTime）
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule()); // 关键：注册 Java 8 日期模块
        // 2. 构建 RestClient
        RestClient restClient = RestClient.builder(HttpHost.create(uris)).build();

        // 3. 构建传输层，使用自定义的 ObjectMapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient,
                new JacksonJsonpMapper(objectMapper) // 传入配置好的 ObjectMapper
        );
        // 4. 创建客户端
        return new ElasticsearchClient(transport);
    }

}
