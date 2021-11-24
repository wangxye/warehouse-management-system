package com.jlrfid.r2ks.serialportexception;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

/**
 * ���ڷ����࣬�ṩ�򿪡��رմ��ڣ���ȡ�����ʹ������ݵȷ��񣨲��õ������ģʽ��
 * 
 * @author zhong
 * 
 */
public class SerialPortTool implements SerialPortEventListener {
	private static SerialPortTool serialTool = null;
	public Thread loopThread = null;

	// ˽�л�SerialTool��Ĺ��췽��������������������SerialTool����
	public SerialPortTool() {
		// �ڸ��౻ClassLoader����ʱ�ͳ�ʼ��һ��SerialTool����
		// if (serialTool == null) {
		// serialTool = new SerialPortDao();
		// }
	}

	/**
	 * ��ȡ�ṩ�����SerialTool����
	 * 
	 * @return serialTool
	 */
	public static SerialPortTool getSerialTool() {
		if (serialTool == null) {
			serialTool = new SerialPortTool();
		}
		return serialTool;
	}

	/**
	 * �������п��ö˿�
	 * ��ȡ���ں��б�
	 * @return ���ö˿������б�
	 */
	@SuppressWarnings("unchecked")
	public static final ArrayList<String> findPort() {
		// ��õ�ǰ���п��ô���
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier
				.getPortIdentifiers();
		ArrayList<String> portNameList = new ArrayList<String>();
		// �����ô�������ӵ�List�����ظ�List
		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
			//String portName = portList.nextElement().getName();
			// �ж��Ƕ˿ھ����
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				// �������port������
				portNameList.add(portId.getName());
				//portNameList.add(portName);
	     	}
		}
		return portNameList;
	}

	/**
	 * �򿪴���
	 * 
	 * @param portName
	 *            �˿�����
	 * @param baudrate
	 *            ������
	 * @return ���ڶ���
	 * @throws SerialPortParameterFailure
	 *             ���ô��ڲ���ʧ��
	 * @throws NotASerialPort
	 *             �˿�ָ���豸���Ǵ�������
	 * @throws NoSuchSerialPort
	 *             û�иö˿ڶ�Ӧ�Ĵ����豸
	 * @throws SerialPortInUse
	 *             �˿��ѱ�ռ��
	 */
	public static final SerialPort openPort(String port, int baudrate)
			throws SerialPortParameterFailure, NotASerialPort, NoSuchSerialPort,
			SerialPortInUse {
		//String port = "COM" + portName;
		try {
			// ͨ���˿���ʶ��˿�
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(port);
			// �򿪶˿ڣ������˿����ֺ�һ��timeout���򿪲����ĳ�ʱʱ�䣩
			CommPort commPort = portIdentifier.open(port, 2000);
			// �ж��ǲ��Ǵ���
			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				try {
					// ����һ�´��ڵĲ����ʵȲ��� baudrate
					serialPort.setSerialPortParams(baudrate,SerialPort.DATABITS_8, SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
				} catch (UnsupportedCommOperationException e) {
					throw new SerialPortParameterFailure();
				}
				System.out.println("Open " + port + " sucessfully !");
				return serialPort;
			} else {
				// ���Ǵ���
				//throw new NotASerialPort();
			}
		} catch (NoSuchPortException e1) {
			//throw new NoSuchPort();
			return null;
		} catch (PortInUseException e2) {
			System.out.println("�쳣 "+ e2.getMessage());
			//throw new PortInUse();
			return null;
		}
		return null;
	}

	/**
	 * �رմ���
	 * 
	 * @param serialport
	 *            ���رյĴ��ڶ���
	 */
	public static void comClose(SerialPort serialPort) {
		if (serialPort != null) {
			serialPort.notifyOnDataAvailable(false);
			serialPort.removeEventListener();
			serialPort.close();
			System.out.println("Close " + serialPort + " sucessfully !");
			//serialPort = null;
		}
	}

	/**
	 * �����ڷ�������
	 * 
	 * @param serialPort
	 *            ���ڶ���
	 * @param data
	 *            ����������
	 * @throws InterruptedException
	 * @throws SendDataToSerialPortFailure
	 *             �򴮿ڷ�������ʧ��
	 * @throws SerialPortOutputStreamCloseFailure
	 *             �رմ��ڶ�������������
	 */
	public static boolean sendToPort(SerialPort serialPort, byte[] data) {
		//A0 03 A8 00 B5
		boolean flag = false;
		OutputStream out = null;
		try {
			out = serialPort.getOutputStream();
			out.write(data);
		    try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			out.flush();
			flag = true;
		} catch (IOException e) {
			try {
				throw new SendDataToSerialPortFailure();
			} catch (SendDataToSerialPortFailure e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
				try {
					throw new SerialPortOutputStreamCloseFailure();
				} catch (SerialPortOutputStreamCloseFailure e1) {
					e1.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * �Ӵ��ڶ�ȡ����
	 * 
	 * @param serialPort
	 *            ��ǰ�ѽ������ӵ�SerialPort����
	 * @return ��ȡ��������
	 * @throws ReadDataFromSerialPortFailure
	 *             �Ӵ��ڶ�ȡ����ʱ����
	 * @throws SerialPortInputStreamCloseFailure
	 *             �رմ��ڶ�������������
	 * @throws InterruptedException
	 */
	public static byte[] readFromPort(SerialPort serialPort)
			throws ReadDataFromSerialPortFailure,
			SerialPortInputStreamCloseFailure, InterruptedException {
		InputStream in = null;
		byte[] bytes = null;
		try {
			in = serialPort.getInputStream();
			int bufflenth = in.available(); // ��ȡbuffer������ݳ���
			while (bufflenth != 0) {
				bytes = new byte[bufflenth]; // ��ʼ��byte����Ϊbuffer�����ݵĳ���
				in.read(bytes);
				bufflenth = in.available();
			}
		} catch (IOException e) {
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//throw new ReadDataFromSerialPortFailure();
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException e) {
				//throw new SerialPortInputStreamCloseFailure();
				return null;
			}
		}
		return bytes;
	}
	/**
	 * ��Ӽ�����
	 * 
	 * @param port
	 *            ���ڶ���
	 * @param listener
	 *            ���ڼ�����
	 * @throws TooManyListener
	 *             ������������
	 */
	public static void addListener(SerialPort port,SerialPortEventListener listener) throws TooManyListeners {
		try {
			// ��������Ӽ�����
			port.addEventListener(listener);
			// ���õ������ݵ���ʱ���Ѽ��������߳�
			port.notifyOnDataAvailable(true);
			// ���õ�ͨ���ж�ʱ�����ж��߳�
			port.notifyOnBreakInterrupt(true);
			//port.removeEventListener();
			//port.notifyOnDataAvailable(false);
			//port.close();
		} catch (TooManyListenersException e) {
			throw new TooManyListeners();
		}
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
			//readComm();
			break;
		default:
			break;
		}
	}
}