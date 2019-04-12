package com.wzn.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginAction {
	@RequestMapping("login")
	public ModelAndView login(String username, String password) {
		System.out.println("username = " + username);
		System.out.println("password = " + password);
		// 将用户名和密码封装成token码(令牌)
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		System.out.println(token);
		// 获取一个subject对象
		Subject subject = SecurityUtils.getSubject();
		// 将token码放入登录验证
		// 接下来提交到自定义的Realm中进行角色(Authentication)和权限(Authorization)的验证
		// 验证时可能出现异常 更具异常进行其他操作
		ModelAndView modelAndView = new ModelAndView("main.jsp");
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			//e.printStackTrace();
			// 将数据存储到request域中
			modelAndView.addObject("msg", "登录失败!");
			// 设置返回的页面
			modelAndView.setViewName("errorPage.jsp");
			System.out.println("登录失败!");
		}
		//获取登录成功后保存到shiro中session的数据
		System.out.println("getPrincipal: " + subject.getPrincipal());
		System.out.println("getPrincipals: " + subject.getPrincipals());
		return modelAndView;
	}
}
