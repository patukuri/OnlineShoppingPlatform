
package com.securecoding.onelineshoppingplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.securecoding.onelineshoppingplatform.Model.Category;
import com.securecoding.onelineshoppingplatform.Model.Product;
import com.securecoding.onelineshoppingplatform.repository.CategoryRepository;
import com.securecoding.onelineshoppingplatform.repository.OrderRepository;
import com.securecoding.onelineshoppingplatform.repository.ProductRepository;

@SpringBootTest
class OnlineShoppingPlatformApplicationTests {

	@Autowired
	CategoryRepository cRepo;

	@Autowired
	ProductRepository pRepo;

	@Autowired
	OrderRepository oRepo;

	@Test

	@Order(1)
	public void testCreat() {

		Category c = new Category();

		c.setCategoryName("ReUsables");
		c.setDescription("This is testing item");
		c.setId(1);
		c.setImageUrl("testing.html");

		cRepo.save(c);
		assertNotNull(cRepo.findById(1).get()); // cRepo.delete(c);

	}

	@Test

	@Order(2)
	public void testReadAll() {
		List list = cRepo.findAll();
		assertThat(list).size().isGreaterThan(0);
	}

	@Test

	@Order(3)
	public void testRead() {
		Category category = cRepo.findById(1).get();
		assertEquals("ReUsables", category.getCategoryName());
	}

	@Test

	@Order(4)
	public void testUpdate() {
		Category p = cRepo.findById(1).get();
		p.setCategoryName("Water made");
		;
		cRepo.save(p);
		assertNotEquals("ReUsables", cRepo.findById(1).get().getCategoryName());
	}

	


	@Test

	@Order(8)
	public void getOrders() {
		List<com.securecoding.onelineshoppingplatform.Model.Order> p = oRepo.findAll(); /// p.setCategoryName("Water
																						/// made");; // cRepo.save(p);

		assertTrue(p.size() > 0);
	}

	@Test

	@Order(9)
	public void getOrder() {
		Optional<com.securecoding.onelineshoppingplatform.Model.Order> order = oRepo.findById(1); /// p.setCategoryName("Water
																									/// made");; //
																									/// cRepo.save(p);

		assertTrue(!order.isEmpty());
	}

	@Test

	@Order(10)
	public void getOrdersPrice() {
		Optional<com.securecoding.onelineshoppingplatform.Model.Order> p = oRepo.findById(1); /// p.setCategoryName("Water
																								/// made");; //
																								/// cRepo.save(p);

		assertTrue(p.get().getTotalPrice() > 0);
	}

}
