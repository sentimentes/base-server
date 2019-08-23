<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/echarts.jsp"%>
	
	
	<html>
	
<head>

	
</head>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<body>

	<form:form id="searchForm" modelAttribute="log" action="${ctx}/sys/log/index" method="post" class="breadcrumb form-search">
		<div>
			<label>操作菜单：</label><input id="title" name="title" type="text" maxlength="50" class="input-mini" value="${log.title}"/>
			
			<label>登录账号：</label><input id="createBy.loginName" name="createBy.loginName" type="text" maxlength="50" class="input-mini" value="${log.createBy.loginName}"/>
			<label>URI：</label><input id="requestUri" name="requestUri" type="text" maxlength="50" class="input-mini" value="${log.requestUri}"/>
		</div><div style="margin-top:8px;">
			<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;&nbsp;
				
				<label>排序类型：&nbsp;</label>
				<form:select path="sortType" style="width:90px;">

					<form:options items="${fns:getDictList('sort_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			&nbsp;<label for="exception"><input id="exception" name="exception" type="checkbox"${log.exception eq '1'?' checked':''} value="1"/>只查询异常信息</label>
			&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		</div>
	
	</form:form>
	
</body>

</html>
	<div id="line_normal"  class="main000"></div>
	<echarts:bar 
	  	id="line_normal"
		title="日志报表++++++++++日期：${timeSlot}" 
		subtitle="日志报表数据对比曲线"
		xAxisData="${xAxisData}" 
		yAxisData="${yAxisData}" 
		xAxisName="日志类型"
		yAxisName="概率（1）" 
		/>
