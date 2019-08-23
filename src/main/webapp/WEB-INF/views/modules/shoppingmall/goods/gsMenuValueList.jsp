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
			$("#changes").change(function(){
				$("#searchForm").attr("action", "${ctx}/shoppingmall/goods/gsMenuValue/list");
		    	$("#searchForm").submit();
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
		    	$("#listForm").attr("action", "${ctx}/shoppingmall/goods/gsMenuValue/updateSort");
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
		<li class="active"><a href="${ctx}/shoppingmall/goods/gsMenuValue/">商品分类细分值列表</a></li>
		<shiro:hasPermission name="shoppingmall:goods:gsMenuValue:edit"><li><a href="${ctx}/shoppingmall/goods/gsMenuValue/form">商品分类细分值添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gsMenuValue" action="${ctx}/shoppingmall/goods/gsMenuValue/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>分类名称：</label>
				<form:select path="gsMenuId" class="input-medium" id="changes">
				<form:option value="" label="请选择"/>
				<form:options items="${gsMenuList}" itemLabel="spType1" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>名称：</label>
				<form:select path="gsMenuSmallId" class="input-medium">
					<form:option value="" label="请选择"/>
				<form:options items="${gsMenuSmallList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<form:form id="listForm" modelAttribute="gsActivity"  action="${ctx}/shoppingmall/goods/gsMenuValue/upDownShelf" method="post" class="form-horizontal" >
	<shiro:hasPermission name="shoppingmall:goods:gsMenuSmall:edit">
		<div>
			<input id="btnSubmit2" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>&nbsp;&nbsp;&nbsp;&nbsp; 
			<input id="btnSubmit1" class="btn btn-primary"  type="submit" value="上下架功能"/>&nbsp;&nbsp;
			<font color="red">提示：使用排序操作时，需要选中才能才能进行排序！&nbsp;&nbsp;&nbsp;&nbsp;使用上架功能批量操作时，若原来是上架状态的则下架，原来下架的变上架，请谨慎操作！</font>
		</div>
	</shiro:hasPermission><br/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:50px;"><a href="#" id="allChecked" onClick="selectAllDels()">全选/取消</a></th>
				<th>序号</th>
				<th>分类</th>
				<th>名称</th>
				<th>值</th>
				<th>上下架</th>
				<th>排序</th>
				<th>商品查看</th>
				<shiro:hasPermission name="shoppingmall:goods:gsMenuValue:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gsMenuValue" varStatus="i">
			<tr>
				<td style="width:30px;text-align:center;">
					<input name="idList" value="${gsMenuValue.id}" type="checkbox"/>
				</td>
				<td style="width:50px;text-align:center;">${i.index+1}</td>
				<td>
					${gsMenuValue.gsMenuName}
				</td>
				<td>
					${gsMenuValue.gsMenuSmallName}
				</td>
				<td>
					${gsMenuValue.name}
				</td>
				<td>
				<c:if test="${gsMenuValue.upDownShelf == 1}">
						<span style="color: red;">上架<span>
					</c:if>
					<c:if test="${gsMenuValue.upDownShelf == 0}">
						下架
					</c:if>
				</td>
				<td style="text-align:center;">
						<shiro:hasPermission name="shoppingmall:goods:gsMenuValue:edit">
							<input type="hidden" name="ids" value="${gsMenuValue.id}"/>
							<input name="sorts" type="text" value="${gsMenuValue.sort}" style="width:50px;margin:0;padding:0;text-align:center;" class="digits required">
						</shiro:hasPermission>
						<shiro:lacksPermission name="shoppingmall:goods:gsMenuValue:edit">
							${gsMenuValue.sort}
						</shiro:lacksPermission>
				</td>
				<td>
					<a href="${ctx}/shoppingmall/goods/gsType/list?vaId=${gsMenuValue.id}">查看</a>
				</td>
				<shiro:hasPermission name="shoppingmall:goods:gsMenuValue:edit"><td>
					<a href="${ctx}/shoppingmall/goods/gsMenuValue/form2?id=${gsMenuValue.id}">添加商品于该值</a>
    				<a href="${ctx}/shoppingmall/goods/gsMenuValue/form?id=${gsMenuValue.id}&gsMenuId=${gsMenuValue.gsMenuId}&gsMenuSmallId=${gsMenuValue.gsMenuSmallId}">修改</a>
					<a href="${ctx}/shoppingmall/goods/gsMenuValue/delete?id=${gsMenuValue.id}" onclick="return confirmx('确认要删除该商品分类细分值吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
		</form:form>
</body>
</html>