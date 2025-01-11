package com.jambura.blog.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    @NotEmpty
    @Size(min = 5, message = "Post title should have at least 5 characters")
    private String title;
    @NotEmpty
    @Size(min=100, message = "Post content should have at least 100 characters")
    private String content;
    @NotEmpty
    @Size(min=20, message = "Post description should have at least 20 characters")
    private String description;
    private Set<CommentDTO> comments;
}
