package cn.zzw.dao;

import java.util.List;

import cn.zzw.pojo.Coordinate;
import cn.zzw.pojo.Scan;

public interface ScanMapper {
	public List<Scan> getRecentScan();
	public List<Scan> getAllScan();
	public Coordinate getMap();
}
