<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品分类细分值管理</title>
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
		<li class="active"><a href="${ctx}/shoppingmall/goods/gsMenuValue/">商品分类细分值列表</a></li>
		<shiro:hasPermission name="shoppingmall:goods:gsMenuValue:edit"><li><a href="${ctx}/shoppingmall/goods/gsMenuValue/form">商品分类细分值添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gsMenuValue" action="${ctx}/shoppingmall/goods/gsMenuValue/form2?id=${id}" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>分类名称：</label>
				<form:select path="gsMenuId" class="input-medium" id="changes">
				<form:option value="" label="请选择"/>
				<form:options items="${gsMenuList}" itemLabel="spType1" itemValue="id" htmlEscape="false"/>
				</form:select>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form:form id="listForm" modelAttribute="gsActivity"  action="${ctx}/shoppingmall/goods/gsMenuValue/addsave?id=${id}" method="post" class="form-horizontal" >
	<input id="btnSubmit1" class="btn btn-primary" type="submit" value="提交" />&nbsp;&nbsp;&nbsp;&nbsp;
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align:center;width:50px;"><a href="#" id="allChecked" onClick="selectAllDels()">全选/取消</a></th>
				<th style="text-align:center;">序号</th>
				<th>名称</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${gsGoodslist}" var="gsGoods" varStatus="i">
			<tr>
				<td style="width:30px;text-align:center;">
					<input name="idList" value="${gsGoods.id}" type="checkbox"/>
				</td>
				<td style="width:50px;text-align:center;">${i.index+1}</td>
				<td><a href="${ctx}/shoppingmall/goods/gsGoods/form?id=${gsGoods.id}">
					${gsGoods.name}
				</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form:form>
</body>
</html>