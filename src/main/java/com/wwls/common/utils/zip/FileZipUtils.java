package com.wwls.common.utils.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Enumeration;
import java.util.zip.ZipException;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * @author xudongdong
 * @version 2015-11-24
 * @desc 文件压缩，以及解压类
 * ***/
public class FileZipUtils {

	protected static Logger logger = LoggerFactory.getLogger(FileZipUtils.class);
	private static String sKey = "1234567890abcDEF";
	/**
	 * 功能：把 sourceDir 目录下的所有文件进行 zip 格式的压缩，保存为指定 zip 文件
	 * 
	 * @param sourceDir
	 * @param zipFile
	 */

	public static void zip(String sourceDir, String zipFile) {
		OutputStream os = null;
		BufferedOutputStream bos = null;
		ZipOutputStream zos = null;
		try {
			os = new FileOutputStream(zipFile);
			bos = new BufferedOutputStream(os);
			zos = new ZipOutputStream(bos);
			File file = new File(sourceDir);
			String basePath = null;
			if (file.isDirectory()) {
				basePath = file.getPath();
			} else {// 直接压缩单个文件时，取父目录
				basePath = file.getParent();
			}
			zipFile(file, basePath, zos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {

			try {
				if (zos != null) {
					zos.flush();
					zos.close();
				}
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 功能：执行文件压缩成zip文件
	 * 
	 * @param source
	 * @param basePath
	 *            待压缩文件根目录
	 * @param zos
	 */

	private static void zipFile(File source, String basePath, ZipOutputStream zos) {
		File[] files = new File[0];
		if (source.isDirectory()) {
			files = source.listFiles();
		} else {
			files = new File[1];
			files[0] = source;
		}

		String pathName;// 存相对路径(相对于待压缩的根目录)
		byte[] buf = new byte[1024];
		int length = 0;
		for (File file : files) {
			if (file.isDirectory()) {
				pathName = file.getPath().substring(basePath.length() + 1) + "/";
				try {
					zos.putNextEntry(new ZipEntry(pathName));
					zipFile(file, basePath, zos);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (zos != null) {
						try {
							zos.closeEntry();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				pathName = file.getPath().substring(basePath.length() + 1);
				InputStream is = null;
				BufferedInputStream bis = null;
				try {
					is = new FileInputStream(file);
					bis = new BufferedInputStream(is);
					zos.putNextEntry(new ZipEntry(pathName));
					while ((length = bis.read(buf)) > 0) {
						zos.write(buf, 0, length);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (bis != null) {
							bis.close();
						}
						if (is != null) {
							is.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			}
		}

	}

	/**
	 * 功能：解压 zip 文件，只能解压 zip 文件
	 * 
	 * @param zipfile
	 * @param destDir
	 */

	public static boolean unZip(String zipfile, String destDir) {

		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(new File(zipfile));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (zipFile == null) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Enumeration<ZipEntry> enumeration = zipFile.getEntries();
		ZipEntry zipEntry = null;
		while (enumeration.hasMoreElements()) {
			zipEntry = (ZipEntry) enumeration.nextElement();
			File loadFile = new File(destDir + zipEntry.getName());
			if (zipEntry.isDirectory()) {
				loadFile.mkdirs();
			} else {
				if (!loadFile.getParentFile().exists()) {
					loadFile.getParentFile().mkdirs();
				}
				outFile(zipFile, zipEntry, loadFile);
			}
		}
		return true;

	}

	/**
	 * 不需要解密的文件
	 * 
	 * @param zipEntry
	 * @return 不需要解密
	 */
	private static boolean noNeedEncry(ZipEntry zipEntry) {
		// 不需要解密
		boolean isNoNeedEncry = false;
		String name = zipEntry.getName();
		
		if (name.startsWith("META-INF/") || name.equals("mimetype")||name.contains("cover.jpg")) {
			isNoNeedEncry = true;
		}
		//logger.debug("进行非加密文件判断===="+name+"=========判断结果==="+isNoNeedEncry);
		return isNoNeedEncry;
	}

	/**
	 * 输出文件
	 * @param zipFile
	 * @param zipEntry
	 * @param loadFile
	 */
	private static void outFile(ZipFile zipFile, ZipEntry zipEntry, File loadFile) {
		boolean isNoNeedEncry = noNeedEncry(zipEntry);
		 byte b[] = new byte[1024];
		int length;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			outputStream = new FileOutputStream(loadFile);
			
			/*inputStream = isNoNeedEncry ? zipFile.getInputStream(zipEntry) : EncryptFileAES
					.decryptInputStream(zipFile.getInputStream(zipEntry));*/
			 inputStream = zipFile.getInputStream(zipEntry);
			 logger.debug("获取的当前文件的名称是====="+loadFile.getName()); 
			
			if(!isNoNeedEncry){
			try {
				logger.debug("进入加密通道");
				EncryptFileAES.encrypt(inputStream,outputStream,sKey);
			} catch (GeneralSecurityException e) {
			
				e.printStackTrace();
			}
			}else{
				while ((length = inputStream.read(b)) > 0) {
				logger.debug("进入非加密通道"+loadFile.getName());
					outputStream.write(b, 0, length);
				}
				try {
					if (outputStream != null) {
						outputStream.flush();
						outputStream.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	
	} 
	
	
	public static void main(String []args){
		
		 unZip("E:/Test/epub/bc1c7aa0e9884703b2a9fd28b551032e.epub", "E:/Test/epub/demo/");
	    //zip("/Users/xudongdong/Documents/upload/epub/bookFileUnZip/f87f98ebf81d47e29f8eaed8208d7e37/","/Users/xudongdong/Documents/test/10407376.epub");
	}
	

}
