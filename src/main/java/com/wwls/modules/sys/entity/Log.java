package com.wwls.modules.sys.entity;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.wwls.common.persistence.DataEntity;
import com.wwls.common.utils.StringUtils;

/**
 * 日志Entity
 * @version 2014-8-19
 */
public class Log extends DataEntity<Log> {

	private static final long serialVersionUID = 1L;
	private String type; 		// 日志类型（1：接入日志；2：错误日志）
	private String title;		// 日志标题
	private String remoteAddr; 	// 操作用户的IP地址
	private String requestUri; 	// 操作的URI
	private String method; 		// 操作的方式
	private String params; 		// 操作提交的数据
	private String userAgent;	// 操作用户代理信息
	private String exception; 	// 异常信息
	
	private Date beginDate;		// 开始日期
	private Date endDate;		// 结束日期
	
	// 日志类型（1：接入日志；2：错误日志）
	public static final String TYPE_ACCESS = "1";
	public static final String TYPE_EXCEPTION = "2";
	private String abnormalType;//异常类型（1登录异常2越权访问3一般异常4ip异常）
	private String sortType;//排序类型（1升序排列2、降序）
	private int loginCount;//登录异常统计
	private int yqfwCount;//越权访问统计
	private int ybycCount;//一般异常统计
	private int ipCount;//ip异常访问
	private int zcCount;//正常访问
	private int totalCount;//总日志数
	
	//2017年7月31号新增字段
	private String resultType;//结果类型（1成功2失败）
	private String infoDescribe;//信息描述
	private String logType;//日志类型
	
	private String objectId;//对象ID
	private String updateBegin;//修改前数据
	private String updateAfter;//修改后数据
	
	
	
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getUpdateBegin() {
		return updateBegin;
	}

	public void setUpdateBegin(String updateBegin) {
		this.updateBegin = updateBegin;
	}

	public String getUpdateAfter() {
		return updateAfter;
	}

	public void setUpdateAfter(String updateAfter) {
		this.updateAfter = updateAfter;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getInfoDescribe() {
		return infoDescribe;
	}

	public void setInfoDescribe(String infoDescribe) {
		this.infoDescribe = infoDescribe;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public int getYqfwCount() {
		return yqfwCount;
	}

	public void setYqfwCount(int yqfwCount) {
		this.yqfwCount = yqfwCount;
	}

	public int getYbycCount() {
		return ybycCount;
	}

	public void setYbycCount(int ybycCount) {
		this.ybycCount = ybycCount;
	}

	public int getIpCount() {
		return ipCount;
	}

	public void setIpCount(int ipCount) {
		this.ipCount = ipCount;
	}

	public int getZcCount() {
		return zcCount;
	}

	public void setZcCount(int zcCount) {
		this.zcCount = zcCount;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getAbnormalType() {
		return abnormalType;
	}

	public void setAbnormalType(String abnormalType) {
		this.abnormalType = abnormalType;
	}

	public Log(){
		super();
	}
	
	public Log(String id){
		super(id);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 设置请求参数
	 * @param paramMap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParams(Map paramMap){
		if (paramMap == null){
			return;
		}
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
			params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
			String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
			params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
		}
		this.params = params.toString();
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}