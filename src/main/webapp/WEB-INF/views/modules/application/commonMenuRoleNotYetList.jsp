<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>未分配菜单角色</title>
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
		function selectAll(){
			var a = document.getElementsByTagName("input");
			if(a[0].checked){
			for(var i = 0;i<a.length;i++){
			if(a[i].type == "checkbox") a[i].checked = false;
			}
			}
			else{
			for(var i = 0;i<a.length;i++){
			if(a[i].type == "checkbox") a[i].checked = true;
			}
			}
			}

           function unselecttAll(){
	         var a = document.getElementsByTagName("input");
	        if(a[0].checked){
	        for(var i = 0;i<a.length;i++){
	         if(a[i].type == "checkbox") a[i].checked = false;
	        }
	        }
	       else{
	      for(var i = 0;i<a.length;i++){
	        if(a[i].type == "checkbox") a[i].checked = true;
             	}
	         }
	       }
           function check(){
	            var list = document.getElementsByTagName("input");
	              for(var i =0;i<list.length;i++){
	               if(list[i].type == "checkbox" && list[i].checked)
	               break;
	            }
	          if(i==list.length){
	             alert("请选择数据");
	              return false;
	            }
	          return true;
              }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/application/commonRoleMenu/notYeList">未分配菜单列表</a></li>
		<shiro:hasPermission name="application:commonRoleMenu:edit"><li><a href="${ctx}/application/commonRoleMenu/list">已经分配菜单角色</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="commonMenu" action="${ctx}/application/commonRoleMenu/notYeList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>菜单名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	
	</form:form>
	<sys:message content="${message}"/>
		<form:form id="inputForm" modelAttribute="commonRoleMenu" action="${ctx}/application/commonRoleMenu/save" method="post" class="form-horizontal" >
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr><th><a href="#" onclick="selectAll()">全选</a></th>
				<th>菜单名称</th>
				<th>菜单类型</th>
			 
				 
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="commonMenu">
			<tr><td> <form:checkbox path="menuIds" value="${commonMenu.id}"/>  </td>
				<td> ${commonMenu.name}</td>
			 <td> ${fns:getDictLabel(commonMenu.menuType, 'menuType', '无')}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="form-actions">
			<shiro:hasPermission name="application:commonRoleMenu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
		</div> 
		</form:form>
	<div class="pagination">${page}</div>

	
</body>
</html>