package com.task.personcompareservice.controller;

import com.task.personcompareservice.dto.TaskDTO;
import com.task.personcompareservice.mapper.ApiMapper;
import com.task.personcompareservice.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks() {
        List<TaskDTO> tasks = taskService.getTasks().stream()
                .map(ApiMapper::convertToTaskDTO)
                .toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable final String id) {
        return taskService.getTaskById(id)
                .map(ApiMapper::convertToTaskDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<List<TaskDTO>> getTasksByPersonId(@PathVariable final String personId) {
        List<TaskDTO> tasks = taskService.getTasksByPersonId(personId).stream()
                .map(ApiMapper::convertToTaskDTO)
                .toList();
        return ResponseEntity.ok(tasks);
    }
}
