package com.githubtask.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {
    private String name;
    private String fullName;
    private Owner owner;
    private boolean fork;
}