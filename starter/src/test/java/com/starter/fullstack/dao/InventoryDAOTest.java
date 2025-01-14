package com.starter.fullstack.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.config.EmbedMongoClientOverrideConfig;

/**
 * Test Inventory DAO.
 * Last modified by Michel T. on 11/28/22
 */
@ContextConfiguration(classes = { EmbedMongoClientOverrideConfig.class })
@DataMongoTest
@RunWith(SpringRunner.class)
public class InventoryDAOTest {

	@Resource
	private MongoTemplate mongoTemplate;
	private InventoryDAO inventoryDAO;
	private static final String NAME = "Amber";
	private static final String PRODUCT_TYPE = "hops";

	@Before
	public void setup() {
		this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
	}

	@After
	public void tearDown() {
		this.mongoTemplate.dropCollection(Inventory.class);
	}

	/**
	 * Test Find All method.
	 */
	@Test
	public void findAll() {
		Inventory inventory = new Inventory();
		inventory.setName(NAME);
		inventory.setProductType(PRODUCT_TYPE);
		this.mongoTemplate.save(inventory);
		List<Inventory> actualInventory = this.inventoryDAO.findAll();
		Assert.assertFalse(actualInventory.isEmpty());
	}


	@Test
	public void create() {
		Inventory inventory = new Inventory();
		inventory.setName(NAME);
		inventory.setProductType(PRODUCT_TYPE);
		Inventory actual = inventoryDAO.create(inventory);
		assertNotNull(actual);
		assertNotNull(actual.getName());
		assertNotNull(actual.getId());
	}

	@Test
	public void deleteInventoryById() {
		Inventory inventory = getInventoryObject();
		Inventory actual = inventoryDAO.create(inventory);
		this.inventoryDAO.delete(actual.getId());
		List<Inventory> actualInventories = this.inventoryDAO.findAll();
		assertEquals(0, actualInventories.size());
	}

	/*
	 * Creates a inventory object with dummy data for test use.
	 */
	public Inventory getInventoryObject() {
		Inventory inventory = new Inventory();
		inventory.setName(NAME);
		inventory.setProductType("Electronics");
		inventory.setAveragePrice(new BigDecimal(100));
		inventory.setAmount(new BigDecimal(300));
		return inventory;
	}
	
	@Test
    public void create() {
        Inventory inventory = new Inventory();
        inventory.setName(NAME);
		inventory.setProductType(PRODUCT_TYPE);
        Inventory actual = inventoryDAO.create(inventory);
        assertNotNull(actual);
        assertNotNull(actual.getName());
        assertNotNull(actual.getId());

	}
}

