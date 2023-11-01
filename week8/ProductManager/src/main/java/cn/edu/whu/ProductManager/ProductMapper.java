package cn.edu.whu.ProductManager;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
	@Select("SELECT products.name, suppliers.name FROM products, suppliers WHERE products.id = #{inputId} and suppliers.id = #{inputId}")
	public List<String> findSuppliersById(Long inputId);
}
