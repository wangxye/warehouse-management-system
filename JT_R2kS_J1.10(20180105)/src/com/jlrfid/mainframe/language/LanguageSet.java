package com.jlrfid.mainframe.language;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.jlrfid.mainframe.MainStart;

public class LanguageSet extends MainStart {
	private static final long serialVersionUID = 1L;

	public static void onclick() {
		cbb_language.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = cbb_language.getSelectedIndex();
				System.out.println();
				if (result == 0) {
					rs = ResourceBundle.getBundle("language", Locale.CHINA);
					LanguageSet.updateLanguage();
				} else if (result == 1) {
					rs = ResourceBundle.getBundle("language", Locale.ENGLISH);
					LanguageSet.updateLanguage();
				}
			}
		});
	}
	
	public static void updateLanguage() {
		languageGeneral();
		languageTagsOperate();
		languageSetCommParam();
		languageDeviceParams();
	}

	public static void languageGeneral() {
		tabbedPane.setTitleAt(0, rs.getString("tabGeneral"));
		panel_CommunicationMode.setBorder(new TitledBorder(null, rs
				.getString("gopCommunicationMode"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		rdoSerialPort.setText(rs.getString("cboserialport"));
		rdoNet.setText(rs.getString("rdobtnNet"));
		btnRefresh.setText(rs.getString("btnRefresh"));
		node_1.setUserObject(rs.getString("LVOnlineEquipment"));
		tree_onlineDevice.updateUI();
		node_2.setUserObject(rs.getString("LVOnlineEquipment"));
		tree_onlineDevice.updateUI();
		btnAddIP.setText(rs.getString("btnAddIP"));
		btnConnect.setText(rs.getString("btnConnect"));
		btnDisconnect.setText(rs.getString("btnDisconnect"));
		labelReadCount.setText(rs.getString("labelReadCount"));
		labelNub.setText(rs.getString("labelNub"));
		btnInventoryOnce.setText(rs.getString("btnInventoryOnce"));
		btnStart.setText(rs.getString("btnStart"));
		btnStop.setText(rs.getString("btnStop"));
		btnClearData.setText(rs.getString("btnClearData"));
		btnReadBuffer.setText(rs.getString("btnReadBuffer"));
		btnClearBuffer.setText(rs.getString("btnClearBuffer"));
		chkSaveFile.setText(rs.getString("strChkSaveFile"));
	}

	public static void languageTagsOperate() {
		tabbedPane.setTitleAt(1, rs.getString("tabTagsOperate"));
		panel_Designatedarea.setBorder(new TitledBorder(null, rs
				.getString("gopLabelDesignatedarea"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		lblRWArea.setText(rs.getString("lblLabelRWArea"));
		lblRWStartAddress.setText(rs.getString("LblLabelRWStartAddr"));
		lblRWLength.setText(rs.getString("lblLabelRWLen"));
		lblRWData.setText(rs.getString("lblLabelRWData"));
		lblRWAccessPwd.setText(rs.getString("lblLabelAccessPassword"));
		btnRWContinuousRead.setText(rs.getString("strBtnConnRead"));
		btnRWStop.setText(rs.getString("strStopRead"));
		btnRWReadCard.setText(rs.getString("btnLabelRWRead"));
		btnRWClear.setText(rs.getString("btnLabelRWClear"));
		btnRWWrite.setText(rs.getString("btnLabelRWWrite"));

		panel_TagAddLockAndUnlock.setBorder(new TitledBorder(null, rs
				.getString("gopLabelUnlock"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		String[] unlockType = {
				rs.getString("cboLabelUnlockTypeunlock"),
				rs.getString("cboLabelUnlockTypePermanentwritable"),
				rs.getString("cboLabelUnlockTypeSafetylock"),
				rs.getString("cboLabelUnlockTypePermanentcannotwrite") };
		cbbUnlockType.removeAllItems();
		for (int i = 0; i < unlockType.length; i++) {
			cbbUnlockType.addItem(unlockType[i]);
		}
		lblUnlockType.setText(rs.getString("lblLabelUnlockType"));
		lblUnlockArea.setText(rs.getString("lblLabelUnlockArea"));
		btnUnlockPerform.setText(rs.getString("btnLabelPerform"));

		panel_destoryTag.setBorder(new TitledBorder(null, rs
				.getString("gopLabelDestoryTag"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		lblDestoryPassword.setText(rs
				.getString("lblLabelRWDestoryPassword"));
		btnDestoryTag.setText(rs.getString("btnDestoryTag"));
	}

	public static void languageSetCommParam() {
		tabbedPane.setTitleAt(2, rs.getString("strTpSetCommParam"));
		panel_NetParams.setBorder(new TitledBorder(null, rs
				.getString("strGbNetParams"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_SerialPortParams.setBorder(new TitledBorder(null, rs
				.getString("strGbSPParams"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		lblPromotion.setText(rs.getString("strLabPromotion"));
		lblDestinationIP.setText(rs.getString("strLabDestIP"));
		lblDestinationPort.setText(rs.getString("strLabDestPort"));
		lblNetworkMode.setText(rs.getString("strLabNetMode"));
		lblIPMode.setText(rs.getString("strLabIPMode"));
		lblIPAddress.setText(rs.getString("strLabIPAdd"));
		lblSubnetMask.setText(rs.getString("strLabMask"));
		lblPort.setText(rs.getString("lblLabelOutPort"));
		lblGateway.setText(rs.getString("strLabGateway"));
		btnSearchDevice.setText(rs.getString("strBtnSearchDev"));
		btnEditDevice.setText(rs.getString("strBtnModifyDev"));
		btnDefaultParams.setText(rs.getString("strBtnDefaultParams"));
		btnSetParams.setText(rs.getString("strBtnSetParams"));
		lblDataBits.setText(rs.getString("strLabCheckBits"));
		lblBaudRate.setText(rs.getString("strLabBaudRate"));
		lblParity.setText(rs.getString("strLabCheckBits"));
	}

	public static void languageDeviceParams() {
		tabbedPane.setTitleAt(3, rs.getString("tabDeviceParams"));
		panel_DeviceAntenna.setBorder(new TitledBorder(null, rs
				.getString("gopDeviceAntenna"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		lblDeviceAntennaWorkTime.setText(rs
				.getString("lblDeviceAntennaWorkTime"));
		lblDeviceAntennaPower.setText(rs
				.getString("lblDeviceAntennaPower"));
		chkDeviceAntenna1.setText(rs.getString("chkDeviceAntenna1"));
		chkDeviceAntenna2.setText(rs.getString("chkDeviceAntenna2"));
		chkDeviceAntenna3.setText(rs.getString("chkDeviceAntenna3"));
		chkDeviceAntenna4.setText(rs.getString("chkDeviceAntenna4"));
		btnDeviceAntennaRead.setText(rs
				.getString("btnDeviceAntennaRead"));
		btnDeviceAntannaSet.setText(rs.getString("btnDeviceAntannaSet"));

		panel_DeviceBuzzer.setBorder(new TitledBorder(null, rs
				.getString("gopDeviceBuzzer"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		rdoDeviceBuzzerOpen.setText(rs
				.getString("rdobtnDeviceBuzzerOpen"));
		rdoDeviceBuzzerClose.setText(rs
				.getString("rdobtnDeviceBuzzerClose"));
		btnDeviceBuzzerRead.setText(rs.getString("btnDeviceBuzzerRead"));
		btnDeviceBuzzerSet.setText(rs.getString("btnDeviceBuzzerSet"));

		panel_SetOutPort.setBorder(new TitledBorder(null, rs
				.getString("gopLabelSetOutPort"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		lblOutPort.setText(rs.getString("lblLabelOutPort"));
		rdoOutOpen.setText(rs.getString("rdobtnLabelOutOpen"));
		rdoOutClose.setText(rs.getString("rdobtnLabelOutClose"));
		btnLabelOutSet.setText(rs.getString("btnLabelOutSet"));

		panel_DeviceWork.setBorder(new TitledBorder(null, rs
				.getString("gopDeviceWork"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		lblDeviceWorkDelay.setText(rs.getString("lblDeviceWorkDelay"));
		btnDeviceWorkRead.setText(rs.getString("btnDeviceWorkRead"));
		btnDeviceWorkSet.setText(rs.getString("btnDeviceWorkSet"));
		String[] deviceWorkMode = { rs.getString("cboAmasterslavemode"),
				rs.getString("cboTimingmode"),
				rs.getString("cboTriggermode") };
		cboDeviceWorkMode.removeAllItems();
		for (int i = 0; i < deviceWorkMode.length; i++) {
			cboDeviceWorkMode.addItem(deviceWorkMode[i]);
		}

		panel_DeviceAdjacent.setBorder(new TitledBorder(null, rs
				.getString("gopDeviceAdjacent"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		lblDeviceAdjacentTime.setText(rs
				.getString("lblDeviceAdjacentTime"));
		btnDeviceAdjacentSet.setText(rs
				.getString("btnDeviceAdjacentSet"));
		btnDeviceAdjacentRead.setText(rs
				.getString("btnDeviceAdjacentRead"));

		panel_Device.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), rs
				.getString("gopDevice"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblDeviceValid.setText(rs.getString("lblDeviceValid"));
		btnDeviceSet.setText(rs.getString("btnDeviceSet"));
		btnDeviceRead.setText(rs.getString("btnDeviceRead"));

		panel_DeviceTime.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), rs
				.getString("gopDeviceTime"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		btnDeviceCurrTime.setText(rs.getString("btnDeviceCurrTime"));
		btnDeviceTimeSet.setText(rs.getString("btnDeviceTimeSet"));
		btnDeviceTimeRead.setText(rs.getString("btnDeviceTimeRead"));

		panel_DeviceSearch.setBorder(new TitledBorder(null, rs
				.getString("gopDeviceSearch"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		rdoDeviceSearchEPC
				.setText(rs.getString("rdobtnDeviceSearchEPC"));
		rdoDeviceSearchCustom.setText(rs
				.getString("rdobtnDeviceSearchCustom"));
		lblDeviceSearchArea.setText(rs.getString("lblDeviceSearchArea"));
		lblDeviceSearchStartAddr.setText(rs
				.getString("lblDeviceSearchStartAddr"));
		lblDeviceSearchLen.setText(rs.getString("lblDeviceSearchLen"));

		String[] deviceSearchArea = { "EPC", "TID",
				rs.getString("cbousersarea") };
		cboDeviceSearchArea.removeAllItems();
		for (int i = 0; i < deviceSearchArea.length; i++) {
			cboDeviceSearchArea.addItem(deviceSearchArea[i]);
		}
		btnDeviceSearchSet.setText(rs.getString("btnDeviceSearchSet"));
		btnDeviceSearchRead.setText(rs
				.getString("btnDeviceAdjacentRead"));

		panel_DeviceOut.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), rs
				.getString("gopDeviceOut"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		btnDeviceOutRead.setText(rs.getString("btnDeviceOutRead"));
		btnDeviceOutSet.setText(rs.getString("btnDeviceSet"));

		String[] deviceOut = { rs.getString("cboserialport"),
				rs.getString("rdobtnNet"), "RS485", "WIFI" };
		cboDeviceOut.removeAllItems();
		for (int i = 0; i < deviceOut.length; i++) {
			cboDeviceOut.addItem(deviceOut[i]);
		}
	}
}
