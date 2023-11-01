package cn.edu.whu.ProductManager.security;
import cn.edu.whu.ProductManager.User;
import cn.edu.whu.ProductManager.InternalUser;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class UserService{
	@Autowired
	private UserMapper mapper;
	@Transactional
	public InternalUser addUser(InternalUser prod) throws Exception{
		prod.setPassword(new BCryptPasswordEncoder().encode(prod.getPassword()));
		mapper.insert(prod);
		return prod;
	}
	@Transactional
	@Cacheable(cacheNames = "user",key = "#id",condition = "#id!=null")
	public InternalUser getUser(String id){
		InternalUser ret = new InternalUser();
		User usr = mapper.selectById(id);
		ret.setId(id);
		ret.setPassword(usr.getPassword());
		ret.setLevel(usr.getLevel());
		ret.setUsername(usr.getUsername());
		return ret;
	}
	@Transactional
	public void deleteUser(String id){
		mapper.deleteById(id);
	}
}
