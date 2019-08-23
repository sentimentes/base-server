<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.wwls.common.mapper.JsonMapper" %>
<%@ page import="com.baidu.ueditor.ActionEnter" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.wwls.common.utils.FileUtils" %>
<%@ page import="com.wwls.common.config.Global" %>
<%@ page import="com.wwls.common.utils.FtpUtil" %>
<%@ page import="org.apache.commons.io.IOUtils" %>
<%@ page import="java.io.*" %>
 
<%@ page trimDirectiveWhitespaces="true" %>
<%
//文件保存目录URL
String saveUrl  = Global.getConfig("wy.file.http.path");
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	String rootPath = request.getSession().getServletContext().getRealPath("/");
	System.out.println("获取的路径是dir="+rootPath);
	//String rootPath = application.getRealPath( "/" );
	System.out.println("获取的路径是"+rootPath);
	String result = new ActionEnter( request, rootPath ).exec();
	System.out.println("返回的结果是===="+result);
    //1.判断状态 是否包含success
  if(result.contains("SUCCESS")){
	  try{
	  Map<String,Object> map = (Map<String,Object>)JsonMapper.fromJsonString(result, Map.class);
	  Object url =map.get("url");
	  System.out.println(url);
	  Object type =map.get("type");
	  System.out.println(type);
	  Object title=map.get("title");
	  System.out.println(title);
	  
  if(title!=null){
	  System.out.println("======开始进入FTP上传===");
	  File file = new File(rootPath+url);
	  InputStream inputStream = new FileInputStream(file);
	  String filePath = FileUtils.getFilePath();
		//boolean tag =	FtpUtil.uploadFileSimple(filePath, title.toString(), inputStream);  
		 System.out.println("获取的数据是=="+url +"type=="+type+"title==="+title);	
		  FileOutputStream fileOutputStream =new FileOutputStream(saveUrl+title.toString());
		  //IOUtils.copy(inputStream,fileOutputStream );
			IOUtils.copy(inputStream,fileOutputStream );
			 String finalUrl = saveUrl+title.toString();
			 map.put("url", finalUrl);
			 result = JsonMapper.toJsonString(map);
		
		 if(file.exists()){
			 file.delete();
		 }
	  }
	  }catch(Exception e){
		  e.printStackTrace();
	  }
  }
System.out.println("返回的结果是===="+result);
out.write(result);
%>