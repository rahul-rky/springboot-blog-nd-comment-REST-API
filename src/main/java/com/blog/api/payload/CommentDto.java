package com.blog.api.payload;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@ToString
public class CommentDto {
    private Long id;

    @NotEmpty(message = "name should not be null or empty")
    private String name;

    @NotEmpty(message = "email should not be null or empty")
    private String email;

    @NotEmpty(message = "body should not be null or empty")
    @Size(min = 10, message = "comment body should be minimum 10 character")
    private String body;

}
