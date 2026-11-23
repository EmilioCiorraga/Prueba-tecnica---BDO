package com.emiliociorraga.incidencias.service.impl;

import com.emiliociorraga.incidencias.model.Issue;
import com.emiliociorraga.incidencias.repository.IssueRepository;
import com.emiliociorraga.incidencias.service.IssueService;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;

    public IssueServiceImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public void createIssue(Issue issue) {
        issue.setStatus(Issue.Status.ABIERTA);
        issue.setCreatedAt(LocalDate.now());
        issueRepository.save(issue);
    }

    @Override
    public List<Issue> getIssues(Issue.Status status) {
        if (status == null) {
            return issueRepository.findAll();
        }
        return issueRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public Issue changeStatus(Long id, Issue.Status status) {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La incidencia no existe"));

        if (issue.getStatus() == Issue.Status.CERRADA) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Una incidencia cerrada no puede reabrirse");
        }
        
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
