package com.jlrfid.r2ks.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * ������ʽ
 * @author zhuQixiang
 *
 */
public class Regex {
	/**
	 * ��֤�����ַ��������(�ַ�������ͬʱ����)
	 * @param str ����֤���ַ���
	 * @return ����Ƿ��ϸ�ʽ���ַ���,���� <b>true </b>,����Ϊ <b>false </b>
	 */
	public static boolean isHexCharacter(String str) {
		String regex = "^[0-9A-Fa-f]+$";
		return match(regex, str);
	}
	/**
	 * ��֤�����豸����������(ֻ�������ֳ���)
	 * @param str ����֤���ַ���
	 * @return ����Ƿ��ϸ�ʽ���ַ���,���� <b>true </b>,����Ϊ <b>false </b>
	 */
	public static boolean isDecNumber(String str) {
		String regex ="^[0-9]+$";
		return match(regex,str);
	}
	/**
	 * ��֤����IP��ַ��������(ֻ�������ֺ�.����)
	 * @param str ����֤���ַ���
	 * @return ����Ƿ��ϸ�ʽ���ַ���,���� <b>true </b>,����Ϊ <b>false </b>
	 */
	public static boolean IsMatch(String str) {
		String regex ="^[0-9.]+$";
		return match(regex,str);
	}
	/**
	 * @param regex ������ʽ�ַ���
	 * @param str Ҫƥ����ַ���
	 * @return ���str ���� regex��������ʽ��ʽ,����true, ���򷵻� false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * ��֤IP�Ƿ���ȷ
	 * @param strIP
	 * @return
	 */
	public static boolean isValidIP(String strIP) {
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
			n = Integer.parseInt(strNumArray[i]);
			if (n > 255) {
				return false;
			}
		}
		return true;
	}
}
