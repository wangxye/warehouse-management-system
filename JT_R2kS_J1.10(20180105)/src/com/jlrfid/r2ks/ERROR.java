package com.jlrfid.r2ks;

import javax.swing.JOptionPane;

/**
 * 
 * @author zhuQixiang createData 2017-08-03
 * 
 */
public class ERROR {
	/**
	 * ������������ȷ���
	 */
	public final static int R_OK = 0;
	/**
	 * ���������ش�����
	 */
	public final static byte HOST_ERROR	= (byte) 0x80;
	/**
	 * ��Ч�����ַ���
	 */
	public final static byte INVALID_HOST_IPADDRESS = -2;
	/**
	 * ��Ч���ں�
	 */
	public final static byte INVALID_COMM_PORT = -3;
	/**
	 * ��Ч�˿ںŻ��߲�����
	 */
	public final static byte INVALID_HOST_PORTORBANDRATE = -4;
	/**
	 * ��ָ��
	 */
	public final static byte INVALID_POINTER = -5;
	/**
	 * ��Ч��ͨ��ģʽ
	 */
	public final static byte INVALID_COMM_MODE = -6;
	/**
	 * ��Ч�����
	 */
	public final static byte INVALID_BANK = -7;
	/**
	 * ��Ч����ʼλ�úʹ�С
	 */
	public final static byte INVALID_BEGIN_SIZE = -8;
	/**
	 * ��Ч����
	 */
	public final static byte INVALID_PASSWORD = -9;
	/**
	 * ��Ч���ʿ���
	 */
	public final static byte INVALID_ACCESS_PASSWORD = -10;
	/**
	 * ��Ч������
	 */
	public final static byte INVALID_KILL_PASSWORD = -11;
	/**
	 * ��Ч������
	 */
	public final static byte INVALID_OPCODE = -12;
	/**
	 * ��Ч�Ķ˿�
	 */
	public final static byte INVALID_PORT_VALUE = -13;
	/**
	 * ��Ч״ֵ̬
	 */
	public final static byte INVALID_STATE_VALUE = -14;
	/**
	 * ��Чģʽֵ
	 */
	public final static byte INVALID_MODE_VALUE = -24;
	/**
	 * ��Ч����
	 */
	public final static byte INVALID_CLOCK = -25;
	/**
	 * ��Ч����
	 */
	public final static byte INVALID_PARAMETERS = -28;
	/**
	 * ��������ʧ��
	 */
	public final static byte SOKET_CONNECT_FAIL = -15;
	/**
	 * ��������ʧ��
	 */
	public final static byte COM_CONNECT_FAIL = -16;
	/**
	 * ��ȡ����ʧ��
	 */
	public final static byte ERROR_GET_ANT = -26;
	/**
	 * �����ʼ����
	 */
	public final static byte ERROR_NET_INIT = -17;
	/**
	 * ���ڳ�ʼ����
	 */
	public final static byte ERROR_COM_INIT = -18;
	/**
	 * �豸���ʹ�
	 */
	public final static byte ERROR_DEV_SEND = -19;
	/**
	 * �豸���մ�
	 */
	public final static byte ERROR_DEV_RECV = -20;
	/**
	 * �豸δ����
	 */
	public final static byte ERROR_DEV_CONNECT = -21;
	/**
	 * ����ʧ��
	 */
	public final static byte ERROR_OPER_FAIL = -23;
	/**
	 * �򿪴��ڴ�
	 */
	public final static byte ERROR_OPEN_COMM = -24;
	/**
	 * ���ô��ڻ�����
	 */
	public final static byte ERROR_SET_COMMBUFFER = -25;
	/**
	 * ���ô��ڳ�ʱ�ṹ
	 */
	public final static byte ERROR_SET_COMMTIMEOUT = -26;
	/**
	 * ����DCB����
	 */
	public final static byte ERROR_SET_DCB = -27;
	/**
	 * ������
	 */
	public static int R_FAIL = -1;
	/**
	 * ȫ�ֱ����������
	 */
	public static int ErrorMessage = 0;
	/**
	 * 
	 * @param content
	 *            �Ի�������ʾ����
	 * @param title
	 *            �Ի������
	 */
	public static void setErrorMessage(Object contaier, String title) {
		JOptionPane.showMessageDialog(null, contaier, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static String format(byte btErrorCode) {
		String strErrorCode = "";
		switch (btErrorCode) {
		case 16:
			strErrorCode = "";
			break;
		default:
			strErrorCode = "δ֪����";
		}
		return strErrorCode;
	}
}