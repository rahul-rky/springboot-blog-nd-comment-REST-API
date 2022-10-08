package com.blog.api.service;

import com.blog.api.payload.PostDto;
import com.blog.api.payload.PostResponse;

public interface PostService{
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePostById(Long id);
}
