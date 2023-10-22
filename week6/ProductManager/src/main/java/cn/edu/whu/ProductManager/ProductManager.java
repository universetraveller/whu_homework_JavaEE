package cn.edu.whu.ProductManager;
import org.springframework.stereotype.Service;
//import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class ProductManager{
	//private Hashtable<Long, Product> products = new Hashtable<Long, Product>();
	@Autowired
	private ProductMapper mapper;
	@Transactional
	public Product addProduct(Product prod) throws Exception{
		mapper.insert(prod);
		return prod;
	}
	@Transactional
	public Product getProduct(Long id){
		return mapper.selectById(id);
	}
	@Transactional
	public List<Product> findProducts(String name, Long id, String brand, String date, Double price){
		LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
		if(id != null)
			wrapper.eq(Product::getId, id);
		if(name != null)
			wrapper.like(Product::getName, name);
		if(date != null)
			wrapper.like(Product::getDate, date);
		if(brand != null)
			wrapper.eq(Product::getBrand, brand);
		if(price != null)
			wrapper.le(Product::getPrice, price);
		List<Product> found = mapper.selectList(wrapper);
		return found;
	}
	@Transactional
	public void updateProduct(Long id, Product prod) throws Exception{
		if(prod.getId().compareTo(id) != 0)
			throw new Exception("Conflict id " + id + " and " + prod.getId());
		mapper.updateById(prod);
	}
	@Transactional
	public void deleteProduct(Long id){
		mapper.deleteById(id);
	}
	@Transactional
	public List<String> findSuppliersById(Long id){
		return mapper.findSuppliersById(id);
	}
}
