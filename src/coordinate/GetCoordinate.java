package coordinate;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class GetCoordinate {
	private double[][] R1 = new double[20][4];//1米高
	private double[][] R2 = new double[20][4];//1.5米高
	public static boolean isNaN(double v) {
        return (v != v);
    }
	public Connection ConnectMySQL() throws ClassNotFoundException, SQLException {
		String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
		String URL = "jdbc:mysql://47.96.118.115:3306/targecodes?useSSL=false";
		String USERNAME = "root";
		String PASSWORD = "root";
		
		Class.forName(DRIVER_CLASS_NAME);
		Connection con = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
//		Statement sta = (Statement) con.createStatement();
		
//		String sql = "SELECT cardNo,Rssi,createDate,antNo,host FROM targeinfo";
//		ResultSet rSet = statement.executeQuery(sql);
		return con;
	}
	
	public double[][] getR1() {
		return R1;
	}
	
	public double[][] getR2(){
		return R2;
	}
	
	public void prinR(double[][] R) {//输出R中的值
		for(int i=0;i<20;i++) {
			for(int j=0;j<4;j++) {
				System.out.print(R[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void setValueOfR(String cardNo) throws Exception {//把格子测到的数据放到第一个表中
		double[] Value = new double[4];
		int ansNo = 1;
		for(int i=0;i<4;i++) {
			if(i<2) {
				Value[i] = getRssi(cardNo, "No", ansNo, "192.168.0.200");
			}else {
				Value[i] = getRssi(cardNo, "No", ansNo, "192.168.0.201");
			}
			if(ansNo==1) {
				ansNo = 2;
			}else {
				ansNo = 1;
			}
		}
		
		Connection con = ConnectMySQL();
		
		Statement sta1 = (Statement) con.createStatement();
		String sql = "SELECT position FROM PositionOfLab";
		ResultSet rSet = sta1.executeQuery(sql);
		rSet.last();
		
		int position = 0;
		if(rSet.last()) {
			position = rSet.getInt("position");
		}else {
			position = -1;
		}
		PreparedStatement sta = (PreparedStatement)con.prepareStatement("INSERT INTO positionoflab(position,one,two,three,four) " + "values(?,?,?,?,?)");
		sta.setInt(1, position+1);
		System.out.print(position+1+" ");
		for(int i=2;i<6;i++) {
			System.out.print(Value[i-2]+" ");
			sta.setDouble(i, Value[i-2]);
		}
		sta.executeUpdate();
		
		String sql1 = "truncate table targeinfo";
		sta1.executeUpdate(sql1);
	}
	
	public void setValueOfR2(String cardNo) throws Exception {//把格子测到的数据放到第二个表中
		double[] Value = new double[4];
		int ansNo = 1;
		for(int i=0;i<4;i++) {
			if(i<2) {
				Value[i] = getRssi(cardNo, "No", ansNo, "192.168.0.200");
			}else {
				Value[i] = getRssi(cardNo, "No", ansNo, "192.168.0.201");
			}
			if(ansNo==1) {
				ansNo = 2;
			}else {
				ansNo = 1;
			}
		}
		
		Connection con = ConnectMySQL();
		
		Statement sta1 = (Statement) con.createStatement();
		String sql = "SELECT position FROM PositionOfLab2";
		ResultSet rSet = sta1.executeQuery(sql);
		int position = 0;
		rSet.last();
		
		if(rSet.last()) {
			position = rSet.getInt("position");
		}else {
			position = -1;
		}
		
		PreparedStatement sta = (PreparedStatement)con.prepareStatement("INSERT INTO positionoflab2(position,one,two,three,four) " + "values(?,?,?,?,?)");
		sta.setInt(1, position+1);
		System.out.print(position+1+" ");
		for(int i=2;i<6;i++) {
			System.out.print(Value[i-2]+" ");
			sta.setDouble(i, Value[i-2]);
		}
		sta.executeUpdate();
		
		String sql1 = "truncate table targeinfo";
		sta1.executeUpdate(sql1);
	}
	
	public void getValueOfR1() throws ClassNotFoundException, SQLException {//从表中读R1的数据
		String sql = "SELECT position,one,two,three,four FROM PositionOfLab";
		Connection con = ConnectMySQL();
		Statement sta = (Statement)con.createStatement();
		ResultSet rSet = sta.executeQuery(sql);
		
		while(rSet.next()) {
			int position = rSet.getInt("position");
			R1[position][0] = rSet.getDouble("one");
			R1[position][1] = rSet.getDouble("two");
			R1[position][2] = rSet.getDouble("three");
			R1[position][3] = rSet.getDouble("four");
		}
	}
	
	public void getValueOfR2() throws ClassNotFoundException, SQLException {//从表中读R2的数据
		String sql = "SELECT position,one,two,three,four FROM PositionOfLab";
		Connection con = ConnectMySQL();
		Statement sta = (Statement)con.createStatement();
		ResultSet rSet = sta.executeQuery(sql);
		
		while(rSet.next()) {
			int position = rSet.getInt("position");
			R2[position][0] = rSet.getDouble("one");
			R2[position][1] = rSet.getDouble("two");
			R2[position][2] = rSet.getDouble("three");
			R2[position][3] = rSet.getDouble("four");
		}
	}
	
	public int[] NumberToCoordinate(int r) {//点和坐标的转换
		int[] p = new int[2];
		p[0] = r%4;
		p[1] = r/4;
		return p;
	}
	
	public String getCreateDate(String createDate) {//获取创建日期
		String temp = new String();
		temp = "";
		for(int i=0;i<createDate.length()-2;i++) {
			temp += createDate.charAt(i);
		}
		return temp;
	}
	
	public Map<Double, Integer> getMinOfEn(double[] En){//@XCY 此方法中我们取K值，建议数值为3或4。把En排序返回。
		Map<Double, Integer> ans = new TreeMap<Double, Integer>();
		for(int i=0;i<20;i++) {
			ans.put(En[i], i);
		}
		//System.out.println(ans[0]+" "+ans[1]+" "+ans[2]);
		return ans;
	}
	
	public ArrayList<Integer> Filter(ArrayList<Integer> Rssi) {//取Rssi中间值以排除飞出去的误差值
		double mid = 0.0;
		Collections.sort(Rssi);
		int len = Rssi.size();
		if(len%2==0) {
			mid = 1.0 * (Rssi.get(len/2-1) + Rssi.get(len/2)) / 2;
		}else {
			mid = 1.0 * Rssi.get(len/2);
		}
		ArrayList<Integer> TrueRssi = new ArrayList<Integer>();
		for(int i=0;i<len;i++) {
			int temp = Rssi.get(i);
			if(temp<mid+7 && temp>mid-7) {
				TrueRssi.add(temp);
			}
		}
		return TrueRssi;
	}
	
	public double getRssi(String cardNo, String createDate, int antNo, String host) throws Exception {//获取targeinfo表中相同时间、host、antNo和EPC的RSSI
//		getValueOfR(R);//@XCY 初始化数组R
		
		String sql = "SELECT cardNo,Rssi,createDate,antNo,host FROM targeinfo";
		Connection con = ConnectMySQL();
		Statement sta = (Statement)con.createStatement();
		ResultSet rSet = sta.executeQuery(sql);
		
		ArrayList<Integer> Rssi = new ArrayList<Integer>();
		
		while(rSet.next()) {
//			System.out.println(getCreateDate(rSet.getString("createDate")));
//			System.out.println("我1");
			if(cardNo.equals(rSet.getString("cardNo")) && createDate.equals(getCreateDate(rSet.getString("createDate"))) && antNo==rSet.getInt("antNo") && host.equals(rSet.getString("host"))) {
				Rssi.add(rSet.getInt("Rssi"));
			}else if(cardNo.equals(rSet.getString("cardNo")) && createDate.equals("No") && antNo==rSet.getInt("antNo") && host.equals(rSet.getString("host"))) {
				Rssi.add(rSet.getInt("Rssi"));
			}
		}
		
		if(Rssi.size() == 0) {
			return 0.0;
		}
		
		ArrayList<Integer> TrueRssi = new ArrayList<Integer>();
		TrueRssi = (ArrayList)Filter(Rssi).clone();
		int sum = 0;
		for(int i=0;i<TrueRssi.size();i++) {
			sum += TrueRssi.get(i);
		}
		
		if(TrueRssi.size()==0) {
			return 0.0;
		}
		
		double ans = 1.0 * sum / TrueRssi.size();
		return ans;
	}
	
	public Map<Double, Integer> getEk(double[][] R, String cardNo, String createDate, int k) throws Exception {//获取Ek
		double[] T = new double[4];
//		System.out.println(T[0]+" "+T[1]+" "+T[2]+" "+T[3]);
		T[0] = getRssi(cardNo, createDate, 1, "192.168.0.200");
		T[1] = getRssi(cardNo, createDate, 2, "192.168.0.200");
		T[2] = getRssi(cardNo, createDate, 1, "192.168.0.201");
		T[3] = getRssi(cardNo, createDate, 2, "192.168.0.201");

		double[] En = new double[20];
		for(int i=0;i<20;i++) {
			for(int j=0;j<4;j++) {
				if(T[j]==0.0){
					continue;//@XCY 排除没读到的情况
				}
				En[i] += Math.pow(T[j] - R[i][j],2);
			}
			En[i] = Math.pow(En[i], 0.5);
		}

		Map<Double, Integer> temp = new TreeMap<Double, Integer>();
//		double[] temp1 = new double[k];
//		int[] r = new int[k];
//		int q = 0;
		temp = getMinOfEn(En);
//		Iterator<Double> iterator = temp.keySet().iterator();
//		while (iterator.hasNext()) {
//		    double key = iterator.next();
//		    temp1[q] = key;
//		    r[q] = temp.get(key);
//		    q++;
//		    if(q==k) {
//		    	break;
//		    }
//		}
		return temp;
	}
	
	public double[] getFinalCoordinates(String cardNo, String createDate, int k) throws Exception {//获取最终位置坐标
		Map<Double, Integer> t1 = getEk(R1, cardNo, createDate, k);
		Map<Double, Integer> t2 = getEk(R2, cardNo, createDate, k);
		//和1米高点进行比较后获取的点
		double[] temp1 = new double[k];
		int[] r1 = new int[k];
		int q1 = 0;
		Iterator<Double> iterator1 = t1.keySet().iterator();
		while (iterator1.hasNext()) {
		    double key = iterator1.next();
		    temp1[q1] = key;
		    r1[q1] = t1.get(key);
		    q1++;
		    if(q1==k) {
		    	break;
		    }
		}
		//和1.5米高点进行比较后获取的点
		double[] temp2 = new double[k];
		int[] r2 = new int[k];
		int q2 = 0;
		Iterator<Double> iterator2 = t2.keySet().iterator();
		while (iterator2.hasNext()) {
		    double key = iterator2.next();
		    temp2[q2] = key;
		    r2[q2] = t2.get(key);
		    q2++;
		    if(q2==k) {
		    	break;
		    }
		}
		
		double SumOfEk = 0.0;
		for(int i=0;i<k;i++) {
			SumOfEk += 1.0/Math.pow(temp1[i], 2);
			SumOfEk += 1.0/Math.pow(temp2[i], 2);
		}
		double[] w1 = new double[k];
		double[] w2 = new double[k];
		
		for(int i=0;i<k;i++) {
			w1[i] = 1.0/Math.pow(temp1[i], 2)/SumOfEk;
			w2[i] = 1.0/Math.pow(temp2[i], 2)/SumOfEk;
		}
		
		int[][] p1 = new int[k][2];
		int[][] p2 = new int[k][2];
		
		for(int i=0;i<k;i++) {
			p1[i] = NumberToCoordinate(r1[i]);
			p2[i] = NumberToCoordinate(r2[i]);
		}
		double[] ans = new double[2];
		for(int i=0;i<2;i++) {
			ans[i] = 0.0;
			for(int j=0;j<k;j++) {
				ans[i] += w1[j]*p1[j][i]*1.0;
				ans[i] += w2[j]*p2[j][i]*1.0;
			}
//			ans[i] = w[0]*p[0][i]*1.0 + w[1]*p[1][i]*1.0 + w[2]*p[2][i]*1.0;
		}
		if(isNaN(ans[0])   || isNaN(ans[1])) {
			ans[0]=-1;
			ans[1]=-1;
		}
		return ans;//@XCY ans[0]是X轴坐标，ans[1[是Y轴坐标
	}
	
	public void setFinalCoordinates(String cardNo, String createDate, int k) throws Exception {//把获取的坐标传递到数据库
		double[] coordinates = getFinalCoordinates(cardNo, createDate, k);
		System.out.println(coordinates[0] + " " + coordinates[1]);//输出坐标瞅一瞅	
		Connection con = ConnectMySQL();
		PreparedStatement sta = (PreparedStatement)con.prepareStatement("INSERT INTO coordinates(horizontal, vertical) " + "values(?,?)");
		sta.setDouble(1, coordinates[0]*100);
		sta.setDouble(2, coordinates[1]*100);
		sta.executeUpdate();
	}
}
