package org.example.cfwl;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentType;
import org.example.cfwl.es.ElasticSearchConfig;
import org.example.cfwl.model.forum.po.ForumPost;
import org.example.cfwl.service.ForumPostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class CfwlApplicationTests {
    private RestHighLevelClient client;
    @Resource
    private ForumPostService forumPostService;
    @Test
    void contextLoads() throws IOException {
        log.info("开始导入论坛数据到Elasticsearch...");

        // 1. 获取数据
        List<ForumPost> forumPosts = forumPostService.getForumSummaryInfo();
        log.info("从数据库获取到 {} 条数据", forumPosts.size());

        if (forumPosts.isEmpty()) {
            log.warn("没有数据需要导入");
            return;
        }

        // 2. 检查索引是否存在，不存在则创建
//        createIndexIfNotExists();

        // 3. 批量导入
        BulkRequest bulkRequest = new BulkRequest();
        int successCount = 0;
        int failCount = 0;
        List<Long> failedIds = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.registerModule(new JavaTimeModule());
        for (ForumPost forumPost : forumPosts) {
            try {
                IndexRequest indexRequest = new IndexRequest("forum")
                        .id(forumPost.getId().toString())
                        .source(mapper.writeValueAsString(forumPost), XContentType.JSON);
                bulkRequest.add(indexRequest);
                successCount++;
            } catch (Exception e) {
                log.error("构建索引请求失败，帖子ID: {}", forumPost.getId(), e);
                failedIds.add(forumPost.getId());
                failCount++;
            }
        }

        log.info("构建了 {} 个索引请求，成功: {}, 失败: {}",
                forumPosts.size(), successCount, failCount);

        // 4. 执行批量操作
        if (bulkRequest.numberOfActions() > 0) {
            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

            // 5. 分析响应结果
            analyzeBulkResponse(bulkResponse);

            // 6. 记录失败的文档
            if (bulkResponse.hasFailures()) {
                logFailedItems(bulkResponse);
            }
        }

        log.info("数据导入完成");
    }

    /**
     * 分析批量响应
     */
    private void analyzeBulkResponse(BulkResponse bulkResponse) {
        log.info("批量操作完成，用时: {}ms", bulkResponse.getTook().getMillis());
        log.info("总操作数: {}", bulkResponse.getItems().length);

        long created = 0;
        long updated = 0;
        long deleted = 0;
        long failed = 0;

        for (BulkItemResponse item : bulkResponse.getItems()) {
            switch (item.getOpType()) {
                case CREATE:
                    if (item.isFailed()) failed++;
                    else created++;
                    break;
                case INDEX:
                    if (item.isFailed()) failed++;
                    else updated++;
                    break;
                case DELETE:
                    if (item.isFailed()) failed++;
                    else deleted++;
                    break;
                default:
                    break;
            }
        }

        log.info("操作统计 - 创建: {}, 更新: {}, 删除: {}, 失败: {}",
                created, updated, deleted, failed);
    }

    /**
     * 记录失败的文档详情
     */
    private void logFailedItems(BulkResponse bulkResponse) {
        log.error("批量操作有失败项，失败详情:");

        for (BulkItemResponse item : bulkResponse.getItems()) {
            if (item.isFailed()) {
                BulkItemResponse.Failure failure = item.getFailure();
                log.error("文档ID: {}, 索引: {}, 失败原因: {}",
                        failure.getId(),
                        failure.getIndex(),
                        failure.getMessage());
            }
        }
    }

    @BeforeEach
    void setUp() {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://localhost:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }

}
