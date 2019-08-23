<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">
		
		<title>个人资料</title>
		<%@ include file="/WEB-INF/views/include/head.jsp"%>
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/css/personal.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/css/infstyle.css" rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/amazeui.js"></script>	
			<link href="${pageContext.request.contextPath}/static/css/vipstyle.css" rel="stylesheet" type="text/css">		
	</head>

	<body>
		<!--头 -->
		<header>
			<article>
				<div class="mt-logo">
					<!--顶部导航条 -->
					<div class="am-container header">
						<ul class="message-l">
							<div class="topMessage">
								<div class="menu-hd">
									<c:if test="${sessionScope.name == null}">
										<a href="${ctx}/shoppingmall/goods/user/" target="_top" class="h">亲，请登录</a>
										<a href="${ctx}/shoppingmall/goods/user/register" target="_top">免费注册</a>
									</c:if>	
									<c:if test="${sessionScope.name != null}">
										Hi,<span class="s-name">${sessionScope.name}</span>
									<a href="${ctx}/shoppingmall/goods/user/beark" style="color: #fb5746;">退出账号</a>
									</c:if>	
								</div>
							</div>
						</ul>
						<ul class="message-r">
					<div class="topMessage home">
						<div class="menu-hd"><a href="${ctx}/shoppingmall/goods/gindex" target="_top" class="h">商城首页</a></div>
					</div>
					<div class="topMessage my-shangcheng">
						<div class="menu-hd MyShangcheng"><a href="${ctx}/shoppingmall/goods/user/people" target="_top"><i class="am-icon-user am-icon-fw"></i>个人中心</a></div>
					</div>
					<div class="topMessage mini-cart">
						<div class="menu-hd"><a id="mc-menu-hd" href="${ctx}/shoppingmall/goods/gindex/shopcart" target="_top"><i class="am-icon-shopping-cart  am-icon-fw"></i><span>购物车</span><strong id="J_MiniCartNum" class="h"></strong></a></div>
					</div>
					<div class="topMessage favorite">
						<div class="menu-hd"><a href="${ctx}/shoppingmall/goods/gindex/list?ss=2" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>退出该商店</span></a></div>
				</ul>
						</div>

						<!--悬浮搜索框-->

						<div class="nav white">
							
							<div class="logoBig" >
								<li style="font-size: 36px;color: red; margin-top: 20px; float: right;">${office.name}</li>
							</div>

					<div class="search-bar pr" style="padding: 20px 70px 0 221px;">
						<a name="index_none_header_sysc" href="#"></a>
						<form:form id="inputForm" modelAttribute="gsGoods" action="${ctx}/shoppingmall/goods/gindex/goodsSame" method="post" class="form-horizontal">
							<form:input path="name" id="searchInput" name="index_none_header_sysc" type="text" placeholder="搜索" autocomplete="off" />
							<input id="ai-topsearch" class="submit am-btn" value="搜索" index="1" type="submit">
						</form:form>
					</div>
						</div>

						<div class="clear"></div>
					</div>
				</div>
			</article>
		</header>

		<div class="nav-table">
			<div class="long-title"><span class="all-goods">全部分类</span></div>
			
		</div>
			<b class="line"></b>
		<div class="center">
			<div class="col-main">
				<div class="main-wrap">

					<div class="user-info">
						<!--标题 -->
						<div class="am-cf am-padding">
							<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">个人资料</strong> / <small>Personal&nbsp;information</small></div>
						</div>
						<sys:message content="${message}"/>
						<hr/>
					<form:form id="inputForm" modelAttribute="gsUser" action="${ctx}/shoppingmall/goods/user/updateUser" method="post" class="form-horizontal" enctype="multipart/form-data" >
						<!--头像 -->
						<div class="user-infoPic">

							<div class="filePic">
								<form:hidden path="image" name="image" class="text" maxlength="255" readonly="true" />
								<input type="file" class="inputPic" id="imageTemp" name="imageTemp" accept=".png,.jpg" value="修改图片">
								<img class="am-circle am-img-thumbnail" src="${fns:getConfig('wy.file.http.path')}${gsUser.image}" alt="" />
							</div>
							<p class="am-form-help">头像</p>

							<div class="info-m">
								<div><b>用户名：<i>${gsUser.name}</i></b></div>
								<div class="vip">
                                      	<div style="color: red;">欢迎你的到来!</div>
								</div>
							</div>
						</div>

						<!--个人信息 -->
						<div class="info-main">
								<form:hidden path="id"/>
								<%-- <div class="am-form-group">
									<label for="user-name" class="am-form-label">姓名</label>
									<div class="am-form-content">
										<form:input path="name"  type="text" name="name" id="user-name2" placeholder="name" style="height: 32px;" />
                              
									</div>
								</div> --%>

								<div class="am-form-group">
									<label class="am-form-label">性别</label>
									<div class="am-form-content sex">
										<form:radiobuttons path="sex" items="${fns:getDictList('gender')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
									</div>
								</div>

								<div class="am-form-group">
									<label for="user-birth" class="am-form-label">生日</label>
									<div class="am-form-content birth">
										<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
										value="<fmt:formatDate value="${gsUser.birthday}" pattern="yyyy-MM-dd" />"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" style="height: 32px;width: 202px;"/>	
									</div>
							
								</div>
								<div class="am-form-group">
									<label for="user-phone" class="am-form-label">电话</label>
									<div class="am-form-content">
										<form:input path="phone" name="phone"  id="user-phone" placeholder="telephonenumber" type="tel" style="height: 32px;" />
									</div>
								</div>
								<div class="am-form-group">
									<label for="user-email" class="am-form-label">电子邮件</label>
									<div class="am-form-content">
										<form:input path="email"  id="user-email" placeholder="Email" type="email" style="height: 32px;" />
									</div>
								</div>
								
								<div class="info-btn">
									<input id="btnSubmit" class="am-btn am-btn-danger" type="submit" value="保存修改" />
								</div>

							
						</div>
				</form:form>
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