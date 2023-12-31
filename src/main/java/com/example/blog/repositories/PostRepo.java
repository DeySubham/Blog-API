package com.example.blog.repositories;

import com.example.blog.entities.Category;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer> {

    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);
}
