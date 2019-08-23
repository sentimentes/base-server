<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
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
	
	<script  src="${ctxStatic}/js/scriptenc.js"></script>
<script  src="${ctxStatic}/img/crypto-js.js"></script>
<script  src="${ctxStatic}/img/jsaes.js"></script>
<script  src="${ctxStatic}/md5/md5.js"></script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/systemConfig/form?id=${dict.id}">邮箱信息配置</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="systemConfig" action="${ctx}/sys/systemConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">邮箱服务器地址:</label>
			<div class="controls">
				<form:input path="smtp" htmlEscape="true" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font>（必填）请输入以smtp.163.com、smtp.126.com、smtp.qq.com的地址</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱服务器端口:</label>
			<div class="controls">
				<form:input path="port" htmlEscape="false" maxlength="50" class="required digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统邮箱地址:</label>
			<div class="controls">
				<form:input path="mailName" htmlEscape="true" maxlength="50" class="required email"/>
				<span class="help-inline"><font color="red">*</font>（必填）请输入邮箱地址</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统邮箱密码:</label>
			<div class="controls">
			<input id="mailPassword" name="mailPassword" type="password" value="" maxlength="420" minlength="8" class="required" onkeyup="value=value.replace(/[\W]/g,'')"/>

			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="sys:systemConfig:edit">
			<input id="nbtnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="Encrypt()"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	<script type="text/javascript">
function Encrypt(){
	     var key = CryptoJS.enc.Utf8.parse(enckey);   
	     var pwd =document.getElementById("mailPassword").value
	     
	     //var username =document.getElementById("username").value
	     var pwdMd5 = hex_md5(pwd);
	     var mPwdAddPwdMd5 = pwdMd5+","+pwd
	     var srcs = CryptoJS.enc.Utf8.parse(mPwdAddPwdMd5);
	     var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7}); 
	     var password = encrypted.toString();
	     
	     document.getElementById("mailPassword").value =password;

	 
}
</script>
</body>
</html>