package com.githubtask.controller;

import com.githubtask.customexception.GithubException;
import com.githubtask.model.General;
import com.githubtask.model.Repository;
import com.githubtask.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    @GetMapping("/repositories/{username}")
    public ResponseEntity<?> getRepositories(@PathVariable String username) {
        try {
            General general = gitHubService.getGeneralData(username);
            List<Repository> repositories = gitHubService.getUserRepositories(username);
            List<Map<String, Object>> response = repositories.stream().map(repository -> Map.of(
                    "authorName", general.getName(),
                    "repositoryName", repository.getName(),
                    "ownerLogin", repository.getOwner().getLogin(),
                    "branches",
                    gitHubService.getRepositoryBranches(repository.getOwner().getLogin(), repository.getName())))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);

        } catch (GithubException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of(
                    "status", e.getStatusCode(),
                    "message", e.getMessage()));
        }
    }
}