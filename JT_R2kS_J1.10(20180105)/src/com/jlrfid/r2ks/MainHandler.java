package com.jlrfid.r2ks;

import java.nio.ByteBuffer;

import com.jlrfid.r2ks.util.Transform;

/**
 * ����Ƶ���ú�����
 * 
 * @author zhuQixiang createDate 2017-10-25
 * 
 */
public class MainHandler extends PACKAGE {
	public final static boolean R_FAIL = false;
	public final static boolean R_OK = true;
	public static int count = 0;

	/**
	 * �����豸
	 * 
	 * @param host
	 *            IP�򴮿�
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static UHFOperate connectDev(String host) {
		UHFOperate reader = new UHFOperate();
		reader.setHost(reader, host);
		if (!reader.connect(reader)) {
			reader.deviceConnected = false;
			reader = null;
		} else {
			reader.deviceConnected = true;
		}
		return reader;
	}

	/**
	 * �Ͽ�����
	 * 
	 * @param r2k
	 *            ����Ƶ���� ����Ƶ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean disconnectDev(UHFOperate r2k) {
		if (!r2k.deviceConnected) {
			return R_FAIL;
		} else {
			r2k.disconnectDev(r2k);
		}
		return R_OK;
	}

	/**
	 * ��ȡ�汾��
	 * 
	 * @param r2k
	 *            ����Ƶ���� ����Ƶ����
	 * @param deviceNo
	 *            �豸��
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static String getDevVersion(UHFOperate r2k) {
		ByteBuffer buffer = ByteBuffer.allocate(16);
		boolean result = r2k.getDevVersion(r2k, buffer);
		if(result){
			return new String(buffer.array());
		}
		return null;
	}

	/**
	 * Ѱ��һ��
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param getReadData
	 *            ��ȡ��ȡ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean invOnce(UHFOperate r2k) {
		return r2k.invOnce(r2k);
	}

	/**
	 * ����ѭ��
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param getReadData
	 *            ��ȡ��ȡ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean beginInv(UHFOperate r2k) {
		return r2k.beginInv(r2k);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static AntStruct getAnt(UHFOperate r2k) {
		if (!r2k.deviceConnected) {
			return null;
		}
		UHFOperate reader = r2k;
		AntStruct struct = new AntStruct();
		ByteBuffer buffer = ByteBuffer.allocate(36);
		reader.getAnt(r2k, buffer);
		int power = 0;
		int time = 0;
		StringBuffer sb = null;
		for (int i = 0; i < buffer.array().length; i++) {
			int getValue = buffer.array()[i];
			if (i < 4) {
				struct.antEnable[i] = (byte) getValue;
			} else if (i > 3 && i < 20) {
				if (time < 4) {
					int count = 0;
					sb = new StringBuffer();
					int minus = 4 + i;
					do {
						String tempData = Transform.bytesToHexString(buffer
								.array()[--minus]);
						if (!tempData.equals("00")) {
							sb.append(tempData);
						}
						count++;
					} while (count < 4);
					i += 3;
				}
				struct.dwellTime[time] = Integer.parseInt(sb.toString(), 16);
				time++;
			} else {
				if (power < 4) {
					int count = 0;
					sb = new StringBuffer();
					int minus = 4 + i;
					do {
						String tempData = Transform.bytesToHexString(buffer
								.array()[--minus]);
						if (!tempData.equals("00")) {
							sb.append(tempData);
						}
						count++;
					} while (count < 4);
					i += 3;
				}
				struct.power[power] = Integer.parseInt(sb.toString(), 16);
				power++;
			}
		}
		return struct;
	}

	/**
	 * ��������
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param antEnable
	 *            ���� 1��,2��,3��,4��
	 * @param dwellTime
	 *            ����ʱ��
	 * @param power
	 *            ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 * @throws RFIDException
	 *             �׳��쳣
	 */
	public static boolean setAnt(UHFOperate r2k, byte[] antEnable,
			int[] dwellTime, int[] power) throws RFIDException {
		AntStruct struct = new AntStruct();
		for (int i = 0; i < 4; i++) {
			if (antEnable[i] != 0 && antEnable[i] != 1) {
				throw new RFIDException("����antEnable��ֵֻ��Ϊ0��1");
			}
			struct.antEnable[i] = antEnable[i];
			if (dwellTime[i] < 50 && dwellTime[i] > 10000) {
				throw new RFIDException("����dwellTime��ֵֻ��Ϊ50-10000");
			}
			struct.dwellTime[i] = dwellTime[i];
			if (power[i] < 20 && power[i] > 33) {
				throw new RFIDException("����power��ֵֻ��Ϊ20-33");
			}
			struct.power[i] = power[i];
		}
		boolean result = r2k.setAnt(r2k, struct);
		return result;
	}

	/**
	 * ֹͣѭ��
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean stopInv(UHFOperate r2k) {
		return r2k.stopInv(r2k);
	}

	/**
	 * ��ȡ���ſ����ݣ������޶���ȡ���򣬿�ʼ��ַ�Ͷ�ȡ����
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param bank
	 *            Ҫ�������򡣸�ֵ���������£� 0���������� 1����EPC�� 2����TID�� 3�����û���
	 * @param begin
	 *            Ҫ�������еĵ�ַ��ȡֵΪ��Χ0-7��
	 * @param size
	 *            Ҫ��ȡ�ĳ��ȣ�ȡֵ��Χ��1��8��1Word = 2Byte =
	 *            16λ����˵����bank=EPC����begin+size��ֵ������8bank=��������begin+size��ֵ������4������
	 * @param password
	 *            ������ ����Ĭ��Ϊ��
	 * @return �ɹ�ʱ������16���������ַ��� ʧ��ʱ������-1
	 * @throws RFIDException
	 *             �׳��쳣
	 */
	public static String readTagData(UHFOperate r2k, byte bank, byte begin,
			byte size, byte[] password) throws RFIDException {
		if (bank < 0 || begin < 0 || size < 0) {
			throw new RFIDException("bank/begin/size��������������");
		}
		if (bank == 1 && (begin + size > 8)) {
			throw new RFIDException(
					"��ȡEPC������ʱ��begin(�������еĵ�ַ)+size(Ҫ��ȡ�ĳ���)��ֵ������8�������������ֵ��");
		}
		if (bank == 0 && (begin + size > 4)) {
			throw new RFIDException(
					"��ȡ����������ʱ��begin(�������еĵ�ַ)+size(Ҫ��ȡ�ĳ���)��ֵ������4�������������ֵ��");
		}
		if (password == null || "".equals(password)) {
			for (int i = 0; i < 4; ++i) {
				password[0] = (byte) 0;
			}
		}
		ByteBuffer buffer = ByteBuffer.allocate(size * 2);
		boolean result = r2k.readTagData(r2k, bank, begin, size, buffer,
				password);
		if (result) {
			return Transform.bytesToHexString(buffer.array());
		}
		return null;
	}

	/**
	 * д���ſ����ݣ������޶�д�����򣬿�ʼ��ַ�Ͷ�ȡ����
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param bank
	 *            Ҫ�������򡣸�ֵ���������£� 0���������� 1����EPC�� 2����TID�� 3�����û���
	 * @param begin
	 *            Ҫ�������еĵ�ַ��ȡֵΪ��Χ0-7��
	 * @param size
	 *            Ҫ��ȡ�ĳ��ȣ�ȡֵ��Χ��1��8��1Word = 2Byte =
	 *            16λ����˵����bank=EPC����begin+size��ֵ������8bank=��������begin+size��ֵ������4������
	 * @param data
	 *            д�������
	 * @param password
	 *            ������ ����Ĭ��Ϊ��
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 * @throws RFIDException
	 */
	public static boolean writeTagData(UHFOperate r2k, byte bank, byte begin,byte size, String data, byte[] password) throws RFIDException {
		if (bank < 0 || begin < 0 || size < 0) {
			throw new RFIDException("bank/begin/size��������������");
		}
		if (bank > 3) {
			throw new RFIDException("bankֻ����0-3");
		}
		if (bank == 1 && (begin + size > 8 || begin < 2)) {
			throw new RFIDException(
					"дEPC������ʱ��begin�����2��ʼ������begin(д�����еĵ�ַ)+size(Ҫд�ĳ���)��ֵ������8�������������ֵ��");
		}
		if (bank == 0 && (begin + size > 4)) {
			throw new RFIDException(
					"д����������ʱ��begin(д�����еĵ�ַ)+size(Ҫд�ĳ���)��ֵ������4�������������ֵ��");
		}
		if (data.length() != 4 * size) {
			throw new RFIDException("data�ĳ��ȱ�����size*4����");
		}
		if (password == null || "".equals(password)) {
			for (int i = 0; i < 4; ++i) {
				password[0] = (byte) 0;
			}
		}
		return r2k.writeTagData(r2k, bank, begin, size, data, password);
	}

	/**
	 * ����
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param opcode
	 *            ������ 0~3 �ֱ��ʾ��������������������ȫ���������ý���,���� 0 ��ʾ����
	 * @param block
	 *            �������� 0~4 �ֱ��ʾKill��Access��EPC��TID ��User
	 * @param password
	 *            ��������
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean lockTag(UHFOperate r2k, byte locktType,
			byte lockBank, byte[] password) {
		return r2k.lockTag(r2k, locktType, lockBank, password);
	}

	/**
	 * ��ȡ����������
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static int getBuzzer(UHFOperate r2k) {
		int total = -1;
		ByteBuffer buffer = ByteBuffer.allocate(1);
		boolean result = r2k.getBuzzer(r2k, buffer);
		if (result) {
			total = buffer.array()[0];
		}
		return total;
	}

	/**
	 * ���÷���������
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param buzz
	 *            1�Ǵ򿪣� 0�ǹرգ�
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean setBuzzer(UHFOperate r2k, byte buzz) {
		return r2k.setBuzzer(r2k, buzz);
	}

	/**
	 * ��ȡDigital input״̬
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean getDI(UHFOperate r2k, ByteBuffer state) {
		return r2k.getDI(r2k, state);
	}

	/**
	 * �趨Digital Output״̬
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param port
	 * @param state
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean setDO(UHFOperate r2k, int port, int state) {
		return r2k.setDO(r2k, port, state);
	}

	/**
	 * ���ù���ģʽ
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param mode
	 *            1�����ӣ�2�Ƕ�ʱ��3�Ǵ�����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean setWorkMode(UHFOperate r2k, int mode) {
		return r2k.setWorkMode(r2k, mode);
	}

	/**
	 * ��ȡ����ģʽ
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param workMode
	 *            1�����ӣ�2�Ƕ�ʱ��3�Ǵ�����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean getWorkMode(UHFOperate r2k, ByteBuffer workMode) {
		return r2k.getWorkMode(r2k, workMode);
	}

	/**
	 * �趨����ʱ��
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param trigTime
	 *            0~254
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean setTrigModeDelayTime(UHFOperate r2k, byte trigTime) {
		return r2k.setTrigModeDelayTime(r2k, trigTime);
	}

	/**
	 * ��ȡ����ʱ��
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param trigTime
	 *            0~254
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean getTrigModeDelayTime(UHFOperate r2k,
			ByteBuffer trigTime) {
		return r2k.getTrigModeDelayTime(r2k, trigTime);
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param enableNJ
	 *            1~254
	 * @param neighJudgeTime
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean getNeighJudge(UHFOperate r2k, ByteBuffer enableNJ,
			ByteBuffer neighJudgeTime) {
		return r2k.getNeighJudge(r2k, enableNJ, neighJudgeTime);
	}

	/**
	 * ���������б�
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param neighJudgeTime
	 *            1~254
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean setNeighJudge(UHFOperate r2k, byte neighJudgeTime) {
		return r2k.setNeighJudge(r2k, neighJudgeTime);
	}

	/**
	 * �����豸��
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param deviceNo
	 *            0-254
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean setDeviceNo(UHFOperate r2k, byte deviceNo) {
		return r2k.setDeviceNo(r2k, deviceNo);
	}

	/**
	 * ��ȡ�豸��
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param deviceNo
	 *            0-254
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static String getDeviceNo(UHFOperate r2k) {
		ByteBuffer buffer = ByteBuffer.allocate(1);
		boolean result = r2k.getDeviceNo(r2k, buffer);
		if (result) {
			int getDevice = Transform.byteToInt(buffer.array()[0]);
			return String.valueOf(getDevice);
		}
		return null;
	}

	/**
	 * ��ȡʱ��
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param clock
	 *            ʱ��
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static String getClock(UHFOperate r2k) {
		ByteBuffer clock = ByteBuffer.allocate(1);
		boolean result = r2k.getClock(r2k, clock);
		if (result) {
			int getDevice = Transform.byteToInt(clock.array()[0]);
			return String.valueOf(getDevice);
		}
		return null;
	}

	/**
	 * ����ʱ��
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param clock
	 *            ��ȡ��ǰʱ��(��-��-��-ʱ-��-��)
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean setClock(UHFOperate r2k, byte[] clock) {
		boolean result = r2k.setClock(r2k, clock);
		if (result) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ������������
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param zone
	 *            01�����Զ����ȡ��Ϣ��00����Ĭ��EPC��Ϣ��
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static String getReadZone(UHFOperate r2k, ByteBuffer zone) {
		ByteBuffer buffer = ByteBuffer.allocate(1);
		boolean result = r2k.getReadZone(r2k, zone);
		if (result) {
			int getDevice = buffer.array()[0];
			getDevice = getDevice < 0 ? getDevice + 256 : getDevice;
			return String.valueOf(getDevice);
		}
		return null;
	}

	/**
	 * ��ȡ�Զ����������
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param bank
	 *            Ҫ�������򡣸�ֵ���������£� 0���������� 1����EPC�� 2����TID�� 3�����û���
	 * @param begin
	 *            Ҫ�������еĵ�ַ��ȡֵΪ��Χ0-7��
	 * @param length
	 *            Ҫ��ȡ�ĳ��ȣ�ȡֵ��Χ��1��8��1Word = 2Byte =
	 *            16λ����˵����bank=EPC����begin+size��ֵ������8bank=��������begin+size��ֵ������4������
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean getReadZonePara(UHFOperate r2k, ByteBuffer bank,
			ByteBuffer begin, ByteBuffer length) {
		return r2k.getReadZonePara(r2k, bank, begin, length);
	}

	/**
	 * �趨������������
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param state
	 *            ״̬
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean setReadZone(UHFOperate r2k, byte state) {
		return r2k.setReadZone(r2k, state);
	}

	/**
	 * �����Զ����������
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param bank
	 *            Ҫ�������򡣸�ֵ���������£� 0���������� 1����EPC�� 2����TID�� 3�����û���
	 * @param begin
	 *            Ҫ�������еĵ�ַ��ȡֵΪ��Χ0-7��
	 * @param length
	 *            Ҫ��ȡ�ĳ��ȣ�ȡֵ��Χ��1��8��1Word = 2Byte =
	 *            16λ����˵����bank=EPC����begin+size��ֵ������8bank=��������begin+size��ֵ������4������
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean setReadZonePara(UHFOperate r2k, byte bank,
			byte begin, byte length) {
		return r2k.setReadZonePara(r2k, bank, begin, length);
	}

	/**
	 * ��ȡ�����ʽ
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param outputMode
	 *            (1 COM 2 NET 3 RS485 4 WIFI/USB->COM)
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean getOutputMode(UHFOperate r2k, ByteBuffer outputMode) {
		return r2k.getOutputMode(r2k, outputMode);
	}

	/**
	 * ���������ʽ
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param outputMode
	 *            (1 COM 2 NET 3 RS485 4 WIFI/USB->COM)
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean setOutputMode(UHFOperate r2k, byte outputMode) {
		return r2k.setOutputMode(r2k, outputMode);
	}

	/**
	 * ��ȡ��ǩ����
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @param getOnceReadData
	 *            ���ݴ���
	 * @param readTime
	 *            ��ȡ��ʱ��
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean readTagBuffer(UHFOperate r2k,
			GetReadData getReadData, int readTime) {
		return r2k.readTagBuffer(r2k, getReadData, readTime);
	}

	/**
	 * ��ձ�ǩ����
	 * 
	 * @param r2k
	 *            ����Ƶ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean resetTagBuffer(UHFOperate r2k) {
		return r2k.resetTagBuffer(r2k);
	}

	/**
	 * ע����Ƭ
	 * @param r2k ����Ƶ����
	 * @param accessPwd �������� 
	 * @param killPwd  �������
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean killTag(UHFOperate r2k, byte[] accessPwd,
			byte[] killPwd) {
		return r2k.killTag(r2k, accessPwd, killPwd);
	}

	/**
	 * ͨѶ�����趨
	 * @param r2k ����Ƶ����
	 * @param interval ʱ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public static boolean setAlive(UHFOperate r2k, byte interval) {
		return r2k.setAlive(r2k, interval);
	}

	/**
	 * ��ȡ����ģʽ����ʱ��
	 * @param r2k ����Ƶ����
	 * @param state ״̬
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public boolean getRelayAutoState(UHFOperate r2k, ByteBuffer state) {
		return r2k.getRelayAutoState(r2k, state);
	}

	/**
	 * ���ô���ģʽ����ʱ��
	 * @param r2k ����Ƶ����
	 * @param state ״̬
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public boolean setRelayAutoState(UHFOperate r2k, byte state) {
		return r2k.setRelayAutoState(r2k, state);
	}

	/**
	 * ��ȡ�豸�������
	 * @param r2k ����Ƶ����
	 * @param para ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public boolean getDeviceConfig(UHFOperate r2k, ByteBuffer para) {
		return r2k.getDeviceConfig(r2k, para);
	}

	/**
	 * �趨�豸�������
	 * @param r2k ����Ƶ����
	 * @param para ����
	 * @return ָ����Ƿ�ɹ���ǣ��ɹ� true,ʧ�� false
	 */
	public boolean setDeviceConfig(UHFOperate r2k, byte[] para) {
		return r2k.setDeviceConfig(r2k, para);
	}
	
	/**
	*��ȡRSSI
	*
	*
	*/
	public boolean getRssi(UHFOperate r2k, ByteBuffer buffer) {
		return r2k.getRssi(r2k, buffer);
	}
}

/**
 * ������
 * 
 * @author zhuQixiang createDate 2017-10-25
 * 
 */
class ReaderCard implements Runnable {
	UHFOperate r2k = null;

	public ReaderCard() {
	}

	public ReaderCard(UHFOperate r2k) {
		this.r2k = r2k;
	}

	public void run() {
		r2k.threadFunc(r2k);
	}
}

