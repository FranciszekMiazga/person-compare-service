package com.task.personcompareservice.service.person;

import com.task.personcompareservice.dto.PersonDTO;
import com.task.personcompareservice.dto.UpsertResultDTO;
import com.task.personcompareservice.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> getPeople();

    Optional<Person> getPersonById(String id);

    UpsertResultDTO savePerson(PersonDTO personDTO);

    UpsertResultDTO updatePerson(final String id, final PersonDTO personDto);

    boolean deletePerson(String id);
}
