package training.qa.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import training.qa.springboot.domain.Item;
import training.qa.springboot.repo.ItemRepo;

@SpringBootTest
public class ItemServiceUnitTest {
	
	@MockBean
	private ItemRepo repo;
	
	@Autowired
	private ItemService service;
	
	@Test
	public void testCreateUnit() {
		// Given
		
		Item item1 = new Item("Test Item 1", 10.99);
		Item item2 = new Item(1L, "Test Item 1", 10.99);
		
		// When
		Mockito.when(this.repo.save(item1)).thenReturn(item2);
		Mockito.when(this.repo.findLatestItem()).thenReturn(item2);		
				
		
		// Then
		assertThat(this.service.create(item1)).isEqualTo(true);
		
		Mockito.verify(this.repo, Mockito.times(1)).save(item1);
	}
	
}
