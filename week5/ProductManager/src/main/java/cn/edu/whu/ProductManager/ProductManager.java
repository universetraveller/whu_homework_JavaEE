package cn.edu.whu.ProductManager;
import org.springframework.stereotype.Service;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;
@Service
public class ProductManager{
	private Hashtable<Long, Product> products = new Hashtable<Long, Product>();
	public Product addProduct(Product prod) throws IllegalArgumentException{
		if(products.containsKey(prod.getId()))
			throw new IllegalArgumentException("Product exists. Id: " + prod.getId());
		products.put(prod.getId(), prod);
		return prod;
	}
	public Product getProduct(long id){
		return products.getOrDefault(id, null);
	}
	public List<Product> findProducts(String name, Long id, String brand, String date, Double price){
		List<Product> found = new ArrayList<Product>();
		if(id != null){
			found.add(this.getProduct(id));
			return found;
		}
		for(Product prod : products.values()){
			if(name != null && !prod.getName().contains(name))
				continue;
			if(date != null && !prod.getDate().contains(date))
				continue;
			if(brand != null && !prod.getBrand().equals(brand))
				continue;
			if(price != null && prod.getPrice() > price)
				continue;
			found.add(prod);
		}
		return found;
	}
	public void updateProduct(long id, Product prod) throws IllegalArgumentException{
		if(!products.containsKey(id))
			throw new IllegalArgumentException("No product found. Id: " + id);
		products.put(id, prod);
	}
	public void deleteProduct(long id){
		products.remove(id);
	}
}
