<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li class="active"><a href="${ctx}/sys/log/">日志列表</a></li> --%>
<!-- 	</ul> -->
	<form:form id="searchForm" modelAttribute="log" action="${ctx}/sys/log/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<label>操作菜单：</label><input id="title" name="title" type="text" maxlength="50" class="input-mini" value="${log.title}"/>
			
			<label>登录账号：</label><input id="createBy.loginName" name="createBy.loginName" type="text" maxlength="50" class="input-mini" value="${log.createBy.loginName}"/>
			<label>URI：</label><input id="requestUri" name="requestUri" type="text" maxlength="50" class="input-mini" value="${log.requestUri}"/>
		</div><div style="margin-top:8px;">
			<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;&nbsp;
				
				<label>日志类型：&nbsp;</label>
				<form:select path="abnormalType" style="width:90px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('abnormal_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				
				<label>分类：&nbsp;</label>
				<form:select path="logType" style="width:90px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('logType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				
				<label>排序类型：&nbsp;</label>
				<form:select path="sortType" style="width:90px;">
					<form:option value="" label=" "/>
					<form:options items="${fns:getDictList('sort_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			&nbsp;<label for="exception"><input id="exception" name="exception" type="checkbox"${log.exception eq '1'?' checked':''} value="1"/>只查询异常信息</label>
			&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		</div>
		<%-- <label>分类：</label>
				<form:select path="abnormalType" class="input-xlarge">
					<form:option value=" "></form:option>
					<form:options items="${fns:getDictList('abnormalType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>--%>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
		<th>操作菜单</th>
		<th>操作用户名称</th>
		<th>操作登录账户</th>
		<th>所在公司</th>
		<th>所在部门</th>
		<th>类型</th>
		<th>分类</th>
		<th>结果</th>
		<th>提交方式</th>
		<th>操作者IP</th>
		<th>操作时间</th>
		<th>链接</th>
		<th>修改前数据</th>
		<th>修改后数据</th>
		</thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<c:forEach items="${page.list}" var="log">
			<tr>
				<td>${log.title}</td>
				<td>${log.createBy.name}</td>
				<td>${log.createBy.loginName}</td>
				<td>${log.createBy.company.name}</td>
				<td>${log.createBy.office.name}</td>
				<td>${fns:getDictLabel(log.abnormalType, 'abnormal_type', '无')}</td>
				<td>${fns:getDictLabel(log.logType, 'logType', '无')}</td>
				<td>${fns:getDictLabel(log.resultType, 'resultType', '无')}</td>
				<td>${log.method}</td>
				<td>${log.remoteAddr}</td>
				<td>${log.requestUri}</td>
				<td><fmt:formatDate value="${log.createDate}" type="both"/></td>
				
				<td>${log.updateBegin}</td>
				<td>${log.updateAfter}</td>
			</tr>
			<!--<c:if test="${not empty log.exception}">--><tr>
				<td colspan="8" style="word-wrap:break-word;word-break:break-all;">
 					用户代理: ${log.userAgent}<br/> 
 					提交参数: ${fns:escapeHtml(log.params)} <br/> 
					异常信息: <br/>
					${fn:replace(fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>'), strTab, '&nbsp; &nbsp; ')}</td>
			</tr><!--</c:if>-->
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>