<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已分配订单角色管理</title>
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
		<li class="active"><a href="${ctx}/application/commonAppRole/list">已分配的角色管理</a></li>
		<shiro:hasPermission name="application:commonAppRole:edit"><li><a href="${ctx}/application/commonAppRole/notYeList">未分配的角色列表</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="commonAppRole" action="${ctx}/application/commonAppRole/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>应用名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>角色名称</th>
				<th>备注</th>
				<shiro:hasPermission name="application:commonAppRole:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="commonAppRole">
			<tr>
				<td> ${commonAppRole.commonRole.name}</td>
			 
				<td> ${commonAppRole.remark}</td>
				<shiro:hasPermission name="application:commonAppRole:edit"><td>
    				<a href="${ctx}/application/commonAppRole/form?id=${commonAppRole.id}">修改</a>
					<a href="${ctx}/application/commonAppRole/delete?id=${commonAppRole.id}" onclick="return confirmx('确认要删除该应用角色关联管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>