package com.jambura.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogAPIException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public BlogAPIException(String message, HttpStatus status, String message1){

    }

}
