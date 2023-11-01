package cn.edu.whu.ProductManager.security;

import cn.edu.whu.ProductManager.InternalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建一个UserDetailsService的Bean，从数据库中读取用户和权限信息
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InternalUser user = userService.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " is not found");
        }

        return User.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(getAuthorities(user))
                .build();
    }

    private static GrantedAuthority[] getAuthorities(InternalUser user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
	int userLevel = user.getLevel();
	if(userLevel > -1)
		authorities.add(new SimpleGrantedAuthority("BASIC_ACCOUNT"));
	if(userLevel > 0)
		authorities.add(new SimpleGrantedAuthority("AUTHORITY_USER"));
	if(userLevel > 10)
		authorities.add(new SimpleGrantedAuthority("AUTHORITY_SUPPLIER"));
	if(userLevel > 100)
		authorities.add(new SimpleGrantedAuthority("AUTHORITY_ADMIN"));
	if(userLevel > 1000)
		authorities.add(new SimpleGrantedAuthority("SYS_ADMIN"));
        return authorities.toArray(new GrantedAuthority[authorities.size()]);
    }

}

