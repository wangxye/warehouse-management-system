package com.jlrfid.service.impl;

import com.jlrfid.dao.TagRecordDao;
import com.jlrfid.dao.impl.TagRecordDaoImpl;
import com.jlrfid.TagRecord;
import com.jlrfid.service.TagRecordService;

public class TagRecordServiceImpl implements TagRecordService {
	TagRecordDao dao = new TagRecordDaoImpl();

	@Override
	public int insert(TagRecord tagRecord) {
		return dao.insert(tagRecord);
	}
}
