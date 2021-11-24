package com.jlrfid;

public class TagRecord {
	/**
	 * 编号
	 */
	private int id;
	/**
	 * 卡号
	 */
	private String cardNo;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 读取天线号
	 */
	private int antNo;
	/**
	 * 读取RSSI
	 */
	private int Rssi;
	/**
	 * 设备号
	 */
	private String host;
	/**
	 * 标签代表Type值
	 */
	private String Type;
	/**
	 * Status
	 */
	private int Statu = 1;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public int getAntNo() {
		return antNo;
	}
	
	public void setAntNo(int antNo) {
		this.antNo = antNo;
	}
	
	public int getRssi() {
		return Rssi;
	}
	
	public void setRssi(int Rssi) {
		this.Rssi = Rssi;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getType() {
		Type = "";
		Type += cardNo.charAt(0);
		return Type;
	}
	
	public int getStatu() {
		return Statu;
	}
	
	public void setStatus(int Statu) {
		this.Statu = Statu;
	}
}
