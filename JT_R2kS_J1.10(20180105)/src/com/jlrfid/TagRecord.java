package com.jlrfid;

public class TagRecord {
	/**
	 * ���
	 */
	private int id;
	/**
	 * ����
	 */
	private String cardNo;
	/**
	 * ����ʱ��
	 */
	private String createDate;
	/**
	 * ��ȡ���ߺ�
	 */
	private int antNo;
	/**
	 * ��ȡRSSI
	 */
	private int Rssi;
	/**
	 * �豸��
	 */
	private String host;
	/**
	 * ��ǩ����Typeֵ
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
