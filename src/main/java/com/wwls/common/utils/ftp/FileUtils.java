package com.wwls.common.utils.ftp;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.wwls.common.config.Global;
import com.wwls.common.utils.FileSizeHelper;
import com.wwls.common.utils.IdGen;
 
/**
 * 文件操作工具类
 * @author hugang
 * @version 2015-06-23
 * @updateDesc 将原来的方法改为静态方法，且返回路径调整为动态路径，不保存绝对路径
 */
public class FileUtils extends com.wwls.common.utils.FileUtils {

	/**
	 * 保存文件
	 * @param file源文件
	 * @param fileType文件类型（image;pdf;txt;audio;epub等等）
	 * @param fileGroup文件所属组(book;author;copyright;goods;news等等)
	 * @return saveFilePath 保存到目标文件的路径(路径+名称)
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	protected static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	public static String saveFiles(MultipartFile file, String fileType,String fileGroup) throws IllegalStateException, IOException {
		String finalPath = "";
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			logger.debug("获取的图书名称是" + fileName);
			String basePath = Global.getConfig("web.upload.path");
			logger.debug("获取的基础路径是--====" + basePath);
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String dateFormat = simpleDateFormat.format(date);
			String saveFileName = IdGen.uuid()+ fileName.substring(fileName.lastIndexOf('.'));
			logger.debug("获取的图书名称是" + saveFileName);
			String saveFilePath = path(basePath + "/" + fileType + "/"+ fileGroup + "/" + dateFormat);
			logger.debug("转换完格式的路径是======" + saveFilePath);
			boolean tag = createDirectory(saveFilePath);
			logger.debug("创建目录的结果是" + tag);
			File targetFile = new File(saveFilePath, saveFileName);
			file.transferTo(targetFile);
			finalPath = saveFilePath + "/" + saveFileName;
			logger.debug("获取的基础路径是" + basePath);
			return "/" + fileType + "/" + fileGroup + "/" + dateFormat + "/"+ saveFileName;
		}
		return finalPath;
	}

	/**
	 * 验证文件大小
	 * @param file
	 * @param fileType
	 * @return
	 */
	public static boolean checkFilesSize(File file, String fileType) {
		String imageFileSize = Global.getConfig("web.upload.file.image.size");
		String pdfFileSize = Global.getConfig("web.upload.file.pdf.size");
		String epubFileSize = Global.getConfig("web.upload.file.epub.size");
		String txtFileSize = Global.getConfig("web.upload.file.txt.size");
		String excelFileSize = Global.getConfig("web.upload.file.excel.size");
		if ("image".equals(fileType)&& FileSizeHelper.getHumanReadableFileSize(file.length()).compareTo(imageFileSize) > 0) {
			return false;
		} else if ("pdf".equals(fileType)&& FileSizeHelper.getHumanReadableFileSize(file.length()).compareTo(pdfFileSize) > 0) {
			return false;
		} else if ("epub".equals(fileType)&& FileSizeHelper.getHumanReadableFileSize(file.length()).compareTo(epubFileSize) > 0) {
			return false;
		} else if ("txt".equals(fileType)&& FileSizeHelper.getHumanReadableFileSize(file.length()).compareTo(txtFileSize) > 0) {
			return false;
		} else if ("excel".equals(fileType)&& FileSizeHelper.getHumanReadableFileSize(file.length()).compareTo(excelFileSize) > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 文件大小转换b--MB
	 * @param fileSize
	 * @param formatType
	 * (B,KB,MB,GB)
	 * @return
	 */
	public static double getFormatByFileSize(long fileSize, String formatType) {
		double resultSize = 0.0;
		int sizeStandard = 0;
		if ("B".equals(formatType)) {
			sizeStandard = 1;
		} else if ("KB".equals(formatType)) {
			sizeStandard = 1024;
		} else if ("MB".equals(formatType)) {
			sizeStandard = 1024 * 1024;
		} else if ("GB".equals(formatType)) {
			sizeStandard = 1024 * 1024 * 1024;
		} else {
			sizeStandard = 1;
		}
		BigDecimal bdFilesize = new BigDecimal(fileSize);
		BigDecimal formatSize = new BigDecimal(sizeStandard);
		resultSize = bdFilesize.divide(formatSize, 2, BigDecimal.ROUND_UP).doubleValue();
		return resultSize;
	}
}
