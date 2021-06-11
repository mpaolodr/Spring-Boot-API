package training.qa.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import training.qa.springboot.domain.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
	
	@Query(value = "SELECT * FROM Item ORDER BY item_id DESC LIMIT 1", nativeQuery=true)
	Item findLatestItem();
}
