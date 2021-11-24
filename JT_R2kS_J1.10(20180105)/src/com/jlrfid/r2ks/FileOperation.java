package com.jlrfid.r2ks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * �ı��ļ�����
 * 
 * @author zhuQixiang
 */
public class FileOperation {

	/**
	 * ���������¼
	 * 
	 * @param content
	 *            ����
	 * @throws Exception
	 */
	public static void writeFile(String content) throws Exception {
		// �ڵ�ǰĿ¼�б����ļ���
		File fileName = new File("tagRecord.txt");
		if (FileOperation.createFile(fileName)) {
			writeTxtFile(content + "\t\t" + getDate(), fileName);
		} else {
			contentToTxt(fileName.getPath(), content);
		}
	}

	/**
	 * �����ı��ļ�
	 * 
	 * @param fileName
	 *            �ļ�����
	 * @return true or false
	 */
	public static boolean createFile(File fileName) throws Exception {
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * д���ı��ļ�
	 * 
	 * @param content
	 *            ����
	 * @param fileName
	 *            �ļ�����
	 * @return true or false
	 * @throws Exception
	 */
	public static boolean writeTxtFile(String content, File fileName)
			throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("UTF-8"));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}

	/**
	 * ����ı��ļ����ڣ������ļ����ۼ�����
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @param content
	 *            ����
	 */
	public static void contentToTxt(String filePath, String content) {
		// ԭ����.txt����
		String str = new String();
		// ���º��.txt����
		String s1 = new String();

		try {
			File f = new File(filePath);
			if (!f.exists()) {
				// ����������ʱ����
				f.createNewFile();
			}
			BufferedReader input = new BufferedReader(new FileReader(f));
			while ((str = input.readLine()) != null) {
				s1 += str + "\r\n";
			}
			// System.out.println(s1);
			input.close();
			s1 += content + "\t\t" + getDate();
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String date = sdf.format(new Date());
		return date;
	}
}
