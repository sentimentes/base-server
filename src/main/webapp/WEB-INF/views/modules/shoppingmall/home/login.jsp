<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />

<script type="text/javascript" src="${pageContext.request.contextPath}/static/basic/js/jquery-1.7.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css" />
<link href="${pageContext.request.contextPath}/static/css/dlstyle.css"
	rel="stylesheet" type="text/css">
	<script type="text/javascript">
			function updateSort() {
		    	$("#listForm").attr("action", "${ctx}/shoppingmall/goods/user/loginer");
		    	$("#listForm").submit();
			}	
	</script>
</head>

<body>

		<div >
			<a href="${ctx}/shoppingmall/goods/gindex" style="color: #fd7a72; text-decoration: none;  	font-size: 31px;padding-left:400px;">商城主页</a>
		</div>

	<div class="login-banner">
		<div class="login-main">
			<div class="login-banner-bg">
				<span></span><img
					src="${pageContext.request.contextPath}/static/images/big.jpg" />
			</div>
			<div class="login-box" style="height: 298px;margin-top: 79px;">

				<h3 class="title">登录商城</h3>

				<div class="clear"></div>
				<span style="color: red;">${message}</span>
				<div class="login-form" >
					<form:form id="listForm" modelAttribute="gsUser" action="${ctx}/shoppingmall/goods/user/loginer" method="post" class="form-horizontal">
						<div class="user-name">
							<label for="user"><i class="am-icon-user" style="height: 40px;margin-top: 12px;"></i></label> 
							<form:input type="text" path="name" htmlEscape="false" maxlength="255" class="input-xlarge " placeholder="邮箱/手机/用户名" autocomplete="off"/>
						</div>
						<div class="user-pass">
							<label for="password"><i class="am-icon-lock" style="height: 40px;margin-top: 12px;"></i></label>
							 <form:input type="password" path="password" htmlEscape="false" maxlength="255" class="input-xlarge " placeholder="请输入密码"/>
						</div>	
				</form:form>
				</div>
				<div class="login-links">
					<label for="remember-me"><input id="remember-me"
						type="checkbox">记住密码</label> <a href="${ctx}/shoppingmall/goods/user/zPassword" class="am-fr">忘记密码</a>
						 <a href="${ctx}/shoppingmall/goods/user/register" class="zcnext am-fr am-btn-default">注册</a> <br />
				</div>	
				<div class="am-cf">
					<input type="button"  onclick="updateSort();" value="登 录" class="am-btn am-btn-primary am-btn-sm">
				</div>
			</div>
		</div>
	</div>

</body>

</html>