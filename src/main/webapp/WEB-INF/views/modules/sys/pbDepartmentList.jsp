<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/office/pbDepartmentList?id=${office.id}&parentIds=${office.parentIds}">行政机构列表</a></li>
		<shiro:hasPermission name="sys:office:edit">
		<li><a href="${ctx}/sys/office/pbDepartmentForm?parent.id=${office.id}">行政机构添加</a>
		</li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
		<th>机构名称</th>
	 	<th>添加时间</th>
		<shiro:hasPermission name="sys:office:edit">
		<th>操作</th>
		</shiro:hasPermission></tr></thead>
	<c:forEach items="${list}" var="office">
	<tr><td>${office.name }</td>
	<td><fmt:formatDate value="${office.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	<td>&nbsp;</td>
	</tr>
	
	</c:forEach>	 
	</table>
	
</body>
</html>