package ra.model.service;

import ra.model.dto.PersonDtoForm;
import ra.model.entity.Person;

import java.util.List;

public interface IPersonService {
    List<Person> findAll();
    Person findByID(Long id);
    void save(PersonDtoForm p);
    void delete(Long id);
}
