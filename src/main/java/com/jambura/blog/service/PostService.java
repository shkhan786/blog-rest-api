package com.jambura.blog.service;

import com.jambura.blog.dto.PostDTO;
import com.jambura.blog.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    String createMultiplePost(List<PostDTO> postDTOS);
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    PostDTO getPostById(Long id);
    PostDTO updatePost(Long id, PostDTO postDTO);
    void deletePost(Long id);
}
