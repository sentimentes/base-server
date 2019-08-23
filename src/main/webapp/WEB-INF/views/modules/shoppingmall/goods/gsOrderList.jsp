<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
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
		<li class="active"><a href="${ctx}/shoppingmall/goods/gsOrder/">订单列表</a></li>
	<%-- 	<shiro:hasPermission name="shoppingmall:goods:gsOrder:edit"><li><a href="${ctx}/shoppingmall/goods/gsOrder/form">订单添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="gsOrder" action="${ctx}/shoppingmall/goods/gsOrder/" method="post" class="breadcrumb form-search">
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
				<th>数量</th>
				<th>参数</th>
				<th>地址</th>
				<th>物流</th>
				<th>价格</th>
				<th>收件人</th>
				<th>电话号码</th>
				<th>状态</th>
				<th>创建时间</th>
				<shiro:hasPermission name="shoppingmall:goods:gsOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
				<c:forEach items="${page.list}" var="gsOrder" varStatus="i">
			<tr>
				<td style="width:50px;text-align:center;">${i.index+1}</td>
				<td>
					${gsOrder.gsName}
				</td>
				<td>
					${gsOrder.gsNumber}
				</td>
				<td>
					${gsOrder.parameter}
				</td>
				<td>
					${gsOrder.addressName}
				</td>
				<td>
					${gsOrder.logisticsName}
				</td>
				
				<td>
					${gsOrder.price}
				</td>
				<td>
					${gsOrder.pepole}
				</td>
				<td>
					${gsOrder.phone}
				</td>
				<td>
					<c:if test="${gsOrder.upDownShelf == 1}">
						已付款
					</c:if>
					<c:if test="${gsOrder.upDownShelf == 0}">
						未付款
					</c:if>
					<c:if test="${gsOrder.upDownShelf == 2}">
						发货中
					</c:if>
					<c:if test="${gsOrder.upDownShelf == 3}">
						已收货
					</c:if>
				</td>
				 <td>
					<fmt:formatDate value="${gsOrder.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> 
				<shiro:hasPermission name="shoppingmall:goods:gsOrder:edit"><td>
    				<a href="${ctx}/shoppingmall/goods/gsOrder/form?id=${gsOrder.id}">修改</a>
    				<c:if test="${gsOrder.upDownShelf == 1}">
    				<a href="${ctx}/shoppingmall/goods/gsOrder/takeh?id=${gsOrder.id}">发货</a>
    				</c:if>
					<a href="${ctx}/shoppingmall/goods/gsOrder/delete?id=${gsOrder.id}" onclick="return confirmx('确认要删除该订单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>