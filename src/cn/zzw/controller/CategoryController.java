package cn.zzw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.zzw.pojo.Category;
import cn.zzw.service.BusinessService;


@Controller
@RequestMapping("/Category")
public class CategoryController {
	
	 @Autowired
	  private BusinessService service;
	
	
       @RequestMapping("/find/{id}")
       public String  findCategory( @PathVariable ("id") String  id,Model model){
    	   Category category= service.findCategory(id);
    	   model.addAttribute("category", category);
    	   return "/pages/editcategory.jsp";
       }
       /*通过@PathVariable可以接收url中传入的参数
 	   *@RequestMapping("/find/{id}")中接收参数使用大括号中加上变量名称, @PathVariable中的变量名称要和RequestMapping
        */
       @RequestMapping("/update")
       public String updateCategory(Category category,Model model){
    	   try{
    		   service.updateCategory(category);
    		   model.addAttribute("message", "更新成功!!");
    	     }catch(Exception e) {
    	    	 e.printStackTrace();
    	    	 model.addAttribute("message", "更新失败!!");
    	     }
    	  return "/message.jsp";
       }
       @RequestMapping("/delete/{id}")
       public String deleteCategory(  @PathVariable ("id") String id,Model model) {
    	   try{
    		     service.deleteCategory(id);
    		     return "/Category/getAll.action";
    	     }catch(Exception e) {
    	    	 e.printStackTrace();
    	    	 model.addAttribute("message", "删除失败，分类正在使用!!");
    	    	 return "/message.jsp";
    	     }
       }
       @RequestMapping("/getAll")
       public String getAllCategory(Model model) {
    	     List<Category> list =service.getAllCategory();
    	     model.addAttribute("categories", list);
    	     return "/pages/listcategory.jsp";
       }
       @RequestMapping("/add")
       public String addCategory(Category category,Model model) {
    	   try {   			
   			category.setId(UUID.randomUUID().toString());
   			service.addCategory(category);
   			model.addAttribute("message", "添加成功!!");
   		} catch (Exception e) {
   			e.printStackTrace();
   			model.addAttribute("message", "添加失败!!");
   		}
   		return  "/message.jsp";
       }
}
