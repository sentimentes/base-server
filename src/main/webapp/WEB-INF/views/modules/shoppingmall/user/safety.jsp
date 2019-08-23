<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">

		<title>安全设置</title>

		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/css/personal.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/css/infstyle.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<!--头 -->
		<header>
			<article>
				<%@ include file="/WEB-INF/views/include/index2.jsp"%>
			</article>
		</header>
            <div class="nav-table">
					   <div class="long-title"><span class="all-goods">全部分类</span></div>
					   
			</div>
			<b class="line"></b>
		<div class="center">
			<div class="col-main">
				<div class="main-wrap">

					<!--标题 -->
					<div class="user-safety">
						<div class="am-cf am-padding">
							<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">账户安全</strong> / <small>Set&nbsp;up&nbsp;Safety</small></div>
						</div>
						<hr/>
						<!--头像 -->
						<div class="user-infoPic">

							<div class="filePic">
								<img class="am-circle am-img-thumbnail" src="${fns:getConfig('wy.file.http.path')}${gsUser.image}" alt="" />
							</div>

							<p class="am-form-help">头像</p>

							<div class="info-m">
								<div><b>用户名：<i>小叮当</i></b></div>
                                <div class="safeText">
                                  	<div style="color: red;">请尽快完成下面的安全认证，不然修改密码将会为你造成麻烦</div>
									<div class="progressBar"><span style="left: -95px;" class="progress"></span></div>
								</div>
							</div>
						</div>

						<div class="check">
							<ul>
								<li>
									<i class="i-safety-lock"></i>
									<div class="m-left">
										<div class="fore1">登录密码</div>
										<div class="fore2"><small>为保证您购物安全，建议您定期更改密码以保护账户安全。</small></div>
									</div>
									<div class="fore3">
										<a href="${ctx}/shoppingmall/goods/user/upPassword?ss=2">
											<div class="am-btn am-btn-secondary">修改</div>
										</a>
									</div>
								</li>
								<li>
									<i class="i-safety-security"></i>
									<div class="m-left">
										<div class="fore1">安全问题</div>
										<div class="fore2"><small>保护账户安全，验证您身份的工具之一。</small></div>
									</div>
									<div class="fore3">
										<a href="${ctx}/shoppingmall/goods/user/question">
											<div class="am-btn am-btn-secondary">认证</div>
										</a>
									</div>
								</li>
							</ul>
						</div>
						
					</div>
				</div>
	
		
				<!--底部-->
				<div class="footer">
					<div class="footer-bd" >
						<p style="margin: 26px 413px;">
							<em>© 2019 商城 www.leixoaming.cn All Rights Reserved</em>
						</p>
					</div>
				</div>
			</div>
				
			<%@ include file="/WEB-INF/views/include/head2.jsp"%>
		</div>
		<div class="navCir">
			<li class="active"><a href="${ctx}/shoppingmall/goods/gindex"><i class="am-icon-home "></i>首页</a></li>
			<li><a href="${ctx}/shoppingmall/goods/gindex/shopcart"><i class="am-icon-shopping-basket"></i>购物车</a></li>	
			<li><a href="${ctx}/shoppingmall/goods/user/people"><i class="am-icon-user"></i>我的</a></li>					
		</div>
	</body>

</html>