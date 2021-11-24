package com.jlrfid;

import com.jlrfid.dao.BaseDao;
import com.jlrfid.dao.impl.TagRecordDaoImpl;

public class TestCon {

	public static void main(String[] args) {
		BaseDao dao =new BaseDao();
		if(dao.getConnection()!=null) {
			System.out.println("true");
		}
		TagRecord t = new TagRecord();
		System.out.println(t.getCardNo());
	}
}
