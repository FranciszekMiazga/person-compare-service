package com.task.personcompareservice.mapper;

import com.task.personcompareservice.dto.PersonDTO;
import com.task.personcompareservice.dto.TaskDTO;
import com.task.personcompareservice.model.Person;
import com.task.personcompareservice.model.Task;

public class ApiMapper {

    public static Person convertToPerson(final PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setSurname(personDTO.getSurname());
        person.setBirthDate(personDTO.getBirthDate());
        person.setCompany(personDTO.getCompany());
        return person;
    }

    public static TaskDTO convertToTaskDTO(final Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setProgress(task.getProgress());
        taskDTO.setPersonId(task.getPerson().getId());
        taskDTO.setResults(task.getResults());
        return taskDTO;
    }

    public static void mapDtoToPerson(PersonDTO dto, Person person) {
        person.setName(dto.getName());
        person.setSurname(dto.getSurname());
        person.setBirthDate(dto.getBirthDate());
        person.setCompany(dto.getCompany());
    }
}
