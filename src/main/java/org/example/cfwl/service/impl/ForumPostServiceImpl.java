package org.example.cfwl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.example.cfwl.mapper.ForumPostMapper;
import org.example.cfwl.model.forum.dto.ForumPostSearchDto;
import org.example.cfwl.model.forum.po.ForumPost;
import org.example.cfwl.service.ForumPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private RestHighLevelClient client;
    @Override
    public List<ForumPost> getForumSummaryInfo() {
        return forumPostMapper.selectList(new LambdaQueryWrapper<ForumPost>().eq(ForumPost::getStatus,1));
    }

    @Override
    public List<ForumPost> getForumSummaryInfoByKey(ForumPostSearchDto forumPostSearchDto) throws IOException {
        SearchRequest request = new SearchRequest("forum");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.multiMatchQuery(
                forumPostSearchDto.getKeyword(),
                "title", "contentText", "summary"
        ));
        sourceBuilder.from(0);
        sourceBuilder.size(10000);
        sourceBuilder.sort("create_time", SortOrder.DESC);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        List<ForumPost> resultList = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            try {
                // 方法1.1：直接获取Map
                Map<String, Object> sourceMap = hit.getSourceAsMap();

                ForumPost post = new ForumPost();
                post.setId(convertToLong(sourceMap.get("id")));
                post.setTitle((String) sourceMap.get("title"));
                post.setContentText((String) sourceMap.get("contentText"));
                post.setSummary((String) sourceMap.get("summary"));
                post.setStatus(convertToInt(sourceMap.get("status")));

                resultList.add(post);

            } catch (Exception e) {
                log.error("转换ES结果失败, hit内容: {}");
            }
        }
        return resultList;
    }
    // 辅助方法
    private Long convertToLong(Object obj) {
        if (obj == null) return null;

        try {
            if (obj instanceof Number) {
                return ((Number) obj).longValue();
            } else if (obj instanceof String) {
                // 字符串转Long
                return Long.parseLong((String) obj);
            } else {
                // 其他类型尝试转换
                return Long.parseLong(obj.toString());
            }
        } catch (NumberFormatException e) {
            log.warn("无法转换为Long: {}");
            return null;
        }
    }

    private Integer convertToInt(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }
}