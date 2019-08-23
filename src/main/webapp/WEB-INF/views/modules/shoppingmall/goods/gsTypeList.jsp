<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品分类管理管理</title>
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
		<li class="active"><a href="${ctx}/shoppingmall/goods/gsType/list?vaId=${vaId}">商品分类管理列表</a></li>
		<%-- <shiro:hasPermission name="shoppingmall:goods:gsType:edit"><li><a href="${ctx}/shoppingmall/goods/gsType/form">商品分类管理添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="gsType" action="${ctx}/shoppingmall/goods/gsType/list?vaId=${vaId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品名称：</label>
				<form:input path="gsName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>商品名称</th>
				<shiro:hasPermission name="shoppingmall:goods:gsType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gsType" varStatus="i">
			<tr>
				<td style="width:50px;text-align:center;">${i.index+1}</td>
				<td>
					${gsType.gsName}
				</td>
				<shiro:hasPermission name="shoppingmall:goods:gsType:edit"><td>
    				<%-- <a href="${ctx}/shoppingmall/goods/gsType/form?id=${gsType.id}">修改</a> --%>
					<a href="${ctx}/shoppingmall/goods/gsType/delete?id=${gsType.id}&vaId=${gsType.vaId}" onclick="return confirmx('确认要删除该商品分类管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>