package training.qa.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import training.qa.springboot.domain.Item;
import training.qa.springboot.service.ItemService;

@RestController
public class ItemController {
	
	private ItemService service;
	
	public ItemController(ItemService service) {
		this.service = service;
	}
	
//	Create
	@GetMapping("/add-item")
	public ResponseEntity<String> addItem(Item item) {
		
		if (this.service.create(item)) {
			return new ResponseEntity<String>("Item successfully added", HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>("Sorry. There was a problem adding this item in the database", HttpStatus.BAD_REQUEST);
	}
	
//	Read
	
	
//	Update
	
	
//	Delete
	

}
