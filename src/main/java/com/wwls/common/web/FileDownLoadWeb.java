package com.wwls.common.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wwls.common.config.Global;
@Controller
@RequestMapping(value = "${adminPath}/common/FileDownLoadWeb")
public class FileDownLoadWeb {
	
	 @RequestMapping({"download"})
	  public void downloadFile(String filePath, HttpServletRequest request, HttpServletResponse response)
	    throws IOException
	  {
	    response.setContentType("text/html;charset=utf-8");
	    request.setCharacterEncoding("UTF-8");
	    BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
	  
	    String path = Global.getConfig("web.upload.path");
	    path = path + "/" + filePath;
	    String file_name = filePath.substring(filePath.lastIndexOf("/") + 1);
	    try
	    {
	      long fileLength = new File(path).length();
	      response.setContentType("application/x-msdownload;");
	      response.setHeader("Content-disposition", "attachment; filename=" + 
	        file_name);
	      response.setHeader("Content-Length", String.valueOf(fileLength));
	      bis = new BufferedInputStream(new FileInputStream(path));
	      bos = new BufferedOutputStream(response.getOutputStream());
	      byte[] buff = new byte[2048];
	      int bytesRead;
	      while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
	      {
	        bos.write(buff, 0, bytesRead);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      if (bis != null)
	        bis.close();
	      if (bos != null)
	        bos.close();
	    }
	  }
	

}
