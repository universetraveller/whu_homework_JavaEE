package cn.edu.whu.ProductManager.security;
import cn.edu.whu.ProductManager.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
