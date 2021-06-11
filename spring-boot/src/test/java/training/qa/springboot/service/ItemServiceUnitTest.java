package training.qa.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		Mockito.when(this.repo.findLatestItem()).thenReturn(item1);				
				
		// Then
		assertThat(this.service.create(item1)).isEqualTo(true);
		
		Mockito.verify(this.repo, Mockito.times(1)).save(item1);
	}
	
	@Test
	public void testReadAll() {
		Item savedItem1 = new Item(1L, "Test Item 1", 10.99);
		Item savedItem2 = new Item(2L, "Test Item 2", 10.99);
		
		List<Item> savedItems = new ArrayList<Item>();
		savedItems.add(savedItem1);
		savedItems.add(savedItem2);
		
		Mockito.when(this.repo.findAll()).thenReturn(savedItems);
		
		assertThat(this.service.readAll()).isEqualTo(savedItems);
		
		Mockito.verify(this.repo, Mockito.times(1)).findAll();
		
	}
	
	@Test
	public void testReadOne() {
		
		Item savedItem1 = new Item(1L, "Test Item 1", 10.99);
		
		Mockito.when(this.repo.findById(1L)).thenReturn(Optional.of(savedItem1));
		
		assertThat(this.service.readOne(1L)).isEqualTo(savedItem1);
		
		Mockito.verify(this.repo, Mockito.times(1)).findById(1L);
		
	}
	
	@Test
	public void testUpdate() {
		Item savedItem = new Item(1L, "Test Item 1", 10.99);
		Item updatedItem = new Item(1L, "Updated", 20.99);
		
		Mockito.when(this.repo.findById(1L)).thenReturn(Optional.of(savedItem));
		Mockito.when(this.repo.save(updatedItem)).thenReturn(updatedItem);
		
		assertThat(this.service.update(1L, updatedItem).getItemName()).isEqualTo(updatedItem.getItemName());
	
		Mockito.verify(this.repo, Mockito.times(1)).findById(1L);
	}
	
	@Test 
	public void testDelete() {
		
		Item savedItem = new Item(1L, "Test Item 1", 10.99);
		
		Mockito.when(this.repo.findById(1L)).thenReturn(Optional.of(savedItem));
		
		assertThat(this.service.delete(1L)).isEqualTo(true);
		
		Mockito.verify(this.repo, Mockito.times(1)).findById(1L);
		Mockito.verify(this.repo, Mockito.times(1)).deleteById(1L);
		
		
	}
	
}
