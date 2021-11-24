package com.jlrfid.mainframe.deviceParams;


import javax.swing.JComboBox;

/**
 * 天线参数设置
 * 
 * @author zhuQixiang
 * 
 */
public class AntennaParameter {
	/**
	 * 天线信号读取的速度选项列表
	 * @param combo 下拉框控件
	 * @return 下拉列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JComboBox comboboxSecond(JComboBox combobox) {
		for (int i = 100; i <= 3000; i = i + 100) {
			combobox.addItem(i);
		}
		return combobox;
	}

	/**
	 * 天线信号读取的选项列表，DBM毫瓦
	 * @param combo 下拉框控件
	 * @return 下拉列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JComboBox comboboxDbm(JComboBox combobox) {
		for (int i = 200; i <= 300; i = i + 10) {
			combobox.addItem(i);
		}
		return combobox;
	}

	
}
