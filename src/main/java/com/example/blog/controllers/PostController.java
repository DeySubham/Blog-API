package com.example.blog.controllers;

import com.example.blog.payloads.ApiResponse;
import com.example.blog.payloads.PostDto;
import com.example.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
    ) {
        PostDto createdPost = postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
        List<PostDto> posts = postService.getPostByUser(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> posts = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getPosts(
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestParam(value = "pageSize") Integer pageSize
    ) {
        return ResponseEntity.ok(postService.getPosts(pageNumber, pageSize));
    }

    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return new ApiResponse("Post deleted successfully", true);
    }

    @PutMapping("posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto updatedPost = postService.updatePost(postDto, postId);
        return ResponseEntity.ok(updatedPost);
    }
}
