<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用菜单管理</title>
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
		<li><a href="${ctx}/application/commonMenu/">菜单列表</a></li>
		<li class="active"><a href="${ctx}/application/commonMenu/form?id=${commonMenu.id}">应用菜单<shiro:hasPermission name="application:commonMenu:edit">${not empty commonMenu.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="application:commonMenu:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="commonMenu" action="${ctx}/application/commonMenu/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge required digits "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">对外接口地址：</label>
			<div class="controls">
				<form:input path="api" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接：</label>
			<div class="controls">
				<form:input path="href" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目标：</label>
			<div class="controls">
				<form:input path="target" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图标：</label>
			<div class="controls">
				<form:input path="icon" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登陆权限：</label>
			<div class="controls">
				<form:select path="isShow" class="input-xlarge  required">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>	
	
		<div class="control-group">
			<label class="control-label">菜单类型：</label>
			<div class="controls">
			<form:select path="menuType" class="input-xlarge required">
				<form:option value=""></form:option>
					<form:options items="${fns:getDictList('menuType')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					
				</form:select>
 			</div>
		</div>
		<div class="control-group">
			<label class="control-label">积分：</label>
			<div class="controls">
				<form:select path="integral" class="input-xlarge required">
				<form:option value=""></form:option>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>	

		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="application:commonMenu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>