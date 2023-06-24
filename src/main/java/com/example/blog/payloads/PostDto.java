package com.example.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String imageName="default.png";
    @NotEmpty
    private Date addedDate;

    private CategoryDto category;
    private UserDto user;
}
