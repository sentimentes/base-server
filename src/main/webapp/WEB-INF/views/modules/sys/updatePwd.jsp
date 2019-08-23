<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>


<html>
<head>
	<title>修改密码</title>
<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
				},
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
<link href="${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<link href="${ctxStatic}/common/jeesite.css" type="text/css" rel="stylesheet" />



</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/updatePwd">修改密码</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/updatePwd" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" value="" maxlength="420" minlength="8" class="required"/>
				<span class="help-inline"><font color="red">*</font>（必填）请输入旧密码 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="420" minlength="8" class="required" onkeyup="value=value.replace(/[\W]/g,'')"/>
				<span class="help-inline"><font color="red">*</font>（必填）密码格式：以字母开头，包含字母、数字下划线，长度再8到20之间 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="420" minlength="8" class="required" equalTo="#newPassword" onkeyup="value=value.replace(/[\W]/g,'')"/>
				<span class="help-inline"><font color="red">*</font>（必填）密码格式：以字母开头，包含字母、数字下划线，长度再8到20之间  </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="nbtnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="Encrypt()"/>
		</div>
	</form:form>
	
	
	<script type="text/javascript">
function Encrypt(){
	     var key = CryptoJS.enc.Utf8.parse(enckey);   
	     var pwd =document.getElementById("newPassword").value
	     
	     //var username =document.getElementById("username").value
	     var pwdMd5 = hex_md5(pwd);
	     var mPwdAddPwdMd5 = pwdMd5+","+pwd
	     var srcs = CryptoJS.enc.Utf8.parse(mPwdAddPwdMd5);
	     var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7}); 
	     var password = encrypted.toString();
	     
 		 var pwdOld =document.getElementById("oldPassword").value
	     var pwdMd5Old = hex_md5(pwdOld);
	     var mPwdAddPwdMd5Old = pwdMd5Old+","+pwdOld
	     var srcsOld = CryptoJS.enc.Utf8.parse(mPwdAddPwdMd5Old);
	     var encryptedOld = CryptoJS.AES.encrypt(srcsOld, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7}); 
	     var passwordOld = encryptedOld.toString();
	     
	     
	     document.getElementById("newPassword").value =encrypted.toString();
	     document.getElementById("confirmNewPassword").value =encrypted.toString();
	     document.getElementById("oldPassword").value =passwordOld;
}
</script>

</body>
</html>