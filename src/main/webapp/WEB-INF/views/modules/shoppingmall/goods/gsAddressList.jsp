<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员地址管理</title>
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
		<li class="active"><a href="${ctx}/shoppingmall/goods/gsAddress/">会员地址列表</a></li>
		<shiro:hasPermission name="shoppingmall:goods:gsAddress:edit"><li><a href="${ctx}/shoppingmall/goods/gsAddress/form">会员地址添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gsAddress" action="${ctx}/shoppingmall/goods/gsAddress/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>地址：</label>
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
				<th>地址</th>
				<th>排序</th>
				<th>上下架</th>
				<shiro:hasPermission name="shoppingmall:goods:gsAddress:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gsAddress">
			<tr>
				<td><a href="${ctx}/shoppingmall/goods/gsAddress/form?id=${gsAddress.id}">
					${gsAddress.name}
				</a></td>
				<td>${gsAddress.sort}</td>
				<td>
				<c:if test="${gsAddress.upDownShelf == 1}">
						<span style="color: red;">上架<span>
					</c:if>
					<c:if test="${gsAddress.upDownShelf == 0}">
						下架
					</c:if>
				</td>
				<shiro:hasPermission name="shoppingmall:goods:gsAddress:edit"><td>
    				<a href="${ctx}/shoppingmall/goods/gsAddress/form?id=${gsAddress.id}">修改</a>
					<a href="${ctx}/shoppingmall/goods/gsAddress/delete?id=${gsAddress.id}" onclick="return confirmx('确认要删除该会员地址吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>