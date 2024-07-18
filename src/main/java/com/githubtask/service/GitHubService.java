package com.githubtask.service;

import com.githubtask.customexception.GithubException;
import com.githubtask.model.Branch;
import com.githubtask.model.General;
import com.githubtask.model.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String GITHUB_API_URL = "https://api.github.com";

    public List<Repository> getUserRepositories(String username) {
        String url = GITHUB_API_URL + "/users/" + username + "/repos";
        Repository[] repositories = this.fetchData(url, Repository[].class);
        return repositories != null
                ? List.of(repositories).stream().filter(repo -> !repo.isFork()).collect(Collectors.toList())
                : List.of();
    }

    public List<Branch> getRepositoryBranches(String owner, String repoName) {
        String url = GITHUB_API_URL + "/repos/" + owner + "/" + repoName + "/branches";
        Branch[] branches = this.fetchData(url, Branch[].class);
        return branches != null ? List.of(branches) : List.of();
    }

    public General getGeneralData(String username) {
        String url = GITHUB_API_URL + "/users/" + username;
        General general = this.fetchData(url, General.class);
        return general != null ? general : null;
    }

    private <T> T fetchData(String url, Class<T> model) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, model).getBody();
        } catch (RestClientResponseException e) {
            throw new GithubException(e.getStatusCode().value(), e.getStatusText(), e);
        }

    }
}
