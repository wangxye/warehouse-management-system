package com.jlrfid.mainframe.deviceParams;


import javax.swing.JComboBox;

/**
 * ���߲�������
 * 
 * @author zhuQixiang
 * 
 */
public class AntennaParameter {
	/**
	 * �����źŶ�ȡ���ٶ�ѡ���б�
	 * @param combo ������ؼ�
	 * @return �����б�
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JComboBox comboboxSecond(JComboBox combobox) {
		for (int i = 100; i <= 3000; i = i + 100) {
			combobox.addItem(i);
		}
		return combobox;
	}

	/**
	 * �����źŶ�ȡ��ѡ���б�DBM����
	 * @param combo ������ؼ�
	 * @return �����б�
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JComboBox comboboxDbm(JComboBox combobox) {
		for (int i = 200; i <= 300; i = i + 10) {
			combobox.addItem(i);
		}
		return combobox;
	}

	
}
