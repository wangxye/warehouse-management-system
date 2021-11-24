package cn.zzw.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.zzw.pojo.User;
import cn.zzw.service.BusinessService;

@Controller
@RequestMapping("/User")
public class UserController {
      @Autowired
      private BusinessService service;
       
      @RequestMapping("/login")
      public String login(String username,String password,Model model,HttpSession session) {
    	  User user =service.login(username,password);
    	  if(user==null) {
    		  model.addAttribute("message", "密码错误!!");
    		  return "/message.jsp";
    	  }
    	  session.setAttribute("user", user);
    	  return "/pages/index.jsp";
      }
      @RequestMapping("/Register")
      public String Regisert(User user,Model model,HttpSession session) {
    	  try {  			
  			user.setId(UUID.randomUUID().toString()); 			  			
  			service.addUser(user);
  			session.setAttribute("user", user);
  			return "/RegisterToIndex.jsp";
  		} catch (Exception e) {
  			e.printStackTrace();
  			model.addAttribute("message", "注册失败!!");
  			return "/message.jsp";
  		}
      }
      @RequestMapping("/update")
      public String update(User user,Model model,HttpSession session) {
    	  try {	 			
 			service.updateUser(user);
 			session.setAttribute("user", user);
 			model.addAttribute("message", "更新成功!!");
 		} catch (Exception e) {
 			e.printStackTrace();
 			model.addAttribute("message", "更新失败!!");
 		}
 		    return "/message.jsp";
      }
      /*通过@PathVariable可以接收url中传入的参数
	   *@RequestMapping("/find/{id}")中接收参数使用大括号中加上变量名称, @PathVariable中的变量名称要和RequestMapping
       */
      @RequestMapping("/find/{id}")
      public String find(@PathVariable("id") String id,Model model) {
    	  User user=service.findUser(id);
    	  model.addAttribute("user", user);
    	  return "/pages/Userinformation.jsp";
      }
      @RequestMapping("/findAllUser")
      public String findAllUser(Model model) {
    	 List<User> users=service.findAllUser();
    	 model.addAttribute("Users", users);
    	 return "/pages/listUser.jsp";
      }
}
