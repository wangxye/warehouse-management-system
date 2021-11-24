package com.jlrfid.mainframe.deviceParams;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;

import com.jlrfid.mainframe.MainStart;
import com.jlrfid.mainframe.tool.R2kUtil;
import com.jlrfid.r2ks.AntStruct;
import com.jlrfid.r2ks.MainHandler;
import com.jlrfid.r2ks.RFIDException;
import com.jlrfid.r2ks.util.Message;
import com.jlrfid.r2ks.util.Regex;

public class DeviceParams extends MainStart {
	private static final long serialVersionUID = 1L;

	public static void getworkMode() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
			return;
		}
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					ByteBuffer workMode = ByteBuffer.allocate(1);
					boolean result = MainHandler.getWorkMode(R2kUtil.r2ks[i], workMode);
					if (result) {
						cboDeviceWorkMode.setSelectedIndex(workMode.array()[0] - 1);
					} else {
						Message.Show(rs.getString("MsgReadjobfailuremodelparam"), "");// "读取工作模式参数失败");
					}
					ByteBuffer trigTime = ByteBuffer.allocate(1);
					result = MainHandler.getTrigModeDelayTime(R2kUtil.r2ks[i], trigTime);
					if (result) {
						txtDeviceWorkDelay.setText(String.valueOf(trigTime.array()[0]));
					} else {
						Message.Show(rs.getString("MsgReadtimedelaytimefailed"), "");// "读取延时时间失败");
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExDeviceWorkRead"), "");
		}
	}

	public static void setWorkMode() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
			return;
		}
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					byte workmode = (byte) (cboDeviceWorkMode.getSelectedIndex() + 1);
					if (0 == workmode) {
						Message.Show(rs.getString("MsgSelectworkingmode"), "");// "请选择工作模式");
						return;
					}
					boolean result = MainHandler.setWorkMode(R2kUtil.r2ks[i], workmode);
					if (!result) {
						Message.Show(rs.getString("MsgSelectworkingmodeFailure"), "");// "设置工作模式参数失败");
						return;
					} else {
						Message.Show(rs.getString("MsgWorkmodeparamsuccess"), "");// "设置工作模式参数成功");
					}
					if (workmode == 3) {
						int trigTime = Integer.parseInt(txtDeviceWorkDelay.getText());
						if (0 == trigTime || 255 < trigTime) {
							Message.Show(rs.getString("MsgInvalidtimedelaytime"), "");// "无效延时时间");
							return;
						}
						result = MainHandler.setTrigModeDelayTime(R2kUtil.r2ks[i], (byte) trigTime);
						if (!result) {
							Message.Show(rs.getString("MsgdelayparamFailure"), "");// "设置延时参数失败");
							return;
						}
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExDeviceWorkSet"), "");
		}
	}

	public static void setDo() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
			return;
		}
		boolean isConn = false;
		try {
			int status = 0;
			int port = cboOutPort.getSelectedItem() == null ? 0
					: Integer.parseInt(cboOutPort.getSelectedItem().toString());

			if (rdoOutOpen.isSelected()) {
				status = 1;
			}
			if (rdoOutClose.isSelected()) {
				status = 0;
			}
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					//ByteBuffer state = ByteBuffer.allocate(2);
				//	boolean results = MainHandler.getDI(R2kUtil.r2ks[i], state);
					boolean result = MainHandler.setDO(R2kUtil.r2ks[i], port, status);
					if (result) {
						labelShowInfo.setText(rs.getString("MsgPortSettingSuccessfully"));// "端口设置成功";
					} else {
						labelShowInfo.setText(rs.getString("MsgPortsettingfailure"));// "端口设置失败";
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExSetDO"), "");
		}
	}

	/**
	 * 获取设备输出
	 */
	public static void getDeviceOut() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
			return;
		}
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					ByteBuffer outputMode = ByteBuffer.allocate(10);
					boolean result = MainHandler.getOutputMode(R2kUtil.r2ks[i], outputMode);
					if (result) {
						cboDeviceOut.setSelectedIndex(outputMode.array()[0] - 1);
					} else {
						Message.Show(rs.getString("MsgGetOutputModeFailed"), "");// "读输出模式参数失败");
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExGetOutputMode"), "");
		}
	}

	public static void setBuzzer() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
			return;
		}
		labelShowInfo.setText("");
		boolean isConn = false;
		try {
			boolean open = rdoDeviceBuzzerOpen.isSelected();
			boolean close = rdoDeviceBuzzerClose.isSelected();
			byte buzzer = 0;
			if (open || close) {
				if (open) {
					buzzer = 1;
				}
			}
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					boolean result = MainHandler.setBuzzer(R2kUtil.r2ks[i], buzzer);
					if (result) {
						Message.Show(rs.getString("MsgSetBuzzerSuccess"), "");// "设置成功");
					} else {
						Message.Show(rs.getString("MsgSetBuzzerFailure"), "");// "设置失败");
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception e) {
			Message.Show(rs.getString("MsgExSetBuzzer"), "");
		}
	}

	public static void getBuzzer() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
			return;
		}
		labelShowInfo.setText("");
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					int result = MainHandler.getBuzzer(R2kUtil.r2ks[i]);
					if (result == 1) {
						rdoDeviceBuzzerOpen.setSelected(true);
					} else if (result == 0) {
						rdoDeviceBuzzerClose.setSelected(true);
					}
				} else {
					Message.Show(rs.getString("MsgGetBuzzerFailure"), "");// "获取峰鸣器状态失败");
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception e) {
			Message.Show(rs.getString("MsgExGetBuzzer"), "");
		}
	}

	/**
	 * 获取天线参数1号,2号,3号,4号
	 */
	public static void getAntParam() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"), "");
			return;
		}
		labelShowInfo.setText("");
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					AntStruct antStruct = MainHandler.getAnt(R2kUtil.r2ks[i]);
					if (antStruct.size() > 0) {
						isConn = true;
						chkDeviceAntenna1.setSelected(antStruct.antEnable[0] > 0);
						chkDeviceAntenna2.setSelected(antStruct.antEnable[1] > 0);
						chkDeviceAntenna3.setSelected(antStruct.antEnable[2] > 0);
						chkDeviceAntenna4.setSelected(antStruct.antEnable[3] > 0);

						cboDeviceAntennaWorkTime1.setSelectedItem(antStruct.dwellTime[0]);
						cboDeviceAntennaWorkTime2.setSelectedItem(antStruct.dwellTime[1]);
						cboDeviceAntennaWorkTime3.setSelectedItem(antStruct.dwellTime[2]);
						cboDeviceAntennaWorkTime4.setSelectedItem(antStruct.dwellTime[3]);

						cboDeviceAntennaPower1.setSelectedItem(antStruct.power[0]);
						cboDeviceAntennaPower2.setSelectedItem(antStruct.power[1]);
						cboDeviceAntennaPower3.setSelectedItem(antStruct.power[2]);
						cboDeviceAntennaPower4.setSelectedItem(antStruct.power[3]);
						labelShowInfo.setText(rs.getString("MsgReadantennaparamsucces"));// "读取天线参数成功";
					} else {
						labelShowInfo.setText(rs.getString("MsgReadantennaparamfailure"));// "读取天线参数失败";
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception e) {
			Message.Show(rs.getString("MsgExGetAnt"), "");
		}
	}

	/**
	 * 设置天线 1号,2号,3号,4号
	 */
	public static void setAntParam() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		labelShowInfo.setText("");
		boolean isConn = false;
		byte[] antEnable = new byte[4];
		int[] dwellTime = new int[4];
		int[] power = new int[4];
		// cboDeviceAntennaWorkTime4
		// cboDeviceAntennaPower1
		antEnable[0] = (byte) (chkDeviceAntenna1.isSelected() ? 1 : 0);
		antEnable[1] = (byte) (chkDeviceAntenna2.isSelected() ? 1 : 0);
		antEnable[2] = (byte) (chkDeviceAntenna3.isSelected() ? 1 : 0);
		antEnable[3] = (byte) (chkDeviceAntenna4.isSelected() ? 1 : 0);

		dwellTime[0] = Integer.parseInt(cboDeviceAntennaWorkTime1.getSelectedItem().toString());
		dwellTime[1] = Integer.parseInt(cboDeviceAntennaWorkTime2.getSelectedItem().toString());
		dwellTime[2] = Integer.parseInt(cboDeviceAntennaWorkTime3.getSelectedItem().toString());
		dwellTime[3] = Integer.parseInt(cboDeviceAntennaWorkTime4.getSelectedItem().toString());

		power[0] = Integer.parseInt(cboDeviceAntennaPower1.getSelectedItem().toString());
		power[1] = Integer.parseInt(cboDeviceAntennaPower2.getSelectedItem().toString());
		power[2] = Integer.parseInt(cboDeviceAntennaPower3.getSelectedItem().toString());
		power[3] = Integer.parseInt(cboDeviceAntennaPower4.getSelectedItem().toString());

		for (int i = 0; i < dwellTime.length; i++) {
			if (dwellTime[i] < 100 || dwellTime[i] > 5000) {
				Message.Show(rs.getString("MsgFindcardtimevalidvalues"), "");// "寻卡时间有效值为100－10000");
				return;
			}
		}
		for (int i = 0; i < power.length; i++) {
			if (power[i] < 100 || power[i] > 5000) {
				Message.Show(rs.getString("MsgValidvalues"), "");// "功率有效值为200－300");
				return;
			}
		}
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					boolean result = MainHandler.setAnt(R2kUtil.r2ks[i], antEnable, dwellTime, power);
					if (result) {
						isConn = true;
						labelShowInfo.setText(rs.getString("MsgSetantennaparamsucces"));// "设置天线参数成功";
						return;
					} else {
						labelShowInfo.setText(rs.getString("MsgSetantennaparamfailure"));// "设置天线参数失败";
						return;
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (RFIDException e) {
			Message.Show(rs.getString("MsgExSetAnt"), "");
		}
	}

	public static void deviceNoSet() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		labelShowInfo.setText("");
		boolean isConn = false;
		try {
			if (!Regex.isHexCharacter(txtDeviceValid.getText())) {
				Message.Show(rs.getString("MsgAdjacentSetInvalidvalues"), "");// "无效值");
				return;
			}
			int deviceNo = txtDeviceValid.getText().trim().equals("") ? 0 : Integer.parseInt(txtDeviceValid.getText());
			if (deviceNo > 254) {
				Message.Show(rs.getString("MsgAdjacentSetInvalidvalues"), "");// "无效值");
				return;
			}
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					boolean result = MainHandler.setDeviceNo(R2kUtil.r2ks[i], (byte) deviceNo);
					if (result) {
						Message.Show(rs.getString("MsgDeviceSetSuccess"), "");// "设置设备号成功");
					} else {
						Message.Show(rs.getString("MsgDeviceSetFailure"), "");// "设置设备号失败");
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExSetDeviceNo"), "");
		}
	}

	public static void setNeighJudge() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		boolean isConn = false;
		try {
			if (!Regex.isHexCharacter(txtDeviceAdjacentTime.getText())) {
				Message.Show(rs.getString("MsgAdjacentSetInvalidvalues"), "");// "无效值");
				return;
			}
			int time = txtDeviceAdjacentTime.getText().equals("") ? 0
					: Integer.parseInt(txtDeviceAdjacentTime.getText());
			if (time > 254) {
				Message.Show(rs.getString("MsgAdjacentSetInvalidvalues"), "");// "无效值");
				return;
			}
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					boolean result = MainHandler.setNeighJudge(R2kUtil.r2ks[i], (byte) time);
					if (result) {
						Message.Show(rs.getString("MsgAdjacentSetSuccess"), "");// "设置成功");
					} else {
						Message.Show(rs.getString("MsgAdjacentSetFailure"), "");// "设置失败");
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExSetNeighJudge"), "");
		}
	}

	public static void getNeighJudge() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		labelShowInfo.setText("");
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					ByteBuffer enableNJ = ByteBuffer.allocate(1);
					ByteBuffer neighJudgeTime = ByteBuffer.allocate(1);
					boolean result = MainHandler.getNeighJudge(R2kUtil.r2ks[i], enableNJ, neighJudgeTime);
					if (result) {
						txtDeviceAdjacentTime.setText(String.valueOf(neighJudgeTime.array()[0]));
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExGetNeighJudge"), "");
		}
	}

	public static void getDeviceNo() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		labelShowInfo.setText("");
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					String result = MainHandler.getDeviceNo(R2kUtil.r2ks[i]);
					if (null != result) {
						txtDeviceValid.setText(result);
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			Message.Show(rs.getString("MsgExGetDeviceNo"), "");
		}
	}

	public static void getDeviceClock() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					String result = MainHandler.getClock(R2kUtil.r2ks[i]);
					if (null != result) {
						
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExGetClock"), "");
		}
	}

	/**
	 * 设置设备输出
	 */
	public static void setDeviceOut() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					int outputMode = cboDeviceOut.getSelectedIndex();
					if (-1 == outputMode) {
						Message.Show(rs.getString("MsgSelectOutputMode"), "");// "请选择输出模式");
					}
					boolean result = MainHandler.setOutputMode(R2kUtil.r2ks[i], (byte) (outputMode + 1));
					if (result) {
						Message.Show(rs.getString("MsgSelectOutputModeSuccess"), "");// "设置输出模式成功");
					} else {
						Message.Show(rs.getString("MsgSelectOutputModeFailure"), "");// "设置输出模式失败");
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExSetOutputMode"), "");
		}
	}

	public static void getSearchArea() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		boolean isConn = false;
		try {
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					ByteBuffer zone = ByteBuffer.allocate(10);
					String result = MainHandler.getReadZone(R2kUtil.r2ks[i], zone);
					if (null != result) {
						if (0 == zone.array()[0]) { // 默认
							rdoDeviceSearchCustom.setSelected(true);
							rdoDeviceSearchEPC.setSelected(true);
						} else { // 定制
							rdoDeviceSearchCustom.setSelected(false);
							rdoDeviceSearchEPC.setSelected(true);
						}
					}
					ByteBuffer bank = ByteBuffer.allocate(10);
					ByteBuffer begin = ByteBuffer.allocate(10);
					ByteBuffer length = ByteBuffer.allocate(10);
					boolean total = MainHandler.getReadZonePara(R2kUtil.r2ks[i], bank, begin, length);
					if (total) {
						cboDeviceSearchArea.setSelectedIndex(bank.array()[0] - 1);
						cboDeviceSearchStartAddr.setSelectedIndex(begin.array()[0]);
						cboDeviceSearchLen.setSelectedIndex(length.array()[0] - 1);
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExGetReadZone"), "");
		}
	}
	
	public static byte[] getTimeByCalendar(){
		byte [] currentDate = new byte[8];
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH);//获取月份
        int day=cal.get(Calendar.DATE);//获取日
        int hour=cal.get(Calendar.HOUR);//小时
        int minute=cal.get(Calendar.MINUTE);//分           
        int second=cal.get(Calendar.SECOND);//秒
		currentDate[0] = (byte) (year-2000);
		currentDate[1] = (byte)(month + 1);
		currentDate[2] = (byte)day;
		currentDate[3] = (byte)hour;
		currentDate[4] = (byte)minute;
		currentDate[5] = (byte)second;
        return currentDate;
        //int WeekOfYear = cal.get(Calendar.DAY_OF_WEEK);//一周的第几天
       // System.out.println("现在的时间是：公元"+year+"年"+month+"月"+day+"日      "+hour+"时"+minute+"分"+second+"秒       星期"+WeekOfYear);
    }
	
	public static void setDeviceClock() {
		if (isContinueReadCard) {
			Message.Show(rs.getString("MsgExNodevicebtnStartEnable"),"");
			return;
		}
		boolean isConn = false;
		try {
			byte [] clock = new byte[8];
			clock = getTimeByCalendar();
			for (int i = 0; i < R2kUtil.r2ks.length && null != R2kUtil.r2ks[i]; i++) {
				if (R2kUtil.r2ks[i] != null) {
					isConn = true;
					boolean result = MainHandler.setClock(R2kUtil.r2ks[i], clock);
					if (result) {
						
					}
				}
			}
			if (!isConn) {
				Message.Show(rs.getString("MsgExNodevicetoconnect"), "");
			}
		} catch (Exception ex) {
			Message.Show(rs.getString("MsgExGetClock"), "");
		}
	}
}
