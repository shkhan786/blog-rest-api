package com.jambura.blog.service.impl;

import com.jambura.blog.dto.CommentDTO;
import com.jambura.blog.entity.Comment;
import com.jambura.blog.entity.Post;
import com.jambura.blog.exception.BlogAPIException;
import com.jambura.blog.exception.ResourceNotFoundException;
import com.jambura.blog.repository.CommentRepository;
import com.jambura.blog.repository.PostRepository;
import com.jambura.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Comment comment = dtoToEntity(commentDTO);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment createdComment = commentRepository.save(comment);
        return entityToDTO(createdComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream().map(this::entityToDTO).toList();
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to the post");
        }
        return entityToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }

        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());


        return entityToDTO(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }
        commentRepository.delete(comment);
    }

    private CommentDTO entityToDTO(Comment comment){

//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setBody(comment.getBody());
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());

        return modelMapper.map(comment, CommentDTO.class);
    }

    private Comment dtoToEntity(CommentDTO commentDTO){

//        Comment comment = new Comment();
//        comment.setBody(commentDTO.getBody());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());

        return modelMapper.map(commentDTO, Comment.class);
    }
}
