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
		    	$("#listForm").attr("action", "${ctx}/shoppingmall/goods/user/zhPassword");
		    	$("#listForm").submit();
			}	
			function updateSort1() {
		    	$("#listForm").attr("action", "${ctx}/shoppingmall/goods/user/qdPassword");
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
		
				<c:if test="${gsUser.id== null}">	
			<div class="login-box" style="height: 223px;margin-top: 110px;">

				<h3 class="title">找回密码</h3>

				<div class="clear"></div>
				<span style="color: red;">${message}</span>							
				<div class="login-form" >
					<form:form id="listForm" modelAttribute="gsUser" action="" method="post" class="form-horizontal">
						<div class="user-name">
							<label for="user"><i class="am-icon-user" style="height: 40px;margin-top: 12px;"></i></label> 
							<form:input type="text" path="name" htmlEscape="false" maxlength="255" class="input-xlarge " placeholder="请输入用户名" autocomplete="off"/>
						</div>	
				</form:form>
				</div>
				<div class="am-cf">
					<input type="button"  onclick="updateSort();" value="提 交" class="am-btn am-btn-primary am-btn-sm">
				</div>
			</div>
				</c:if>
					
				<c:if test="${gsUser.id != null}">	
				<div class="login-box" style="height: 345px;margin-top: 69px;">
				<h3 class="title">找回密码</h3>
				<div class="clear"></div>
				<span style="color: red;">${message}</span>								
				<div class="login-form" >
					<form:form id="listForm" modelAttribute="gsUser" action="" method="post" class="form-horizontal">
						<form:hidden path="id"  id="ids"/>
						<div class="user-name">
							<label for="user"><i class="am-icon-user" style="height: 40px;margin-top: 12px;"></i></label> 
							<form:input type="text" path="passwordOne" htmlEscape="false" maxlength="255" class="input-xlarge "  autocomplete="off"/>
						</div>
						<div class="user-name">
							<label for="user"><i class="am-icon-user" style="height: 40px;margin-top: 12px;"></i></label> 
							<form:input type="text" path="answerOne" htmlEscape="false" maxlength="255" class="input-xlarge " placeholder="请输入上面问题的答案" autocomplete="off"/>
						</div>	
						<div class="user-name">
							<label for="user"><i class="am-icon-user" style="height: 40px;margin-top: 12px;"></i></label> 
							<form:input type="text" path="passwordTwo" htmlEscape="false" maxlength="255" class="input-xlarge "  autocomplete="off"/>
						</div>	
						<div class="user-name">
							<label for="user"><i class="am-icon-user" style="height: 40px;margin-top: 12px;"></i></label> 
							<form:input type="text" path="answerTwo" htmlEscape="false" maxlength="255" class="input-xlarge " placeholder="请输入上面问题的答案" autocomplete="off"/>
						</div>	
				</form:form>
				</div>
				<div class="am-cf">
					<input type="button"  onclick="updateSort1();" value="提 交" class="am-btn am-btn-primary am-btn-sm">
				</div>
			</div>
				</c:if>
				
		</div>
	</div>

</body>

</html>