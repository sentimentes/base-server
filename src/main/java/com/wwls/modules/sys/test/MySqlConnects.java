package com.wwls.modules.sys.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MySqlConnects {
		static String sql = null;  
		static MySqlConnects db2 = null;  
		static ResultSet ret = null;
		//jdbc:mysql://127.0.0.1/student
	 	public static final String url = "jdbc:mysql://123.56.101.94:3306/greatserver";  
	    public static final String name = "com.mysql.jdbc.Driver";  
	    public static final String user = "root";  
	    public static final String password = "zhongxin225";
	  
	    public Connection conn = null;  
	    public PreparedStatement pst = null;  
	  
	    public MySqlConnects(String sql) {  
	        try {  
	            Class.forName(name);//指定连接类型  
	            conn = DriverManager.getConnection(url, user, password);//获取连接  
	            pst = conn.prepareStatement(sql);//准备执行语句  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    public void close() {  
	        try {  
	            this.conn.close();  
	            this.pst.close();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }


	    public static void main(String[] args) {  
	        sql = "select * from citic_book_info limit 10";//SQL语句  
	        db2 = new MySqlConnects(sql);//创建DBHelper对象  
	  
	        try {  
	            ret = db2.pst.executeQuery();//执行语句，得到结果集  
	            while (ret.next()) {  
	                String uid = ret.getString(1);  
	                String ufname = ret.getString(2);  
	                String ulname = ret.getString(3);  
	                String udate = ret.getString(4);  
	                System.out.println(uid + "\t" + ufname + "\t" + ulname + "\t" + udate );  
	            }//显示数据  
	            ret.close();  
	            db2.close();//关闭连接  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }  

}
