<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用角色管理</title>
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
		<li class="active"><a href="${ctx}/application/commonRole/">应用角色列表</a></li>
		<shiro:hasPermission name="application:commonRole:edit"><li><a href="${ctx}/application/commonRole/form">应用角色添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="commonRole" action="${ctx}/application/commonRole/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>角色名称：</label>
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
				<th>标识</th>
				<th>备注</th>
				<shiro:hasPermission name="application:commonRole:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="commonRole">
			<tr>
				<td><a href="${ctx}/application/commonRole/form?id=${commonRole.id}">
					${commonRole.name}
				</a></td>
				<td>
				 ${commonRole.sn}
				</td>
			    <td>
				 ${commonRole.remark}
				</td>
				
			
				<shiro:hasPermission name="application:commonRole:edit"><td>
    				<a href="${ctx}/application/commonRole/form?id=${commonRole.id}">修改</a>
					<a href="${ctx}/application/commonRole/delete?id=${commonRole.id}" onclick="return confirmx('确认要删除该应用角色吗？', this.href)">删除</a>
					<a href="${ctx}/application/commonRoleMenu/index?roleId=${commonRole.id}">绑定菜单</a>
					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>