package com.example.blog.services;

import com.example.blog.entities.Post;
import com.example.blog.payloads.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer postId);
    void deletePost(Integer postId);
    List<PostDto> getPosts(Integer pageNumber, Integer pageSize);
    PostDto getPostById(Integer postId);
    List<PostDto> getPostByCategory(Integer categoryId);
    List<PostDto> getPostByUser(Integer userId);
    List<Post> searchPosts(String user);
}
