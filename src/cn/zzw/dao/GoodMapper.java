package cn.zzw.dao;

import java.util.List;

import cn.zzw.pojo.UpdateGoodVo;
import cn.zzw.pojo.good;
import cn.zzw.pojo.goodview;

public interface GoodMapper {
	public void addGood(good g);
	public  void deleteGood(String id);
	public  void updateGood( UpdateGoodVo good) ;
	public goodview findGood(String id) ;
	public List<good> findforCategory(String category_id);
	public List<good> getAllGood() ;
}
