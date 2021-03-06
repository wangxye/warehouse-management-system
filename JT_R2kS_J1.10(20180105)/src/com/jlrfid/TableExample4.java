package com.jlrfid;

import javax.swing.*;
import javax.swing.table.*;

import com.jlrfid.mainframe.MainStart;

import javax.swing.border.*;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;

public class TableExample4 {

	public TableExample4() {
		JFrame frame = new JFrame("Table");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Take the dummy data from SwingSet.
		final String[] names = { "First Name", "Last Name", "Favorite Color", "Favorite Number", "Vegetarian" };
		final Object[][] data = { { "Mark", "Andrews", "Red", new Integer(2), Boolean.TRUE },
				{ "Tom", "Ball", "Blue", new Integer(99), Boolean.FALSE },
				{ "Arnaud", "Weber", "Green", new Integer(44), Boolean.FALSE } };

		// Create a model of the data.
		TableModel dataModel = new AbstractTableModel() {
			// These methods always need to be implemented.
			public int getColumnCount() {
				return names.length;
			}

			public int getRowCount() {
				return 0;
			}

			public Object getValueAt(int row, int col) {
				return data[5][0];
			}

			// The default implementations of these methods in
			// AbstractTableModel would work, but we can refine them.
			public String getColumnName(int column) {
				return names[column];
			}

			public Class getColumnClass(int c) {
				return getValueAt(0, c).getClass();
			}

			public boolean isCellEditable(int row, int col) {
				return true;
			}

			public void setValueAt(Object aValue, int row, int column) {
				System.out.println("Setting value to: " + aValue);
				data[row][column] = aValue;
			}
		};

		// Create the table
		JTable tableView = new JTable(dataModel);
		
		// Turn off auto-resizing so that we can set column sizes
		// programmatically.
		// In this mode, all columns will get their preferred widths, as set
		// blow.
		tableView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Finish setting up the table.
		JScrollPane scrollpane = new JScrollPane(tableView);
		scrollpane.setBorder(new BevelBorder(BevelBorder.LOWERED));
		scrollpane.setPreferredSize(new Dimension(430, 200));
		frame.getContentPane().add(scrollpane);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new TableExample4();
		
	}
}
