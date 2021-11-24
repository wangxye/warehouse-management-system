package com.jlrfid.r2ks;

import gnu.io.SerialPort;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.jlrfid.r2ks.serialportexception.NoSuchSerialPort;
import com.jlrfid.r2ks.serialportexception.NotASerialPort;
import com.jlrfid.r2ks.serialportexception.ReadDataFromSerialPortFailure;
import com.jlrfid.r2ks.serialportexception.SerialPortInUse;
import com.jlrfid.r2ks.serialportexception.SerialPortInputStreamCloseFailure;
import com.jlrfid.r2ks.serialportexception.SerialPortParameterFailure;
import com.jlrfid.r2ks.serialportexception.SerialPortTool;
import com.jlrfid.r2ks.util.Transform;

public class UHFOperate extends PACKAGE {

	public final static boolean R_FAIL = false;
	public final static boolean R_OK = true;
	private GetReadData callBack = null;

	/**
	 * 回调函数
	 * 
	 * @param r2k
	 *            超高频对象
	 * @param callBack
	 */
	public void setCallBack(UHFOperate r2k, GetReadData callBack) {
		this.callBack = callBack;
		doSome(r2k);
	}

	/**
	 * 循环读卡丢出来的数据
	 * 
	 * @param r2k
	 *            超高频对象
	 */
	public void doSome(final UHFOperate r2k) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < r2k.data.length; i++) {
			sb.append(Transform.bytesToHexString(r2k.data[i]));//可能是这边传入数据库
		}
		int antenna = 0;
		if (sb.length() > 24 && sb.length() < 33) {
			String ant = sb.substring(sb.length() - 2, sb.length());//@XCY 可能修改处
			try {
				antenna = Integer.parseInt(ant);
			} catch (NumberFormatException e) {
				// e.printStackTrace();
				return;
			}
		}
		callBack.getReadData(r2k, sb.toString(), antenna + 1);
	}

	public void setHost(UHFOperate r2k, String host) {
		String comm = host.substring(0, 1);
		r2k.bIsComPort = (comm.equals("C") || comm.equals("c"));
		r2k.host = host;
		if (r2k.bIsComPort) {
			r2k.port = 115200;// 卓岚设置为230400
		} else {
			r2k.port = 20058;
		}
	}

	public boolean connect(UHFOperate r2k) {
		boolean flag = R_FAIL;
		if (r2k.bIsComPort) {
			try {
				r2k.serialPort = SerialPortTool.openPort(r2k.host, r2k.port);
				if (r2k.serialPort != null) {
					flag = R_OK;
				} else {
					flag = R_FAIL;
				}
			} catch (SerialPortParameterFailure e) {
				e.printStackTrace();
			} catch (NotASerialPort e) {
				e.printStackTrace();
			} catch (NoSuchSerialPort e) {
				e.printStackTrace();
			} catch (SerialPortInUse e) {
				e.printStackTrace();
			}
		} else {
			r2k.socket = NetworkTool.openPort(r2k.host, r2k.port);
			if (r2k.socket != null) {
				flag = R_OK;
			} else {
				flag = R_FAIL;
			}
		}
		r2k.head_count = 0;
		r2k.data_count = 0;
		return flag;
	}

	public boolean invOnce(UHFOperate r2k) {
		boolean ret = R_FAIL;
		if (!r2k.deviceConnected) {
			return ret;
		}
		// R2K reader = r2k;
		// 一次寻卡时，读取天线工作状态, 用于控制线程何时结束
		AntStruct ant = new AntStruct();
		ByteBuffer buffer = ByteBuffer.allocate(100);
		boolean getAnt = r2k.getAnt(r2k, buffer);
		if (!getAnt) {
			return ret;
		}
		for (int i = 0; i < 4; ++i) { // 不工作的天线，状态直接设为1，表示已完成寻卡
			r2k.antIsEnd[i] = ant.antEnable[i] > 0 ? 1 : ant.antEnable[i];
		}
		// reader.clearBuffer();
		// 工作天线状态设为0,需等待结束包代表完成寻卡
		r2k.threadStart = true;
		r2k.head_count = 0;
		r2k.data_count = 0;
		if (r2k.sendData(r2k, CMD.UHF_INV_ONCE, null, 0)) {
			ReaderCard readerCard = new ReaderCard(r2k);
			Thread thread = new Thread(readerCard);
			thread.start();
			ret = R_OK;
		}
		return ret;
	}

	public boolean beginInv(UHFOperate r2k) {
		boolean ret = R_FAIL;
		if (!r2k.deviceConnected) {
			return R_FAIL;
		}
		r2k.threadStart = true;
		r2k.head_count = 0;
		r2k.data_count = 0;
		r2k.clearBuffer(r2k);
		if (r2k.sendData(r2k, CMD.UHF_INV_MULTIPLY_BEGIN, null, 0)) {
			ReaderCard readThread = new ReaderCard(r2k);
			Thread loopThread = new Thread(readThread);
			loopThread.start();
			ret = R_OK;
		} else {
			ret = R_FAIL;
		}
		return ret;
	}

	public void disconnectDev(UHFOperate r2k) {
		if (r2k.deviceConnected) {
			if (r2k.bIsComPort) {
				SerialPortTool.comClose(r2k.serialPort);
				r2k.serialPort = null;
			} else {
				NetworkTool.socketClose(r2k.socket);
				r2k.socket = null;
			}
			r2k.deviceConnected = false;
		}
	}

	public void clearBuffer(UHFOperate r2k) {
		if (!r2k.bIsComPort) {
			// sock.Recv((char*)&recv_buf[0], 1024, 1);
		}
	}

	public static byte getCheckSum(byte[] startcode, int length) {
		byte sum = 0;
		for (int i = 0; i < length; ++i) {
			sum ^= startcode[i];
		}
		return sum;
	}

	public boolean sendData(UHFOperate r2k, byte cmd, byte[] sendBuf,
			int bufsize) {
		if (r2k.bIsComPort) { // COM
			r2k.startcode[0] = CMD.COM_START_CODE0;
			r2k.startcode[1] = cmd;
			r2k.cmd = 0;
			r2k.seq = 0;
			r2k.len[0] = (byte) bufsize;
			r2k.len[1] = 0;
			r2k.bcc = 0;
			if (bufsize > 0) {
				r2k.data = Arrays.copyOf(sendBuf, bufsize + 1);
				r2k.bcc = getCheckSum(sendBuf, bufsize);
			} else {
				r2k.data = Arrays.copyOf(r2k.data, 1);
			}
		} else { // socket
			r2k.startcode[0] = CMD.NET_START_CODE1;
			r2k.startcode[1] = CMD.NET_START_CODE2;
			r2k.cmd = cmd;
			r2k.seq = 0;
			r2k.len[0] = (byte) bufsize; // 取低8位
			r2k.len[1] = (byte) (bufsize / 256);// 长度高8位
			r2k.bcc = 0;
			if (bufsize > 0) {
				r2k.data = Arrays.copyOf(sendBuf, bufsize + 1);
				r2k.bcc = getCheckSum(sendBuf, bufsize);
			} else {
				r2k.data = Arrays.copyOf(r2k.data, 1);
			}
		}
		
		//显示出发送的命令
		byte[] receiveData = r2k.getSendCMD(r2k, bufsize);
		System.out.println("\n");
		System.out.print("发送的命令: ");
		for (int i = 0; i < receiveData.length; i++) {
		System.out.print(Transform.bytesToHexString(receiveData[i]) + " ");
		}
		System.out.println();
		//到这边
		
		r2k.data[bufsize] = r2k.bcc;
		boolean size = false;
		if (r2k.bIsComPort) {
			size = comSendData(r2k.serialPort, r2k.getSendCMD(r2k, bufsize));
		} else {
			size = socketSend(r2k.socket, r2k.getSendCMD(r2k, bufsize));
		}
		return size;
	}

	public boolean socketSend(Socket socket, byte[] sendData) {
		return NetworkTool.sendToPort(socket, sendData);
	}

	private boolean comSendData(SerialPort serialPort, byte[] sendData) {
		return SerialPortTool.sendToPort(serialPort, sendData);
	}

	public boolean stopInv(UHFOperate r2k) {
		if (r2k == null) {
			return R_FAIL;
		}
		r2k.threadStart = false; // 设置线程结束标志
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_INV_MULTIPLY_END, null, 0)) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (r2k.readData(r2k, CMD.UHF_INV_MULTIPLY_END, buffer, 1)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public byte[] getAnt(AntStruct antStruct) {
		byte buf[] = new byte[36];
		System.arraycopy(antStruct.antEnable, 0, buf, 0, 4);
		int time = 0;
		int power = 0;
		StringBuffer sb = null;
		for (int i = 4; i < buf.length; i++) {
			if (time < 4) {
				sb = new StringBuffer();
				String dwellTime = Integer
						.toHexString(antStruct.dwellTime[time]);
				if (dwellTime.length() > 2) {
					int length = 8 - dwellTime.length();
					for (int j = 0; j < length; j++) {
						sb.append("0");
					}
					sb.append(dwellTime);
					int count = 0;
					for (int j = sb.length(); j > 0; j -= 2) {
						buf[i] = (byte) Integer.parseInt(
								sb.substring(j - 2, j), 16);
						if (count == 3) {
							break;
						}
						count++;
						i++;
					}
				} else {
					buf[i] = (byte) Integer.parseInt(dwellTime, 16);
					i += 3;
				}
				time++;
			} else if (power < 4) {
				sb = new StringBuffer();
				String powers = Integer.toHexString(antStruct.power[power]);
				if (powers.length() > 2) {
					int length = 8 - powers.length();
					for (int j = 0; j < length; j++) {
						sb.append("0");
					}
					sb.append(powers);
					int count = 0;
					for (int j = sb.length(); j > 0; j -= 2) {
						buf[i] = (byte) Integer.parseInt(
								sb.substring(j - 2, j), 16);
						if (count == 3) {
							break;
						}
						count++;
						i++;
					}
				} else {
					buf[i] = (byte) Integer.parseInt(powers, 16);
					i += 3;
				}
				power++;
			}
		}
		return buf;
	}

	public boolean getAnt(UHFOperate r2k, ByteBuffer buffer) {
		if (null == r2k) {
			return R_FAIL;
		}
		if (r2k.sendData(r2k, CMD.UHF_GET_ANT_CONFIG, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_GET_ANT_CONFIG, buffer,CMD.ANT_CFG_LENGTH)) {
				if (compareStartCode(r2k) && r2k.data[0] != ERROR.HOST_ERROR) {
					return true;
				}
			}
		}
		return R_FAIL;
	}

	public boolean setAnt(UHFOperate r2k, AntStruct antStruct) {
		boolean flag = R_FAIL;
		if (null == r2k) {
			return flag;
		}
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_SET_ANT_CONFIG, getAnt(antStruct),
				CMD.ANT_CFG_LENGTH)) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (r2k.readData(r2k, CMD.UHF_SET_ANT_CONFIG, buffer, 1)) {
				if (compareStartCode(r2k) && r2k.data[0] == 0) {
					return true;
				}
			}
		}
		return R_FAIL;
	}

	public boolean compareStartCode(UHFOperate r2k) {
		if (r2k.bIsComPort) {
			if (r2k.startcode[0] == CMD.COM_START_CODE0) {
				return R_OK;
			}
		} else {
			if (r2k.startcode[0] == CMD.NET_START_CODE1
					&& r2k.startcode[1] == CMD.NET_START_CODE2) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public int comReceiveData(UHFOperate r2k) {//xcy：串口用的
		int dwNumRead = 0;
		try {
			byte[] result = SerialPortTool.readFromPort(r2k.serialPort);
			if (result == null) {
				return 0;
			} else {
				dwNumRead = result.length;
				r2k.recv_buf = Arrays.copyOf(result, result.length);
			}
			//显示接收原始数据
//			System.out.print("接收的原始数据: ");
//			for (int i = 0; i < result.length; i++) {
//				System.out.print(Transform.bytesToHexString(result[i]) + " ");
//			}
//			System.out.println();
			//*******到这里结束*******
		} catch (ReadDataFromSerialPortFailure e) {
			e.printStackTrace();
		} catch (SerialPortInputStreamCloseFailure e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (dwNumRead > 0) {
			return dwNumRead;
		} else {
			return 0;
		}
	}

	public boolean readData(UHFOperate r2k, byte cmd, ByteBuffer buffer,int length) {
		boolean flag = false;
		long begin = System.currentTimeMillis();
		long timeout = 1000;
		boolean once = false;
		while (r2k.deviceConnected) {
			long end = System.currentTimeMillis();
			if (end - begin > timeout) {
				// return flag;
			}
			if (once) {
				return flag;
			}
			once = true;
			int retVal = 0;
			if (r2k.bIsComPort) {
				retVal = comReceiveData(r2k);
			} else {
				retVal = r2k.socketRecv(r2k);
			}
			for (int i = 0; i < retVal && retVal > 0; ++i) {
				if (trandPackage(r2k, r2k.recv_buf[i])) {
					if (r2k.cmd == cmd) {
						if (null != buffer && buffer.limit() > 0) {
							buffer.put(r2k.data);// 去掉附加的数据长度
						}
						flag = true;
					}
				}
			}
			if (cmd == 0x2a || cmd == 0x25) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(80);// 稍候再读
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public int socketRecv(UHFOperate r2k) {
		int dwNumRead = 0;
		byte[] result = NetworkTool.readFromPort(r2k.socket);
		if (result == null) {
			return 0;
		} else {
			dwNumRead = result.length;
			r2k.recv_buf = result;
		}
		//显示接收的原始数据
		System.out.print("接收的原始数据: ");
		for (int i = 0; i < result.length; i++) {
			System.out.print(Transform.bytesToHexString(result[i]) + " ");
		}
		System.out.println();
		//*******到这里结束*******
		if (dwNumRead > 0) {
			return dwNumRead;
		} else {
			return 0;
		}
	}

	public boolean trandPackage(UHFOperate r2k, byte data) {
		if (r2k.bIsComPort) {// 串口
			if (r2k.head_count < CMD.HEAD_LENGTH) {
				switch (r2k.head_count) {
				case 0: // 串口起始码
					if (data == CMD.COM_START_CODE0) {
						r2k.head_count++;
					}
					break;
				case 1: // cmd
					r2k.cmd = data;
					r2k.head_count++;
					break;
				case 2: // 顺序号
					r2k.seq = data;
					r2k.head_count++;
					break;
				case 3:
					r2k.head_count++;
					break;
				case 4:
					r2k.pkg_len = data;
					r2k.len[0] = data;
					r2k.data = Arrays.copyOf(r2k.data, r2k.pkg_len);
					r2k.head_count++;
					break;
				case 5:
					r2k.len[1] = data;
					r2k.head_count++;
					break;
				}
			} else if (r2k.data_count < r2k.pkg_len) {
				r2k.data[r2k.data_count++] = data;
			} else {
				r2k.bcc = getCheckSum(r2k.data, r2k.pkg_len);
				if (r2k.bcc == data) {
					r2k.head_count = 0;
					r2k.data_count = 0;
					return true;
				} else {
					r2k.head_count = 0;
					r2k.data_count = 0;
					return false;
				}
			}
		} else { // socket
			if (r2k.head_count < CMD.HEAD_LENGTH) {
				switch (r2k.head_count) {
				case 0: // 网口起始码
					if (data == CMD.NET_START_CODE1) {
						r2k.head_count++;
					}
					break;
				case 1: // 网口起始码
					if (data == CMD.NET_START_CODE2) {
						r2k.head_count++;
					}
					break;
				case 2: // cmd
					r2k.cmd = data;
					r2k.head_count++;
					break;
				case 3: // 顺序号
					r2k.seq = data;
					r2k.head_count++;
					break;
				case 4:
					r2k.pkg_len = data;
					r2k.len[0] = data;
					r2k.data = Arrays.copyOf(r2k.data, r2k.pkg_len);
					r2k.head_count++;
					break;
				case 5:
					r2k.len[1] = data;
					r2k.head_count++;
					break;
				}
			} else if (r2k.data_count < r2k.pkg_len) {
				r2k.data[r2k.data_count++] = data;
			} else {
				r2k.bcc = getCheckSum(r2k.data, r2k.pkg_len);
				if (r2k.bcc == data) {
					r2k.head_count = 0;
					r2k.data_count = 0;
					return true;
				} else {
					r2k.head_count = 0;
					r2k.data_count = 0;
					return false;
				}
			}
		}
		return false;
	}

	public void threadFunc(UHFOperate r2k) {
		int size = 0;
		do {
			size = 0;
			size = r2k.deviceReadBuffer(r2k);
			if (size > 0) {
				r2k.deviceTransBuffer(r2k, size);
			}
		} while (r2k.threadStart);
		// 清理线程资源
		r2k.pkg_len = 0;
		r2k.head_count = 0;
		r2k.data_count = 0;
	}

	public int deviceReadBuffer(UHFOperate r2k) {
		int size = 0;
		if (null != r2k) {
			if (r2k.bIsComPort) {
				size = comReceiveData(r2k);
			} else {
				size = r2k.socketRecv(r2k);// 设置1秒超时
			}
		}
		return size;
	}

	public void deviceTransBuffer(UHFOperate r2k, int size) {
		UHFOperate operate = null;
		for (int i = 0; i < size; i++) {
			if (trandPackage(r2k, r2k.recv_buf[i])) {
				switch (r2k.cmd) {
				case 0x25:// 寻卡一次
					operate = new UHFOperate();
					
					//data here
					operate.setCallBack(r2k, new GetReadData() {
						@Override
						public void getReadData(UHFOperate r2k, String data,
								int antNo) {
						}
					});
					
					
					r2k.isData = true;
					String data = Transform.bytesToHexString(r2k.data[0]);
					// 检查是不是结束包
					if (1 == r2k.pkg_len && data.equals("F0")) {// 某天线寻卡结束数据包
						// Base.antIsEnd[PACKAGE.data[0] - 0xF0] = 1;
						// 所有天线都工作结束
						if (r2k.antIsEnd[0] > 0 && r2k.antIsEnd[1] > 0
								&& r2k.antIsEnd[2] > 0 && r2k.antIsEnd[3] > 0) {

						}
						r2k.threadStart = false; // 设置线程结束标志
					}
					break;
				case 0x2A:// 连续寻卡模式，返回数据
					operate = new UHFOperate();
					operate.setCallBack(r2k, new GetReadData() {
						@Override
						public void getReadData(UHFOperate r2k, String data,int antNo) {
						}
					});
					r2k.isData = true;
					break;
				default:
					break;
				}
			}
		}
	}

	public boolean readTagData(UHFOperate r2k, byte bank, byte begin,
			byte size, ByteBuffer getBuffer, byte[] password) {
		if (!r2k.deviceConnected) {
			return R_FAIL;
		}
		if (getBuffer.limit() < 1) {
			return R_FAIL;
		}
		if (bank == 0) {// 保留区
			if (begin + size > 4) {
				return R_FAIL;
			}
		} else if (bank == 1) { // EPC区
			if (begin + size > 8) {
				return R_FAIL;
			}
		} else if (bank == 2) { // TID区
			if (begin + size > 6) {
				return R_FAIL;
			}
		} else if (bank == 3) { // 用户区
			if (begin + size > 32) {
				return R_FAIL;
			}
		} else { // 无效的bank值
			return R_FAIL;
		}
		byte sendBuf[] = new byte[256];
		int bufsize = 7;
		sendBuf[0] = (byte) bank;
		sendBuf[1] = (byte) begin;
		sendBuf[2] = (byte) size;
		System.arraycopy(password, 0, sendBuf, 3, 4);
		// 用来接收数据存放的buffer
		ByteBuffer buffer = ByteBuffer.allocate(20);
		if (r2k.sendData(r2k, CMD.UHF_READ_TAG_DATA, sendBuf, bufsize)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (r2k.readData(r2k, CMD.UHF_READ_TAG_DATA, buffer, size * 2)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					byte[] data = r2k.data;
					for (int i = 0; i < data.length; i++) {
						getBuffer.put(data[i]);
					}
					return R_OK;
				}
				return R_FAIL;
			}
		}
		return R_FAIL;
	}

	public boolean writeTagData(UHFOperate r2k, int bank, int begin,
			int length, String data, byte[] password) {
		if (null == r2k) {
			return R_FAIL;
		}
		byte buf[] = new byte[256];
		int bufsize = 3 + length * 2 + 4;// length是字
		buf[0] = (byte) bank;
		buf[1] = (byte) begin;
		buf[2] = (byte) length;
		System.arraycopy(password, 0, buf, 3, 4);
		byte[] inData = new byte[data.length() / 2];
		int count = 0;
		for (int i = 0; i < inData.length; i++) {
			int result = Integer.parseInt(data.substring(count, count + 2), 16);
			count += 2;
			inData[i] = (byte) result;
		}
		System.arraycopy(inData, 0, buf, 3 + 4, length * 2);// 要写入的数据
		ByteBuffer buffer = ByteBuffer.allocate(20);
		if (r2k.sendData(r2k, CMD.UHF_WRITE_TAG_DATA, buf, bufsize)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (r2k.readData(r2k, CMD.UHF_WRITE_TAG_DATA, buffer, 1)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean lockTag(UHFOperate r2k, byte locktType, byte lockBank,
			byte[] password) {
		if (!r2k.deviceConnected) {
			return R_FAIL;
		}
		if (password.length < 1) {
			return R_FAIL;
		}
		if (locktType < 0 || locktType > 3) {
			return R_FAIL;
		}
		if (lockBank < 0 || lockBank > 4) {
			return R_FAIL;
		}
		byte buf[] = new byte[12];
		buf[0] = locktType;
		buf[1] = lockBank;
		System.arraycopy(password, 0, buf, 2, password.length);
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_LOCK_TAG, buf, 6)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (r2k.readData(r2k, CMD.UHF_LOCK_TAG, buffer, 1)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean getBuzzer(UHFOperate r2k, ByteBuffer buffer) {
		if (null == r2k) {
			return R_FAIL;
		}
		if (r2k.sendData(r2k, CMD.UHF_GET_BUZZ, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_GET_BUZZ, buffer, 0)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean setBuzzer(UHFOperate r2k, byte buzz) {
		if (null == r2k) {
			return R_FAIL;
		}
		byte[] buf = new byte[2];
		buf[0] = buzz;
		ByteBuffer buffer = ByteBuffer.allocate(10);
		if (r2k.sendData(r2k, CMD.UHF_SET_BUZZ, buf, 1)) {
			if (r2k.readData(r2k, CMD.UHF_SET_BUZZ, buffer, 0)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}
/**********************************************************************/	
	
	public boolean getRssi(UHFOperate r2k, ByteBuffer buffer) {
		if(null == r2k) {
			return R_FAIL;
		}
		if(r2k.sendData(r2k, CMD.UHF_GET_RSSI, null, 0)) {
			if(r2k.readData(r2k, CMD.UHF_GET_RSSI, buffer, 0)) {
				if(r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}
	
/**********************************************************************/
	/**
	 * 获取Digital input状态
	 */
	public boolean getDI(UHFOperate r2k, ByteBuffer buffer) {
		if (null == r2k) {
			return R_FAIL;
		}
		if (r2k.sendData(r2k, CMD.UHF_GET_DI_STATE, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_GET_DI_STATE, buffer, 2)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	/**
	 * 设置Digital Output状态
	 */
	public boolean setDO(UHFOperate r2k, int port, int state) {
		if (null == r2k) {
			return R_FAIL;
		}
		byte buf[] = new byte[32];
		if (port > 2 || port == 0) {
			return R_FAIL;
		}
		buf[0] = (byte) port;
		buf[1] = (byte) state;
		ByteBuffer buffer = ByteBuffer.allocate(10);
		if (r2k.sendData(r2k, CMD.UHF_SET_DO_STATE, buf, 2)) {
			if (r2k.readData(r2k, CMD.UHF_SET_DO_STATE, buffer, 1)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean setWorkMode(UHFOperate r2k, int mode) {
		if (null == r2k) {
			return R_FAIL;
		}
		byte buf[] = new byte[8];
		int bufsize = 1;
		buf[0] = (byte) mode;// / 256;
		// buf[1] = deviceNo % 256;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_SET_MODE, buf, bufsize)) {
			if (r2k.readData(r2k, CMD.UHF_SET_MODE, buffer, 1)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean getWorkMode(UHFOperate r2k, ByteBuffer workMode) {
		if (null == r2k) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(1);
		if (r2k.sendData(r2k, CMD.UHF_GET_MODE, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_GET_MODE, buffer, 1)) {
				workMode.put(buffer.array()[0]);
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean setTrigModeDelayTime(UHFOperate r2k, byte trigTime) {
		if (null == r2k) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		buf[0] = trigTime;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_SET_TRIGGER_TIME, buf, 1)) {
			if (r2k.readData(r2k, CMD.UHF_SET_TRIGGER_TIME, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean getTrigModeDelayTime(UHFOperate r2k, ByteBuffer trigTime) {
		if (null == r2k) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_GET_TRIGGER_TIME, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_GET_TRIGGER_TIME, buffer, 1)) {
				trigTime.put(buffer.array()[0]);
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean getNeighJudge(UHFOperate r2k, ByteBuffer enableNJ,
			ByteBuffer neighJudgeTime) {
		if (null == r2k) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(2);
		if (r2k.sendData(r2k, CMD.UHF_SET_TAG_FILTER, null, 0)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (r2k.readData(r2k, CMD.UHF_SET_TAG_FILTER, buffer, 2)) {
				enableNJ.put(buffer.array()[0]);
				neighJudgeTime.put(buffer.array()[1]);
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean setNeighJudge(UHFOperate r2k, byte neighJudgeTime) {
		if (null == r2k) {
			return R_FAIL;
		}
		int bufsize = 2;
		byte[] buf = new byte[16];
		buf[0] = (byte) (neighJudgeTime > 0 ? 1 : 0); // time为0,
														// 取消相邻判定，非0，设置相邻判定
		buf[1] = neighJudgeTime;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_SET_NEIGH_JUDGE, buf, bufsize)) {
			if (r2k.readData(r2k, CMD.UHF_SET_NEIGH_JUDGE, buffer, 1)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean getDeviceNo(UHFOperate r2k, ByteBuffer deviceNo) {
		if (null == r2k) {
			return R_FAIL;
		}
		if (r2k.sendData(r2k, CMD.UHF_GET_DEVICE_NO, null, 0)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (r2k.readData(r2k, CMD.UHF_GET_DEVICE_NO, deviceNo, 1)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean setDeviceNo(UHFOperate r2k, byte deviceNo) {
		if (null == r2k) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		int bufsize = 1;// llp 2;
		buf[0] = deviceNo;// / 256;
		// buf[1] = deviceNo % 256;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_SET_DEVICE_NO, buf, bufsize)) {
			if (r2k.readData(r2k, CMD.UHF_SET_DEVICE_NO, buffer, 1)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean getClock(UHFOperate r2k, ByteBuffer clock) {
		if (null == r2k) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(10);
		if (r2k.sendData(r2k, CMD.UHF_GET_CLOCK, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_GET_CLOCK, buffer, 6)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					clock.put(Arrays.copyOf(buffer.array(), 6));
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean setClock(UHFOperate r2k, byte[] clock) {
		if (null == r2k) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		System.arraycopy(clock, 0, buf, 0, 6);
		ByteBuffer buffer = ByteBuffer.allocate(10);
		if (r2k.sendData(r2k, CMD.UHF_SET_CLOCK, buf, 6)) {
			if (r2k.readData(r2k, CMD.UHF_SET_CLOCK, buffer, 1)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean getReadZone(UHFOperate r2k, ByteBuffer zone) {
		if (null == r2k) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (r2k.sendData(r2k, CMD.UHF_GET_READ_ZONE, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_GET_READ_ZONE, buffer, 1)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					zone.put(buffer.array()[0]);
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean getReadZonePara(UHFOperate r2k, ByteBuffer bank,
			ByteBuffer begin, ByteBuffer length) {
		if (null == r2k) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (r2k.sendData(r2k, CMD.UHF_GET_READZONE_PARA, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_GET_READZONE_PARA, buffer, 3)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					bank.put(buffer.array()[0]);
					begin.put(buffer.array()[1]);
					length.put(buffer.array()[2]);
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean setReadZone(UHFOperate r2k, byte state) {
		if (null == r2k) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (0 == state) {
			buf[0] = 0;
		} else {
			buf[0] = 1;
		}
		if (r2k.sendData(r2k, CMD.UHF_SET_READ_ZONE, buf, 1)) {
			if (r2k.readData(r2k, CMD.UHF_SET_READ_ZONE, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean setReadZonePara(UHFOperate r2k, byte bank, byte begin,
			byte length) {
		if (null == r2k) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		buf[0] = bank;
		buf[1] = begin;
		buf[2] = length;
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (r2k.sendData(r2k, CMD.UHF_SET_READZONE_PARA, buf, 3)) {
			if (r2k.readData(r2k, CMD.UHF_SET_READZONE_PARA, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean getOutputMode(UHFOperate r2k, ByteBuffer outputMode) {
		if (null == r2k) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (r2k.sendData(r2k, CMD.UHF_GET_OUTPUT, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_GET_OUTPUT, buffer, 1)) {
				outputMode.put(buffer.array()[0]);
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean setOutputMode(UHFOperate r2k, byte outputMode) {
		if (null == r2k) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		buf[0] = outputMode;
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (r2k.sendData(r2k, CMD.UHF_SET_OUTPUT, buf, 1)) {
			if (r2k.readData(r2k, CMD.UHF_SET_OUTPUT, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean readTagBuffer(UHFOperate r2k, GetReadData getReadData,
			int readTime) {
		if (null == r2k) {
			return R_FAIL;
		}
		if (r2k.sendData(r2k, CMD.UHF_GET_TAG_BUFFER, null, 0)) {
			// 暂未做
			return R_OK;
		}
		return R_FAIL;
	}

	public boolean resetTagBuffer(UHFOperate r2k) {
		if (null == r2k) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(16);
		if (r2k.sendData(r2k, CMD.UHF_RESET_TAG_BUFFER, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_RESET_TAG_BUFFER, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean killTag(UHFOperate r2k, byte[] accessPwd, byte[] killPwd) {
		if (null == r2k) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		System.arraycopy(killPwd, 0, buf, 0, 4);
		System.arraycopy(accessPwd, 0, buf, 4, 4);
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_KILL_TAG, buf, 8)) {
			if (r2k.readData(r2k, CMD.UHF_KILL_TAG, buffer, 1)) {
				if (r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	/**
	 * 获取设备版本号
	 * 
	 * @return 版本号字符串
	 */
	public boolean getDevVersion(UHFOperate r2k, ByteBuffer buffer) {
		if (r2k == null) {
			return R_FAIL;
		}
		if (buffer.limit() < 0) {
			return R_FAIL;
		}
		if (r2k.sendData(r2k, CMD.UHF_GET_VERSION, null, 0)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (r2k.readData(r2k, CMD.UHF_GET_VERSION, buffer,
					CMD.VERSION_LENGTH)) {
				if (compareStartCode(r2k) && r2k.data[0] != ERROR.HOST_ERROR) {
					return R_OK;
				}
			}
		}
		return R_FAIL;
	}

	public boolean setAlive(UHFOperate r2k, byte interval) {
		if (r2k == null) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		buf[0] = interval;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_ALIVE, buf, 1)) {
			if (r2k.readData(r2k, CMD.UHF_ALIVE, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean getRelayAutoState(UHFOperate r2k, ByteBuffer state) {
		if (r2k == null) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_GET_TRIGGER_TIME, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_GET_TRIGGER_TIME, buffer, 1)) {
				state.put(buffer.array()[0]);
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean setRelayAutoState(UHFOperate r2k, byte time) {
		if (r2k == null) {
			return R_FAIL;
		}
		byte buf[] = new byte[16];
		buf[0] = time;
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_SET_TRIGGER_TIME, buf, 1)) {
			if (r2k.readData(r2k, CMD.UHF_SET_TRIGGER_TIME, buffer, 1)) {
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean getDeviceConfig(UHFOperate r2k, ByteBuffer para) {
		if (r2k == null) {
			return R_FAIL;
		}
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_GET_TRIGGER_TIME, null, 0)) {
			if (r2k.readData(r2k, CMD.UHF_GET_TRIGGER_TIME, buffer, 20)) {
				// memcpy(pPara, &buf[0], 20);
				return R_OK;
			}
		}
		return R_FAIL;
	}

	public boolean setDeviceConfig(UHFOperate r2k, byte[] para) {
		if (r2k == null) {
			return R_FAIL;
		}
		byte buf[] = new byte[128];
		byte bufSize = 20;
		// memcpy(&buf[0], pPara, bufSize);
		System.arraycopy(para, 0, buf, 0, bufSize);
		ByteBuffer buffer = ByteBuffer.allocate(100);
		if (r2k.sendData(r2k, CMD.UHF_SET_CONFIGURE, buf, bufSize)) {
			if (r2k.readData(r2k, CMD.UHF_SET_CONFIGURE, buffer, 1)) {
				// memcpy(pPara, &buf[0], 20);
				return R_OK;
			}
		}
		return R_FAIL;
	}
}
