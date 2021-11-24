package com.jlrfid.r2ks;

public class CMD {
	// ------------------- ��ʼ�롢����----------------------------------------
	/**
	 * ������ʼ��1
	 */
	public static byte NET_START_CODE1 = (0x43);
	/**
	 * ������ʼ��2
	 */
	public static byte NET_START_CODE2 = (0x4D);
	/**
	 * ������ʼ��
	 */
	public static byte COM_START_CODE0 = (0x1B);
	/**
	 * ͨѶ�����趨
	 */
	public static byte ALIVE_CODE = (0x10);
	/**
	 * ����ͷ���ȣ�����bcc
	 */
	public static byte HEAD_LENGTH = (0x06);
	/**
	 * �����������ݽṹ����
	 */
	public static byte ANT_CFG_LENGTH = (36);
	/**
	 * �汾�����ݳ���
	 */
	public static byte VERSION_LENGTH = (16);
	// --------------------����ID------------------------------------------------
	/**
	 * ͨѶ�����趨
	 */
	public static byte UHF_ALIVE = (0x10);
	/**
	 * Ѱ��һ��
	 */
	public static byte UHF_INV_ONCE = (0x25);
	/**
	 * ��������
	 */
	public static byte UHF_READ_TAG_DATA = (0x26);
	/**
	 * д������
	 */
	public static byte UHF_WRITE_TAG_DATA = (0x27);
	/**
	 * ��ʼѭ��Ѱ��
	 */
	public static byte UHF_INV_MULTIPLY_BEGIN = (0x2A);
	/**
	 * ֹͣѭ��Ѱ��
	 */
	public static byte UHF_INV_MULTIPLY_END = (0x2B);
	/**
	 * ����Ƭ
	 */
	public static byte UHF_LOCK_TAG = (0x2D);
	/**
	 * ע����Ƭ
	 */
	public static byte UHF_KILL_TAG = (0x2E);
	/**
	 * ��ȡ�豸�汾��
	 */
	public static byte UHF_GET_VERSION = (0x31);
	/**
	 * ��ȡ���߲���
	 */
	public static byte UHF_GET_ANT_CONFIG = (0x32);
	/**
	 * �趨���߲���
	 */
	public static byte UHF_SET_ANT_CONFIG = (0x33);
	/**
	 * ��ȡDigital Input״̬
	 */
	/**
	 * �趨Digital Output״̬
	 */
	public static byte UHF_SET_DO_STATE = (0x39);
	/**
	 * �趨������������(0x1B)
	 */
	public static byte UHF_SET_READ_ZONE = (0x68);
	/**
	 * ����Ĭ��epc����(0x18)
	 */
	public static byte UHF_SET_DEFAULT_ZONE = (0x66);
	/**
	 * ���������ж�ʱ��
	 */
	public static byte UHF_SET_NEIGH_JUDGE = (0x1A);
	/**
	 * ��ȡ����
	 */
	public static byte UHF_GET_PASSWORD = (0X60);
	/**
	 * �趨��������(0x1C)
	 */
	//public static byte UHF_SET_PASSWORD = (0x61);
	/**
	 * ��ȡ�豸��(0x21)
	 */
	public static byte UHF_GET_DEVICE_NO = (0x62);
	/**
	 * �趨�豸��(0x20)
	 */
	public static byte UHF_SET_DEVICE_NO = (0x63);
	/**
	 * �趨AB������ʽ(0x1D)
	 */
	public static byte UHF_SET_AB_MODE = (0x1C);
	/**
	 * �趨�豸ʱ��
	 */
	public static byte UHF_SET_CLOCK = (0x40);
	/**
	 * �趨�豸ʱ��
	 */
	public static byte UHF_GET_CLOCK = (0x3F);
	/**
	 * ��ȡ����
	 */
	public static byte UHF_GET_BUZZ = (0x1D);
	/**
	 * ��������
	 */
	public static byte UHF_SET_BUZZ = (0x1E);
	/**
	 * ������43 4D(����) 1E 00 01 00 01(��) 00
	 */
	public static byte UHF_SET_BUZZ_OPEN = (0x01);
	/**
	 * �ر�����43 4D(����) 1E 00 01 00 00(�ر�) 00
	 */
	public static byte UHF_SET_BUZZ_CLOSE = (0x00);
	/**
	 * ��ȡ��ǩ����
	 */
	public static byte UHF_GET_TAG_BUFFER = (0x3A);
	/**
	 * ��ձ�ǩ����
	 */
	public static byte UHF_RESET_TAG_BUFFER = (0x3B);
	/**
	 * ��ȡƵ���������
	 */
	public static byte UHF_GET_REGION_CONFIG = (0x34);
	/**
	 * �趨Ƶ���������
	 */
	public static byte UHF_SET_REGION_CONFIG = (0x35);
	/**
	 * ��ȡ��Դ����ģʽ
	 */
	public static byte UHF_GET_POWMODE = (0x36);
	/**
	 * �趨��Դ����ģʽ
	 */
	public static byte UHF_SET_POWMODE = (0x37);
	/**
	 * ��ȡDigital Input״̬
	 */
	public static byte UHF_GET_DI_STATE = (0x38);
	/**
	 * ִ�б�ǩ˽��ָ��
	 */
	public static byte UHF_EXE_TAG_SPEC = (0x3C);
	/**
	 * ��ǩ˽��ָ�����������
	 */
	public static byte UHF_ERASE_BLOCK_SPEC = (0x3D);
	/**
	 * ��ȡ�豸ͳ����Ϣ
	 */
	public static byte UHF_GET_STAT = (0x3E);
	/**
	 * ��ȡ�豸�������
	 */
	public static byte UHF_GET_CONFIGURE = (0x3F);
	/**
	 * �趨�豸�������
	 */
	public static byte UHF_SET_CONFIGURE = (0x40);
	/**
	 * �趨��������(0x1C)
	 */
	public static byte UHF_SET_PASSWORD = (0x12);
	/**
	 * ��ȡ������������ 01�����Զ����ȡ��Ϣ��00����Ĭ��EPC��Ϣ��
	 */
	public static byte UHF_GET_READ_ZONE = (0x16);
	/**
	 * ��ȡ�Զ����������
	 */
	public static byte UHF_GET_READZONE_PARA = (0x18);
	/**
	 * �����Զ����������
	 */
	public static byte UHF_SET_READZONE_PARA = (0x19);
	/**
	 * ��ȡ��ǩ����ʱ��
	 */
	public static byte UHF_GET_TAG_FILTER = (0x1A);
	/**
	 * ���ñ�ǩ����ʱ��
	 */
	public static byte UHF_SET_TAG_FILTER = (0x1B);
	/**
	 * ��ȡ����ģʽ����
	 */
	public static byte UHF_GET_MODE = (0x59);
	/**
	 * ���豸�Ĺ���ģʽ, 01����ģʽ��02��ʱģʽ��03����ģʽ
	 */
	public static byte UHF_SET_MODE = (0x5A);
	/**
	 * ��ȡ����ʱ��
	 */
	public static byte UHF_GET_TRIGGER_TIME = (0x5B);
	/**
	 * �趨����ʱ��
	 */
	public static byte UHF_SET_TRIGGER_TIME = (0x5C);
	/**
	 * ��ȡ�����ʽ
	 */
	public static byte UHF_GET_OUTPUT = (0x70);
	/**
	 * ���������ʽ 1 COM 2 NET 3 RS485 4 WIFI/USB->COM
	 */
	public static byte UHF_SET_OUTPUT = (0x71);
	/**
	 * ��ȡ�Զ����ͨ������
	 */
	public static byte UHF_GET_AUTO_OUT = (0x70);
	/**
	 * �趨�Զ����ͨ������
	 */
	public static byte UHF_SET_AUTO_OUT = (0x71);
	/**
	 * ��ȡRSSI
	 */
	public static byte UHF_GET_RSSI = (0x73);
	// ----------------���������뼰��������-------------------------------------
	byte UNLOCK = (0x0);
	byte PERMANENCE_WRIALBE = (0x1);
	byte SECURITY_LOCK = (0x2);
	byte PERMANENCE_UNWRIABLE = (0x3);

	byte BLOCK_KILL = (0x0);
	byte BLOCK_ACCESS = (0x1);
	byte BLOCK_EPC = (0x2);
	byte BLOCK_TID = (0x3);
	byte BLOCK_USER = (0x4);
	// ---------------------------------------------------------------------------
}
