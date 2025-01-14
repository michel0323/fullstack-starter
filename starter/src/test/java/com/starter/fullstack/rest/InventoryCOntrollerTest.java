package com.starter.fullstack.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starter.fullstack.api.Inventory;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class InventoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@After
	public void teardown() {
		mongoTemplate.dropCollection(Inventory.class);
	}

	/**
	 * Test create endpoint for product.
	 * Last update on 12/08/22 by Michel T.
	 * @throws Throwable see MockMvc
	 */
	@Test
	public void create() throws Throwable {
		mockMvc.perform(post("/inventory")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getInventoryObject())))
				.andExpect(status().isOk());

		Assert.assertEquals(1, this.mongoTemplate.findAll(Inventory.class).size());
	}
	
	/**
	 * Test delete endpoint for inventory.
	 * @throws Throwable see MockMvc
	 */
	@Test
	public void removeInventory() throws Exception {
		Inventory inventory = mongoTemplate.save(getInventoryObject());
		this.mockMvc.perform(delete("/inventory/" + inventory.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(inventory.getId()))
				.andExpect(status().isOk());

		Assert.assertEquals(0, this.mongoTemplate.findAll(Inventory.class).size());
	}
	
	/*
	 * Creates a inventory object with dummy data for test use.
	 */
	public Inventory getInventoryObject() {
		Inventory inventory = new Inventory();
		inventory.setId("123465");
		inventory.setName("Dummy inventory");
		inventory.setProductType("Electronics");
		inventory.setAveragePrice(new BigDecimal(100));
		inventory.setAmount(new BigDecimal(300));
		return inventory;
	}
}