package org.example.cfwl.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;

/**
 * @Author: 张文化
 * @Description: $
 * @DateTime: 2025/11/26$ 17:38$
 * @Params: $
 * @Return $
 */
public class ElasticSearchConfig {
    @Bean
    public RestHighLevelClient client(){
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://localhost:9200")
        ));
    }

}
