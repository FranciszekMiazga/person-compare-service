package com.task.personcompareservice.service.task;

import com.task.personcompareservice.model.Person;
import com.task.personcompareservice.model.Task;
import com.task.personcompareservice.repository.TaskRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskRunner taskRunner;

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(final String id) {
        return taskRepository.findById(id);
    }

    public Task createTaskForPerson(final Person actualPerson, final Person previousPerson) {
        return createTask(actualPerson, previousPerson);
    }

    public List<Task> getTasksByPersonId(final String personId) {
        return taskRepository.findByPersonId(personId);
    }

    private Task createTask(final Person currentPerson, final Person previousPerson) {
        Task task = new Task();
        task.setPerson(currentPerson);
        var taskSaved = taskRepository.save(task);

        taskRunner.runTaskAsync(taskSaved, previousPerson, currentPerson);
        return taskSaved;
    }
}
