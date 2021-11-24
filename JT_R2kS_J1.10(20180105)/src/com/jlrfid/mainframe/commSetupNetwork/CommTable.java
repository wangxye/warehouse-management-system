package com.jlrfid.mainframe.commSetupNetwork;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class CommTable {

	/**
	 * ���ñ����ʽ
	 * 
	 * @param panel
	 */
	public static void setTableStyleZL(JTable table) {
		// ��ͷ���
		CommTableHeadUI ui = new CommTableHeadUI();
	    // ���ͷ���������
		table.getTableHeader().setUI(ui);
		// ���ñ�ͷ�Ĵ�С��Ҫ�������߶����Ҫ�ͱ�ͷ�ĸ߶�һ�����������ֶ��ಿ��
		table.getTableHeader().setPreferredSize(new Dimension(450, 30));
		table.setRowHeight(25);
		table.setEnabled(false);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		table.setPreferredScrollableViewportSize(new Dimension(350, 190));
		TableColumn firsetColumn = table.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(50);
		firsetColumn.setMaxWidth(50);
		firsetColumn.setMinWidth(50);
		TableColumn secondColumn = table.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(140);
		secondColumn.setMaxWidth(140);
		secondColumn.setMinWidth(140);
	}
}
