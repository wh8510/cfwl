package org.example.cfwl.repository;

import org.example.cfwl.model.forum.po.ForumPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ForumPostRepository extends ElasticsearchRepository<ForumPost, String> {
    Page<ForumPost> findByTitleContainingOrContentTextContainingOrSummaryContaining(String keyword1, String keyword2, String keyword3, Pageable pageable);
}
