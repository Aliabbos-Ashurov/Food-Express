package com.pdp.backend.web.service.person;

import com.pdp.backend.web.model.person.Person;
import com.pdp.backend.web.repository.person.PersonRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface PersonService extends BaseService<Person, List<Person>> {
    PersonRepository repository = new PersonRepository();
}
