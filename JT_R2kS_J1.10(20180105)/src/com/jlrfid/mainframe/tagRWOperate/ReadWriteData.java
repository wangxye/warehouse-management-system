package com.jlrfid.mainframe.tagRWOperate;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ReadWriteData {
	/**
	 * 默认加载下拉框
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void defaultLoadCombobox(JComboBox area, JComboBox startAddre, JComboBox length) {
		// 默认加载操作区域
		area.setModel(new DefaultComboBoxModel(OperationAreaMap.getArea()));
		String operationArea = "0_" + area.getSelectedIndex();
		// 默认加载起始地址
		startAddre.setModel(new DefaultComboBoxModel(OperationAreaMap.getAddress(operationArea)));
		String startAddress = "0_" + area.getSelectedIndex() + "_" + startAddre.getSelectedIndex();
		// 默认加载长度
		length.setModel(new DefaultComboBoxModel(OperationAreaMap.getLength(startAddress)));
	}

	/**
	 * 下拉框连动
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void comboboxInterlock(JComboBox area, JComboBox startAddress, JComboBox length) {
		// 操作区域下拉框
		String operationArea = "0_" + area.getSelectedIndex();
		startAddress.setModel(new DefaultComboBoxModel(OperationAreaMap.getAddress(operationArea)));
		comboboxAddress(area, startAddress, length);
	}

	/**
	 * 地址下拉框连动
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void comboboxAddress(JComboBox area, JComboBox startAddre, JComboBox length) {
		String startAddress = "0_" + area.getSelectedIndex() + "_" + startAddre.getSelectedIndex();
		length.setModel(new DefaultComboBoxModel(OperationAreaMap.getLength(startAddress)));
	}
}
