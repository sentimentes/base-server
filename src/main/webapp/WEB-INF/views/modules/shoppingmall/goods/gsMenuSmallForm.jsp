<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品分类细分管理</title>
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
		<li><a href="${ctx}/shoppingmall/goods/gsMenuSmall/">商品分类细分列表</a></li>
		<li class="active"><a href="${ctx}/shoppingmall/goods/gsMenuSmall/form?id=${gsMenuSmall.id}">商品分类细分<shiro:hasPermission name="shoppingmall:goods:gsMenuSmall:edit">${not empty gsMenuSmall.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shoppingmall:goods:gsMenuSmall:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="gsMenuSmall" action="${ctx}/shoppingmall/goods/gsMenuSmall/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">分类名称：</label>
			<div class="controls">
				<form:select path="gsMenuId" class="input-xlarge required" style="width:285px">
					<form:option value="" label=""/>
					<form:options items="${gsMenuList}" itemLabel="spType1" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font>必填字段</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">细分名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>必填字段</span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">机构id：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${gsMenuSmall.office.id}" labelName="office.name" labelValue="${gsMenuSmall.office.name}"
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
	<%-- 	<div class="control-group">
			<label class="control-label">上下架：</label>
			<div class="controls">
				<form:input path="upDownShelf" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="shoppingmall:goods:gsMenuSmall:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>