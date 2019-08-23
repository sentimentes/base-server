/**

 */
package com.wwls.modules.sys.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串校验工具
 */
public class JiaoYanUtils {
	
	
	/**
	 * 只留正整数
	 * @param int
	 * @return
	 */
	public static String numFilter(String str)throws PatternSyntaxException {
		String regex = "[^0-9]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	
	
	/**
	 * 验证手机号码
	 * @param mobiles
	 * @return
	 */
	public static boolean checkPhone(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 过滤一般字符串
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String stringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	
	/**
	 * 过滤邮箱字符串
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String emailFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!#$%^&*()+=|{}':;',\\[\\]<>/?~！#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	
	/**
	 * 校验链接地址
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static boolean checkUrl(String str) throws PatternSyntaxException {
		boolean flag = false;
		try {
			Pattern regex = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
			Matcher matcher = regex.matcher(str);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	
	/**
	 * 过滤地址字符串
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String urlFilter(String str) throws PatternSyntaxException {
		// 清除掉所有特殊字符
		String regEx = "[`~@!#$%^&*()+=|{}';',\\[\\]<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	/**
	 * 校验日期合法性
	 * @param date
	 * @return
	 */
	public static boolean isDate(String date) {
		/**
		 * 判断日期格式和范围
		 */
		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

		Pattern pat = Pattern.compile(rexp);

		Matcher mat = pat.matcher(date);

		boolean dateType = mat.matches();

		return dateType;
	}
	
	
	/**
	 * 过滤生日字符串
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String birthdayFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	
	/**
	   * 验证邮箱
	  * @param email
	   * @return
	   */
	  public static boolean checkEmail(String email){
	   boolean flag = false;
	   try{
	     String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	     Pattern regex = Pattern.compile(check);
	     Matcher matcher = regex.matcher(email);
	     flag = matcher.matches();
	    }catch(Exception e){
	     flag = false;
	    }
	   return flag;
	  }
	  
	  
	  /**
		 * 过滤登录名称字符串
		 * @param str
		 * @return
		 * @throws PatternSyntaxException
		 */
		public static String loginNameFilter(String str) throws PatternSyntaxException {
			// 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
			// 清除掉所有特殊字符
			String regEx = "[`~!@#$%^&*()=|{}':;',\\[\\].<>?~！@#￥%……&*（）——|{}【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			return m.replaceAll("").trim();
		}

}
