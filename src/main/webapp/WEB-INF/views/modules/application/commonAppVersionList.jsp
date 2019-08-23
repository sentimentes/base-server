<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>版本管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/application/commonAppVersion/">版本管理列表</a></li>
		<li><a href="${ctx}/application/commonAppVersion/form">版本管理添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="commonAppVersion" action="${ctx}/application/commonAppVersion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align:center;">序号</th>
				<th>app名称</th>
				<th>所属机构</th>
				<th>app类型</th>
				<th>下载地址</th>
				<th>版本号</th>
				<th>介绍</th>
				<th>创建日期</th>
				<shiro:hasPermission name="application:commonAppVersion:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="commonAppVersion" varStatus="i">
			<tr>
				<td style="width:50px;text-align:center;">${i.index+1}</td>
				<td>${commonAppVersion.appName}</td>
				<td>${commonAppVersion.office.name}</td>
				<td>${fns:getDictLabel(commonAppVersion.appType, 'app_type', '无')}</td>
				<td>${commonAppVersion.downUrl}</td>
				<td>${commonAppVersion.version}</td>
				<td>${commonAppVersion.info}</td>
				<td>
					<fmt:formatDate value="${commonAppVersion.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				
				<shiro:hasPermission name="application:commonAppVersion:edit"><td>
    				<a href="${ctx}/application/commonAppVersion/form?id=${commonAppVersion.id}">修改</a>
					<a href="${ctx}/application/commonAppVersion/delete?id=${commonAppVersion.id}" onclick="return confirmx('确认要删除该版本管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>