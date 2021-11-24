package com.jlrfid.r2ks.util;

/**
 * ת��ʮ������
 * 
 * @author zhuqixiang createDate 2017-05-12
 * 
 */
public class Transform {
	/**
	 * Convert byte[] to hex
	 * string.�������ǿ��Խ�byteת����int��Ȼ������Integer.toHexString(int)��ת����16�����ַ�����
	 * @param src
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			char[] str = hv.toCharArray();
			for (int j = 0; j < str.length; j++) {
				if ((int) str[j] >= 97 && (int) str[j] <= 122) {
					str[j] -= 32;
				}
			}
			hv = new String(str);
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * ���ֽ�����ת����10�����ַ���
	 * @param bArray ��Ҫ��ת����byte����
	 * @return ת����10�����ַ���
	 */
	public static final String bytesToDecimalismString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		for (int i = 0; i < bArray.length; i++) {
			String temp = null;
			// ��Ϊunsigned byte = byte & 0xFF��0~255����ɫ��byte-128~127��ʾ
			// ����0~127��Ӧ0~127 ��-128~-1 ��Ӧ128~255��������и�ֵ��ͨ����λ�ķ�ʽ���л��㡣
			// ����127��Ϊ����,��Ҫת����
			if (bArray[i] < 0) {
				temp = String.valueOf(bArray[i] & 0xFF);
			} else {
				temp = String.valueOf(bArray[i]);
			}
			if (temp.length() < 2){
				sb.append(0);
			}
		   sb.append(temp.toUpperCase()+" ");
		}
		return sb.toString();
	}

	/**
	 * Convert byte to hex
	 * string.�������ǿ��Խ�byteת����int��Ȼ������Integer.toHexString(int)��ת����16�����ַ�����
	 * 
	 * @param src �ַ���
	 * @return hex string
	 */
	public static String bytesToHexString(byte src) {
		StringBuilder stringBuilder = new StringBuilder("");
		int v = src & 0xFF;
		String hv = Integer.toHexString(v);
		char [] str = hv.toCharArray();
		for (int i = 0; i < str.length; i++) {
			if ((int) str[i] >= 97 && (int) str[i] <= 122) {
				str[i] -= 32;
			}
		}
		hv=new String(str);
		if (hv.length() < 2) {
			stringBuilder.append(0);
		}
		stringBuilder.append(hv);
		return stringBuilder.toString();
	}
    /**
     *  ��16�����ַ���ת�����ֽ����飨�Զ����ַ���ת��Ϊ��д��
     * @param hex �ַ���
     * @return �ַ���ת�����byte����
     */
	public static byte[] hexStringToByte(String hex) {
		hex = hex.toUpperCase();
		int len = hex.length() / 2;
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = ((byte) (toByte(achar[pos]) << 4 | toByte(achar[(pos + 1)])));
		}
		return result;
	}
	
	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
	
	/**
	 * �ַ���ת��Ϊ16����
	 * 
	 * @param str
	 * @return
	 */
	public static String strHexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString();
	}

	/**
	 * 16���Ƶ��ַ�����ʾת���ֽ�����
	 *
	 * @param hexString
	 *			16���Ƹ�ʽ���ַ���
	 * @return ת������ֽ�����
	 **/
	public static byte[] hexStr2ByteArray(String hexString) {
		if (hexString.isEmpty()){
			throw new IllegalArgumentException("this hexString must not be empty");
		}
		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {
						//��Ϊ��16���ƣ����ֻ��ռ��4λ��ת�����ֽ���Ҫ����16���Ƶ��ַ�����λ����
						//��hex ת����byte   "&" ����Ϊ�˷�ֹ�������Զ���չ
						// hexת����byte ��ʵֻռ����4λ��Ȼ��Ѹ�λ����������λ
						// Ȼ��|������  ����λ ���ܵõ� ���� 16������ת����һ��byte.
						//
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}

   /**
    * 16�����ַ���ת����byte����
    * @param 16�����ַ���
    * @return ת�����byte����
    */
  	public static byte[] hex2Byte(String hex) {
		String digital = "0123456789ABCDEF";
		char[] hex2char = hex.toCharArray();
		byte[] bytes = new byte[hex.length() / 2];
		int temp;
		for (int i = 0; i < bytes.length; i++) {
		// ��ʵ������ĺ�����һ���� multiple 16 ��������4λ �����ͳ��˸�4λ��
		// Ȼ��͵���λ��ӣ� �൱�� λ����"|" 
		//��Ӻ������ ���� λ "&" ���� ��ֹ�������Զ���չ. {0xff byte����ʾ��}
			temp = digital.indexOf(hex2char[2 * i]) * 16;
			temp += digital.indexOf(hex2char[2 * i + 1]);
			bytes[i] = (byte) (temp & 0xff);
		}
		return bytes;
	}

  	/**
	 * �ַ���תAscll
	 * @param src �ַ���
	 * @return ascll��
	 */
	public static byte [] stringToByte(String src){
		byte [] result = new byte[src.length()];
		char [] charStr = src.toCharArray();
		for (int i = 0; i < charStr.length; i++) {
			result[i] = (byte)charStr[i];
		}
		return result;
	}
	
	/**
	 * Convert byte to int
	 * �������ǿ��Խ�byteת����int��
	 * @param src �ֽڳ���127��ֵ
	 * @return hex int
	 */
	public static final int byteToInt(byte str) {
		int result = 0;
		// ��Ϊunsigned byte = byte & 0xFF��0~255����ɫ��byte-128~127��ʾ
		// ����0~127��Ӧ0~127 ��-128~-1 ��Ӧ128~255��������и�ֵ��ͨ����λ�ķ�ʽ���л��㡣
		// ����127��Ϊ����,��Ҫת����
		if (str < 0) {
			result = str & 0xFF;
		} else {
			return str;
		}
		return result;
	}
	
	/**
	 * ���żӿո�
	 * @param cardNo
	 * @return
	 */
	public static String getCardNo(String cardNo) {
		char[] ch = cardNo.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			sb.append(ch[i]);
			if ((i + 1) % 2 == 0 && i < cardNo.length()-1) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
}
