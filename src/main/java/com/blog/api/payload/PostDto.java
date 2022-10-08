package com.blog.api.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "post title should at leat 2 character")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "post description should be at leat 10 character")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
