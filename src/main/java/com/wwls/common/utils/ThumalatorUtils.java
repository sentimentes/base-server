package com.wwls.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import com.wwls.common.config.Global;
import net.coobird.thumbnailator.Thumbnails;

public class ThumalatorUtils {
	protected static Logger logger = LoggerFactory.getLogger(ThumalatorUtils.class);
	public static int[] getHeightWeight(File file ){
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(file);
			int width = bufferedImage.getWidth();   
			int height = bufferedImage.getHeight(); 
			int[] intArray = {width,height};  
			return   intArray;
		} catch (IOException e) {
			e.printStackTrace();
		}   
	 return 	null;
	}
	
	
	public static boolean imageCompress(String orginalPath){
		boolean isTrue = false;
		 File file = new File(orginalPath);
		 try {
				 long startTime=System.currentTimeMillis();//获取开始时间
				int[] wh= getHeightWeight(file);
				int wedithScal =1;
				int heightScal =1;
				try {
					wedithScal = wh[0]/1024;
					heightScal = wh[1]/1024;
				} catch (Exception e) {
				}
				logger.info("获取的宽高壁纸是=="+wedithScal+"==="+heightScal);
				int scal= wedithScal>=heightScal?wedithScal:heightScal;
				float realScal = 1f;
				if(scal>1){
					realScal=1/scal;
					if(realScal<0.25){
						realScal=0.25f;
					}
				}
				logger.info("获取的宽高是==="+wh[0]+"==="+wh[1]);
				 long fileSize= file.length()/1024;
				 logger.info("图片片大小=="+fileSize);
				 //质量比例分母
				int denominator = Integer.parseInt(Global.getConfig("thumalator.Denominator"));
				float quality = fileSize/denominator;
				float realQuality=1.0f;
				if(quality>=1){
					realQuality=1/quality;
				}
				
				if(realQuality<0.5){
					realQuality=0.5f;
				}
				logger.info("需要的压缩比例是=="+realScal);
				logger.info("需要的质量比例是=="+realQuality);
				if(realScal<1||realQuality<1){
					 Thumbnails.of(orginalPath).scale((realScal)).outputQuality(realQuality).toFile(file.getAbsolutePath()+"_25=.jpg");
					 isTrue = true;
				}else{
					 isTrue = false;
					 logger.info("无需压缩");
				}
				 long endTime=System.currentTimeMillis(); //获取结束时间
				 logger.info("程序运行时间： "+(endTime-startTime)+"ms");
		} catch (IOException e) {
		 
			e.printStackTrace();
		}
		 return isTrue;
	}

	
	//判断文件类型
	public static boolean checkFileType(String absPath){
		
		if(absPath.endsWith(".jpg")||absPath.endsWith(".png")||absPath.endsWith(".JPG")){
			return true;
		}
		return false;
		
	}
	 public static List<String> showAllFiles(File dir) throws Exception{
		 
		 List<String> list = new ArrayList<String>();
		  File[] fs = dir.listFiles();
		  for(int i=0; i<fs.length; i++){
		   if(fs[i].isDirectory()){
		    try{
		     showAllFiles(fs[i]);
		    }catch(Exception e){}
		   }else{
			   String absPath = fs[i].getAbsolutePath();
			   if(checkFileType(absPath)){
				   list.add(absPath);
				   logger.info(absPath);  
			   }
		   }
		  }
		  return list;
		 }
	/****
	 * 处理图片上传压缩
	 * @param: path :文件的全路径
	 */
	 
	public static String getNewPath(String path){
		 
		String newPath="";
		//获取本地图片校验是否压缩
				try {
				 boolean tag = imageCompress(path);
				 if(tag){
					 newPath =  path+"_25=.jpg";
				 }else{
					 newPath =  path;
				 }
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return newPath;
		
	}
	 

	public static String getNewPath(MultipartFile logo){
		String newPath="";
		String path ="";
		String basePath = Global.getConfig("web.upload.path");
		try {
			//String pathName = logo.getOriginalFilename();
			 // 文件转存路径  
			   path = FileUtils.saveFiles(logo, "image", "");
			//file.transferTo(new File("D:/uploadfile/cover/"+ pathName));
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}  
		//获取本地图片校验是否压缩
		try {
		 boolean tag = imageCompress(basePath+path);
		 if(tag){
			 File fileDelete = new File(basePath+path);
				if(fileDelete.exists()){
					fileDelete.delete();
					}
			 newPath = basePath+path+"_25=.jpg";
		 }else{
			 newPath = basePath+path;
		 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newPath;
	}
	
	public static void main(String []args){
		File file = new File("E:/uploadfile/cover");
		try {
		List<String> list =	showAllFiles(file);
		for(String filePath :list){
		 imageCompress(filePath);
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
