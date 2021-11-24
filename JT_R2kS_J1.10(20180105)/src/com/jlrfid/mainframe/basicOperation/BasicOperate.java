package com.jlrfid.mainframe.basicOperation;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.jlrfid.mainframe.MainStart;
import com.jlrfid.mainframe.tool.R2kUtil;
import com.jlrfid.r2ks.GetReadData;
import com.jlrfid.r2ks.MainHandler;
import com.jlrfid.r2ks.UHFOperate;
import com.jlrfid.r2ks.util.Message;
import com.jlrfid.r2ks.util.Regex;

public class BasicOperate extends MainStart {
	private static final long serialVersionUID = 1L;

	public static void readerBuffer() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					MainHandler.readTagBuffer(R2kUtil.r2ks[i],new GetData(), 3000);
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExReadTagBuffer"), "");
		}
	}

	public static void clearBuffer() {
		boolean isConn = false;
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					MainHandler.resetTagBuffer(R2kUtil.r2ks[i]);
				}
			}
			if (!isConn) {
				Message.Show(
						rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExResetTagBuffer"), "");
		}
	}
	
	public static void stopReadCard() {
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.MAX_DEVICE_NUM
					&& R2kUtil.r2ks[i] != null; i++) {
				if (R2kUtil.r2ks[i] != null) {
					MainHandler.stopInv(R2kUtil.r2ks[i]);
					isConn = true;
					btnStop.setEnabled(false);
					isContinueReadCard = false;
					//BasicOperate.buttonSet(true);
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExStopInv"), "");
		}
	}
	
	/**
	 * 断开连接
	 */
	public static void disconnect() {
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.MAX_DEVICE_NUM && R2kUtil.r2ks[i] != null; i++) {
				if (R2kUtil.r2ks[i] != null) {
					MainHandler.stopInv(R2kUtil.r2ks[i]);
				}
			}
			for (int i = 0; i < R2kUtil.MAX_DEVICE_NUM && R2kUtil.r2ks[i] != null; i++) {
				if (R2kUtil.r2ks[i] != null) {
					if (MainHandler.disconnectDev(R2kUtil.r2ks[i])) {
						R2kUtil.r2ks[i] = null;
					}
					isConn = true;
					isContinueReadCard = false;
				}
			}
			if (!isConn){
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
			if (rdoSerialPort.isSelected()) {
				BasicTree.getDeviceComm(model, tree_onlineDevice, nodeTree,node_1);
				tree_onlineDevice.updateUI();
			} else {
				BasicTree.getDeviceIP(model, tree_onlineDevice, nodeTree,node_1);
				tree_onlineDevice.updateUI();
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgConnectionfailure"),"");// "连接断开失败");
		}
	}

	public static void inventoryOnce() {
		boolean isConn = false;
		System.out.println(isContinueReadCard);
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		try {
			for (int i = 0; i < R2kUtil.MAX_DEVICE_NUM && R2kUtil.r2ks[i] != null; i++) {
				if (R2kUtil.r2ks[i] != null) {
					btnStart.setEnabled(false);
					btnStop.setEnabled(false);
					btnClearData.setEnabled(false);
					btnReadBuffer.setEnabled(false);
					btnClearBuffer.setEnabled(false);
					MainHandler.stopInv(R2kUtil.r2ks[i]);
					MainHandler.invOnce(R2kUtil.r2ks[i]);
					GetCallBack callBack = new GetCallBack(R2kUtil.r2ks[i]);
					Thread thread = new Thread(callBack);
					thread.start();
					isConn = true;
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
			btnClearData.setEnabled(true);
			btnReadBuffer.setEnabled(true);
			btnClearBuffer.setEnabled(true);
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExInvOnce"), "");
	    }
	}

	/**
	 * 连续读卡
	 */
	public static void start() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.MAX_DEVICE_NUM && R2kUtil.r2ks[i] != null; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					MainHandler.beginInv(R2kUtil.r2ks[i]);
					GetCallBack callBack = new GetCallBack(R2kUtil.r2ks[i]);
					Thread thread = new Thread(callBack);
					thread.start();
					btnStop.setEnabled(true);
					isContinueReadCard = true;
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExInvOnce"), "");
		}
	}

	/**
	 * 连接设备
	 */
	public static void connect() {
		// 获取当前节点值
		note = (DefaultMutableTreeNode) tree_onlineDevice.getLastSelectedPathComponent();
		System.out.println(note);
		try {
			if (null == note || note.toString().equals(rs.getString("LVOnlineEquipment"))) {
				Message.Show(rs.getString("MsgChoosebtnConnect"), "");
				return;
			}
			// 获取选中节点的父节点
			parent = (DefaultMutableTreeNode) note.getParent();
			// 获取选中的IP或串口号
			String ipOrComm = note.toString();
			UHFOperate r2k = null;
			int i = 0;
			for (; i < R2kUtil.MAX_DEVICE_NUM; i++) {
				if (R2kUtil.r2ks[i] == null) {
					r2k = MainHandler.connectDev(ipOrComm);
					break;
				}
			}
			if (null == r2k) {
				Message.Show(MainStart.rs.getString("MsgChooseConnectFail"), "");
				return;
			}
			if (i == R2kUtil.MAX_DEVICE_NUM) {
				Message.Show(rs.getString("MsgChoosebtnConnectOut"), "");// "连接设备数已达上限");
				return;
			}
			if (null != r2k) {
				R2kUtil.r2ks[i] = r2k;
				String flag = MainHandler.getDevVersion(r2k);
				if (flag == null) {
					// System.out.println("失败!");
				}
				String ipConn = ipOrComm + rs.getString("Msgconnected");// "_已连接"
				btnDisconnect.setEnabled(true);
				btnStart.setEnabled(true);
				btnInventoryOnce.setEnabled(true);
				btnReadBuffer.setEnabled(true);
				btnClearBuffer.setEnabled(true);
				TreePath path = tree_onlineDevice.getSelectionPath();
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
				node.setUserObject(ipConn);
				model.nodeChanged(node);
				tree_onlineDevice.setSelectionPath(path);
				// repaint();
				tree_onlineDevice.updateUI();
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgChooseConnectFail"), "");
		}
	}

	public static void addIP() {
		MutableTreeNode nodeNew = new DefaultMutableTreeNode();
		// 获取选中的树形节点
		// TreePath path = tree_onlineDevice.getSelectionPath();
		TreePath path = tree_onlineDevice.getPathForRow(0);
		MutableTreeNode nodePar = (MutableTreeNode) path.getLastPathComponent();
		int count = nodePar.getChildCount();
		String addIPAddress = txtIP.getText();
		if (!Regex.isValidIP(addIPAddress)) {
			Message.Show(MainStart.rs.getString("strMsgFailed"), "");
			return;
		}
		nodeNew.setUserObject(addIPAddress);
		model.insertNodeInto(nodeNew, nodePar, count);
		TreePath NewPath = path.pathByAddingChild(nodeNew);
		if (!tree_onlineDevice.isVisible(NewPath)) {
			tree_onlineDevice.makeVisible(NewPath);
		}
	}

	public static void refresh() {
		if (R2kUtil.r2ks[0] != null) {
			BasicOperate.disconnect();
			note = null;
		}
		if (rdoNet.isSelected()) {
			BasicTree.getDeviceIP(model, tree_onlineDevice, nodeTree, node_1);
			TreeNode root = (TreeNode) tree_onlineDevice.getModel().getRoot();
			model.reload(root);
		} else if (rdoSerialPort.isSelected()) {
			BasicTree.getDeviceComm(model, tree_onlineDevice, nodeTree, node_1);
			TreeNode root = (TreeNode) tree_onlineDevice.getModel().getRoot();
			model.reload(root);
		}
	}
}

class GetData implements GetReadData {
	public void getReadData(UHFOperate r2k, String data, int antNo) {
		// 读取卡片时的信息处理
		BasicTable.tableInfoShow(r2k, data, antNo);
	}
}

class GetCallBack implements Runnable {
	UHFOperate r2k = null;

	public GetCallBack() {

	}

	public GetCallBack(UHFOperate r2k) {
		this.r2k = r2k;
	}

	@Override
	public void run() {
		do{
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (r2k.isData) {
				r2k.setCallBack(r2k, new GetData());
				r2k.isData = false;
			}
		}while (r2k.threadStart);
	}
}