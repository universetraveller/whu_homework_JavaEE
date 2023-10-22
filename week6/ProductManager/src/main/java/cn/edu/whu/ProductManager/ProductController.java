package cn.edu.whu.ProductManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.util.List;
import org.springframework.beans.factory.annotation.*;
@RestController()
@RequestMapping("products")
public class ProductController{
	@Autowired
	ProductManager productManager;

	@GetMapping("/test")
	public ResponseEntity<String> testLoad(){
		return ResponseEntity.ok("Load successfully.\n");
	}

	@GetMapping("/help")
	public ResponseEntity<String> productsMsg(){
		return ResponseEntity.ok("GET /products/help -> this message\nGET /products/{id} -> get product\nGET /products?{key=value} -> find product\n" +
				"PUT /products/{id} REQUEST -> update product\nPOST /products REQUEST -> add product\n" + 
				"DELETE /products/{id} remove product\n");
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable long id){
		Product prod = productManager.getProduct(id);
		if(prod == null){
			return ResponseEntity.status(400).build();
		}
		return ResponseEntity.ok(prod);
	}

	@GetMapping("")
	public ResponseEntity<List<Product>> findProducts(String name, Long id, String brand, String date, Double price){
		return ResponseEntity.ok(productManager.findProducts(name, id, brand, date, price));
	}

	@PostMapping("")
	public ResponseEntity<Product> addProduct(@RequestBody Product prod){
		try{
			prod = productManager.addProduct(prod);
		}catch(Exception e){
			//e.printStackTrace();
			return ResponseEntity.status(400).build();
		}
		return ResponseEntity.ok(prod);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateProduct(@PathVariable long id, @RequestBody Product prod){
		try{
			productManager.updateProduct(id, prod);
		}catch(Exception e){
			//e.printStackTrace();
			return ResponseEntity.status(400).build();
		}
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable long id){
		productManager.deleteProduct(id);
		return ResponseEntity.ok().build();
	}

}
