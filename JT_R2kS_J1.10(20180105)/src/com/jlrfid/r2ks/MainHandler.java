package com.jlrfid.r2ks;

import java.nio.ByteBuffer;

import com.jlrfid.r2ks.util.Transform;

/**
 * 超高频调用函数类
 * 
 * @author zhuQixiang createDate 2017-10-25
 * 
 */
public class MainHandler extends PACKAGE {
	public final static boolean R_FAIL = false;
	public final static boolean R_OK = true;
	public static int count = 0;

	/**
	 * 连接设备
	 * 
	 * @param host
	 *            IP或串口
	 * @return 指令发送是否成功标记：成功 true,失败 false
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
	 * 断开连接
	 * 
	 * @param r2k
	 *            超高频对象 超高频对象
	 * @return 指令发送是否成功标记：成功 true,失败 false 指令发送是否成功标记：成功 true,失败 false
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
	 * 获取版本号
	 * 
	 * @param r2k
	 *            超高频对象 超高频对象
	 * @param deviceNo
	 *            设备号
	 * @return 指令发送是否成功标记：成功 true,失败 false
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
	 * 寻卡一次
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param getReadData
	 *            获取读取数据
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean invOnce(UHFOperate r2k) {
		return r2k.invOnce(r2k);
	}

	/**
	 * 连续循卡
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param getReadData
	 *            获取读取数据
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean beginInv(UHFOperate r2k) {
		return r2k.beginInv(r2k);
	}

	/**
	 * 获取天线
	 * 
	 * @param r2k
	 *            超高频对象
	 * @return 指令发送是否成功标记：成功 true,失败 false
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
	 * 设置天线
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param antEnable
	 *            天线 1号,2号,3号,4号
	 * @param dwellTime
	 *            工作时间
	 * @param power
	 *            功率
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 * @throws RFIDException
	 *             抛出异常
	 */
	public static boolean setAnt(UHFOperate r2k, byte[] antEnable,
			int[] dwellTime, int[] power) throws RFIDException {
		AntStruct struct = new AntStruct();
		for (int i = 0; i < 4; i++) {
			if (antEnable[i] != 0 && antEnable[i] != 1) {
				throw new RFIDException("数组antEnable的值只能为0或1");
			}
			struct.antEnable[i] = antEnable[i];
			if (dwellTime[i] < 50 && dwellTime[i] > 10000) {
				throw new RFIDException("数组dwellTime的值只能为50-10000");
			}
			struct.dwellTime[i] = dwellTime[i];
			if (power[i] < 20 && power[i] > 33) {
				throw new RFIDException("数组power的值只能为20-33");
			}
			struct.power[i] = power[i];
		}
		boolean result = r2k.setAnt(r2k, struct);
		return result;
	}

	/**
	 * 停止循卡
	 * 
	 * @param r2k
	 *            超高频对象
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean stopInv(UHFOperate r2k) {
		return r2k.stopInv(r2k);
	}

	/**
	 * 读取单张卡数据，可以限定读取区域，开始地址和读取长度
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param bank
	 *            要读的区域。各值的意义如下： 0——保留区 1——EPC区 2——TID区 3——用户区
	 * @param begin
	 *            要读区域中的地址，取值为范围0-7。
	 * @param size
	 *            要读取的长度，取值范围是1到8（1Word = 2Byte =
	 *            16位）（说明：bank=EPC区，begin+size的值不超过8bank=保留区，begin+size的值不超过4．）。
	 * @param password
	 *            读密码 可以默认为空
	 * @return 成功时：返回16进制数的字符串 失败时：返回-1
	 * @throws RFIDException
	 *             抛出异常
	 */
	public static String readTagData(UHFOperate r2k, byte bank, byte begin,
			byte size, byte[] password) throws RFIDException {
		if (bank < 0 || begin < 0 || size < 0) {
			throw new RFIDException("bank/begin/size必须是正整数！");
		}
		if (bank == 1 && (begin + size > 8)) {
			throw new RFIDException(
					"读取EPC区内容时，begin(读区域中的地址)+size(要读取的长度)的值不超过8．请检查输入参数值！");
		}
		if (bank == 0 && (begin + size > 4)) {
			throw new RFIDException(
					"读取保留区内容时，begin(读区域中的地址)+size(要读取的长度)的值不超过4．请检查输入参数值！");
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
	 * 写单张卡数据，可以限定写入区域，开始地址和读取长度
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param bank
	 *            要读的区域。各值的意义如下： 0——保留区 1——EPC区 2——TID区 3——用户区
	 * @param begin
	 *            要读区域中的地址，取值为范围0-7。
	 * @param size
	 *            要读取的长度，取值范围是1到8（1Word = 2Byte =
	 *            16位）（说明：bank=EPC区，begin+size的值不超过8bank=保留区，begin+size的值不超过4．）。
	 * @param data
	 *            写入的数据
	 * @param password
	 *            读密码 可以默认为空
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 * @throws RFIDException
	 */
	public static boolean writeTagData(UHFOperate r2k, byte bank, byte begin,byte size, String data, byte[] password) throws RFIDException {
		if (bank < 0 || begin < 0 || size < 0) {
			throw new RFIDException("bank/begin/size必须是正整数！");
		}
		if (bank > 3) {
			throw new RFIDException("bank只能是0-3");
		}
		if (bank == 1 && (begin + size > 8 || begin < 2)) {
			throw new RFIDException(
					"写EPC区内容时，begin必须从2开始，并且begin(写区域中的地址)+size(要写的长度)的值不超过8．请检查输入参数值！");
		}
		if (bank == 0 && (begin + size > 4)) {
			throw new RFIDException(
					"写保留区内容时，begin(写区域中的地址)+size(要写的长度)的值不超过4．请检查输入参数值！");
		}
		if (data.length() != 4 * size) {
			throw new RFIDException("data的长度必须是size*4个！");
		}
		if (password == null || "".equals(password)) {
			for (int i = 0; i < 4; ++i) {
				password[0] = (byte) 0;
			}
		}
		return r2k.writeTagData(r2k, bank, begin, size, data, password);
	}

	/**
	 * 锁卡
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param opcode
	 *            操作码 0~3 分别表示：解锁、永久锁定、安全锁定、永久解锁,其中 0 表示解锁
	 * @param block
	 *            操作区域 0~4 分别表示Kill、Access、EPC、TID 及User
	 * @param password
	 *            操作密码
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean lockTag(UHFOperate r2k, byte locktType,
			byte lockBank, byte[] password) {
		return r2k.lockTag(r2k, locktType, lockBank, password);
	}

	/**
	 * 获取蜂鸣器声音
	 * 
	 * @param r2k
	 *            超高频对象
	 * @return 指令发送是否成功标记：成功 true,失败 false
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
	 * 设置蜂鸣器声音
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param buzz
	 *            1是打开； 0是关闭；
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean setBuzzer(UHFOperate r2k, byte buzz) {
		return r2k.setBuzzer(r2k, buzz);
	}

	/**
	 * 获取Digital input状态
	 * 
	 * @param r2k
	 *            超高频对象
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean getDI(UHFOperate r2k, ByteBuffer state) {
		return r2k.getDI(r2k, state);
	}

	/**
	 * 设定Digital Output状态
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param port
	 * @param state
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean setDO(UHFOperate r2k, int port, int state) {
		return r2k.setDO(r2k, port, state);
	}

	/**
	 * 设置工作模式
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param mode
	 *            1是主从；2是定时；3是触发；
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean setWorkMode(UHFOperate r2k, int mode) {
		return r2k.setWorkMode(r2k, mode);
	}

	/**
	 * 获取工作模式
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param workMode
	 *            1是主从；2是定时；3是触发；
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean getWorkMode(UHFOperate r2k, ByteBuffer workMode) {
		return r2k.getWorkMode(r2k, workMode);
	}

	/**
	 * 设定触发时间
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param trigTime
	 *            0~254
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean setTrigModeDelayTime(UHFOperate r2k, byte trigTime) {
		return r2k.setTrigModeDelayTime(r2k, trigTime);
	}

	/**
	 * 获取触发时间
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param trigTime
	 *            0~254
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean getTrigModeDelayTime(UHFOperate r2k,
			ByteBuffer trigTime) {
		return r2k.getTrigModeDelayTime(r2k, trigTime);
	}

	/**
	 * 获取相邻判别
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param enableNJ
	 *            1~254
	 * @param neighJudgeTime
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean getNeighJudge(UHFOperate r2k, ByteBuffer enableNJ,
			ByteBuffer neighJudgeTime) {
		return r2k.getNeighJudge(r2k, enableNJ, neighJudgeTime);
	}

	/**
	 * 设置相邻判别
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param neighJudgeTime
	 *            1~254
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean setNeighJudge(UHFOperate r2k, byte neighJudgeTime) {
		return r2k.setNeighJudge(r2k, neighJudgeTime);
	}

	/**
	 * 设置设备号
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param deviceNo
	 *            0-254
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean setDeviceNo(UHFOperate r2k, byte deviceNo) {
		return r2k.setDeviceNo(r2k, deviceNo);
	}

	/**
	 * 获取设备号
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param deviceNo
	 *            0-254
	 * @return 指令发送是否成功标记：成功 true,失败 false
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
	 * 获取时钟
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param clock
	 *            时钟
	 * @return 指令发送是否成功标记：成功 true,失败 false
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
	 * 设置时钟
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param clock
	 *            获取当前时间(年-月-日-时-分-秒)
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean setClock(UHFOperate r2k, byte[] clock) {
		boolean result = r2k.setClock(r2k, clock);
		if (result) {
			return true;
		}
		return false;
	}

	/**
	 * 获取连续读卡区域
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param zone
	 *            01代表自定义读取信息；00代表默认EPC信息。
	 * @return 指令发送是否成功标记：成功 true,失败 false
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
	 * 获取自定义读卡参数
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param bank
	 *            要读的区域。各值的意义如下： 0——保留区 1——EPC区 2——TID区 3——用户区
	 * @param begin
	 *            要读区域中的地址，取值为范围0-7。
	 * @param length
	 *            要读取的长度，取值范围是1到8（1Word = 2Byte =
	 *            16位）（说明：bank=EPC区，begin+size的值不超过8bank=保留区，begin+size的值不超过4．）。
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean getReadZonePara(UHFOperate r2k, ByteBuffer bank,
			ByteBuffer begin, ByteBuffer length) {
		return r2k.getReadZonePara(r2k, bank, begin, length);
	}

	/**
	 * 设定连续读卡区域
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param state
	 *            状态
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean setReadZone(UHFOperate r2k, byte state) {
		return r2k.setReadZone(r2k, state);
	}

	/**
	 * 设置自定义读卡参数
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param bank
	 *            要读的区域。各值的意义如下： 0——保留区 1——EPC区 2——TID区 3——用户区
	 * @param begin
	 *            要读区域中的地址，取值为范围0-7。
	 * @param length
	 *            要读取的长度，取值范围是1到8（1Word = 2Byte =
	 *            16位）（说明：bank=EPC区，begin+size的值不超过8bank=保留区，begin+size的值不超过4．）。
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean setReadZonePara(UHFOperate r2k, byte bank,
			byte begin, byte length) {
		return r2k.setReadZonePara(r2k, bank, begin, length);
	}

	/**
	 * 获取输出方式
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param outputMode
	 *            (1 COM 2 NET 3 RS485 4 WIFI/USB->COM)
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean getOutputMode(UHFOperate r2k, ByteBuffer outputMode) {
		return r2k.getOutputMode(r2k, outputMode);
	}

	/**
	 * 设置输出方式
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param outputMode
	 *            (1 COM 2 NET 3 RS485 4 WIFI/USB->COM)
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean setOutputMode(UHFOperate r2k, byte outputMode) {
		return r2k.setOutputMode(r2k, outputMode);
	}

	/**
	 * 读取标签缓存
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param getOnceReadData
	 *            数据处理
	 * @param readTime
	 *            读取的时间
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean readTagBuffer(UHFOperate r2k,
			GetReadData getReadData, int readTime) {
		return r2k.readTagBuffer(r2k, getReadData, readTime);
	}

	/**
	 * 清空标签缓存
	 * 
	 * @param r2k
	 *            超高频对象
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean resetTagBuffer(UHFOperate r2k) {
		return r2k.resetTagBuffer(r2k);
	}

	/**
	 * 注销卡片
	 * @param r2k 超高频对象
	 * @param accessPwd 访问密码 
	 * @param killPwd  灭活密码
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean killTag(UHFOperate r2k, byte[] accessPwd,
			byte[] killPwd) {
		return r2k.killTag(r2k, accessPwd, killPwd);
	}

	/**
	 * 通讯心跳设定
	 * @param r2k 超高频对象
	 * @param interval 时间间隔
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public static boolean setAlive(UHFOperate r2k, byte interval) {
		return r2k.setAlive(r2k, interval);
	}

	/**
	 * 获取触发模式工作时间
	 * @param r2k 超高频对象
	 * @param state 状态
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public boolean getRelayAutoState(UHFOperate r2k, ByteBuffer state) {
		return r2k.getRelayAutoState(r2k, state);
	}

	/**
	 * 设置触发模式工作时间
	 * @param r2k 超高频对象
	 * @param state 状态
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public boolean setRelayAutoState(UHFOperate r2k, byte state) {
		return r2k.setRelayAutoState(r2k, state);
	}

	/**
	 * 获取设备扩充参数
	 * @param r2k 超高频对象
	 * @param para 参数
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public boolean getDeviceConfig(UHFOperate r2k, ByteBuffer para) {
		return r2k.getDeviceConfig(r2k, para);
	}

	/**
	 * 设定设备扩充参数
	 * @param r2k 超高频对象
	 * @param para 参数
	 * @return 指令发送是否成功标记：成功 true,失败 false
	 */
	public boolean setDeviceConfig(UHFOperate r2k, byte[] para) {
		return r2k.setDeviceConfig(r2k, para);
	}
	
	/**
	*获取RSSI
	*
	*
	*/
	public boolean getRssi(UHFOperate r2k, ByteBuffer buffer) {
		return r2k.getRssi(r2k, buffer);
	}
}

/**
 * 读卡类
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

