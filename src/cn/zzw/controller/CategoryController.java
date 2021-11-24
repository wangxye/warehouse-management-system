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
       /*ͨ��@PathVariable���Խ���url�д���Ĳ���
 	   *@RequestMapping("/find/{id}")�н��ղ���ʹ�ô������м��ϱ�������, @PathVariable�еı�������Ҫ��RequestMapping
        */
       @RequestMapping("/update")
       public String updateCategory(Category category,Model model){
    	   try{
    		   service.updateCategory(category);
    		   model.addAttribute("message", "���³ɹ�!!");
    	     }catch(Exception e) {
    	    	 e.printStackTrace();
    	    	 model.addAttribute("message", "����ʧ��!!");
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
    	    	 model.addAttribute("message", "ɾ��ʧ�ܣ���������ʹ��!!");
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
   			model.addAttribute("message", "��ӳɹ�!!");
   		} catch (Exception e) {
   			e.printStackTrace();
   			model.addAttribute("message", "���ʧ��!!");
   		}
   		return  "/message.jsp";
       }
}
