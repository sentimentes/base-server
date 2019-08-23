<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">

		<title>个人中心</title>

		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/css/personal.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/css/vipstyle.css" rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
	<%-- 	<link href="${pageContext.request.contextPath}/static/basic/css/demo.css" rel="stylesheet" type="text/css" /> --%>
		<style>
/* .menu
{
    margin-top: -684px;
} */
</style>
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
					<div class="wrap-left">
						<div class="wrap-list">
							<div class="m-user">
								<!--个人信息 -->
								<div class="m-userinfo">
									<a href="">
										<div class="tipsBox"><i class="am-icon-envelope"></i></div>
									</a>
									<div class="m-baseinfo">
										<a class="m-pic" href="information">
											<img src="${fns:getConfig('wy.file.http.path')}${gsUser.image}">
										</a>
										<div class="m-info">
											<em class="s-name">${sessionScope.name}</em>
											<div class="vip1"><a href="#"><span></span><em>会员专享</em></a></div>
											
											<div class="m-address" style="margin-top: 30px;">
												<a href="${ctx}/shoppingmall/goods/user/address" class="i-trigger">收货地址<i class="am-icon-angle-right" style="padding-left:5px ;"></i></a>
											</div>
										</div>
									</div>
								</div>

								

								<!--我的钱包-->
								<div class="wallet" style="left: 268px;">
									<div class="s-bar">
										<i class="s-icon"></i>商城
									</div>
									<p class="m-big squareS">
										<a href="#">
											<i><img src="${pageContext.request.contextPath}/static/images/shopping.png"/></i>
											<span class="m-title">能购物</span>
										</a>
									</p>
									<p class="m-big squareA">
										<a href="#">
											<i><img src="${pageContext.request.contextPath}/static/images/safe.png"/></i>
											<span class="m-title">够安全</span>
										</a>
									</p>
									<p class="m-big squareL">
										<a href="#">
											<i><img src="${pageContext.request.contextPath}/static/images/profit.png"/></i>
											<span class="m-title">很灵活</span>
										</a>
									</p>
								</div>

							</div>
							<div class="box-container-bottom"></div>

							<!--订单 -->
							<div class="m-order">
								<div class="s-bar">
									<i class="s-icon"></i>我的订单
									<span style="color: red; font-size: 18px;">${message}</span>
									<a class="i-load-more-item-shadow" href="${ctx}/shoppingmall/goods/user/dingdan">全部订单</a>
								</div>
								<c:forEach items="${gsOrderList}" var="gsOrder" varStatus="i">
								
								<div class="orderContentBox">
									<div class="orderContent">
										<div class="orderContentpic">
											<div class="imgBox">
												<img src="${fns:getConfig('wy.file.http.path')}${gsOrder.image}">
											</div>
										</div>
										<div class="detailContent">
											
											<c:if test="${gsOrder.upDownShelf == 1}">
													已付款
												</c:if>
												<c:if test="${gsOrder.upDownShelf == 0}">
													未付款
												</c:if>
												<c:if test="${gsOrder.upDownShelf == 2}">
													发货中
												</c:if>
												<c:if test="${gsOrder.upDownShelf == 3}">
													已收货
												</c:if>
											
											<div class="orderID">
												<span class="time">	<fmt:formatDate value="${gsOrder.createDate}" pattern="yyyy-MM-dd"/></span>
												<span class="splitBorder">|</span>
												<span class="time">	<fmt:formatDate value="${gsOrder.createDate}" pattern="HH:mm:ss"/></span>
											</div>
											<div class="orderID">
												<span class="num">共${gsOrder.gsNumber}件商品</span>
											</div>
										</div>
										<div class="state"><c:if test="${gsOrder.upDownShelf == 1}">
													待发货
												</c:if>
												<c:if test="${gsOrder.upDownShelf == 0}">
													待付款
												</c:if>
												<c:if test="${gsOrder.upDownShelf == 2}">
													待收货
												</c:if>
												<c:if test="${gsOrder.upDownShelf == 3}">
													订单完成
												</c:if></div>
										<div class="price"><span class="sym">¥ ${gsOrder.price}</span></div>

									</div>
									<c:if test="${gsOrder.upDownShelf == 1}">
												<a href="javascript:void(0);" class="btnPay">待发货</a>
												</c:if>
												<c:if test="${gsOrder.upDownShelf == 0}">
												<a href="${ctx}/shoppingmall/goods/user/goAlipays?orderId=${gsOrder.id}&sum=${gsOrder.gsNumber}" class="btnPay">去付款</a>
												</c:if>
												<c:if test="${gsOrder.upDownShelf == 2}">
											<a href="${ctx}/shoppingmall/goods/user/shouh?id=${gsOrder.id}&ss=1" class="btnPay">确定收货</a>
												</c:if>
												<c:if test="${gsOrder.upDownShelf == 3}">
											<a href="javascript:void(0);" class="btnPay">订单完成</a>
												</c:if>
								
								</div>
							</c:forEach>
							</div>
							<div class="user-squaredIcon">
								<div class="s-bar">
									<i class="s-icon"></i>我的常用
								</div>
								<ul>
									<a href="${ctx}/shoppingmall/goods/user/information">
										<li class="am-u-sm-4"><i class="am-icon-truck am-icon-md"></i>
											<p>个人信息</p>
										</li>
									</a>
									<a href="${ctx}/shoppingmall/goods/user/anquan">
										<li class="am-u-sm-4"><i class="am-icon-heart am-icon-md"></i>
											<p>安全设置</p>
										</li>
									</a>
									<a href="${ctx}/shoppingmall/goods/user/address">
										<li class="am-u-sm-4"><i class="am-icon-paw am-icon-md"></i>
											<p>地址管理</p>
										</li>
									</a>

								</ul>
							</div>
							
						</div>
					</div>
     				<div class="clear"></div>
     				
     				<!--收藏和足迹-->
     				<!--九宫格-->
							
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
		<!--引导 -->
		<div class="navCir">
			<li class="active"><a href="${ctx}/shoppingmall/goods/gindex"><i class="am-icon-home "></i>首页</a></li>
			<li><a href="${ctx}/shoppingmall/goods/gindex/shopcart"><i class="am-icon-shopping-basket"></i>购物车</a></li>	
			<li><a href="${ctx}/shoppingmall/goods/user/people"><i class="am-icon-user"></i>我的</a></li>					
		</div>
	</body>

</html>