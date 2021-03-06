package com.jlrfid.r2ks;

/**
 * 获取读取数据接口
 * 
 * @author zhuQixiang createDate 2017-10-25
 * 
 */
public interface GetReadData {
	/**
	 * 循环读卡或者寻卡一次回调函数
	 * 
	 * @param data
	 *            电子标签数据
	 * @param antNo
	 *            天线编号
	 */
	void getReadData(UHFOperate r2k,String data, int antNo);
}
