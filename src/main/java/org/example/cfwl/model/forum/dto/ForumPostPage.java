package org.example.cfwl.model.forum.dto;

import lombok.Data;

@Data
public class ForumPostPage {

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
}
