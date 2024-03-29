<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
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
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/form?id=${user.id}">用户信息修改</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/updateInfo" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%-- <div class="control-group">
			<label class="control-label">头像:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>--%>
		
		<div class="control-group">
			<label class="control-label">归属公司:</label>
			<div class="controls">
                <sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
					title="公司" url="/sys/office/treeData?type=1" cssClass="required"/><span class="help-inline"><font color="red">*</font> </span>
			</div>
			
		</div>
		<div class="control-group">
			<label class="control-label">归属部门:</label>
			<div class="controls">
                <sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="required" /><span class="help-inline"><font color="red">*</font> </span>
			</div>

		</div>
		
		<div class="control-group">
			<label class="control-label">工号:</label>
			<div class="controls">
				<form:input path="no" htmlEscape="false" maxlength="50" class="required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name"  htmlEscape="false" maxlength="50" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登录名:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
				<form:input path="loginName" htmlEscape="false" maxlength="420" minlength="8" class="required" onkeyup="value=value.replace(/[\W]/g,'')"/>
				<span class="help-inline"><font color="red">*</font> 以字母开头，包含字母、数字下划线，长度再8到32之间</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="420" minlength="8" class="${empty user.id?'required':''}"  onkeyup="value=value.replace(/[\W]/g,'')" class="required"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				<c:if test="${not empty user.id}"><span class="help-inline">密码格式：以字母开头，包含字母、数字下划线，长度再8到20之间</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="420" minlength="8" equalTo="#newPassword"  onkeyup="value=value.replace(/[\W]/g,'')" class="required"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font>密码格式：以字母开头，包含字母、数字下划线，长度再8到20之间 </span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="100" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="100" class="phone"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="100" class="mobile"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">是否允许登录:</label>
			<div class="controls">
				<form:select path="loginFlag">
					<form:options items="${fns:getDictList('loginFlag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户类型:</label>
			<div class="controls">
				<form:select path="userType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
				<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<c:if test="${not empty user.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">最后登陆:</label>
				<div class="controls">
					<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="form-actions">

			<shiro:hasPermission name="sys:user:edit">
			<input id="nbtnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="Encrypt()"/>&nbsp;&nbsp;
			</shiro:hasPermission>

			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
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
	     
	     var loginName =document.getElementById("loginName").value//登录名
	   	 var loginMd5 = hex_md5(loginName);
	   	 var srcLogin = CryptoJS.enc.Utf8.parse(loginMd5+","+loginName);
	     var encryptedLogin = CryptoJS.AES.encrypt(srcLogin, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7}); 
	     var newLogin = encryptedLogin.toString();
	     
	     var loginNameO =document.getElementById("oldLoginName").value//登录名
	   	 var loginMd5O = hex_md5(loginNameO);
	   	 var srcLoginO = CryptoJS.enc.Utf8.parse(loginMd5O+","+loginNameO);
	     var encryptedLoginO = CryptoJS.AES.encrypt(srcLoginO, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7}); 
	     var newLoginO = encryptedLoginO.toString();
	     
	     document.getElementById("newPassword").value =encrypted.toString();
	     document.getElementById("confirmNewPassword").value =encrypted.toString();
	     document.getElementById("loginName").value =newLogin;
	     document.getElementById("oldLoginName").value =newLoginO;
}
</script>
</body>
</html>