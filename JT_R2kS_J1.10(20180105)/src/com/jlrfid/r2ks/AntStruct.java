package com.jlrfid.r2ks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.sun.jna.Structure;


public class AntStruct extends Structure {
	/**
	 * 天线是否启用数组，四个天线对应数组四个变量，0是不启用，1是启用
	 */
	public byte[] antEnable = new byte[4];
	/**
	 * 天线运行时间数组，四个天线对应数组四个变量，时间取值范围50-10000
	 */
	public int[] dwellTime = new int[4];
	/**
	 * 天线功率数组，四个天线对应数组四个变量，功率取值范围20-33
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