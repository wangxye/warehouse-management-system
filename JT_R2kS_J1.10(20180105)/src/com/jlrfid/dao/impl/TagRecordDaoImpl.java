package com.jlrfid.dao.impl;

import com.jlrfid.dao.BaseDao;
import com.jlrfid.dao.TagRecordDao;
import com.jlrfid.TagRecord;

/**
 * ���Ա�ӿ� ʵ����
 * @author Administrator
 *
 */
public class TagRecordDaoImpl extends BaseDao implements TagRecordDao {
	/**
	 * ���ӿ���
	 */
	public int insert(TagRecord tagRecord) {
		String sql = "insert into targeinfo values(?,now(),?,?,?,?,?)";
		return this.ExecuteNonQuery(sql, tagRecord.getCardNo(),tagRecord.getAntNo(),tagRecord.getRssi(),tagRecord.getHost(),tagRecord.getType(),tagRecord.getStatu());
	}
}

