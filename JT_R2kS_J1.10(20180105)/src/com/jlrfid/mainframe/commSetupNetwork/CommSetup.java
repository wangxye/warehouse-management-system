package com.jlrfid.mainframe.commSetupNetwork;

import javax.swing.table.DefaultTableModel;
import com.jlrfid.mainframe.MainStart;
import com.jlrfid.mainframe.tool.R2kUtil;
import com.jlrfid.r2ks.ZLDMHandler;
import com.jlrfid.r2ks.util.Message;
import com.jlrfid.r2ks.util.Regex;

public class CommSetup extends MainStart{
	private static final long serialVersionUID = 1L;
	public String reverseStr(String s) {
		int length = s.length();
		if (length <= 1)
			return s;
		String left = s.substring(0, length / 2);
		String right = s.substring(length / 2, length);
		return reverseStr(right) + reverseStr(left); // 调用递归
	}
	
	public static void defaultSetup() {
		cbbIPMode.setSelectedIndex(0);
		cbbNetworkMode.setSelectedIndex(0);
		txtIPAddress.setText("192.168.1.200");
		txtSubnetMask.setText("255.255.255.0");
		txtPort.setText("20058");
		txtGateway.setText("192.168.1.1");
		txtDestinationIP.setText("192.168.1.100");
		txtDestinationPort.setText("20058");
		cbbSerialPortBaudRate.setSelectedIndex(12);
		cboDataBits.setSelectedIndex(0);
		cbbParity.setSelectedIndex(0);
	}
	public static void setParameter() {
		// 检查IP地址
		String IPAddress = txtIPAddress.getText().trim();
		if (!Regex.isValidIP(IPAddress)) {
			Message.Show(rs.getString("strMsgInvalidIP"), "");
			return;
		}
		// 检查掩码
		String subnetMask = txtSubnetMask.getText().trim();
		if (!Regex.isValidIP(subnetMask)) {
			Message.Show(rs.getString("strMsgInvalidMask"), "");
			return;
		}
		// 检查网关
		String gateWay = txtGateway.getText().trim();
		if (!Regex.isValidIP(gateWay)) {
			Message.Show(rs.getString("strMsgInvalidGateway"), "");
			return;
		}
		// 检查目标IP
		String destinationIP = txtDestinationIP.getText().trim();
		if (!Regex.isValidIP(destinationIP)) {
			Message.Show(rs.getString("strMsgInvalidDestIP"), "");
			return;
		}
		// 检查端口号
		int portNo = Integer.parseInt((txtPort.getText().trim()));
		if (portNo < 1000 || portNo > 65535) {
			Message.Show(rs.getString("strMsgInvalidPort"), "");
			return;
		}
		int destinationPort = Integer.parseInt((txtDestinationPort.getText().trim()));
		if (destinationPort < 1000 || destinationPort > 65535) {
			Message.Show(rs.getString("strMsgInvalidDestPort"), "");
			return;
		}

		short port = (short) ReverseByte(portNo);
		short destport = (short) ReverseByte(destinationPort);

		byte[] ip = new byte[32];
		byte[] netmask = new byte[32];
		byte[] gateway = new byte[32];
		byte[] destip = new byte[32];
		ip = IPAddress.getBytes();
		netmask = subnetMask.getBytes();
		gateway = gateWay.getBytes();
		destip = destinationIP.getBytes();

		R2kUtil.secondhandler.setWorkMode(ZLDMHandler.selectedDevNo, (byte) cbbNetworkMode.getSelectedIndex());
		R2kUtil.secondhandler.setIPMode(ZLDMHandler.selectedDevNo, (byte)cbbIPMode.getSelectedIndex());
		R2kUtil.secondhandler.setIP(ZLDMHandler.selectedDevNo, ip);

		R2kUtil.secondhandler.setNetMask(ZLDMHandler.selectedDevNo, netmask);

		// R2kUtil.secondhandler.setGateWay(ZLDMHandler.selectedDevNo, gateway);
		R2kUtil.secondhandler.setDestName(ZLDMHandler.selectedDevNo, destip);
		R2kUtil.secondhandler.setPort(ZLDMHandler.selectedDevNo, port);
		R2kUtil.secondhandler.setDestPort(ZLDMHandler.selectedDevNo, destport);
		R2kUtil.secondhandler.setBaudrateIndex(ZLDMHandler.selectedDevNo, (byte) cbbSerialPortBaudRate.getSelectedIndex());
		R2kUtil.secondhandler.setDataBits(ZLDMHandler.selectedDevNo, (byte) cboDataBits.getSelectedIndex());
		R2kUtil.secondhandler.setParity(ZLDMHandler.selectedDevNo, (byte) cbbParity.getSelectedIndex());
		boolean res = R2kUtil.secondhandler.setParam(ZLDMHandler.selectedDevNo);
		if (res) {
			Message.Show("成功", "");
			// Message.Show(rs.getString("strMsgSucceedSetCommParam"),
			// "");
		} else {
			Message.Show("失败", "");
			// Message.Show(rs.getString("strMsgFailedSetCommParam"),
			// "");
		}
	}
	
	public static void updateCommParamControl() {
		if (R2kUtil.secondhandler.devCount > 0) {
			byte nNum = ZLDMHandler.selectedDevNo;
			int workMode = R2kUtil.secondhandler.getWorkMode(nNum);
			cbbNetworkMode.setSelectedIndex(workMode);
			int IPMode = R2kUtil.secondhandler.getIPMode(nNum);
			cbbIPMode.setSelectedIndex(IPMode);
			String IP = R2kUtil.secondhandler.getIP(nNum);
			txtIPAddress.setText(IP);
			String netMask = String.valueOf(R2kUtil.secondhandler.getNetMask(nNum));
			txtSubnetMask.setText(netMask);
			int port = ReverseByte(R2kUtil.secondhandler.getPort(nNum));
			txtPort.setText(String.valueOf(port));
			String gateWay = R2kUtil.secondhandler.getGateWay(nNum);
			txtGateway.setText(gateWay);
			String destName = R2kUtil.secondhandler.getDestName(nNum);
			txtDestinationIP.setText(destName);
			int destPort = ReverseByte(R2kUtil.secondhandler.getDestPort(nNum));
			txtDestinationPort.setText(String.valueOf(destPort));
			int baudrateIndex = R2kUtil.secondhandler.getBaudrateIndex(nNum);
			cbbSerialPortBaudRate.setSelectedIndex(baudrateIndex);
			int dataBits = R2kUtil.secondhandler.getDataBits(nNum);
			cboDataBits.setSelectedIndex(dataBits);
			int parity = R2kUtil.secondhandler.getParity(nNum);
			cbbParity.setSelectedIndex(parity);
		}
	}
	
	public static void showTableZL() {
		boolean flag = false;
		if (!R2kUtil.secondhandler.dllInit("dll/ZLDevManage")) {
			return;
		}
		// get network IP
		int devCount = R2kUtil.secondhandler.startSearchDev();
		R2kUtil.secondhandler.devCount = devCount;
		// clear table data
		((DefaultTableModel) table_ZL.getModel()).getDataVector().clear();
		// Notification model update
		((DefaultTableModel) table_ZL.getModel()).fireTableDataChanged();
		// The refresh table
		table_ZL.updateUI();
		// add data to table
		for (byte i = 0; i < devCount; i++) {
			String IP = R2kUtil.secondhandler.getIP(i);
			int port = ReverseByte(R2kUtil.secondhandler.getPort(i));
			int devId = R2kUtil.secondhandler.getDevID(i);
			Object[] rowValues = { i + 1, IP, port };
			// add a line
			ZLtableModel.addRow(rowValues);
		}
		if (devCount > 0) {
			CommSetup.updateCommParamControl();
		}
	}
	// 大小端转换
	public static int ReverseByte(int value) {
		return ((value & 0xFF) << 8 | (value & 0xFF00) >> 8);
	}
}
