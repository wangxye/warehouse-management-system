package cn.zzw.controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.zzw.pojo.Coordinate;
import cn.zzw.pojo.Scan;
import cn.zzw.service.BusinessService;
import coordinate.GetCoordinate;
import utils.addSecond;

@Controller
@RequestMapping("/Scan")
public class ScanController {
   @Autowired
	  private BusinessService service;
   @RequestMapping("/find")
   public String  find(Model model){	
	   Coordinate  c=new Coordinate() ;
	   c.setHorizontal(0);
	   c.setVertical(0);
		List<Scan> scan=service.getRecentScan();
		model.addAttribute("scans", scan);
		return "/pages/scan.jsp";		
   }
   @RequestMapping("/findAll")
   public String  findAll(Model model){	    
		List<Scan> scan=service.getAllScan();
		model.addAttribute("scans", scan);
		return "/pages/HistoryScan.jsp";		
   }
   @RequestMapping("/getfirstMap/{createDate}/{goodiid}")
   public String  getfirstMap(Model model,@PathVariable ("createDate") Timestamp  createDate,@PathVariable ("goodiid")String  goodiid ){	    
	  GetCoordinate g=new GetCoordinate();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
		try {
			Coordinate c=new Coordinate();
			c.setHorizontal(100);
			c.setVertical(20);
			model.addAttribute("coordinate", c);
			model.addAttribute("createDate", createDate);
			//System.out.println(goodiid+"-----------------");
			model.addAttribute("goodiid", goodiid);//拿到的goodiid 不带空格
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//关闭清楚数据表
	   return "/pages/result.jsp";
   }
   @RequestMapping("/getMap/{createDate}/{goodiid}/{horizontal}/{vertical}")
   public String  getMap(Model model,@PathVariable ("createDate") Timestamp  createDate,@PathVariable ("goodiid")String  goodiid,@PathVariable ("horizontal")double  oldhorizontal,@PathVariable ("vertical")double  oldvertical  ){	    
	  GetCoordinate g=new GetCoordinate();
	   try {
		g.getValueOfR1();
		g.getValueOfR2();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
		String Date=sdf.format(createDate);
		Coordinate oldCoordinate =new Coordinate();
		oldCoordinate.setHorizontal(oldhorizontal);
		oldCoordinate.setVertical(oldvertical);	
		goodiid=goodiid+" ";//拿到的gooiid 后面不带空格  
		addSecond a=new addSecond();
		createDate=a.addOneSecond(createDate);
		try {
			g.setFinalCoordinates(goodiid, Date, 3);
			Coordinate c=service.getMap();
			/*if(oldhorizontal!=100 &&  oldvertical!=20) 
			{
				if(   Math.abs(oldhorizontal-c.getHorizontal())>300 ||  Math.abs(oldvertical-c.getVertical())>300 )
				{
					c=oldCoordinate;
				}
			}*/
			model.addAttribute("coordinate", c);
			model.addAttribute("createDate", createDate);
			model.addAttribute("goodiid", goodiid);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//关闭清楚数据表
	} catch (ClassNotFoundException | SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	   return "/pages/result.jsp";
   }
   @RequestMapping("/getnowtime/{goodiid}")
   public String  getnowtime(Model model,@PathVariable ("goodiid")String  goodiid ){	    
	  GetCoordinate g=new GetCoordinate();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
		try {
			Coordinate c=new Coordinate();
			c.setHorizontal(300);
			c.setVertical(0);
			Date date = new Date();
			String Date=sdf.format((date));
			Timestamp createDate = Timestamp.valueOf(Date);
			model.addAttribute("coordinate", c);
			model.addAttribute("createDate", createDate);
			//System.out.println(goodiid+"-----------------");
			model.addAttribute("goodiid", goodiid);//拿到的goodiid 不带空格
			System.out.println("一:"+date);
			System.out.println("一:"+createDate);
			System.out.println("初始时间成功");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//关闭清楚数据表
	   return "/pages/NewFile.jsp";
   }
   @RequestMapping("/getMap2/{createDate}/{goodiid}/{horizontal}/{vertical}")
   public String  getMap2(Model model,@PathVariable ("createDate") Timestamp  createDate,@PathVariable ("goodiid")String  goodiid,@PathVariable ("horizontal")double  oldhorizontal,@PathVariable ("vertical")double  oldvertical  ){	    
	  GetCoordinate g=new GetCoordinate();
	   try {
		g.getValueOfR1();
		g.getValueOfR2();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
		String Date=sdf.format(createDate);
		Coordinate oldCoordinate =new Coordinate();
		oldCoordinate.setHorizontal(oldhorizontal);
		oldCoordinate.setVertical(oldvertical);	
		goodiid=goodiid+" ";//拿到的gooiid 后面不带空格  
		addSecond a=new addSecond();
		createDate=a.addOneSecond(createDate);
		System.out.println("二:"+goodiid);//带空格
		System.out.println("二:"+Date);
		try {
			g.setFinalCoordinates(goodiid, Date, 4);
			System.out.println("设置成功");
			Coordinate c=service.getMap();
			/*if(oldhorizontal!=100 &&  oldvertical!=20) 
			{
				if(   Math.abs(oldhorizontal-c.getHorizontal())>300 ||  Math.abs(oldvertical-c.getVertical())>300 )
				{
					c=oldCoordinate;
				}
			}*/
			model.addAttribute("coordinate", c);
			model.addAttribute("createDate", createDate);
			model.addAttribute("goodiid", goodiid);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("失败");
		}
		//关闭清楚数据表
	} catch (ClassNotFoundException | SQLException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	   return "/pages/NewFile.jsp";
   }
}