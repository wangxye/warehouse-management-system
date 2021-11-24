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
 * 网口服务类，提供打开、关闭网口，读取、发送网口数据等服务（采用单例设计模式）
 * 
 * @author zhuQixiang createDate 2017-10-25
 * 
 */
public class NetworkTool {
	private static NetworkTool networkImpl = null;
	static {
		// 在该类被ClassLoader加载时就初始化一个NetworkTool对象
		if (networkImpl == null) {
			networkImpl = new NetworkTool();
		}
	}

	// 私有化NetworkTool类的构造方法，不允许其他类生成NetworkTool对象
	public NetworkTool() {
	}

	/**
	 * 获取提供服务的NetworkTool对象
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
	 * 打开网口
	 * 
	 * @param IPAddress
	 *            IP地址
	 * @param port
	 *            端口
	 * @return
	 * @throws UnknownHostException 
	 */
	public final static Socket openPort(String IPAddress, int port){
		// 通过端口名识别端口
		Socket socket = new Socket();  
		try {
			InetAddress addr = InetAddress.getByName(IPAddress);
			try {
				socket.connect(new InetSocketAddress( addr, port), 300);
				//socket.setSendBufferSize(100); 
				System.out.println("Open " + IPAddress + " sucessfully !");
				return socket;
			} catch (IOException e) {
				System.out.println("连接失败");
				//e.printStackTrace();
			}  
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}  
		return null;
	}

	/**
	 * 关闭网口
	 * 
	 * @param socket
	 *            待关闭的socket对象
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
	 * 往网口发送数据
	 * 
	 * @param socket
	 *            网口对象
	 * @param order
	 *            待发送数据
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
	 * 从网口读取数据
	 * 
	 * @param socket
	 *            网口对象
	 * @return 读取到的数据
	 * @throws IOException
	 */
	public static byte[] readFromPort(Socket socket) {
		InputStream in = null;
		byte[] bytes = null;
		try {
			in = socket.getInputStream();
			int bufflenth = in.available(); // 获取buffer里的数据长度
			while (bufflenth != 0) {
				bytes = new byte[bufflenth]; // 初始化byte数组为buffer中数据的长度
				in.read(bytes);
				bufflenth = in.available();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return bytes;
	}

	/**
	 * 检查IP格式是否正
	 * 
	 * @param strIP
	 * @return
	 */
	public static boolean IsValidIP(String strIP) {
		// 先检查有无非数字字符
		if (!Regex.IsMatch(strIP)) {
			return false;
		}
		// 再检查数据范围是否合理
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
	 * 初始化网口   //以后完善
	 * 
	 * @return
	 */
	public static boolean socketInit() {
		boolean flag = true;
		return flag;
	}
}