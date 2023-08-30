package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Person;

@Repository
public interface IPerSonRepository extends JpaRepository<Person,Long> {
}
