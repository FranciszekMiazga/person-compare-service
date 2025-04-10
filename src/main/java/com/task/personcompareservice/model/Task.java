package com.task.personcompareservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Data
@Table
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;

    private int progress;

    @ElementCollection
    @CollectionTable(name = "task_results", joinColumns = @JoinColumn(name = "task_id"))
    @MapKeyColumn(name = "field_name", length = 50)
    @Enumerated(EnumType.STRING)
    @Column(name = "classification", length = 20)
    private Map<String, Classification> results = new LinkedHashMap<>();

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "personId", nullable = false)
    private Person person;

}
