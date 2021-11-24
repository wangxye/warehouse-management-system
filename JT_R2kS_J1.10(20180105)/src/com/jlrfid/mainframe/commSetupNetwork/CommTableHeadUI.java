package com.jlrfid.mainframe.commSetupNetwork;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.JTableHeader;

import com.jlrfid.mainframe.MainStart;

public class CommTableHeadUI extends BasicTableHeaderUI {
	private JTableHeader header;

	public void paint(Graphics g, JComponent c) {
		header = (JTableHeader) c;
		JLabel label = getLabel(MainStart.rs.getString("strLvHeadDevNo"));
		rendererPane.paintComponent(g, label, header, getX(4), 1, getWidth(0),20, true);
		label = getLabel(MainStart.rs.getString("strZlHeadNo"));
		rendererPane.paintComponent(g, label, header, getX(0), 1, getWidth(0),20, true);
		label = getLabel(MainStart.rs.getString("strZlHeadIP"));
		rendererPane.paintComponent(g, label, header, getX(1), 1, getWidth(1),20, true);
		label = getLabel(MainStart.rs.getString("strZlHeadPort"));
		rendererPane.paintComponent(g, label, header, getX(2), 1, getWidth(2),20, true);
		label = getLabel(MainStart.rs.getString("strZlHeadMAC"));
		rendererPane.paintComponent(g, label, header, getX(3), 1, getWidth(3),20, true);
	}
	
	/**
	 * Get the starting coordinate of the specified column
	 * @param column
	 * @return
	 */
	private int getX(int column) {
		int x = 0;
		for (int i = 0; i < column; i++)
			x += header.getColumnModel().getColumn(i).getWidth();
		return x;
	}
	
	/**
	 * The width of the specified column
	 * @param column
	 * @return
	 */
	private int getWidth(int column) {
		return header.getColumnModel().getColumn(column).getWidth();
	}

	/**
	 * Get the label that has the specified text
	 * @param text
	 * @return
	 */
	private JLabel getLabel(String text) {
		JLabel label = new JLabel(text, JLabel.CENTER);
		label.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		return label;
	}
}
