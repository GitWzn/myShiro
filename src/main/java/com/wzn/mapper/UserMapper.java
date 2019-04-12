package com.wzn.mapper;

import java.util.Set;

import com.wzn.bean.User;

public interface UserMapper {
	//以用户名和密码查询用户数据
	public User findUserByNameAndPwd(User user);
	//定义出查询用户相关角色和权限的方法
	public Set<String> findUserRoleByName(String username);
	public Set<String> findUserPermissionByName(String username);
}
