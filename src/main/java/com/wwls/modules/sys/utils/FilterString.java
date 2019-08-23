package com.wwls.modules.sys.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FilterString {

	/**
	 * 判断特殊字符 
	 * @Title : FilterStr 
	 * @Type : FilterString 
	 * @Description : 判断特殊字符 
	 * @param str 
	 * @return 
	 * @throws
	 * PatternSyntaxException
	 */
	public static String FilterStr(String str) throws PatternSyntaxException {
		/** * 特殊字符 */
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_]";
		/**
		 * Pattern p = Pattern.compile("a*b"); * Matcher m =
		 * p.matcher("aaaaab"); * boolean b = m.matches();
		 */
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		/**返回替换结果 */
		return mat.replaceAll("").trim();
	}

	/**
	 * 邮箱过滤
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String FilterEmail(String str) throws PatternSyntaxException {
		/** * 特殊字符 */
		String regEx = "[`~!#$%^&*()+=|{}':;',\\[\\]<>/?~！#￥%……&*（）——+|{}【】‘；：”“’。，、？_]";
		/**
		 * Pattern p = Pattern.compile("a*b"); * Matcher m =
		 * p.matcher("aaaaab"); * boolean b = m.matches();
		 */
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		/**返回替换结果 */
		return mat.replaceAll("").trim();
	}
	
	public static void main(String[] args) {
		
		/**测试字符串 */
		String totalStr = "~`<>?^&*()you@##%$$#^%^h^&&*&*()<>?ai@#@$~~`_+|dong?><:";
		/**打印测试字符串 */
		System.out.println("打印测试字符串:" + totalStr);
		/**调用过滤字符串的方法 */
		String filterStr = FilterStr(totalStr);
		/**打印过滤字符串 */
		System.out.println("打印过滤字符串:" + filterStr); 
	}

}
