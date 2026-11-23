package com.emiliociorraga.incidencias.service.impl;

import com.emiliociorraga.incidencias.model.Issue;
import com.emiliociorraga.incidencias.repository.IssueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IssueServiceImplTest {

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueServiceImpl issueService;

    @Test
    void changeStatusUpdatesOpenIssue() {
        // Preparar una incidencia existente y las respuestas del repositorio
        Issue issue = new Issue();
        issue.setId(1L);
        issue.setStatus(Issue.Status.ABIERTA);
        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));
        when(issueRepository.save(issue)).thenReturn(issue);

        Issue result = issueService.changeStatus(1L, Issue.Status.EN_CURSO);

        // Comprobar el resultado y que se solicita guardar el cambio
        assertEquals(1L, result.getId());
        assertEquals(Issue.Status.EN_CURSO, result.getStatus());
        verify(issueRepository).save(issue);
    }

    @Test
    void changeStatusRejectsReopeningClosedIssue() {
        // Preparar una incidencia que ya esta cerrada
        Issue issue = new Issue();
        issue.setId(1L);
        issue.setStatus(Issue.Status.CERRADA);
        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));

        // Intentar reabrirla y capturar la excepcion esperada
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> issueService.changeStatus(1L, Issue.Status.ABIERTA));

        // Comprobar el conflicto y que la incidencia queda intacta
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals(Issue.Status.CERRADA, issue.getStatus());
        verify(issueRepository, never()).save(any(Issue.class));
    }
}
