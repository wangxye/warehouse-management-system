package com.jlrfid.entity;

public class TargeInfo {
	private int id;
	private String cardNo;
	private String createDate;

	public TargeInfo() {
	}

	public String getCardNo() {
		return cardNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
