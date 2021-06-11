package training.qa.springboot.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	@PostMapping("/add-item")
	public ResponseEntity<String> addItem(@RequestBody Item item) {
		
		if (this.service.create(item)) {
			return new ResponseEntity<String>("Item successfully added", HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>("Sorry. There was a problem adding this item in the database", HttpStatus.BAD_REQUEST);
	}
	
//	Read ONE
	@GetMapping("/read/{itemId}")
	public ResponseEntity<String> readOne(@PathVariable Long itemId) {
		
		Item i = this.service.readOne(itemId);
		
		if (i != null) {
			String response = String.format("Item ID: %d, Item Name: %s, Item Price: %.2f", i.getItemId(), i.getItemName(), i.getItemPrice());
			return new ResponseEntity<String>(response, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("No item with that ID was found", HttpStatus.BAD_REQUEST);
	}
	
//	Read ALL
	@GetMapping("/read-all") 
	public ResponseEntity<List<Item>> readAll() {
		return new ResponseEntity<List<Item>>(this.service.readAll(), HttpStatus.OK);
	}
	
	
//	Update
	
	
//	Delete
	

}
