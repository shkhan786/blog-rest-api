package com.jambura.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CommentDTO {
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Not a valid email")
    private String email;
    @NotEmpty
    @Size(min = 20, message = "Body should not be less than 20 character")
    private String body;
}
