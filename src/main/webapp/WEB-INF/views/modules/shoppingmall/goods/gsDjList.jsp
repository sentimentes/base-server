<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>积分等级管理管理</title>
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
		<li class="active"><a href="${ctx}/shoppingmall/goods/gsDj/">积分等级管理列表</a></li>
		<shiro:hasPermission name="shoppingmall:goods:gsDj:edit"><li><a href="${ctx}/shoppingmall/goods/gsDj/form">积分等级管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gsDj" action="${ctx}/shoppingmall/goods/gsDj/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>等级名称：</label>
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
				<th style="text-align:center;">序号</th>
				<th>等级名称</th>
				<th>积分范围</th>
				<shiro:hasPermission name="shoppingmall:goods:gsDj:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gsDj" varStatus="i">
			<tr>
				<td style="width:50px;text-align:center;">${i.index+1}</td>
			
				<td><a href="${ctx}/shoppingmall/goods/gsDj/form?id=${gsDj.id}">		
					${gsDj.name}
				</a></td>
					<td>${gsDj.start}~${gsDj.end}</td>
				<shiro:hasPermission name="shoppingmall:goods:gsDj:edit"><td>
    				<a href="${ctx}/shoppingmall/goods/gsDj/form?id=${gsDj.id}">修改</a>
					<a href="${ctx}/shoppingmall/goods/gsDj/delete?id=${gsDj.id}" onclick="return confirmx('确认要删除该积分等级管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>