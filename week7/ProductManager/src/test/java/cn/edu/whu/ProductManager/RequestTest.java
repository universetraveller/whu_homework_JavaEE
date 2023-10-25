package cn.edu.whu.ProductManager;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestTest{
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testAddProduct() throws Exception{
		String request = "{\"id\":1, \"price\": 5.0, \"date\": \"date1\", \"brand\": \"brand1\", \"name\": \"Name of product1\"}";
		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(request).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(1));
		String request2 = "{\"id\":2, \"price\": 6.0, \"date\": \"date2\", \"brand\": \"brand2\", \"name\": \"Name of product2\"}";
		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(request2).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(2));
		String errorRequset ="{\"id\":2, \"price\": 1.0, \"date\": \"date2\", \"brand\": \"brand2\", \"name\": \"Name of product2\"}";
		mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON).content(errorRequset)
            .accept(MediaType.APPLICATION_JSON)) 
            .andExpect(status().isBadRequest());
	}

	@Test
	public void testGetProduct() throws Exception{
		String request = "{\"id\":100, \"price\": 5.0, \"date\": \"date1\", \"brand\": \"brand1\", \"name\": \"Name of product1\"}";
		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(request).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(100));
		mockMvc.perform(get("/products/100")).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(100));
		mockMvc.perform(get("/products/101")).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void testFindProducts() throws Exception{
		String request = "{\"id\":101, \"price\": 5.0, \"date\": \"date1\", \"brand\": \"testFindProducts\", \"name\": \"Name of product1\"}";
		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(request).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(101));
		String request1 = "{\"id\":102, \"price\": 5.0, \"date\": \"date1\", \"brand\": \"testFindProducts\", \"name\": \"Name of product1\"}";
		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(request1).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(102));
		mockMvc.perform(get("/products?brand=testFindProducts")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("},{")));
		mockMvc.perform(get("/products")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("},{")));
	}
	@Test
	public void testUpdateProduct() throws Exception{
		String request = "{\"id\":103, \"price\": 5.0, \"date\": \"date1\", \"brand\": \"testUpdateProduct\", \"name\": \"Name of product1\"}";
		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(request).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(103));
		String request1 = "{\"id\":104, \"price\": 5.0, \"date\": \"date1\", \"brand\": \"testUpdateProduct\", \"name\": \"Name of product1\"}";
		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(request1).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(104));
		String request2 = "{\"id\":103, \"price\": 5.0, \"date\": \"date1\", \"brand\": \"updatedProduct\", \"name\": \"Name of product1\"}";
		mockMvc.perform(put("/products/103").contentType(MediaType.APPLICATION_JSON).content(request2)).andDo(print()).andExpect(status().isOk());
		mockMvc.perform(put("/products/105").contentType(MediaType.APPLICATION_JSON).content(request2)).andDo(print()).andExpect(status().isBadRequest());

	}
	@Test
	public void testDeleteProduct() throws Exception{
		String request = "{\"id\":10, \"price\": 5.0, \"date\": \"date1\", \"brand\": \"brand1\", \"name\": \"Name of product1\"}";
		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(request).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(10));
		String request2 = "{\"id\":11, \"price\": 6.0, \"date\": \"date2\", \"brand\": \"brand2\", \"name\": \"Name of product2\"}";
		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(request2).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(11));
		mockMvc.perform(delete("/products/11")).andDo(print()).andExpect(status().isOk());
		mockMvc.perform(get("/products/10")).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(10));
		mockMvc.perform(get("/products/11")).andDo(print()).andExpect(status().isBadRequest());
	}
}
