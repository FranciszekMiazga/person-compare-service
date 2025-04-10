package com.task.personcompareservice.controller;

import com.task.personcompareservice.dto.PersonDTO;
import com.task.personcompareservice.dto.UpsertResultDTO;
import com.task.personcompareservice.model.Person;
import com.task.personcompareservice.service.person.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getPeople() {
        return ResponseEntity.ok(personService.getPeople());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable final String id) {
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UpsertResultDTO> createPerson(@RequestBody @Valid final PersonDTO personDTO) {
        UpsertResultDTO response = personService.savePerson(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpsertResultDTO> updatePerson(@PathVariable final String id,
                                                        @Valid @RequestBody final PersonDTO personDto) {
        try {
            UpsertResultDTO response = personService.updatePerson(id, personDto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable final String id) {
        var deleted = personService.deletePerson(id);
        if(!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Person deleted successfully");
    }
}
