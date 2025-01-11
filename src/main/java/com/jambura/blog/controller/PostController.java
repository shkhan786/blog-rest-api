package com.jambura.blog.controller;

import com.jambura.blog.dto.PostDTO;
import com.jambura.blog.dto.PostResponse;
import com.jambura.blog.service.PostService;
import com.jambura.blog.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDirection) {
        return ResponseEntity.ok(postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }


    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @PostMapping("/multiple")
    public ResponseEntity<String> createMultiplePost(@RequestBody List<PostDTO> postDTOS) {
        return new ResponseEntity<>(postService.createMultiplePost(postDTOS), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Long id) {
        return new ResponseEntity<>(postService.updatePost(id, postDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(String.format("Deleted post with id %s", id));
    }
}
