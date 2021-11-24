package com.jlrfid.mainframe.tagRWOperate;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ReadWriteData {
	/**
	 * Ĭ�ϼ���������
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void defaultLoadCombobox(JComboBox area, JComboBox startAddre, JComboBox length) {
		// Ĭ�ϼ��ز�������
		area.setModel(new DefaultComboBoxModel(OperationAreaMap.getArea()));
		String operationArea = "0_" + area.getSelectedIndex();
		// Ĭ�ϼ�����ʼ��ַ
		startAddre.setModel(new DefaultComboBoxModel(OperationAreaMap.getAddress(operationArea)));
		String startAddress = "0_" + area.getSelectedIndex() + "_" + startAddre.getSelectedIndex();
		// Ĭ�ϼ��س���
		length.setModel(new DefaultComboBoxModel(OperationAreaMap.getLength(startAddress)));
	}

	/**
	 * ����������
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void comboboxInterlock(JComboBox area, JComboBox startAddress, JComboBox length) {
		// ��������������
		String operationArea = "0_" + area.getSelectedIndex();
		startAddress.setModel(new DefaultComboBoxModel(OperationAreaMap.getAddress(operationArea)));
		comboboxAddress(area, startAddress, length);
	}

	/**
	 * ��ַ����������
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void comboboxAddress(JComboBox area, JComboBox startAddre, JComboBox length) {
		String startAddress = "0_" + area.getSelectedIndex() + "_" + startAddre.getSelectedIndex();
		length.setModel(new DefaultComboBoxModel(OperationAreaMap.getLength(startAddress)));
	}
}
