package com.emiliociorraga.incidencias.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "issues")
@Data
public class Issue {

    public enum Priority {
        BAJA, MEDIA, ALTA
    }

    public enum Status {
        ABIERTA, EN_CURSO, CERRADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "assigned_user_id")
    private Long assignedUserId;

    public Issue() {
    }

    public Issue(String title, String description, Priority priority, Status status, LocalDate createdAt, Long assignedUserId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
        this.assignedUserId = assignedUserId;
    }
}
