package training.qa.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.qa.springboot.domain.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {

}
