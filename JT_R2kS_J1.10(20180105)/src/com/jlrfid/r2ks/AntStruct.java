package com.jlrfid.r2ks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.sun.jna.Structure;


public class AntStruct extends Structure {
	/**
	 * �����Ƿ��������飬�ĸ����߶�Ӧ�����ĸ�������0�ǲ����ã�1������
	 */
	public byte[] antEnable = new byte[4];
	/**
	 * ��������ʱ�����飬�ĸ����߶�Ӧ�����ĸ�������ʱ��ȡֵ��Χ50-10000
	 */
	public int[] dwellTime = new int[4];
	/**
	 * ���߹������飬�ĸ����߶�Ӧ�����ĸ�����������ȡֵ��Χ20-33
	 */
	public int[] power = new int[4];

	
	public static class ByReferenceextends extends AntStruct implements Structure.ByReference {
	}

	public static class ByValue extends AntStruct implements Structure.ByValue {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getFieldOrder() {
		List fields = new ArrayList();
		fields.addAll(Arrays.asList(new String[] { "antEnable", "dwellTime","power" }));
		return fields;
	}
}