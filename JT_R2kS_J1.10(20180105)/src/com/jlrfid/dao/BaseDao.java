package com.jlrfid.dao;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class BaseDao {
		static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
		static final String URL = "jdbc:mysql://47.96.118.115:3306/targecodes?useSSL=false";
		static final String USERNAME = "root";
		static final String PASSWORD = "root";
		
		static{
			try {
				Class.forName(DRIVER_CLASS_NAME);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		public Connection getConnection(){
			Connection con = null;
			try {
				con =  (Connection) DriverManager.getConnection(URL,USERNAME,PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
		}
		
		public void closeAll(Connection con, Statement stmt, ResultSet rs){
			if(null != rs){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(null != stmt){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(null != con){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		public int ExecuteNonQuery(String sql, Object...params){
			int count = 0;
			Connection con = null;
			PreparedStatement pstmt = null;
			try{
				con = this.getConnection();
				pstmt = (PreparedStatement) con.prepareStatement(sql);
				if(null != params){
					for(int i=0; i<params.length; i++){
						pstmt.setObject(i+1, params[i]);
					}
				}
				count = pstmt.executeUpdate();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				this.closeAll(con, pstmt, null);
			}
			return count;
		}
	}

