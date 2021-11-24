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
	 * ͨ��@PathVariable���Խ���url�д���Ĳ���
	 * 
	 * @RequestMapping("/find/{id}")
	 * �н��ղ���ʹ�ô������м��ϱ�������, @PathVariable�еı�������Ҫ��RequestMapping
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
			model.addAttribute("message", "���³ɹ�!!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "����ʧ��!!");
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
		return "/pages/listGood.jsp";// ����ת�� ����ͬһ��request
	}

	@RequestMapping("/delete/{id}")
	public String deletegood(@PathVariable("id") String id, Model model) {
		try {
			service.deleteGood(id);
			return "/Good/listgood.action";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "ɾ��ʧ��!!");
			return "/message.jsp";// request.getRequestDispatcher()������ת����ǰ��ҳ�湲��һ��request ; ������ڷ�������еģ����������˵��͸���ġ�
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
				// 1. ��ȡͼƬ��������
				String fileStr = pictureFile.getOriginalFilename();
				// 2. ʹ��������ɵ��ַ���+ԴͼƬ��չ������µ�ͼƬ����,��ֹͼƬ����
				String newfileName = UUID.randomUUID().toString() + fileStr.substring(fileStr.lastIndexOf("."));			 
				// 3. ��ͼƬ���浽Ӳ��
				pictureFile.transferTo(new File(pathRoot +"images\\" + newfileName));	
				// 4.��ͼƬ���Ʊ��浽���ݿ�
				good.setImage(newfileName);
			}
			else {
				good.setImage("");
			}
			good.setId(UUID.randomUUID().toString());
			service.addGood(good);
			model.addAttribute("message", "��ӳɹ�!!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "���ʧ��!!");
		}
		return "/message.jsp";
	}
}
