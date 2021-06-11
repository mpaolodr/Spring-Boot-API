package training.qa.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import training.qa.springboot.domain.Item;
import training.qa.springboot.repo.ItemRepo;

@Service
public class ItemService {
	
	private ItemRepo repo;
	
	public ItemService(ItemRepo repo) {
		this.repo = repo;
	}
	
//	CREATE
	public boolean create(Item item) {
		this.repo.save(item);
		
		if (this.repo.findLatestItem().equals(item)) {
			return true;
		}
		
		return false;
	}
	
	
//	READ ALL
	public List<Item> readAll() {
		return this.repo.findAll();
	}
	
//	READ ONE
	public Item readOne(Long itemId) {
		
		Optional<Item> foundItem = this.repo.findById(itemId);
		
		if (foundItem.isPresent()) {
			Item i = foundItem.get();
			
			return i;
		}
		
		return null;
	}
	
	
//	UPDATE
	public Item update(Long itemId, Item newItem) {
		
		Optional<Item> toUpdate = this.repo.findById(itemId);
		
		if (toUpdate.isPresent()) {
			Item i = toUpdate.get();
			
			i.setItemName(newItem.getItemName());
			i.setItemPrice(newItem.getItemPrice());
			
			this.repo.save(i);
			
			return i;
		}
		
		return null;
	}
	
	
//	DELETE
	public boolean delete(Long itemId) {
		
		Optional<Item> toDelete = this.repo.findById(itemId);
		
		if (toDelete.isPresent()) {
			this.repo.deleteById(itemId);
			return true;
		}
		
		return false;
	}

}
