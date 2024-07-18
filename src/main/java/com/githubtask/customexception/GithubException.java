package com.githubtask.customexception;

import org.springframework.web.client.RestClientResponseException;
import lombok.Getter;

@Getter
public class GithubException extends RuntimeException {
    private Integer statusCode;
    private String message;

    public GithubException(Integer statusCode, String message, RestClientResponseException e) {
        super(message, e);
        this.message = message;
        this.statusCode = statusCode;
    }
}