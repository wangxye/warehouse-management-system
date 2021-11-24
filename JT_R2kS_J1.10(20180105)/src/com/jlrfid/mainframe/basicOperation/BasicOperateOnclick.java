package com.jlrfid.mainframe.basicOperation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.tree.TreeNode;

import com.jlrfid.mainframe.MainStart;
import com.jlrfid.mainframe.tool.R2kUtil;

public class BasicOperateOnclick extends MainStart {
	private static final long serialVersionUID = 1L;

	public static void onclick() {
		btnReadBuffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicOperate.readerBuffer();
			}
		});
		btnClearBuffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicOperate.clearBuffer();
			}
		});
		btnAddIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicOperate.addIP();
			}
		});
		chkSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkSaveFile.isSelected()) {
					chooseSaveFile = true;
				} else {
					chooseSaveFile = false;
				}
			}
		});
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicOperate.refresh();
			}
		});
		rdoNet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (R2kUtil.r2ks[0] != null) {
					BasicOperate.disconnect();
				}
				BasicTree.getDeviceIP(model, tree_onlineDevice, nodeTree, node_1);
				TreeNode root = (TreeNode) tree_onlineDevice.getModel().getRoot();
				model.reload(root);
			}
		});
		rdoSerialPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (R2kUtil.r2ks[0] != null) {
					BasicOperate.disconnect();
				}
				BasicTree.getDeviceComm(model, tree_onlineDevice, nodeTree, node_1);
				TreeNode root = (TreeNode) tree_onlineDevice.getModel().getRoot();
				model.reload(root);
			}
		});
		
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicOperate.disconnect();
			}
		});
		
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicOperate.connect();
			}
		});
		
		btnClearData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// total labels count
				labelCount.setText("0");
				labelTagCount.setText("0");
				tableModel.setRowCount(0);
			}
		});
		
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicOperate.stopReadCard();
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicOperate.start();
			}
		});
		
		btnInventoryOnce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicOperate.inventoryOnce();
			}
		});
	}
}
