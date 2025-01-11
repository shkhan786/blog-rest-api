package com.jambura.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    private List<PostDTO> data;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean isLastPage;
}
