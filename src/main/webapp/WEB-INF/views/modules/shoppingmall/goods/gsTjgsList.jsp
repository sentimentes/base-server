<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>特价商品绑定管理</title>
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
		<li class="active"><a href="#">特价商品绑定列表</a></li>
	<%-- 	<shiro:hasPermission name="shoppingmall:goods:gsTjgs:edit"><li><a href="${ctx}/shoppingmall/goods/gsTjgs/form">特价商品绑定添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="gsTjgs" action="${ctx}/shoppingmall/goods/gsTjgs/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="shoppingmall:goods:gsTjgs:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gsTjgs"  varStatus="i">
			<tr>
				<td style="width:50px;text-align:center;">${i.index+1}</td>
				<td>
					${gsTjgs.gsName}
				</td>
				<shiro:hasPermission name="shoppingmall:goods:gsTjgs:edit"><td>
    			<%-- 	<a href="${ctx}/shoppingmall/goods/gsTjgs/form?id=${gsTjgs.id}">修改</a> --%>
					<a href="${ctx}/shoppingmall/goods/gsTjgs/delete?id=${gsTjgs.id}&tjgsId=${gsTjgs.tjgsId}" onclick="return confirmx('确认要删除该特价商品绑定吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>