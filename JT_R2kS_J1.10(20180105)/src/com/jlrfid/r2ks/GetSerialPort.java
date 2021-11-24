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
 * 获取本机上所有的串口号
 * 
 * @author zhuQixiang
 * 
 */
public class GetSerialPort implements SerialPortEventListener {
	// 检测系统中可用的通讯端口类
	private static CommPortIdentifier portId;
	// Enumeration 为枚举型类,在util中
	private static Enumeration<CommPortIdentifier> portList;
	// 输入输出流
	private InputStream inputStream;
	private OutputStream outputStream;
	// RS-232的串行口
	private SerialPort serialPort;
	public static String test = "";// 保存串口返回信息
	private static GetSerialPort uniqueInstance;// 单例创建

	/**
	 * 获取串口号列表
	 * @return 口号列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getComm() {
		// 获取系统中所有的通讯端口
		portList = CommPortIdentifier.getPortIdentifiers();
		List commList = new ArrayList();
		// 获取所有的端口
		// e.hasMoreElements()
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			// 判断是端口就添加
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				// 打出所有port的名字
				commList.add(portId.getName());
				//System.out.println(portId.getName());
			}
		}
		return commList;
	}

	/**
	 * 根据串口获取编号
	 * 
	 * @param comm 串口号 
	 * @return 如:com4 则返回4
	 */
	public static int splitComm(String comm) {
		if (comm.length() < 1) {
			return 0;
		}
		int number = Integer.parseInt(comm.substring(3, comm.length()));
		return number;
	}
	
	/**
	 * 实现接口SerialPortEventListener中的方法 读取从串口中接收的数据
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
		case SerialPortEvent.DATA_AVAILABLE:// 获取到串口返回信息
			readComm();
			break;
		default:
			break;
		}
	}

	/**
	 * 读取串口返回信息
	 */
	public void readComm() {
		byte[] readBuffer = new byte[1024];
		try {
			inputStream = uniqueInstance.serialPort.getInputStream(); // 从线路上读取数据流
			int len = 0;
			while ((len = inputStream.read(readBuffer)) != -1) {
				//System.out.println("实时反馈："+ new String(readBuffer, 0, len).trim() + new Date());
				test += new String(readBuffer, 0, len).trim();
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭串口
	 **/
	public static void closeSerialPort() {
		uniqueInstance.serialPort.close();
	}

	/**
	 * 向串口发送信息方法
	 */
	public void sendMsg() {
		// 要发送的内容
		String information = "AT\r";
		try {
			outputStream.write(information.getBytes());
			inputStream = serialPort.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
