package com.rakesh.blog_application.service;

import com.rakesh.blog_application.payload.PostDto;
import com.rakesh.blog_application.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto,Long id);

    void deletePostById(Long id);
    List<PostDto> getPostsByCategory(Long categoryId);
}
