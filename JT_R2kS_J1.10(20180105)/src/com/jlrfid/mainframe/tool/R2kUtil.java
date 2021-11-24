package com.jlrfid.mainframe.tool;

import com.jlrfid.r2ks.UHFOperate;
import com.jlrfid.r2ks.ZLDMHandler;


public class R2kUtil {
	/**
	 * 限制连接设备数量
	 */
	public final static int MAX_DEVICE_NUM = 50;
	/**
	 * 获取网络IP的_卓岚动态库dll文件
	 */
	public static ZLDMHandler secondhandler = new ZLDMHandler();
	/**
	 * 连接对象数组R2K
	 */
	public final static UHFOperate[] r2ks = new UHFOperate[MAX_DEVICE_NUM];
}
