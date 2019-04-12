package com.wzn.action;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//处理user的增删改查
@Controller
public class UserAction {
	
	@RequestMapping("editUser")
	@RequiresAuthentication    //次注解是要求必须登录后才可以操作次功能   判断是否登录成功是判断shiro的session中时候有用户数据
	@RequiresRoles("manager")  //指定用户的角色才可以进行此操作
	@RequiresPermissions("user:edit")
	public ModelAndView editUser() {
		System.out.println("这里是修改功能");
		return null;
	}
	@RequestMapping("addUser")
	public ModelAndView addUser() {
		System.out.println("这里是添加功能");
		return null;
	}
}
