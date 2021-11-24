package com.jlrfid.mainframe.deviceParams;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import com.jlrfid.mainframe.MainStart;
import com.jlrfid.mainframe.tagRWOperate.TagRWOperate;

public class DeviceParamsOnclick extends MainStart {
	private static final long serialVersionUID = 1L;

	public static void onclick() {
		btnDeviceAntannaSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.setAntParam();
			}
		});
		btnDeviceAntennaRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.getAntParam();
			}
		});
		btnDeviceBuzzerRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.getBuzzer();
			}
		});
		btnDeviceBuzzerSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.setBuzzer();
			}
		});
		btnLabelOutSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeviceParams.setDo();
			}
		});
		
		btnDeviceWorkSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.setWorkMode();
			}
		});
		btnDeviceWorkRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.getworkMode();
			}
		});
		btnDeviceAdjacentRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.getNeighJudge();
			}
		});

		btnDeviceAdjacentSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.setNeighJudge();
			}
		});
		btnDeviceRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.getDeviceNo();
			}
		});
		btnDeviceSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.deviceNoSet();
			}
		});
		btnDeviceCurrTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				datepick.setDate(new Date());
			}
		});
		btnDeviceTimeRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.getDeviceClock();
			}
		});
		btnDeviceTimeSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.setDeviceClock();
			}
		});
		btnDeviceSearchSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TagRWOperate.setSearchArea();
			}
		});
		btnDeviceSearchRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.getSearchArea();
			}
		});
		btnDeviceOutRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.getDeviceOut();
			}
		});
		btnDeviceOutSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeviceParams.setDeviceOut();
			}
		});
	}
}
