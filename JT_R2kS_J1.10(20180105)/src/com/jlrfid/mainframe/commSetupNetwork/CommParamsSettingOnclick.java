package com.jlrfid.mainframe.commSetupNetwork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.jlrfid.mainframe.MainStart;

public class CommParamsSettingOnclick extends MainStart{
	private static final long serialVersionUID = 1L;

	public static void onclick() {
		btnSearchDevice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommSetup.showTableZL();
			}
		});
		btnEditDevice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table_ZL.getRowCount() > 0) {
					CommSetup.updateCommParamControl();
				}
			}
		});
		btnDefaultParams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommSetup.defaultSetup();
			}
		});
		btnSetParams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommSetup.setParameter();
			}
		});
	}
}
