package com.jlrfid.mainframe.basicOperation;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.jlrfid.TagRecord;
import com.jlrfid.dao.impl.TagRecordDaoImpl;
import com.jlrfid.entity.TargeInfo;
import com.jlrfid.mainframe.MainStart;
import com.jlrfid.r2ks.FileOperation;
import com.jlrfid.r2ks.UHFOperate;
import com.jlrfid.r2ks.util.Message;
import com.jlrfid.service.impl.TagRecordServiceImpl;

public class BasicTable extends MainStart{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String getCardNo(String cardNo) {
		if (cardNo.length() < 24) {
			return null;
		}
		cardNo = cardNo.substring(0, 24);
		char[] ch = cardNo.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			sb.append(ch[i]);
			if ((i + 1) % 2 == 0 && i < 24) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	public static int getRssi(UHFOperate r2k, String Rssi) {
//		String host = r2k.host;
//		if(host.equals("192.168.0.200")) {
			Rssi = Rssi.substring(30, 32);
//		}
//		else {
//			Rssi = Rssi.substring(32, 34);
//		}
		char[] ch = Rssi.toCharArray();
		int rssi = 0;
		rssi = (ch[0]-'0')*16+ch[1]-'0';
		rssi = -1*rssi;
		return rssi;
	}
	
	public static int getAntNo(UHFOperate r2k, String AntNo) {
//		String host = r2k.host;
//		if(host.equals("192.168.0.200")) {
			AntNo = AntNo.substring(33, 34);
//		}
//		else {
//			AntNo = AntNo.substring(31, 32);
//		}
		int antNo = AntNo.charAt(0)-'0'+1;
		return antNo;
	}
	
	/**
	 * 读取卡的信息处理后显示在表格中
	 * @param data 电子标签数据
	 * @param antNo 天线编号
	 */
	public static void tableInfoShow(UHFOperate r2k,String data,int antNo) {
		String cardNo = BasicTable.getCardNo(data);	
		
		if (cardNo == null) {
			return;
		}
		
		/**
		 * @XCY 获取RSSI和正确的天线号
		 */
		int rssi = BasicTable.getRssi(r2k, data);
		antNo = BasicTable.getAntNo(r2k, data);
		System.out.println("我是测试的:"+data+" "+antNo);
		
		/**
		 * @XCY 忽略实验室中找不到的标签
		 */
		String[] ignore = new String[10];
		ignore[0] = "C2 00 41 45 31 07 01 43 15 40 75 46 ";
		ignore[1] = "F2 00 41 45 31 07 01 54 15 40 75 72 ";
		ignore[2] = "E2 00 41 45 31 07 01 54 15 40 75 72 ";
		ignore[3] = "E2 00 00 16 13 16 00 65 12 00 A2 49 ";
		ignore[4] = "00 00 41 45 31 07 01 43 15 40 75 46 ";
		ignore[5] = "00 00 41 45 31 07 01 54 15 40 75 72 ";
		ignore[6] = "E2 00 40 74 89 09 01 90 27 40 07 B1 ";
		ignore[7] = "C2 00 00 16 13 16 00 65 12 00 A2 49 ";
		ignore[8] = "A2 00 41 45 31 07 01 54 15 40 75 72 ";
		//ignore[9] = "B1 00 00 00 00 00 00 00 00 00 00 00 ";
		for(int i=0;i<10;i++) {
			if(cardNo.equals(ignore[i])) {
				System.out.println("成功忽略"+cardNo);
				return;
			}
		}
		
		
		//String getDeviceId = data.substring(data.length()-4, data.length()-2);
		//int deviceId = Integer.parseInt(getDeviceId.trim(),16);
		String deviceId = r2k.host;
		try {
			if(chooseSaveFile){
			    FileOperation.writeFile(cardNo);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			Message.Show(rs.getString("strMsgFailedSaveFile"), "");
		}
		boolean flag = false;
		TagRecordDaoImpl dao = new  TagRecordDaoImpl();
		// 添加数据到table中
		for (int j = 0; j < MainStart.tbl_showTagInfo.getRowCount(); j++) {
			// 如果表格中有数据与集合数据对比,如果表格中有相同的数据则增加读取的次数
			String rows = (String) MainStart.tbl_showTagInfo.getValueAt(j, 1);
			int ant = Integer.parseInt(MainStart.tbl_showTagInfo.getValueAt(j, 3).toString());
			String device = MainStart.tbl_showTagInfo.getValueAt(j, 4).toString();
			if (rows.equals(cardNo)&& ant == antNo && deviceId.equals(device)) {
				int count = Integer.parseInt(MainStart.tbl_showTagInfo.getValueAt(j, 2).toString());
				count++;
				MainStart.tbl_showTagInfo.setValueAt(count, j, 2);
				// @XCY 添加数据到数据库
				TagRecord tag =new TagRecord();
				tag.setCardNo(cardNo);
				tag.setAntNo(antNo);
				tag.setHost(r2k.host);
				tag.setRssi(rssi);
				dao.insert(tag);
				flag = true;
				break;
			}
		}
		// 如果表格中没有与集合中相同的数据则新增一行数据
		if (!flag) {
			// 获取集合中的数据
			Object[] rowValues = { MainStart.tbl_showTagInfo.getRowCount() + 1, cardNo,1, antNo, deviceId};
			MainStart.tableModel.addRow(rowValues); // 添加一行
			// @XCY 添加数据到数据库
			TagRecord tag =new TagRecord();
			tag.setCardNo(cardNo);
			tag.setAntNo(antNo);
			tag.setHost(r2k.host);
			tag.setRssi(rssi);
			dao.insert(tag);
		}
		// 计算读卡的次数
		int cardResultTime = 0;
		for (int i = 0; i < MainStart.tbl_showTagInfo.getRowCount(); i++) {
			cardResultTime += Integer.parseInt(MainStart.tbl_showTagInfo.getValueAt(i, 2).toString());
		}
		MainStart.labelCount.setText(String.valueOf(cardResultTime));
		// 总共有几张标签
		MainStart.labelTagCount.setText(String.valueOf(MainStart.tbl_showTagInfo.getRowCount()));
	}

	/**
	 * 设置表格样式
	 * 
	 * @param panel
	 *            面板(相当于一个html中的div)
	 */
	public static void setTableStyle(JTable tbl_showTagInfo) {
		// 建头组件
		BasicTableHeadUI ui = new BasicTableHeadUI();
		// 获得头组件并设置
		tbl_showTagInfo.getTableHeader().setUI(ui);

		// 设置表头的大小，要够长，高度最好要和表头的高度一样，否则会出现多余部分
		tbl_showTagInfo.getTableHeader().setPreferredSize(new Dimension(450, 30));
		tbl_showTagInfo.setRowHeight(25);
		tbl_showTagInfo.setEnabled(false);
		// 设置table内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		tbl_showTagInfo.setDefaultRenderer(Object.class, tcr);
		// 设置table列头居中显示
		((DefaultTableCellRenderer) tbl_showTagInfo.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		tbl_showTagInfo.setPreferredScrollableViewportSize(new Dimension(500, 260));
		// 设置列宽
		TableColumn firsetColumn = tbl_showTagInfo.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(50);
		firsetColumn.setMaxWidth(50);
		firsetColumn.setMinWidth(50);

		TableColumn secondColumn = tbl_showTagInfo.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(250);
		secondColumn.setMaxWidth(250);
		secondColumn.setMinWidth(250);
	}
}
