package cn.edu.whu.ProductManager;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class SupplierService{
        @Autowired
        private SupplierMapper mapper;
        @Transactional
	public void addSupplier(Supplier s){
		mapper.insert(s);
	}
        @Transactional
	public void updateSupplier(Supplier s){
		mapper.updateById(s);
	}
        @Transactional
	public void deleteSupplier(Long id){
		mapper.deleteById(id);
	}
        @Transactional
	public List<Supplier> findSuppliers(Long id){
		LambdaQueryWrapper<Supplier> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Supplier::getId, id);
		wrapper.select(Supplier::getName);
                return mapper.selectList(wrapper);
	}
}

