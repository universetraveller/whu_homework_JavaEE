package cn.edu.whu.ProductManager;
import com.baomidou.mybatisplus.extension.api.R;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.sqlite.SQLiteException;

@SpringBootTest
public class ProductMapperTest{
	@Autowired
	ProductManager m; 

	@Autowired
	ProductMapper mapper;

	@Autowired
	SupplierService ss;

	@Test
	public void testAddProduct() throws Exception{
		Product p = new Product();
		p.setId(461L);
		p.setDate("date1");
		p.setBrand("brand1");
		p.setPrice(1.0);
		p.setName("p1");
		m.addProduct(p);
		assertEquals(p.getName(), mapper.selectById(461L).getName());
	}

	@Test
	public void testGetProduct() throws Exception{
		Product p = new Product();
		p.setId(462L);
		p.setDate("date1");
		p.setBrand("brand1");
		p.setPrice(1.0);
		p.setName("p1");
		m.addProduct(p);
		assertEquals(m.getProduct(462L).getName(), "p1");
		assertNull(m.getProduct(463L));
	}

	@Test
	public void testFindProducts() throws Exception{
		Product p = new Product();
		p.setId(463L);
		p.setDate("date1");
		p.setBrand("brand1");
		p.setPrice(1.0);
		p.setName("p2");
		m.addProduct(p);
		p = new Product();
		p.setId(464L);
		p.setDate("date1");
		p.setBrand("brand1");
		p.setPrice(1.0);
		p.setName("p2");
		m.addProduct(p);
		assertEquals(2, m.findProducts("p2", null, null, null, null).size());
		p = new Product();
		p.setId(465L);
		p.setDate("date1");
		p.setBrand("brand1");
		p.setPrice(1.0);
		p.setName("p2");
		m.addProduct(p);
		assertEquals(3, m.findProducts("p2", null, null, null, null).size());
	}

	@Test
	public void testUpdateProduct() throws Exception{
		Product p = new Product();
		p.setId(466L);
		p.setDate("date1");
		p.setBrand("brand1");
		p.setPrice(1.0);
		p.setName("p3");
		m.addProduct(p);
		assertEquals("date1", m.getProduct(466L).getDate());
		p = new Product();
		p.setId(466L);
		p.setDate("date2");
		p.setBrand("brand1");
		p.setPrice(1.0);
		p.setName("p3");
		m.updateProduct(466L, p);
		assertEquals("date2", m.getProduct(466L).getDate());
		boolean pass = false;
		try{
			p = new Product();
			p.setId(467L);
			p.setDate("date2");
			p.setBrand("brand1");
			p.setPrice(1.0);
			p.setName("p3");
			m.updateProduct(466L, p);
		}catch(Exception e){
			pass = true;
		}
		assertTrue(pass);
	}

	@Test
	public void testDeleteProduct() throws Exception{
		Product p = new Product();
		p.setId(467L);
		p.setDate("date1");
		p.setBrand("brand1");
		p.setPrice(1.0);
		p.setName("p3");
		m.addProduct(p);
		assertEquals(m.getProduct(467L).getName(), "p3");
		m.deleteProduct(467L);
		assertNull(m.getProduct(467L));
	}

	@Test
	public void testQueryingSuppliers() throws Exception{
		Product p = new Product();
		p.setId(468L);
		p.setDate("date1");
		p.setBrand("brand1");
		p.setPrice(1.0);
		p.setName("p3");
		m.addProduct(p);
		p = new Product();
		p.setId(469L);
		p.setDate("date1");
		p.setBrand("brand1");
		p.setPrice(1.0);
		p.setName("p3");
		m.addProduct(p);
		Supplier s = new Supplier();
		s.setId(468L);
		s.setName("s1");
		ss.addSupplier(s);
		s = new Supplier();
		s.setId(469L);
		s.setName("s2");
		ss.addSupplier(s);
		s = new Supplier();
		s.setId(468L);
		s.setName("s3");
		ss.addSupplier(s);
		s = new Supplier();
		s.setId(469L);
		s.setName("s4");
		ss.addSupplier(s);
		s = new Supplier();
		s.setId(468L);
		s.setName("s5");
		ss.addSupplier(s);
		assertEquals(3, m.findSuppliersById(468L).size());
		assertEquals(2, m.findSuppliersById(469L).size());
	}
}
