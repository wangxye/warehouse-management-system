package com.jlrfid.r2ks;

import java.net.Socket;

import gnu.io.SerialPort;

public class PACKAGE {
	/**
	 * 网口
	 */
	public Socket socket = null;
	/**
	 * 串口
	 */
	public SerialPort serialPort = null;
	/**
	 * 天线个数
	 */
	public static final int ANT_NUMBER = 4;
	/**
	 * 天线数组
	 */
	public int antIsEnd[] = new int[ANT_NUMBER];
	/**
	 * 设备是否连接
	 */
	public boolean deviceConnected = false;
	/**
	 * 循卡线程是否结束
	 */
	public boolean threadStart = false;
	/**
	 * 判断连续读卡是否有数据
	 */
	public boolean isData = false;
	/**
	 * 端口号
	 */
	public int port;
	/**
	 * 存储主机字符串，端口固定为20058
	 */
	public String host = "";
	/**
	 * TrandPackage中包头计算
	 */
	public int head_count = 0;
	/**
	 * 转换过程中数据计数
	 */
	public int data_count = 0;
	/**
	 * 串口连接
	 */
	public boolean bIsComPort = false;
	/*
	 * 从接收缓冲区转换后的实际数据长度
	 */
	public int pkg_len = 0;
	/**
	 * 接收数据数组
	 */
	public byte recv_buf[] = new byte[4 * 1024];

	/**
	 * 2个字节的起始码
	 */
	public byte startcode[] = new byte[2];
	/**
	 * 命令码
	 */
	public byte cmd;
	/**
	 * 顺序号
	 */
	public byte seq;
	/**
	 * 长度，0为low,1为high
	 */
	public byte len[] = new byte[2];
	/**
	 * 数据
	 */
	public byte data[] = new byte[520];
	/**
	 * 校验码
	 */
	public byte bcc;

	/**
	 * 获取的命令
	 * 
	 * @return
	 */
	public byte[] getSendCMD(UHFOperate r2k, int length) {
		byte[] sendData = new byte[7 + length];
		if (r2k.bIsComPort) { // com
			sendData[0] = startcode[0];
			sendData[1] = startcode[1];
			sendData[2] = cmd;
			sendData[3] = seq;
			sendData[4] = len[0];
			sendData[5] = len[1];
			int count = 0;
			int i = 6;
			if (length > 0) {
				for (; i < sendData.length && count < length; i++) {
					sendData[i] = data[count];
					count++;
				}
			}
			sendData[i] = bcc;
		} else { // socket
			sendData[0] = startcode[0];
			sendData[1] = startcode[1];
			sendData[2] = cmd;
			sendData[3] = seq;
			sendData[4] = len[0];
			sendData[5] = len[1];
			int count = 0;
			int i = 6;
			if (length > 0) {
				for (; i < sendData.length && count < length; i++) {
					sendData[i] = data[count];
					count++;
				}
			}
			sendData[i] = bcc;
		}
		return sendData;
	}
}