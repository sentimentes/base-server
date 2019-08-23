<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>session管理管理</title>
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
		<li class="active"><a href="${ctx}/sys/sysSessionManage/form?id=${sysSessionManage.id}">会话配置</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysSessionManage" action="${ctx}/sys/sysSessionManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">会话结束时间：</label>
			<div class="controls">
				<form:input path="timeout" htmlEscape="false" maxlength="9" class="input-xlarge required digits"/>
				<span class="help-inline">分钟<font color="red">*</font>请输入会话结束时间，单位分钟。请输入3到120的正整数,默认30分钟 </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">同时在线人数：</label>
			<div class="controls">
				<form:input path="onlineCount" htmlEscape="false" maxlength="9" class="input-xlarge required digits"/>
				<span class="help-inline">个<font color="red">*</font>请输入在线人数，请输入1到10000的正整数，默认10 </span>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysSessionManage:edit">
			<input id="nbtnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>