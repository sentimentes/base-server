<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理管理</title>
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
		<li class="active"><a href="${ctx}/shoppingmall/goods/gsUser/">用户管理列表</a></li>
		<shiro:hasPermission name="shoppingmall:goods:gsUser:edit"><li><a href="${ctx}/shoppingmall/goods/gsUser/form">用户管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gsUser" action="${ctx}/shoppingmall/goods/gsUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名字：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名字</th>
				<shiro:hasPermission name="shoppingmall:goods:gsUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gsUser">
			<tr>
				<td><a href="${ctx}/shoppingmall/goods/gsUser/form?id=${gsUser.id}">
					${gsUser.name}
				</a></td>
				<shiro:hasPermission name="shoppingmall:goods:gsUser:edit"><td>
    				<a href="${ctx}/shoppingmall/goods/gsUser/form?id=${gsUser.id}">修改</a>
					<a href="${ctx}/shoppingmall/goods/gsUser/delete?id=${gsUser.id}" onclick="return confirmx('确认要删除该用户管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>