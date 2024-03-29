<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>特价商品类型管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#listForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$("#btnSubmit1").attr('disabled', "true"); //使按钮不能被点击
					$("#btnSubmit2").attr('disabled', "true"); //使按钮不能被点击
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		 function selectAllDels() {
				var allCheckBoxs = document.getElementsByName("idList");
				var desc = document.getElementById("allChecked");
				var selectOrUnselect = false;
				for (var i = 0; i < allCheckBoxs.length; i++) {
					if (allCheckBoxs[i].checked) {
						selectOrUnselect = true;
						break;
					}
				}
				if (selectOrUnselect) {
					_allUnchecked(allCheckBoxs);
				} else {
					_allchecked(allCheckBoxs);
				}
			}
			function _allchecked(allCheckBoxs) {
				for (var i = 0; i < allCheckBoxs.length; i++) {
					allCheckBoxs[i].checked = true;
				}
			}
			function _allUnchecked(allCheckBoxs) {
				for (var i = 0; i < allCheckBoxs.length; i++) {
					allCheckBoxs[i].checked = false;
				}
			}
			
			function updateSort() {
		    	$("#listForm").attr("action", "${ctx}/shoppingmall/goods/tjgsType/updateSort");
		    	$("#listForm").submit();
			}
	</script>
	<style type="text/css">
		.table tr td{
			text-overflow: ellipsis;
			-moz-text-overflow:ellipsis;
			overflow: hidden;
			white-space: nowrap;
			table-layout: fixed;
   			max-width: 100px;
   			text-align:center;
		}
		.table tr th{
   			text-align:center;
		}
	</style>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/shoppingmall/goods/tjgsType/">特价商品类型列表</a></li>
		<shiro:hasPermission name="shoppingmall:goods:tjgsType:edit"><li><a href="${ctx}/shoppingmall/goods/tjgsType/form">特价商品类型添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tjgsType" action="${ctx}/shoppingmall/goods/tjgsType/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类型名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form:form id="listForm" modelAttribute="tjgsType"  action="${ctx}/shoppingmall/goods/tjgsType/upDownShelf" method="post" class="form-horizontal" >
	<shiro:hasPermission name="shoppingmall:goods:tjgsType:edit">
		<div>
			<input id="btnSubmit2" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>&nbsp;&nbsp;&nbsp;&nbsp; 
			<input id="btnSubmit1" class="btn btn-primary"  type="submit" value="上下架功能"/>&nbsp;&nbsp;
			<font color="red">提示：使用排序操作时，需要选中才能才能进行排序！&nbsp;&nbsp;&nbsp;&nbsp;使用上架功能批量操作时，若原来是上架状态的则下架，原来下架的变上架，请谨慎操作！</font>
		</div>
	</shiro:hasPermission><br/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				
				<th style="text-align:center;width:50px;"><a href="#" id="allChecked" onClick="selectAllDels()">全选/取消</a></th>
				<th style="text-align:center;">序号</th>
				<th>类型名称</th>
				<th>上下架</th>
				<th>排序</th>	
				<th>商品查看</th>	
				<shiro:hasPermission name="shoppingmall:goods:tjgsType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tjgsType" varStatus="i">
			<tr>
				<td style="width:30px;text-align:center;">
					<input name="idList" value="${tjgsType.id}" type="checkbox"/>
				</td>
				<td style="width:50px;text-align:center;">${i.index+1}</td>
				<td><a href="${ctx}/shoppingmall/goods/tjgsType/form?id=${tjgsType.id}">
					${tjgsType.name}
				</a></td>
				<td>
					<c:if test="${tjgsType.upDownShelf == 1}">
					<span style="color: red;">上架<span>
					</c:if>
					<c:if test="${tjgsType.upDownShelf == 0}">
						下架
					</c:if>
				</td>
				<td style="text-align:center;">
						<shiro:hasPermission name="shoppingmall:goods:tjgsType:edit">
							<input type="hidden" name="ids" value="${tjgsType.id}"/>
							<input name="sorts" type="text" value="${tjgsType.sort}" style="width:50px;margin:0;padding:0;text-align:center;" class="digits required">
						</shiro:hasPermission>
						<shiro:lacksPermission name="shoppingmall:goods:tjgsType:edit">
							${tjgsType.sort}
						</shiro:lacksPermission>
				</td>
				<td>
					<a href="${ctx}/shoppingmall/goods/gsTjgs/list?tjgsId=${tjgsType.id}">查看</a>
				</td>
				<shiro:hasPermission name="shoppingmall:goods:tjgsType:edit"><td>
					<a href="${ctx}/shoppingmall/goods/tjgsType/form2?id=${tjgsType.id}">添加商品</a>
    				<a href="${ctx}/shoppingmall/goods/tjgsType/form?id=${tjgsType.id}">修改</a>
					<a href="${ctx}/shoppingmall/goods/tjgsType/delete?id=${tjgsType.id}" onclick="return confirmx('确认要删除该特价商品类型管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</form:form>
</body>
</html>