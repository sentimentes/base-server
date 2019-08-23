<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户属性配置管理</title>
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
<script>
	function test1(o) {
		var v = parseInt(o.value);
		if (v<1 || v>90) {
			alert("只能输入1到90的数字");
			o.focus();
		}
	}
	
	function test2(o) {
		var v = parseInt(o.value);
		if (v<1 || v>10) {
			alert("只能输入1到10的数字");
			o.focus();
		}
	}
	
	function test3(o) {
		var v = parseInt(o.value);
		if (v<20) {
			alert("只能输入不小于20的数字");
			o.focus();
		}
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/sysUserAttribute/form?userId=${userId}">用户属性配置<shiro:hasPermission name="sys:sysUserAttribute:edit">${not empty sysUserAttribute.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysUserAttribute:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysUserAttribute" action="${ctx}/sys/sysUserAttribute/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input name="userId" type="text" value="${userId}" style="display:none"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">锁定触发次数：</label>
			<div class="controls">
				<form:input path="abnormalTimes" htmlEscape="false" maxlength="4" class="input-xlarge digits required" onblur="test2(this)"/>
				<span class="help-inline">次<font color="red">*</font>请配置连续登录失败几次后触发锁定，锁定状态下用户不能登录，默认值3</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">锁定时间：</label>
			<div class="controls">
				<form:input path="lockTime" htmlEscape="false" maxlength="10" class="input-xlarge digits required" onblur="test3(this)"/>
				<span class="help-inline">（分）<font color="red">*</font>请配置连续登录失败几次后触发锁定的时间 默认值20</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">禁止访问开始时间：</label>
			<div class="controls">
				<input name="visitStart" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${sysUserAttribute.visitStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					<span class="help-inline">请配置用户禁止访问的开始时间 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">禁止访问结束时间：</label>
			<div class="controls">
				<input name="visitEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${sysUserAttribute.visitEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					<span class="help-inline">请配置用户禁止访问的结束时间 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">受限IP：</label>
			<div class="controls">
				<form:input path="boundedIp" htmlEscape="false" maxlength="64" class="input-xlarge "/>
				<span class="help-inline">请配置不让访问ip地址 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">口令有效期限：</label>
			<div class="controls">
				<form:input path="expirationDate" htmlEscape="false" maxlength="4" class="input-xlarge digits required" onblur="test1(this)"/>
				<span class="help-inline">（天）<font color="red">*</font>请配置用户口令的有效时间，一般是1到90天 ，默认值30</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysUserAttribute:edit">
			<input id="nbtnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>