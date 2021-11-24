package com.jlrfid.r2ks;

/**
 * 操作标签错误时异常信息
 * @author zhuQixiang
 *
 */
public class RFIDException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public RFIDException(String message){
		super(message);
	}
}
