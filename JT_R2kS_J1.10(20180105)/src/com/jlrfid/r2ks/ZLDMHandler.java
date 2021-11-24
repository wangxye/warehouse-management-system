package com.jlrfid.r2ks;

/**
 * Dll ׿᰺��Ĳ�����
 * 
 * @author zhuQixiang createData 2017-05-15
 * 
 */
public class ZLDMHandler {
	// ���������豸����
	public int devCount = 0;

	public static byte selectedDevNo = 0;

	/**
	 * ����Dll�ļ�
	 * 
	 * @param dllName
	 *            dll ���ļ�����,����ļ�λ������Ŀ��Ŀ¼�£�dllNameֱ�����ļ������ɣ�����ļ����Ʋ�����Ŀ��Ŀ¼�£�
	 *            dllName������ļ�·��
	 * @return �����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public boolean dllInit(String dllName) {
		return ZLDMOperate.init(dllName);
	}

	/**
	 * ���������豸
	 * 
	 * @return �豸��
	 */
	public short startSearchDev() {
		return ZLDMOperate.lib.ZLDM_StartSearchDev();
	}

	/**
	 * ��ȡ�汾��
	 * 
	 * @return �汾��
	 */
	public int getVer() {
		return ZLDMOperate.lib.ZLDM_GetVer();
	}

	/**
	 * ���ò���
	 * 
	 * @param nNum
	 *            �豸��
	 * @return ���óɹ�������true������false
	 */
	public boolean setParam(byte nNum) {
		return ZLDMOperate.lib.ZLDM_SetParam(nNum);
	}

	/**
	 * ��ȡ�豸���
	 * 
	 * @param nNum
	 *            �豸��
	 * @return �豸���
	 */
	public int getDevID(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetDevID(nNum);
	}

	/**
	 * ��ȡ�豸����
	 * 
	 * @param nNum
	 *            �豸��
	 * @return �豸����
	 */
	public String getDevName(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetDevName(nNum);
	}

	/**
	 * ��ȡIPģʽ
	 * 
	 * @param nNum
	 *            �豸��
	 * @return IPģʽ
	 */
	public byte getIPMode(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetIPMode(nNum);
	}

	/**
	 * ��ȡIP
	 * 
	 * @param nNum
	 *            �豸��
	 * @return IP
	 */
	public String getIP(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetIP(nNum);
	}

	/**
	 * ��ȡ�˿�
	 * 
	 * @param nNum
	 *            �豸��
	 * @return �˿�
	 */
	public short getPort(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetPort(nNum);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param nNum
	 *            �豸��
	 * @return ����
	 */
	public String getGateWay(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetGateWay(nNum);
	}

	/**
	 * ��ȡ����ģʽ
	 * 
	 * @param nNum
	 *            �豸��
	 * @return ����ģʽ
	 */
	public byte getWorkMode(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetWorkMode(nNum);
	}

	/**
	 * ��ȡ ��������
	 * 
	 * @param nNum
	 *            �豸��
	 * @return ��������
	 */
	public String getNetMask(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetNetMask(nNum);
	}

	/**
	 * ��ȡĿ������
	 * 
	 * @param nNum
	 *            �豸��
	 * @return Ŀ������
	 */
	public String getDestName(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetDestName(nNum);
	}

	/**
	 * ��ȡĿ�Ķ˿�
	 * 
	 * @param nNum
	 *            �豸��
	 * @return Ŀ�Ķ˿�
	 */
	public short getDestPort(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetDestPort(nNum);
	}

	/**
	 * ��ȡ������ָ��
	 * 
	 * @param nNum
	 *            �豸��
	 * @return ������ָ��
	 */

	public byte getBaudrateIndex(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetBaudrateIndex(nNum);
	}

	/**
	 * ����У��λ
	 * 
	 * @param nNum
	 *            �豸��
	 * @return У��λ
	 */
	public byte getParity(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetParity(nNum);
	}

	/**
	 * ��ȡ����λ
	 * 
	 * @param nNum
	 *            �豸��
	 * @return ����λ
	 */
	public byte getDataBits(byte nNum) {
		return ZLDMOperate.lib.ZLDM_GetDataBits(nNum);
	}

	/**
	 * ���������豸����
	 * 
	 * @param nNum
	 *            �豸��
	 * @param DevName
	 *            �豸����
	 * @return ���óɹ�������true������false
	 */
	public boolean setDevName(byte nNum, char DevName) {
		return ZLDMOperate.lib.ZLDM_SetDevName(nNum, DevName);
	}

	/**
	 * ����IP
	 * 
	 * @param nNum
	 *            �豸��
	 * @param IP
	 *            IP
	 * @return ���óɹ�������true������false
	 */
	public boolean setIP(byte nNum, byte[] IP) {
		return ZLDMOperate.lib.ZLDM_SetIP(nNum, IP);
	}

	/**
	 * ��������
	 * 
	 * @param nNum
	 *            �豸��
	 * @param GateWay
	 *            ����
	 * @return ���óɹ�������true������false
	 */
	public boolean setGateWay(byte nNum, byte[] GateWay) {
		return ZLDMOperate.lib.ZLDM_SetGateWay(nNum, GateWay);
	}

	/**
	 * ������������
	 * 
	 * @param nNum
	 *            �豸��
	 * @param NetMask
	 *            ��������
	 * @return ���óɹ�������true������false
	 */
	public boolean setNetMask(byte nNum, byte[] NetMask) {
		return ZLDMOperate.lib.ZLDM_SetNetMask(nNum, NetMask);
	}

	/**
	 * �����豸����
	 * 
	 * @param nNum
	 *            �豸��
	 * @param DestName
	 *            �豸����
	 * @return ���óɹ�������true������false
	 */
	public boolean setDestName(byte nNum, byte[] DestName) {
		return ZLDMOperate.lib.ZLDM_SetDestName(nNum, DestName);
	}

	/**
	 * ����IPģʽ
	 * 
	 * @param nNum
	 *            �豸��
	 * @param IPMode
	 *            IPģʽ
	 * @return ���óɹ�������true������false
	 */
	public boolean setIPMode(byte nNum, byte IPMode) {
		return ZLDMOperate.lib.ZLDM_SetIPMode(nNum, IPMode);
	}

	/**
	 * ���ö˿�
	 * 
	 * @param nNum
	 *            �豸��
	 * @param Port
	 *            �˿�
	 * @return ���óɹ�������true������false
	 */
	public boolean setPort(byte nNum, short Port) {
		return ZLDMOperate.lib.ZLDM_SetPort(nNum, Port);
	}

	/**
	 * ���ù���ģʽ
	 * 
	 * @param nNum
	 *            �豸��
	 * @param WorkMode
	 *            ����ģʽ
	 * @return ���óɹ�������true������false
	 */
	public boolean setWorkMode(byte nNum, byte WorkMode) {
		return ZLDMOperate.lib.ZLDM_SetWorkMode(nNum, WorkMode);
	}

	/**
	 * ����Ŀ�Ķ˿�
	 * 
	 * @param nNum
	 *            �豸��
	 * @param DestPort
	 *            Ŀ�Ķ˿�
	 * @return ���óɹ�������true������false
	 */
	public boolean setDestPort(byte nNum, short DestPort) {
		return ZLDMOperate.lib.ZLDM_SetDestPort(nNum, DestPort);
	}

	/**
	 * ���ò�����
	 * 
	 * @param nNum
	 *            �豸��
	 * @param BaudrateIndex
	 *            ������ָ��
	 * @return ���óɹ�������true������false
	 */
	public boolean setBaudrateIndex(byte nNum, byte BaudrateIndex) {
		return ZLDMOperate.lib.ZLDM_SetBaudrateIndex(nNum, BaudrateIndex);
	}

	/**
	 * ����У��λ
	 * 
	 * @param nNum
	 *            �豸��
	 * @param Parity
	 *            У��λ
	 * @return ���óɹ�������true������false
	 */
	public boolean setParity(byte nNum, byte Parity) {
		return ZLDMOperate.lib.ZLDM_SetParity(nNum, Parity);
	}

	/**
	 * ��������λ
	 * 
	 * @param nNum
	 *            �豸��
	 * @param DataBits
	 *            ����λ
	 * @return ���óɹ�������true������false
	 */
	public boolean setDataBits(byte nNum, byte DataBits) {
		return ZLDMOperate.lib.ZLDM_SetDataBits(nNum, DataBits);
	}
}
