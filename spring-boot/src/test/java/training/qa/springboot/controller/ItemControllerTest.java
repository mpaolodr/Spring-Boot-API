package training.qa.springboot.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import training.qa.springboot.domain.Item;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ItemControllerTest {

	@Autowired
	private MockMvc mock; // mock our controller as well as any relevant mappers

	@Autowired
	private ObjectMapper mapper; // converts requests to JSON format

	@Test
	public void testCreate() throws Exception {
		Item item = new Item("Test Item 1", 10.99);

//		convert to JSON
		String itemAsJSON = this.mapper.writeValueAsString(item);

//		mock request
		RequestBuilder mockRequest = post("/item/add-item").contentType(MediaType.APPLICATION_JSON).content(itemAsJSON);

//		only if expected result is an instance
////		create saved item
//		Item savedItem = new Item(1L, "Test Item 1", 10.99);
//		
////		convert our saved item to JSON
//		String savedItemAsJSON = this.mapper.writeValueAsString(savedItem);

//		check that status is 201
		ResultMatcher matchStatus = status().isCreated();

//		check response body
//		ResultMatcher matchBody = content().json(savedItemAsJSON);
		ResultMatcher matchBody = content().string("Item successfully added");

//		build the request
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchBody);

//		other way
//		this.mock.perform(
//
//				post("/item/add-item").contentType(MediaType.APPLICATION_JSON)
//						.content(this.mapper.writeValueAsString(new Item("Test Item 1", 10.99)))
//
//		).andExpect(status().isCreated()).andExpect(content().string("Item successfully added"));
	}
	
	@Test
	public void testReadAll() throws Exception{
		
		String item1 = this.mapper.writeValueAsString(new Item("Test Item 1", 10.99));
		String item2 = this.mapper.writeValueAsString(new Item("Test Item 2", 20.99));
		String item3 = this.mapper.writeValueAsString(new Item("Test Item 3", 30.99));
		
		this.mock.perform(post("/item/add-item").contentType(MediaType.APPLICATION_JSON).content(item1));
		this.mock.perform(post("/item/add-item").contentType(MediaType.APPLICATION_JSON).content(item2));
		this.mock.perform(post("/item/add-item").contentType(MediaType.APPLICATION_JSON).content(item3));
		
		Item savedItem1 = new Item(1L, "Test Item 1", 10.99);
		Item savedItem2 = new Item(2L, "Test Item 2", 20.99);
		Item savedItem3 = new Item(3L, "Test Item 3", 30.99);
		
		
		List<Item> savedItems = new ArrayList<Item>();
		
		savedItems.add(savedItem1);
		savedItems.add(savedItem2);
		savedItems.add(savedItem3);
		
		
		String savedItemsAsJSON = this.mapper.writeValueAsString(savedItems);
		
		RequestBuilder mockRequest = 
				get("/item/read-all")
				.contentType(MediaType.APPLICATION_JSON);
		
		ResultMatcher matchStatus = status().isOk();
		ResultMatcher matchBody = content().string(savedItemsAsJSON);
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchBody);
	}
	
	@Test
	public void testReadOne() throws Exception{
		
		String item1 = this.mapper.writeValueAsString(new Item("Test Item 1", 10.99));
		String item2 = this.mapper.writeValueAsString(new Item("Test Item 2", 20.99));
		String item3 = this.mapper.writeValueAsString(new Item("Test Item 3", 30.99));
		
		this.mock.perform(post("/item/add-item").contentType(MediaType.APPLICATION_JSON).content(item1));
		this.mock.perform(post("/item/add-item").contentType(MediaType.APPLICATION_JSON).content(item2));
		this.mock.perform(post("/item/add-item").contentType(MediaType.APPLICATION_JSON).content(item3));
		
		String savedItem1 = "Item ID: 1, Item Name: Test Item 1, Item Price: 10.99";
		RequestBuilder mockRequest = get("/item/read/1").contentType(MediaType.APPLICATION_JSON);
		
		ResultMatcher matchStatus = status().isOk();
		ResultMatcher matchBody = content().string(savedItem1);
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchBody);
	
	}
	
	@Test
	public void testUpdate() throws Exception{
		
		String item1 = this.mapper.writeValueAsString(new Item("Test Item 1", 10.99));
		String item2 = this.mapper.writeValueAsString(new Item("Test Item 2", 20.99));
		String item3 = this.mapper.writeValueAsString(new Item("Test Item 3", 30.99));
		
		this.mock.perform(post("/item/add-item").contentType(MediaType.APPLICATION_JSON).content(item1));
		this.mock.perform(post("/item/add-item").contentType(MediaType.APPLICATION_JSON).content(item2));
		this.mock.perform(post("/item/add-item").contentType(MediaType.APPLICATION_JSON).content(item3));
		
		String updateItem2 = this.mapper.writeValueAsString(new Item(2L, "Update", 20.99));	
		
		String response = "Item updated successfully";
		
		RequestBuilder mockRequest = put("/item/update/2").contentType(MediaType.APPLICATION_JSON).content(updateItem2);
		
		ResultMatcher matchStatus = status().isAccepted();
		ResultMatcher matchBody = content().string(response);
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchBody);
	
	}
	
	@Test
	public void testDelete() throws Exception{
		
		String item1 = this.mapper.writeValueAsString(new Item("Test Item 1", 10.99));	
		this.mock.perform(post("/item/add-item").contentType(MediaType.APPLICATION_JSON).content(item1));
		
	
		String response = "Item was successfully removed";
		
		RequestBuilder mockRequest = delete("/item/remove/1").contentType(MediaType.APPLICATION_JSON);
		
		ResultMatcher matchStatus = status().isAccepted();
		ResultMatcher matchBody = content().string(response);
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchBody);
	
	}
}
