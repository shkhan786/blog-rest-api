package com.jambura.blog.controller;

import com.jambura.blog.dto.CommentDTO;
import com.jambura.blog.entity.Comment;
import com.jambura.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "id") Long postId,@Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(postId,commentDTO), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getPostComments(@PathVariable(name = "id") Long postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long postId, @PathVariable Long commentId){
        CommentDTO commentDTO = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(commentDTO);
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId,@Valid @RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDTO));
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(String.format("Comment with id: %s for the post id: %s deleted successfully", commentId, postId));
    }

}
