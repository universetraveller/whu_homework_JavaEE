package cn.edu.whu.ProductManager;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName(value="users")
public class User {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    String id;

    private String password; //密码在数据库中需要加密保存
	private String username;
	private int level;

}
