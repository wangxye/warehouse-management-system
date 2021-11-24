package cn.zzw.pojo;

import java.sql.Timestamp;

public class Scan {
  private String  goodname;
  private String  goodiid;
  private String  peopleiid;
  private String  peoplename;
  private  Timestamp createDate;
  private int statu;
public String getGoodname() {
	return goodname;
}
public void setGoodname(String goodname) {
	this.goodname = goodname;
}

public String getGoodiid() {
	return goodiid;
}
public void setGoodiid(String goodiid) {
	this.goodiid = goodiid;
}
public String getPeopleiid() {
	return peopleiid;
}
public void setPeopleiid(String peopleiid) {
	this.peopleiid = peopleiid;
}
public String getPeoplename() {
	return peoplename;
}
public void setPeoplename(String peoplename) {
	this.peoplename = peoplename;
}
public Timestamp getCreateDate() {
	return createDate;
}
public void setCreateDate(Timestamp createDate) {
	this.createDate = createDate;
}
public int getStatu() {
	return statu;
}
public void setStatu(int statu) {
	this.statu = statu;
}
 
}
