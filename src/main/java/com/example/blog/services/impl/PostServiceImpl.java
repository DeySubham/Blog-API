package com.example.blog.services.impl;

import com.example.blog.entities.Category;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.PostDto;
import com.example.blog.payloads.PostResponse;
import com.example.blog.repositories.CategoryRepo;
import com.example.blog.repositories.PostRepo;
import com.example.blog.repositories.UserRepo;
import com.example.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", categoryId));
        Post post = postRepo.save(dtoToPost(postDto));
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        postRepo.save(post);
        return postToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        return postToDto(postRepo.save(post));
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        postRepo.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        return postToDto(post);
    }

    @Override
    public PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        Page<Post> pagePosts = postRepo.findByCategory(category, pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> postToDto(post)).collect(Collectors.toList());
        return new PostResponse(postDtos, pageNumber, pageSize, pagePosts.getTotalElements(), pagePosts.getTotalPages(), pagePosts.isLast());
    }

    @Override
    public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
        Page<Post> pagePosts = postRepo.findByUser(user, pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> postToDto(post)).collect(Collectors.toList());
        return new PostResponse(postDtos, pageNumber, pageSize, pagePosts.getTotalElements(), pagePosts.getTotalPages(), pagePosts.isLast());
    }

    @Override
    public PostResponse getPosts(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> pagePosts = postRepo.findAll(pageable);
        List<Post> allPosts = pagePosts.getContent();
        List<PostDto> postDtos = allPosts.stream().map(post -> postToDto(post)).collect(Collectors.toList());

        return new PostResponse(postDtos, pageNumber, pageSize, pagePosts.getTotalElements(), pagePosts.getTotalPages(), pagePosts.isLast());
    }

    @Override
    public List<Post> searchPosts(String user) {
        return null;
    }

    private PostDto postToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    private Post dtoToPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
}
