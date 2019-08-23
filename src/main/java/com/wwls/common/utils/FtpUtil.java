package com.wwls.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import com.wwls.common.config.Global;


/**
 * ftp上传下载工具类
 * <p>Title: FtpUtil</p>
 * <p>Description: </p>
 * @author	
 * @date	2015年7月29日下午8:11:51
 * @version 1.0
 */
public class FtpUtil {

	/** 
	 * Description: 向FTP服务器上传文件 
	 * @param host FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param basePath FTP服务器基础目录
	 * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
	 * @param filename 上传到FTP服务器上的文件名 
	 * @param input 输入流 
	 * @return 成功返回true，否则返回false 
	 */  
	public static boolean uploadFile(String host, int port, String username, String password, String basePath,
			String filePath, String filename, InputStream input) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
		 	ftp.login(username, password);// 登录
	 
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			//切换到上传目录
			System.out.println("获取的路径是＝＝＝"+basePath+filePath);
			if (!ftp.changeWorkingDirectory(basePath+filePath)) {
				//如果目录不存在创建目录
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir)) continue;
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(tempPath)) {
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			//设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//上传文件
			if (!ftp.storeFile(filename, input)) {
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}
	
	
	
	 /**  
     *   
     * @param file 上传的文件或文件夹  
     * @throws Exception  
     */    
    public static  List<String> upload(File file) throws Exception{ 
    	
    	String host= Global.getConfig("ftp.host");
		int port =Integer.parseInt(Global.getConfig("ftp.port")) ;
		String username = Global.getConfig("ftp.username");
		String password= Global.getConfig("ftp.password");
		String basePath= Global.getConfig("ftp.base.path");
		List<String> list = new ArrayList<String>();
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			 
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return list;
			}
        if(file.isDirectory()){ 
         String absolutePath =	file.getAbsolutePath();
         System.out.println("获取的目录是"+absolutePath);
       String tempPath =  absolutePath.replace(Global.getConfig("web.upload.path"), "");
          boolean tag=  ftp.makeDirectory(basePath+"/"+tempPath);   
          System.out.println("目录创建成功"+tag);
            System.out.println("切换的路径是===="+file.getName());
            ftp.changeWorkingDirectory(file.getName());      
            String[] files = file.list();             
            for (int i = 0; i < files.length; i++) {      
                File file1 = new File(file.getPath()+"/"+files[i] );      
                if(file1.isDirectory()){    
                    upload(file1);      
                    ftp.changeToParentDirectory();      
                }else{     
                	
                     list.add(file.getPath()+"/"+files[i]);
                 }                 
            }      
        }else{   
            list.add(file.getPath()+"/"+file.getName());
                
        }
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
	 
		return list;    
    }      
	
	/****
	 * 最简单的文件FTP上传到文件服务器上
	 * @author xudongdong
	 * @version 2016-06-254
	 * *****/
	public static boolean uploadFileSimple(String filePath, String filename, InputStream input) {
		String host= Global.getConfig("ftp.host");
		int port =Integer.parseInt(Global.getConfig("ftp.port")) ;
		String username = Global.getConfig("ftp.username");
		String password= Global.getConfig("ftp.password");
		String basePath= Global.getConfig("ftp.base.path");
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			//切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath+filePath)) {
				//如果目录不存在创建目录
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir)) continue;
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(tempPath)) {
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			//设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//上传时重启线程
			ftp.enterLocalPassiveMode();
			ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			//上传文件
			if (!ftp.storeFile(new String(filename.getBytes("iso-8859-1"),"UTF-8"),input)) {
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}
	/** 
	 * Description: 从FTP服务器下载文件 
	 * @param host FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param remotePath FTP服务器上的相对路径 
	 * @param fileName 要下载的文件名 
	 * @param localPath 下载后保存到本地的路径 
	 * @return 
	 */  
	public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
			String fileName, String localPath) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
	/*	try {  
	        FileInputStream in=new FileInputStream(new File("D:\\temp\\image\\gaigeming.jpg"));  
	        boolean flag = uploadFile("192.168.25.133", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images","/2015/01/21", "gaigeming.jpg", in);  
	        System.out.println(flag);  
	    } catch (FileNotFoundException e) {  
	        e.printStackTrace();  
	    }  */
		 
		/*FileInputStream in;
		 * try {
			in = new FileInputStream(new File("D://a.jpg"));
			String host =Global.getConfig("ftp.host");
			System.out.println("获取的IP地址是=="+host+"======");
			System.out.println("获取的IP地址的长度是=="+host.length());
			String myHost = "115.28.71.87";
			System.out.println("两个值是否相等"+host.equals(myHost));
			 boolean flag = uploadFile(myHost, 21, "ftpuser", "mooyun123", "/docs/","2017", "1.png", in);
			  System.out.println(flag);  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/  
	 File file = 	new File("/Users/xudongdong/Documents/temp/demo/") ;
	 System.out.println("咿咿呀呀");
		try {
			upload(file);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
