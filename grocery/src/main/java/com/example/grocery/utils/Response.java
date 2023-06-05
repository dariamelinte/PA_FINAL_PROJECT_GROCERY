package com.example.grocery.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response<T> {
    HttpStatus statusCode;
    String message;
    String error;
    T data;
}
