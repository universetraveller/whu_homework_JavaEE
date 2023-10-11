package cn.edu.whu.ProductManager;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductManagerApplicationTests {

	@Autowired
	private ProductController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
