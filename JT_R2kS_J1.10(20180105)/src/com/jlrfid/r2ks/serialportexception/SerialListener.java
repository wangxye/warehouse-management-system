package com.jlrfid.r2ks.serialportexception;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import javax.swing.JOptionPane;

/**
 * ���ڲ�����ʽ����һ�����ڼ�����
 * 
 * @author zhong
 *
 */
public class SerialListener implements SerialPortEventListener {
	/**
	 * �����ص��Ĵ����¼�
	 */
	public void serialEvent(SerialPortEvent serialPortEvent,SerialPort serialPort) {
		switch (serialPortEvent.getEventType()) {
		case SerialPortEvent.BI: // 10 ͨѶ�ж�
			JOptionPane.showMessageDialog(null, "�봮���豸ͨѶ�ж�", "����", JOptionPane.INFORMATION_MESSAGE);
			break;
		case SerialPortEvent.OE: // 7 ��λ�����������

		case SerialPortEvent.FE: // 9 ֡����

		case SerialPortEvent.PE: // 8 ��żУ�����

		case SerialPortEvent.CD: // 6 �ز����

		case SerialPortEvent.CTS: // 3 �������������

		case SerialPortEvent.DSR: // 4 ����������׼������

		case SerialPortEvent.RI: // 5 ����ָʾ

		case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 ��������������
			break;

		case SerialPortEvent.DATA_AVAILABLE: // 1 ���ڴ��ڿ�������
			// System.out.println("found data");
			byte[] data = null;
			try {
				if (serialPort == null) {
					JOptionPane.showMessageDialog(null, "���ڶ���Ϊ�գ�����ʧ�ܣ�", "����", JOptionPane.INFORMATION_MESSAGE);
				} else {
					try {
						data = SerialPortTool.readFromPort(serialPort);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // ��ȡ���ݣ������ֽ�����
					// System.out.println(new String(data));

					// �Զ���������̣�����ʵ��ʹ�ù����п��԰����Լ��������ڽ��յ����ݺ�����ݽ��н���
					if (data == null || data.length < 1) { // ��������Ƿ��ȡ��ȷ
						JOptionPane.showMessageDialog(null, "��ȡ���ݹ�����δ��ȡ����Ч���ݣ������豸�����", "����",
								JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					} else {
						String dataOriginal = new String(data); // ���ֽ���������ת��λΪ������ԭʼ���ݵ��ַ���
						String dataValid = ""; // ��Ч���ݣ���������ԭʼ�����ַ���ȥ���ͷ*���Ժ���ַ�����
						String[] elements = null; // �������水�ո���ԭʼ�ַ�����õ����ַ�������
						// ��������
						if (dataOriginal.charAt(0) == '*') { // �����ݵĵ�һ���ַ���*��ʱ��ʾ���ݽ�����ɣ���ʼ����
							dataValid = dataOriginal.substring(1);
							elements = dataValid.split(" ");
							if (elements == null || elements.length < 1) { // ��������Ƿ������ȷ
								JOptionPane.showMessageDialog(null, "���ݽ������̳��������豸�����", "����",
										JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);
							} else {
								try {
							
								} catch (ArrayIndexOutOfBoundsException e) {
									JOptionPane.showMessageDialog(null, "���ݽ������̳������½�������ʧ�ܣ������豸�����", "����",
											JOptionPane.INFORMATION_MESSAGE);
									System.exit(0);
								}
							}
						}
					}

				}

			} catch (ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e) {
				JOptionPane.showMessageDialog(null, e, "����", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0); // ������ȡ����ʱ��ʾ������Ϣ���˳�ϵͳ
			}
			break;
		}
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
