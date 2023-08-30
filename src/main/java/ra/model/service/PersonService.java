package ra.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import ra.model.dto.PersonDtoForm;
import ra.model.entity.Person;
import ra.model.repository.IPerSonRepository;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IPersonService{
    private String pathImage = "C:\\Users\\hung1\\DemoLogin\\hibernate-Orm\\src\\main\\webapp\\WEB-INF\\upload\\";

    @Autowired
    private IPerSonRepository personRepository;
    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findByID(Long id) {
        Optional<Person> o= personRepository.findById(id);
        if (o.isPresent()){
            return o.get();
        }
        return null;
    }

    @Override
    public void save(PersonDtoForm p) {
        // xử lí chuyển đổi
        // upload file
        String filename = null;
        if(!(p.getAvatar().isEmpty())){
            filename = p.getAvatar().getOriginalFilename();
            try {
                FileCopyUtils.copy(p.getAvatar().getBytes(),new File(pathImage+filename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // chuyển từ dto thành entity
        Person person = new Person(p.getId()
                ,p.getName(),p.getAge()
                ,filename,p.isSex(),p.getAddress());
        personRepository.save(person);
    }

    @Override
    public void delete(Long id) {
        personRepository.delete(findByID(id));
    }
}
