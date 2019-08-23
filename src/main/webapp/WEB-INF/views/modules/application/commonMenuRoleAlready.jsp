<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已经分配菜单角色</title>
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
		<li class="active"><a href="${ctx}/application/commonRoleMenu/list">已经分配菜单角色</a></li>
		<shiro:hasPermission name="application:commonRoleMenu:edit"><li><a href="${ctx}/application/commonRoleMenu/notYeList">未分配菜单角色</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="commonRoleMenu" action="${ctx}/application/commonRoleMenu/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>菜单名称：</label>
				<form:input path="commonMenu.name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			   <th>	菜单名称</th>
		  
				<th>备注</th>
				<shiro:hasPermission name="application:commonRoleMenu:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="commonRoleMenu">
			<tr>
				<%-- <td><a href="${ctx}/application/commonRoleMenu/form?id=${commonRoleMenu.id}"> --%>
				 <td>${commonRoleMenu.commonMenu.name}
				</td>
			 
				<td>${commonRoleMenu.remark}</td>
				<shiro:hasPermission name="application:commonRoleMenu:edit"><td>
    				<a href="${ctx}/application/commonRoleMenu/form?id=${commonRoleMenu.id}">修改</a>
					<a href="${ctx}/application/commonRoleMenu/delete?id=${commonRoleMenu.id}" onclick="return confirmx('确认要删除该菜单角色关联管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>