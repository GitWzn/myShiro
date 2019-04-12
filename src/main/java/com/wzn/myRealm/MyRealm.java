package com.wzn.myRealm;

import java.util.Set;
import javax.annotation.Resource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import com.wzn.bean.User;
import com.wzn.mapper.UserMapper;

//当没有继承AuthorizingRealm,而AuthorizingRealm继承AuthenticatingRealm时只是普通类   不会实现角色和权限的验证
@Component // 注入依赖
public class MyRealm extends AuthorizingRealm {
	@Resource    //从spring容器中获取映射接口对象
	private UserMapper userMapper;

	@Override // 进行用户登录验证     使用登录验证提交后触发此方法          参数自动接受提交到Realm的token码
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("这里是登录验证");
		// 获取用户的身份(用户名)
		String username = (String) token.getPrincipal();
		//获取用户的密码
		String password = new String((char[]) token.getCredentials());
		System.out.println("username = " + username);
		System.out.println("password = " + password);
		User user = new User(0, username, password, null);
		//使用接口对象操作数据库
		User loginUser = userMapper.findUserByNameAndPwd(user);
		System.out.println("user = " + loginUser);
		/*if (loginUser == null) {
			throw new AuthenticationException("用户名或密码错误！");
		} else {
			return new SimpleAuthenticationInfo(loginUser, loginUser.getUserPassword(), getName());
		}*/
		//登录成功后将数据保存到shiro的session中  第一个参数就是保存session的数据   第二个参数是密码  shiro会自动验证是否一致   一致则保存   不一致则抛出异常
		return new SimpleAuthenticationInfo(loginUser, loginUser.getUserPassword(), getName());
	}
	
	@Override // 进行授权验证     角色与权限     只有使用注解后才会触发此方法      参数是shiro的session数据
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("这里是授权验证");
		String username = ((User) principals.getPrimaryPrincipal()).getUserName();
		System.out.println("用户名：" + username);
		Set<String> roles = userMapper.findUserRoleByName(username);
		Set<String> permissions = userMapper.findUserPermissionByName(username);
		System.out.println("用户角色 ：" + roles);
		System.out.println("用户权限 ：" + permissions);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//将角色和权限提交    为后面的操作提供验证
		info.setRoles(roles);
		info.setStringPermissions(permissions);
		return info;
	}
}
