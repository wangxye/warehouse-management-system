package com.jlrfid.mainframe.tool;

import com.jlrfid.r2ks.UHFOperate;
import com.jlrfid.r2ks.ZLDMHandler;


public class R2kUtil {
	/**
	 * ���������豸����
	 */
	public final static int MAX_DEVICE_NUM = 50;
	/**
	 * ��ȡ����IP��_׿ᰶ�̬��dll�ļ�
	 */
	public static ZLDMHandler secondhandler = new ZLDMHandler();
	/**
	 * ���Ӷ�������R2K
	 */
	public final static UHFOperate[] r2ks = new UHFOperate[MAX_DEVICE_NUM];
}
