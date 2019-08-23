package com.wwls.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wwls.common.config.Global;

public class RNSqlServerUtils {
	protected static Logger logger = LoggerFactory.getLogger(RNSqlServerUtils.class);
	//这里可以设置数据库名称
    private static String URL ="jdbc:sqlserver://hnrunneng.gnway.cc:15616;DatabaseName=hbposv10";
    private static String USER="rn";
    private static String PASSWORD="rn2018@#";
    //静态代码块（将加载驱动、连接数据库放入静态块中）
    static{
        try {
        	USER=Global.getConfig("rn.sqlservers.user");
        	PASSWORD=Global.getConfig("rn.sqlservers.password");
        	URL= "jdbc:sqlserver://"+Global.getConfig("rn.sqlservers.hostname")+":"
        			+Global.getConfig("rn.sqlservers.port")+";DatabaseName="+Global.getConfig("rn.sqlservers.databasename");
            //1.加载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //2.获得数据库的连接
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
    }
    
    //对外提供一个方法来获取数据库连接
    public static Connection getConnection(){
    	Connection conn=null;
    	try {
			if(conn==null||conn.isClosed()){
				conn=(Connection)DriverManager.getConnection(URL,USER,PASSWORD);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return conn;
    }
    
    
    //测试用例
    public static void main(String[] args) throws Exception{
        
        //3.通过数据库的连接操作数据库，实现增删改查
        Statement stmt = getConnection().createStatement();
        //ResultSet executeQuery(String sqlString)：执行查询数据库的SQL语句   ，返回一个结果集（ResultSet）对象。
//        ResultSet rs = stmt.executeQuery("select * from t_temp_pay_modify");
//        int i=0;
//        while(rs.next()){//如果对象中有数据，就会循环打印出来
//            System.out.println(rs.getString("vch_id")+","+rs.getString("vch_phone")+","+rs.getString("vch_password"));
//            i++;
//        }
//        ResultSet rs = stmt.executeQuery("select * from t_temp_consume");//t_temp_consume
//        int i=0;
//        while(rs.next()){//如果对象中有数据，就会循环打印出来
//            System.out.println(rs.getString("vip_phone")+","+rs.getString("vch_voucher")
//            +","+rs.getString("vch_memo2")+","+rs.getDouble("sale_consume")+
//            ","+rs.getString("oper_date")+ ","+rs.getString("ch_state"));
//            i++;
//        }
        
        ResultSet rs = stmt.executeQuery("select * from t_temp_detail");//t_temp_consume
        int i=0;
        while(rs.next()){//如果对象中有数据，就会循环打印出来
            System.out.println(rs.getString("flow_no")+","+rs.getString("oper_date")
            +","+rs.getString("vch_operid")+","+rs.getDouble("sale_amount")+
            ","+rs.getString("oper_date")+ ","+rs.getString("vip_phone"));
            i++;
        }
        System.out.println(i);
    }
    
   
}