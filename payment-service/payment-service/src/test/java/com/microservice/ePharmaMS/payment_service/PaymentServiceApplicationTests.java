package com.microservice.ePharmaMS.payment_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentServiceApplicationTests {

	@Autowired
	private IProductRepository iProductRepository;
	@Autowired
	private ICategoryRepository iCategoryRepository;

	@Test
	void contextLoads() {
		iProductRepository.findAll().forEach(System.out::println);
		iCategoryRepository.findAll().forEach(System.out::println);
	}

	@Test
	void mapperTest() {
	}

}
