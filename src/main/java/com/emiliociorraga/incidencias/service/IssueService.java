package com.emiliociorraga.incidencias.service;

import java.util.List;

import com.emiliociorraga.incidencias.model.Issue;

public interface IssueService {
    void createIssue(Issue issue);
    List<Issue> getIssues(Issue.Status status);
    Issue changeStatus(Long id, Issue.Status status);
}
