<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>

<head lang="en">
<meta charset="UTF-8">
<title>注册</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.min.css" />
<link href="${pageContext.request.contextPath}/static/css/dlstyle.css"
	rel="stylesheet" type="text/css">
<script
	src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/amazeui.min.js"></script>
	<script type="text/javascript">
			function updateSort() {
		    	$("#listForm").attr("action", "${ctx}/shoppingmall/goods/user/save");
		    	$("#listForm").submit();
			}	
	</script>
</head>

<body>

	<div >
			<a href="${ctx}/shoppingmall/goods/gindex" style="color: #fd7a72; text-decoration: none;  	font-size: 31px;padding-left:400px;">商城主页</a>
	</div>

	<div class="res-banner">
		<div class="res-main">
			<div class="login-banner-bg">
				<span></span><img
					src="${pageContext.request.contextPath}/static/images/big.jpg" />
			</div>
			<div class="login-box" style="height: 364px;margin-top: 59px;">
		
				<div class="am-tabs" id="doc-my-tabs">
					<ul class="am-tabs-nav am-nav am-nav-tabs am-nav-justify">
						<li class="am-active"><a href="">用户注册</a></li>
					</ul>
					<span style="color: red;">${message}</span>
					<div class="am-tabs-bd">
						<div class="am-tab-panel am-active">
							<form:form id="listForm" modelAttribute="gsUser" action="${ctx}/shoppingmall/goods/login/user" method="post" class="form-horizontal" enctype="multipart/form-data" >
								<input type="file" class="inputPic" id="imageTemp" name="imageTemp" accept=".png,.jpg" value="修改图片" style="display:none">
								<div class="user-email">
									<label for="email"><i class="am-icon-envelope-o"
										style="height: 40px; margin-top: 12px;"></i></label> 
										<form:input type="email" path="name" htmlEscape="false" maxlength="255" class="input-xlarge " placeholder="请输入你要注册的账号" autocomplete="off"/>
								</div>
								<div class="user-pass">
									<label for="password"><i class="am-icon-lock"
										style="height: 40px; margin-top: 12px;"></i></label> 
										<form:input type="password" path="password" htmlEscape="false" maxlength="255" class="input-xlarge " placeholder="设置密码" autocomplete="off"/>
								</div>
								<div class="user-pass">
									<label for="passwordRepeat"><i class="am-icon-lock"
										style="height: 40px; margin-top: 12px;"></i></label> 
								<form:input type="password" path="beiFei" htmlEscape="false" maxlength="255" class="input-xlarge " placeholder="确认密码" autocomplete="off"/>
								</div>
						</form:form>

							<div class="login-links">
								<label for="reader-me"> <input id="reader-me"
									type="checkbox"> 点击表示您同意商城《服务协议》
								</label>
							</div>
							<div class="am-cf">
								<input type="button"  onclick="updateSort();" value="注册" class="am-btn am-btn-primary am-btn-sm am-fl">
							</div>
							<a class="am-btn-warning btn" href="${ctx}/shoppingmall/goods/user/" style="color: #ea1212;float: right; margin-top: -10px;background-color: #fff;">返回登录</a>
						</div>
						
						<script>
							$(function() {
								$('#doc-my-tabs').tabs();
							})
						</script>

					</div>
				</div>

			</div>
		</div>
</body>

</html>