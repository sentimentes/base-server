<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用信息管理</title>
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
		<li><a href="${ctx}/application/commonAppInfo/">应用信息列表</a></li>
		<li class="active"><a href="${ctx}/application/commonAppInfo/form?id=${commonAppInfo.id}">应用信息<shiro:hasPermission name="application:commonAppInfo:edit">${not empty commonAppInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="application:commonAppInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="commonAppInfo" action="${ctx}/application/commonAppInfo/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">应用名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">秘钥：</label>
			<div class="controls">
				<form:input path="secretKey" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">键值版本：</label>
			<div class="controls">
				<form:input path="keyVersion" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">所属公司：</label>
			<div class="controls">
			<form:select path="shopId" class="input-xlarge ">
					<form:options items="${fns:getCompanyList('084171b2e70d4073aad57ff17a0a6b56')}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			 
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label">app图片：</label>
			<div class="controls">
			<form:input path="appImage" name="appImage" class="text" maxlength="255" readonly="true"/>
				 <input type="file" id="appTemp" name="appTemp" class="button" value="选择文件" />
			    <c:if test="${not empty commonAppInfo.appImage}">
					<a href="${fns:getConfig('ftp.file.http.path')}${commonAppInfo.appImage}" target="blank">图片查看</a>
				</c:if>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">启动积分机制：</label>
			<div class="controls">
				<form:select path="isIntegral" class="input-large">
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
 			</div>
		</div>
		
		 <div class="control-group">
			<label class="control-label">短信Id：</label>
			<div class="controls">
				<form:input path="msgClientid" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		
		
		 <div class="control-group">
			<label class="control-label">短信前半部分：</label>
			<div class="controls">
				<form:input path="msgFirst" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">短信后半部分：</label>
			<div class="controls">
				<form:input path="msgLast" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">短信模板：</label>
			<div class="controls">
				<form:input path="msgTemplate" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">APP识别码：</label>
			<div class="controls">
				<form:input path="bundleIdentifier" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<h6>&nbsp;&nbsp;&nbsp;&nbsp;微信企业号或公众号部分</h6>
			 <div class="control-group">
			<label class="control-label">微信APPId：</label>
			<div class="controls">
				<form:input path="wxAppId" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
			 <div class="control-group">
			<label class="control-label">微信appSecret</label>
			<div class="controls">
				<form:input path="wxAppSecret" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> 
			 <div class="control-group">
			<label class="control-label">微信appToken：</label>
			<div class="controls">
				<form:input path="wxToken" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
	    <div class="control-group">
			<label class="control-label">微信APPAES key：</label>
			<div class="controls">
				<form:input path="wxAesKey" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用序列号wxAgentid：</label>
			<div class="controls">
				<form:input path="wxAgentid" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
			 <div class="control-group">
			<label class="control-label">微信appType：</label>
			<div class="controls">
			<form:select path="wxType" class="input-xlarge">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
 			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="application:commonAppInfo:edit">
			<input id="nbtnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>