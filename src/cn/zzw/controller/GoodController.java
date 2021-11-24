package cn.zzw.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.zzw.pojo.Category;
import cn.zzw.pojo.UpdateGoodVo;
import cn.zzw.pojo.good;
import cn.zzw.pojo.goodview;
import cn.zzw.service.BusinessService;

@Controller
@RequestMapping("/Good")
public class GoodController {
	@Autowired
	private BusinessService service;

	@RequestMapping("/listgood")
	public String Tolistgood(Model model) {
		List<Category> categories = service.getAllCategory();
		model.addAttribute("categories", categories);
		List<good> goods = service.getAllGood();
		model.addAttribute("goods", goods);
		return "/pages/listGood.jsp";
	}

	/*
	 * 通过@PathVariable可以接收url中传入的参数
	 * 
	 * @RequestMapping("/find/{id}")
	 * 中接收参数使用大括号中加上变量名称, @PathVariable中的变量名称要和RequestMapping
	 */
	@RequestMapping("/editgood/{id}")
	public String Toeditgood(@PathVariable("id") String id, Model model) {
		List<Category> categories = service.getAllCategory();
		model.addAttribute("categories", categories);
		goodview good = service.findGood(id);
		model.addAttribute("good", good);
		return "/pages/editgood.jsp";
	}

	@RequestMapping("/update")
	public String updategood(UpdateGoodVo good, Model model) {
		try {
			service.updateGood(good);
			model.addAttribute("message", "更新成功!!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "更新失败!!");
		}
		return "/message.jsp";
	}

	@RequestMapping("/findforCategory")
	public String findforCategory(String category, Model model) {

		model.addAttribute("category_id", category);
		List<Category> categories = service.getAllCategory();
		model.addAttribute("categories", categories);
		List<good> goods = service.findforCategory(category);
		model.addAttribute("goods", goods);
		return "/pages/listGood.jsp";// 请求转发 共享同一个request
	}

	@RequestMapping("/delete/{id}")
	public String deletegood(@PathVariable("id") String id, Model model) {
		try {
			service.deleteGood(id);
			return "/Good/listgood.action";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "删除失败!!");
			return "/message.jsp";// request.getRequestDispatcher()是请求转发，前后页面共享一个request ; 这个是在服务端运行的，对浏览器来说是透明的。
		}
	}

	@RequestMapping("/ToaddGood")
	public String ToaddGood(Model model) {
		List<Category> categories = service.getAllCategory();
		model.addAttribute("categories", categories);
		return "/pages/addGood.jsp";
	}

	@RequestMapping("/add")
	public String addgood(@RequestParam("pictureFile")MultipartFile pictureFile, good good, Model model,HttpServletRequest request) {
		try {
				
			if (!pictureFile.isEmpty()) {
				String pathRoot = request.getSession().getServletContext().getRealPath("");  
				//D:\tomcat\webapps\storage2.0\
				// 1. 获取图片完整名称
				String fileStr = pictureFile.getOriginalFilename();
				// 2. 使用随机生成的字符串+源图片扩展名组成新的图片名称,防止图片重名
				String newfileName = UUID.randomUUID().toString() + fileStr.substring(fileStr.lastIndexOf("."));			 
				// 3. 将图片保存到硬盘
				pictureFile.transferTo(new File(pathRoot +"images\\" + newfileName));	
				// 4.将图片名称保存到数据库
				good.setImage(newfileName);
			}
			else {
				good.setImage("");
			}
			good.setId(UUID.randomUUID().toString());
			service.addGood(good);
			model.addAttribute("message", "添加成功!!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "添加失败!!");
		}
		return "/message.jsp";
	}
}
