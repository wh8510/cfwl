package org.example.cfwl.model.forum.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ForumPostSearchDto extends ForumPostPage {
    private String keyword;
}
