package com.jambura.blog.service.impl;

import com.jambura.blog.dto.PostDTO;
import com.jambura.blog.entity.Post;
import com.jambura.blog.exception.ResourceNotFoundException;
import com.jambura.blog.repository.PostRepository;
import com.jambura.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = dtoToEntity(postDTO);

        Post newPost = postRepository.save(post);

        return entityToDTO(newPost);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream().map(this::entityToDTO).toList();
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return entityToDTO(post);
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());
        Post updatedPost = postRepository.save(post);
        return entityToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    private PostDTO entityToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setDescription(post.getDescription());
        return postDTO;
    }

    private Post dtoToEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());
        return post;
    }
}
