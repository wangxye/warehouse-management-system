package com.jlrfid.r2ks.util;

import javax.swing.JOptionPane;

/**
 * ������Ϣ��ʾ
 * @author zhuqixiang createDate 2017-05-12
 * 
 */
public class Message {
	/**
	 * 
	 * @param content �Ի�������ʾ����
	 * @param title �Ի������
	 */
	public static void Show(String contaier, String title) {
		JOptionPane.showMessageDialog(null, contaier, title,
				JOptionPane.INFORMATION_MESSAGE);
	}
}
