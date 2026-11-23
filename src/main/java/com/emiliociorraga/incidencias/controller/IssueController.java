package com.emiliociorraga.incidencias.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emiliociorraga.incidencias.model.Issue;
import com.emiliociorraga.incidencias.service.IssueService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/incidencias")
public class IssueController {

    private IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    } 

    @PostMapping
    public ResponseEntity<?> createIssue(@RequestBody Issue issue) {
        if (issue.getTitle() == null || issue.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El titulo es obligatorio");
        }
        if (issue.getTitle().length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El titulo admite como maximo 100 caracteres");
        }
        if (issue.getPriority() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La prioridad debe ser BAJA, MEDIA o ALTA");
        }

        issueService.createIssue(issue);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> getIssues(@RequestParam(required = false) Issue.Status status) {
        return ResponseEntity.ok(issueService.getIssues(status));
    }

    @PatchMapping("/{id}/estado/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @PathVariable Issue.Status status) {
        return ResponseEntity.ok(issueService.changeStatus(id, status));
    }
}
