<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>版本管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$("#nbtnSubmit").attr('disabled', "true"); //使按钮不能被点击
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
		<li><a href="${ctx}/application/commonAppVersion/">版本管理列表</a></li>
		<li class="active"><a href="${ctx}/application/commonAppVersion/form?id=${commonAppVersion.id}">版本管理</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="commonAppVersion" action="${ctx}/application/commonAppVersion/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
		
		
		<div class="control-group">
			<label class="control-label">版本号：</label>
			<div class="controls">
				<form:input path="version" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>（必填）请输入版本号</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">描述信息：</label>
			<div class="controls">
				<form:textarea path="info" htmlEscape="false" rows="4" maxlength="500" class="input-xlarge"/>
				<span class="help-inline">（选填）请输入版本描述，不超过200字</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">app文件：</label>
			<div class="controls">
				<form:input path="downUrl" name="downUrl" class="text" maxlength="255" readonly="true"/>
			    <input type="file" id="downUrlTemp" name="downUrlTemp" class="button" value="选择文件" />
			    <c:if test="${not empty commonAppVersion.downUrl}">
					<a href="${fns:getConfig('ftp.file.http.path')}${commonAppVersion.downUrl}" target="blank">文件下载</a>
				</c:if>
				<span class="help-inline">（选填）请上传app安装包</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">app类型：</label>
			<div class="controls">
				<form:select path="appType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('app_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font>（必填）请选择app类型</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="10" class="input-xlarge digits required"/>
				<span class="help-inline">（选填）请输入排序整数 (数值大的排序靠前)</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="application:commonAppVersion:edit">
			<input id="nbtnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>