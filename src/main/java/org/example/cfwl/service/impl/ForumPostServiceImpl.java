package org.example.cfwl.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import co.elastic.clients.elasticsearch._types.SortOrder;
import org.example.cfwl.mapper.ForumPostMapper;
import org.example.cfwl.model.forum.dto.ForumPostSearchDto;
import org.example.cfwl.model.forum.po.ForumPost;
import org.example.cfwl.repository.ForumPostRepository;
import org.example.cfwl.service.ForumPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 张文化
 * @Description: 论坛帖子Service实现类
 * @DateTime: 2025/11/27 17:25
 * @Params: 
 * @Return 
 */
@Service
public class ForumPostServiceImpl extends ServiceImpl<ForumPostMapper, ForumPost> implements ForumPostService {
    @Resource
    private ForumPostMapper forumPostMapper;
    @Resource
    private ElasticsearchClient client;
    @Resource
    private ForumPostRepository forumPostRepository;
    @Override
    public List<ForumPost> getForumSummaryInfo() {
        return forumPostMapper.selectList(new LambdaQueryWrapper<ForumPost>().eq(ForumPost::getStatus,1));
    }

    @Override
    public List<ForumPost> getForumSummaryInfoByKey(ForumPostSearchDto forumPostSearchDto) throws IOException {
//        // 1. 构建搜索请求（新客户端链式构建器，替代旧的 SearchSourceBuilder）
//        SearchRequest request = SearchRequest.of(builder -> builder
//                .index("forum") // 指定索引
//                // 2. 多字段匹配查询（替代旧的 QueryBuilders.multiMatchQuery）
//                .query(q -> q
//                        .multiMatch(m -> m
//                                .query(forumPostSearchDto.getKeyword()) // 搜索关键词
//                                .fields("title", "contentText", "summary") // 要匹配的字段
//                        )
//                )
//                // 3. 分页配置（替代 sourceBuilder.from()/size()）
//                .from(0)
//                .size(2001) // 注意：size=10000 可能触发 ES 分页限制，建议用 scroll 或 search after（如需大量数据）
//                // 4. 排序配置（替代 sourceBuilder.sort()）
//                .sort(s -> s
//                        .field(f -> f
//                                .field("create_time") // 排序字段
//                                .order(SortOrder.Desc) // 排序方向（Desc/Asc）
//                        )
//                )
//        );
//        // 5. 执行搜索（新客户端语法：第二个参数传入实体类，自动反序列化）
//        SearchResponse<ForumPost> response = client.search(request, ForumPost.class);
//        // 6. 解析结果（无需手动 Map 转对象，直接获取实体类）
//        List<ForumPost> resultList = new ArrayList<>();
//        for (Hit<ForumPost> hit : response.hits().hits()) {
//            try {
//                ForumPost post = hit.source(); // 自动反序列化为 ForumPost，无需手动 set 字段
//                if (post != null) {
//                    resultList.add(post);
//                }
//            } catch (Exception e) {
//                // 修复日志：传入 hit 具体内容和异常堆栈，便于排查
//                log.error("转换ES结果失败, hit内容: {}");
//            }
//        }
//        return resultList;
        return forumPostRepository.findByTitleContainingOrContentTextContainingOrSummaryContaining(forumPostSearchDto.getKeyword(), forumPostSearchDto.getKeyword(), forumPostSearchDto.getKeyword());
    }
}