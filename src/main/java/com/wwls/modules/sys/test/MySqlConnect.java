package com.wwls.modules.sys.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.wwls.common.utils.ActivitiCodeUtils;




public class MySqlConnect {

	private static String dbDriver = "com.mysql.jdbc.Driver";
	private static String dbUrl = "jdbc:mysql://101.201.72.168:3306/shop_test?useUnicode=true&characterEncoding=utf-8";// 根据实际情况变化
	private static String dbUser = "root";
	private static String dbPass = "MooyunWangyue";

	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);// 注意是三个参数
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
    
    
    public static void main(String[] args) {  
//        String sql94="insert into mc_product(id,name,sn) values(?,?,?)";//插入语句
//        String sqlQ="insert into mc_product(id,name,sn) values(?,?,?)";//查询语句
//        Connection cnn=getConn();
//      
//        try{
//            PreparedStatement preStmt =cnn.prepareStatement(sql94);
//            preStmt.setString(1,"1321");  
//            preStmt.setString(2,"张三1");//或者：preStmt.setInt(1,值); 
//            preStmt.setString(3,"1231");//或者：preStmt.setInt(1,值); 
//            preStmt.executeUpdate();
//            System.out.println("操作成功");
//            preStmt.close();
//            cnn.close();
//        }  
//        catch (SQLException e)
//        {
//        	System.out.println("操作失败");
//            e.printStackTrace();
//        }
    	
    	//生成8位数随机数
    	int[] randomNumber8 = ActivitiCodeUtils.randomCommon(100000000,999999999,4);
    	for(int i=0;i<randomNumber8.length;i++){
    		System.out.println("999===="+randomNumber8[i]);
    	}
    	
    	int[] randomNumber9 = ActivitiCodeUtils.randomCommon(100000000,999999999,4);
    	for(int i=0;i<randomNumber9.length;i++){
    		System.out.println("4444===="+randomNumber9[i]);
    	}
       
    }  
}
