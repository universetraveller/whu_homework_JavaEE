package cn.edu.whu.ProductManager;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
@Data
@TableName(value = "suppliers")
public class Supplier{
	Long id;
	String name;
}
