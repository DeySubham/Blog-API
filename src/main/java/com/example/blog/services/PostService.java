package com.example.blog.services;

import com.example.blog.entities.Post;
import com.example.blog.payloads.PostDto;
import com.example.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer postId);
    void deletePost(Integer postId);
    PostDto getPostById(Integer postId);
    PostResponse getPosts(Integer pageNumber, Integer pageSize);
    PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
    PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);
    List<Post> searchPosts(String user);
}
