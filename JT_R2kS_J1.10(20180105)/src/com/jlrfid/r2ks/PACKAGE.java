package com.jlrfid.r2ks;

import java.net.Socket;

import gnu.io.SerialPort;

public class PACKAGE {
	/**
	 * ����
	 */
	public Socket socket = null;
	/**
	 * ����
	 */
	public SerialPort serialPort = null;
	/**
	 * ���߸���
	 */
	public static final int ANT_NUMBER = 4;
	/**
	 * ��������
	 */
	public int antIsEnd[] = new int[ANT_NUMBER];
	/**
	 * �豸�Ƿ�����
	 */
	public boolean deviceConnected = false;
	/**
	 * ѭ���߳��Ƿ����
	 */
	public boolean threadStart = false;
	/**
	 * �ж����������Ƿ�������
	 */
	public boolean isData = false;
	/**
	 * �˿ں�
	 */
	public int port;
	/**
	 * �洢�����ַ������˿ڹ̶�Ϊ20058
	 */
	public String host = "";
	/**
	 * TrandPackage�а�ͷ����
	 */
	public int head_count = 0;
	/**
	 * ת�����������ݼ���
	 */
	public int data_count = 0;
	/**
	 * ��������
	 */
	public boolean bIsComPort = false;
	/*
	 * �ӽ��ջ�����ת�����ʵ�����ݳ���
	 */
	public int pkg_len = 0;
	/**
	 * ������������
	 */
	public byte recv_buf[] = new byte[4 * 1024];

	/**
	 * 2���ֽڵ���ʼ��
	 */
	public byte startcode[] = new byte[2];
	/**
	 * ������
	 */
	public byte cmd;
	/**
	 * ˳���
	 */
	public byte seq;
	/**
	 * ���ȣ�0Ϊlow,1Ϊhigh
	 */
	public byte len[] = new byte[2];
	/**
	 * ����
	 */
	public byte data[] = new byte[520];
	/**
	 * У����
	 */
	public byte bcc;

	/**
	 * ��ȡ������
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