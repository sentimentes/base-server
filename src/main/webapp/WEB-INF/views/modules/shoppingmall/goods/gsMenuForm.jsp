<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品菜单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shoppingmall/goods/gsMenu/">商品菜单列表</a></li>
		<li class="active"><a href="${ctx}/shoppingmall/goods/gsMenu/form?id=${gsMenu.id}">商品菜单<shiro:hasPermission name="shoppingmall:goods:gsMenu:edit">${not empty gsMenu.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shoppingmall:goods:gsMenu:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="gsMenu" action="${ctx}/shoppingmall/goods/gsMenu/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">分类名称：</label>
			<div class="controls">
				<form:input path="spType1" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>必填字段</span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">二级分类：</label>
			<div class="controls">
				<form:input path="spType2" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">机构id：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${gsMenu.office.id}" labelName="office.name" labelValue="${gsMenu.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>必填字段</span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">上下架：</label>
			<div class="controls">
				<form:input path="upDownShelf" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="shoppingmall:goods:gsMenu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>