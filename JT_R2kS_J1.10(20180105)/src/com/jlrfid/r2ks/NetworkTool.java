package com.jlrfid.r2ks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.jlrfid.r2ks.util.Regex;

/**
 * ���ڷ����࣬�ṩ�򿪡��ر����ڣ���ȡ�������������ݵȷ��񣨲��õ������ģʽ��
 * 
 * @author zhuQixiang createDate 2017-10-25
 * 
 */
public class NetworkTool {
	private static NetworkTool networkImpl = null;
	static {
		// �ڸ��౻ClassLoader����ʱ�ͳ�ʼ��һ��NetworkTool����
		if (networkImpl == null) {
			networkImpl = new NetworkTool();
		}
	}

	// ˽�л�NetworkTool��Ĺ��췽��������������������NetworkTool����
	public NetworkTool() {
	}

	/**
	 * ��ȡ�ṩ�����NetworkTool����
	 * 
	 * @return serialTool
	 */
	public static NetworkTool getNetworkImpl() {
		if (networkImpl == null) {
			networkImpl = new NetworkTool();
		}
		return networkImpl;
	}

	/**
	 * ������
	 * 
	 * @param IPAddress
	 *            IP��ַ
	 * @param port
	 *            �˿�
	 * @return
	 * @throws UnknownHostException 
	 */
	public final static Socket openPort(String IPAddress, int port){
		// ͨ���˿���ʶ��˿�
		Socket socket = new Socket();  
		try {
			InetAddress addr = InetAddress.getByName(IPAddress);
			try {
				socket.connect(new InetSocketAddress( addr, port), 300);
				//socket.setSendBufferSize(100); 
				System.out.println("Open " + IPAddress + " sucessfully !");
				return socket;
			} catch (IOException e) {
				System.out.println("����ʧ��");
				//e.printStackTrace();
			}  
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}  
		return null;
	}

	/**
	 * �ر�����
	 * 
	 * @param socket
	 *            ���رյ�socket����
	 */
	public static void socketClose(Socket socket) {
		if (socket != null) {
			try {
				socket.close();
				System.out.println("Close " + socket + " sucessfully !");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
			socket = null;
		}
	}

	/**
	 * �����ڷ�������
	 * 
	 * @param socket
	 *            ���ڶ���
	 * @param order
	 *            ����������
	 */
	public static boolean sendToPort(Socket socket, byte[] data) {
		boolean flag = false;
		OutputStream out = null;
		try {
			out = socket.getOutputStream();
			out.write(data);
			Thread.sleep(100);
			out.flush();
			flag = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return flag;
	}

	/**
	 * �����ڶ�ȡ����
	 * 
	 * @param socket
	 *            ���ڶ���
	 * @return ��ȡ��������
	 * @throws IOException
	 */
	public static byte[] readFromPort(Socket socket) {
		InputStream in = null;
		byte[] bytes = null;
		try {
			in = socket.getInputStream();
			int bufflenth = in.available(); // ��ȡbuffer������ݳ���
			while (bufflenth != 0) {
				bytes = new byte[bufflenth]; // ��ʼ��byte����Ϊbuffer�����ݵĳ���
				in.read(bytes);
				bufflenth = in.available();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return bytes;
	}

	/**
	 * ���IP��ʽ�Ƿ���
	 * 
	 * @param strIP
	 * @return
	 */
	public static boolean IsValidIP(String strIP) {
		// �ȼ�����޷������ַ�
		if (!Regex.IsMatch(strIP)) {
			return false;
		}
		// �ټ�����ݷ�Χ�Ƿ����
		String[] strNumArray = strIP.split("\\.");
		if (strNumArray.length != 4) {
			return false;
		}
		int n = 0;
		for (int i = 0; i < 4; ++i) {
			n = Integer.parseInt((strNumArray[i]));
			if (n > 255) {
				return false;
			}
		}
		return true;
	}


	/**
	 * ��ʼ������   //�Ժ�����
	 * 
	 * @return
	 */
	public static boolean socketInit() {
		boolean flag = true;
		return flag;
	}
}