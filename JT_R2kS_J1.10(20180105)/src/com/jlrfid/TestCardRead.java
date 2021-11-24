package com.jlrfid;

import com.jlrfid.r2ks.GetReadData;
import com.jlrfid.r2ks.MainHandler;
import com.jlrfid.r2ks.UHFOperate;

/**
 * 连续读卡
 * @author zhuQixiang
 *
 */
public class TestCardRead {
	public static void main(String[] args) {
		UHFOperate r2k = null;
		try {
			// 串口号(COM5)或IP地址(192.168.1.200)
			r2k = MainHandler.connectDev("COM4");
			// 停止读标签
			MainHandler.stopInv(r2k);
			// 连续读标签
			MainHandler.beginInv(r2k);
			ReadCard readCard = new ReadCard(r2k);
			Thread read = new Thread(readCard);
			read.start();
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			MainHandler.stopInv(r2k);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			MainHandler.disconnectDev(r2k);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class GetCallBack implements GetReadData {
	@Override
	public void getReadData(UHFOperate r2k, String data, int antNo) {
		// data获取到的卡号,antNo天线号
		System.out.println("data1 " + data);
		
	}
}

class ReadCard implements Runnable {
	UHFOperate r2k = null;

	public ReadCard() {
	}

	public ReadCard(UHFOperate r2k) {
		this.r2k = r2k;
	}

	@Override
	public void run() {
		do {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (r2k.isData) {
				r2k.setCallBack(r2k, new GetCallBack());
				r2k.isData = false;
			}
		} while (r2k.threadStart);
	}
}
