package com.task.personcompareservice.service.person;

import com.task.personcompareservice.dto.PersonDTO;
import com.task.personcompareservice.dto.UpsertResultDTO;
import com.task.personcompareservice.model.Person;
import com.task.personcompareservice.model.Task;
import com.task.personcompareservice.repository.PersonRepository;
import com.task.personcompareservice.service.task.TaskService;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.task.personcompareservice.mapper.ApiMapper.convertToPerson;
import static com.task.personcompareservice.mapper.ApiMapper.mapDtoToPerson;

@Service
@Data
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final TaskService taskService;

    public List<Person> getPeople() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(final String id) {
        return personRepository.findById(id);
    }

    @Transactional
    public UpsertResultDTO savePerson(final PersonDTO personDTO) {
        var person = convertToPerson(personDTO);
        var personCreated = personRepository.save(person);

        final Task task = taskService.createTaskForPerson(personCreated, null);
        return new UpsertResultDTO(personCreated.getId(), task.getId(), "Person created successfully");
    }

    @Transactional
    public UpsertResultDTO updatePerson(final String id, final PersonDTO personDTO) {
        Optional<Person> existingPerson = personRepository.findById(id);

        if (existingPerson.isEmpty()) {
            throw new RuntimeException("Person not found with id: " + id);
        }

        Person person = existingPerson.get();
        Person previousState = clonePerson(person);

        mapDtoToPerson(personDTO, person);
        var updatedPerson = personRepository.save(person);

        Task task = taskService.createTaskForPerson(updatedPerson, previousState);

        return new UpsertResultDTO(updatedPerson.getId(), task.getId(), "Person updated successfully");
    }

    public boolean deletePerson(final String id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.delete(person);
                    return true;
                }).orElse(false);
    }

    private Person clonePerson(Person person) {
        Person personClone = new Person();
        personClone.setId(person.getId());
        personClone.setName(person.getName());
        personClone.setSurname(person.getSurname());
        personClone.setBirthDate(person.getBirthDate());
        personClone.setCompany(person.getCompany());
        return personClone;
    }
}
