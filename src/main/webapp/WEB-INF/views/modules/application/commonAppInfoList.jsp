<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用信息管理</title>
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
		<li class="active"><a href="${ctx}/application/commonAppInfo/">应用信息列表</a></li>
		<shiro:hasPermission name="application:commonAppInfo:edit">
		<li><a href="${ctx}/application/commonAppInfo/form">应用信息添加</a></li>
		<li><a href="${ctx}/application/commonAppInfo/commonForm">工会党建应用信息添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="commonAppInfo" action="${ctx}/application/commonAppInfo/" method="post" class="breadcrumb form-search">
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
			<tr> <th>客户端Id</th>
				<th>应用名称</th>
				<th>应用版本</th>
			    <th>应用秘钥</th>
			    <th>所属公司</th>
				<th>是否启动积分</th>
				<th>修改时间</th>
				<shiro:hasPermission name="application:commonAppInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="commonAppInfo">
			<tr><td>
				
				${commonAppInfo.id}
				
				</td>
				<td><a href="${ctx}/application/commonAppInfo/form?id=${commonAppInfo.id}">
					${commonAppInfo.name}
				</a></td>
				<td>
				
				${commonAppInfo.keyVersion}
				
				</td>
				<td>
				
				${commonAppInfo.secretKey }
				
				</td>
				<td>${commonAppInfo.shopId}</td>
				<td>
				${ commonAppInfo.isIntegral}
				</td>
				<td>
					<fmt:formatDate value="${commonAppInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="application:commonAppInfo:edit"><td>
    				<a href="${ctx}/application/commonAppInfo/form?id=${commonAppInfo.id}">修改</a>
    				<a href="${ctx}/application/commonAppInfo/commonForm?id=${commonAppInfo.id}">党建工会修改</a>
					<a href="${ctx}/application/commonAppInfo/delete?id=${commonAppInfo.id}" onclick="return confirmx('确认要删除该应用信息吗？', this.href)">删除</a>
				    <a href="${ctx}/application/commonAppRole/index?appId=${commonAppInfo.id}">分配角色</a>
				   <%--   <a href="${ctx}/application/commonRole/role">分配角色1</a> --%>
					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>