package com.task.personcompareservice.service.task;

import com.task.personcompareservice.model.Person;
import com.task.personcompareservice.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> getTasks();

    Optional<Task> getTaskById(String id);

    Task createTaskForPerson(Person actualPerson, Person previousPerson);

    List<Task> getTasksByPersonId(String personId);

}
