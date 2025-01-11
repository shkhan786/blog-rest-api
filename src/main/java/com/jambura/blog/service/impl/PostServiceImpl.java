package com.jambura.blog.service.impl;

import com.jambura.blog.dto.PostDTO;
import com.jambura.blog.dto.PostResponse;
import com.jambura.blog.entity.Post;
import com.jambura.blog.exception.ResourceNotFoundException;
import com.jambura.blog.repository.PostRepository;
import com.jambura.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = dtoToEntity(postDTO);

        Post newPost = postRepository.save(post);

        return entityToDTO(newPost);
    }

    @Override
    public String createMultiplePost(List<PostDTO> postDTOS) {
        List<Post> posts = postDTOS.stream().map(this::dtoToEntity).toList();
        postRepository.saveAll(posts);
        return "Posts saved successfully!";
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        //Get content from page
        List<Post> posts = postPage.getContent();
        List<PostDTO> data = posts.stream().map(this::entityToDTO).toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setData(data);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setIsLastPage(postPage.isLast());

        return postResponse;
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
        //        PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setContent(post.getContent());
//        postDTO.setDescription(post.getDescription());
        return modelMapper.map(post, PostDTO.class);
    }

    private Post dtoToEntity(PostDTO postDTO) {
//        Post post = new Post();
//        post.setId(postDTO.getId());
//        post.setTitle(postDTO.getTitle());
//        post.setContent(postDTO.getContent());
//        post.setDescription(postDTO.getDescription());
        return modelMapper.map(postDTO, Post.class);
    }
}
