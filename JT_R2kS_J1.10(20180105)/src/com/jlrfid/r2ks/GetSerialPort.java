package com.jlrfid.r2ks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * ��ȡ���������еĴ��ں�
 * 
 * @author zhuQixiang
 * 
 */
public class GetSerialPort implements SerialPortEventListener {
	// ���ϵͳ�п��õ�ͨѶ�˿���
	private static CommPortIdentifier portId;
	// Enumeration Ϊö������,��util��
	private static Enumeration<CommPortIdentifier> portList;
	// ���������
	private InputStream inputStream;
	private OutputStream outputStream;
	// RS-232�Ĵ��п�
	private SerialPort serialPort;
	public static String test = "";// ���洮�ڷ�����Ϣ
	private static GetSerialPort uniqueInstance;// ��������

	/**
	 * ��ȡ���ں��б�
	 * @return �ں��б�
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getComm() {
		// ��ȡϵͳ�����е�ͨѶ�˿�
		portList = CommPortIdentifier.getPortIdentifiers();
		List commList = new ArrayList();
		// ��ȡ���еĶ˿�
		// e.hasMoreElements()
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			// �ж��Ƕ˿ھ����
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				// �������port������
				commList.add(portId.getName());
				//System.out.println(portId.getName());
			}
		}
		return commList;
	}

	/**
	 * ���ݴ��ڻ�ȡ���
	 * 
	 * @param comm ���ں� 
	 * @return ��:com4 �򷵻�4
	 */
	public static int splitComm(String comm) {
		if (comm.length() < 1) {
			return 0;
		}
		int number = Integer.parseInt(comm.substring(3, comm.length()));
		return number;
	}
	
	/**
	 * ʵ�ֽӿ�SerialPortEventListener�еķ��� ��ȡ�Ӵ����н��յ�����
	 */
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:// ��ȡ�����ڷ�����Ϣ
			readComm();
			break;
		default:
			break;
		}
	}

	/**
	 * ��ȡ���ڷ�����Ϣ
	 */
	public void readComm() {
		byte[] readBuffer = new byte[1024];
		try {
			inputStream = uniqueInstance.serialPort.getInputStream(); // ����·�϶�ȡ������
			int len = 0;
			while ((len = inputStream.read(readBuffer)) != -1) {
				//System.out.println("ʵʱ������"+ new String(readBuffer, 0, len).trim() + new Date());
				test += new String(readBuffer, 0, len).trim();
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �رմ���
	 **/
	public static void closeSerialPort() {
		uniqueInstance.serialPort.close();
	}

	/**
	 * �򴮿ڷ�����Ϣ����
	 */
	public void sendMsg() {
		// Ҫ���͵�����
		String information = "AT\r";
		try {
			outputStream.write(information.getBytes());
			inputStream = serialPort.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
