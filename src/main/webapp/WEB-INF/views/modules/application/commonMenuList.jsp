<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用菜单管理</title>
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
		<li class="active"><a href="${ctx}/application/commonMenu/">应用菜单列表</a></li>
		<shiro:hasPermission name="application:commonMenu:edit"><li><a href="${ctx}/application/commonMenu/form">应用菜单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="commonMenu" action="${ctx}/application/commonMenu/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
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
				<th>名称</th>
				<th>排序</th>
				<th>对外接口地址</th>
				<th>链接</th>
				<th>菜单类型</th>	
				<th>是否需要登陆</th>
				<shiro:hasPermission name="application:commonMenu:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="commonMenu">
			<tr>
				<td style = "width:200px;">${commonMenu.name}</td>
				<td>${commonMenu.sort}</td>
				<td>${commonMenu.api}</td>
				<td>${commonMenu.href}</td>
				<td>${fns:getDictLabel(commonMenu.menuType, 'menuType', '无')}</td>
				<td>${fns:getDictLabel(commonMenu.isShow, 'yes_no', '无')}</td>
				<shiro:hasPermission name="application:commonMenu:edit"><td style = "width:200px;">
    				<a href="${ctx}/application/commonMenu/form?id=${commonMenu.id}">修改</a>
					<a href="${ctx}/application/commonMenu/delete?id=${commonMenu.id}" onclick="return confirmx('确认要删除该应用菜单吗？', this.href)">删除</a>
					<a href="${ctx}/common/parameter/commonParameter/?menuId=${commonMenu.id}"><span class="help-inline"><font color="red">接口参数管理</font></span></a>				
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>