<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">

<title>订单管理</title>

<link
	href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/admin.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/basic/css/demo.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/personal.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/orstyle.css"
	rel="stylesheet" type="text/css">

<script
	src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/amazeui.js"></script>

</head>

<body>
	<!--头 -->
	<header>
		<article>
			<%@ include file="/WEB-INF/views/include/index2.jsp"%>
		</article>
	</header>
	<div class="nav-table">
		<div class="long-title">
			<span class="all-goods">全部分类</span>
		</div>

	</div>
	<b class="line"></b>
	<div class="center">
		<div class="col-main">
			<div class="main-wrap">

				<div class="user-order">

					<!--标题 -->
					<div class="am-cf am-padding">
						<div class="am-fl am-cf">
							<strong class="am-text-danger am-text-lg">订单管理</strong> / <small>Order</small>
						</div>
					</div>
					<hr />

					<div class="am-tabs am-tabs-d2 am-margin" data-am-tabs>

						<ul class="am-avg-sm-5 am-tabs-nav am-nav am-nav-tabs">
							<li class="am-active"><a href="#tab1">所有订单</a></li>
							<li><a href="#tab2">待付款</a></li>
							<li><a href="#tab3">待发货</a></li>
							<li><a href="#tab4">待收货</a></li>
							<li><a href="#tab5">已完成</a></li>
						</ul>

						<div class="am-tabs-bd">
							<div class="am-tab-panel am-fade am-in am-active" id="tab1">
								<div class="order-top">
									<div class="th th-item">
										<td class="td-inner">商品</td>
									</div>
									<div class="th th-price">
										<td class="td-inner">单价</td>
									</div>
									<div class="th th-number">
										<td class="td-inner">数量</td>
									</div>
									<div class="th th-operation">
										<td class="td-inner">商品操作</td>
									</div>
									<div class="th th-amount">
										<td class="td-inner">合计</td>
									</div>
									<div class="th th-status">
										<td class="td-inner">交易状态</td>
									</div>
									<div class="th th-change">
										<td class="td-inner">交易操作</td>
									</div>
								</div>

								<div class="order-main">
									<div class="order-list">
										<c:forEach items="${gsOrderLists}" var="gsOrder" varStatus="i">
											<!--交易成功-->
											<div class="order-status5">
												<div class="order-title">
													<div class="dd-num" style="width: 70%; max-width: 600px">
														订单编号：<a href="javascript:;">${gsOrder.id}</a>
													</div>
													<span>成交时间：<fmt:formatDate
															value="${gsOrder.createDate}"
															pattern="yyyy-MM-dd HH:mm:ss" />
													</span>
												</div>
												<div class="order-content">
													<div class="order-left">

														<ul class="item-list">
															<li class="td td-item">
																<div class="item-pic">
																	<a href="#" class="J_MakePoint"> <img
																		src="${fns:getConfig('wy.file.http.path')}${gsOrder.image}"
																		class="itempic J_ItemImg">
																	</a>
																</div>
																<div class="item-info">
																	<div class="item-basic-info">
																		<a href="#">
																			<p>${gsOrder.gsName}</p>
																			<p class="info-little">${gsOrder.parameter}</p>
																		</a>
																	</div>
																</div>
															</li>
															<li class="td td-price">
																<div class="item-price">
																	${gsOrder.price/gsOrder.gsNumber}</div>
															</li>
															<li class="td td-number">
																<div class="item-number">
																	<span>×</span>${gsOrder.gsNumber}
																</div>
															</li>
															<li class="td td-operation">
																<div class="item-operation"></div>
															</li>
														</ul>

													</div>
													<div class="order-right">
														<li class="td td-amount">
															<div class="item-amount">合计：${gsOrder.price}</div>
														</li>
														<div class="move-right">
															<li class="td td-status">
																<div class="item-status">
																	<p class="Mystatus">
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
																	</p>
																</div>
															</li>
															<li class="td td-change">
																<div class="am-btn am-btn-danger anniu">
																	<c:if test="${gsOrder.upDownShelf == 1}">
																		<a href="javascript:void(0);" style="color: #fff;">收货中</a>
																	</c:if>
																	<c:if test="${gsOrder.upDownShelf == 0}">
																		<a style="color: #fff;"
																			href="${ctx}/shoppingmall/goods/user/goAlipays?orderId=${gsOrder.id}&sum=${gsOrder.gsNumber}">去付款</a>
																	</c:if>
																	<c:if test="${gsOrder.upDownShelf == 2}">
																		<a style="color: #fff;"
																			href="${ctx}/shoppingmall/goods/user/shouh?id=${gsOrder.id}">确定收货</a>
																	</c:if>
																	<c:if test="${gsOrder.upDownShelf == 3}">
																		<a style="color: #fff;" href="javascript:void(0);">订单完成</a>
																	</c:if>
																</div>
															</li>
														</div>
													</div>
												</div>
											</div>
										</c:forEach>



										<!--不同状态订单-->

									</div>

								</div>

							</div>
							<div class="am-tab-panel am-fade" id="tab2">

								<div class="order-top">
									<div class="th th-item">
										<td class="td-inner">商品</td>
									</div>
									<div class="th th-price">
										<td class="td-inner">单价</td>
									</div>
									<div class="th th-number">
										<td class="td-inner">数量</td>
									</div>
									<div class="th th-operation">
										<td class="td-inner">商品操作</td>
									</div>
									<div class="th th-amount">
										<td class="td-inner">合计</td>
									</div>
									<div class="th th-status">
										<td class="td-inner">交易状态</td>
									</div>
									<div class="th th-change">
										<td class="td-inner">交易操作</td>
									</div>
								</div>

								<div class="order-main">
									<div class="order-list">
										<c:forEach items="${gsOrderLists}" var="gsOrder" varStatus="i">
											<c:if test="${gsOrder.upDownShelf == 0}">
												<!--交易成功-->
												<div class="order-status5">
													<div class="order-title">
														<div class="dd-num" style="width: 70%; max-width: 600px">
															订单编号：<a href="javascript:;">${gsOrder.id}</a>
														</div>
														<span>成交时间：<fmt:formatDate
																value="${gsOrder.createDate}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</span>
													</div>
													<div class="order-content">
														<div class="order-left">

															<ul class="item-list">
																<li class="td td-item">
																	<div class="item-pic">
																		<a href="#" class="J_MakePoint"> <img
																			src="${fns:getConfig('wy.file.http.path')}${gsOrder.image}"
																			class="itempic J_ItemImg">
																		</a>
																	</div>
																	<div class="item-info">
																		<div class="item-basic-info">
																			<a href="#">
																				<p>${gsOrder.gsName}</p>
																				<p class="info-little">${gsOrder.parameter}</p>
																			</a>
																		</div>
																	</div>
																</li>
																<li class="td td-price">
																	<div class="item-price">
																		${gsOrder.price/gsOrder.gsNumber}</div>
																</li>
																<li class="td td-number">
																	<div class="item-number">
																		<span>×</span>${gsOrder.gsNumber}
																	</div>
																</li>
																<li class="td td-operation">
																	<div class="item-operation"></div>
																</li>
															</ul>

														</div>
														<div class="order-right">
															<li class="td td-amount">
																<div class="item-amount">合计：${gsOrder.price}</div>
															</li>
															<div class="move-right">
																<li class="td td-status">
																	<div class="item-status">
																		<p class="Mystatus">
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
																		</p>
																	</div>
																</li>
																<li class="td td-change">
																	<div class="am-btn am-btn-danger anniu">
																		<c:if test="${gsOrder.upDownShelf == 1}">
																			<a href="javascript:void(0);" style="color: #fff;">待发货</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 0}">
																			<a style="color: #fff;"
																				href="${ctx}/shoppingmall/goods/user/goAlipays?orderId=${gsOrder.id}&sum=${gsOrder.gsNumber}">去付款</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 2}">
																			<a style="color: #fff;"
																				href="${ctx}/shoppingmall/goods/user/shouh?id=${gsOrder.id}">确定收货</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 3}">
																			<a style="color: #fff;" href="javascript:void(0);">订单完成</a>
																		</c:if>
																	</div>
																</li>
															</div>
														</div>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</div>

								</div>
							</div>
							<div class="am-tab-panel am-fade" id="tab3">
								<div class="order-top">
									<div class="th th-item">
										<td class="td-inner">商品</td>
									</div>
									<div class="th th-price">
										<td class="td-inner">单价</td>
									</div>
									<div class="th th-number">
										<td class="td-inner">数量</td>
									</div>
									<div class="th th-operation">
										<td class="td-inner">商品操作</td>
									</div>
									<div class="th th-amount">
										<td class="td-inner">合计</td>
									</div>
									<div class="th th-status">
										<td class="td-inner">交易状态</td>
									</div>
									<div class="th th-change">
										<td class="td-inner">交易操作</td>
									</div>
								</div>

								<div class="order-main">
									<div class="order-list">
										<c:forEach items="${gsOrderLists}" var="gsOrder" varStatus="i">
											<c:if test="${gsOrder.upDownShelf == 1}">
												<!--交易成功-->
												<div class="order-status5">
													<div class="order-title">
														<div class="dd-num" style="width: 70%; max-width: 600px">
															订单编号：<a href="javascript:;">${gsOrder.id}</a>
														</div>
														<span>成交时间：<fmt:formatDate
																value="${gsOrder.createDate}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</span>
													</div>
													<div class="order-content">
														<div class="order-left">

															<ul class="item-list">
																<li class="td td-item">
																	<div class="item-pic">
																		<a href="#" class="J_MakePoint"> <img
																			src="${fns:getConfig('wy.file.http.path')}${gsOrder.image}"
																			class="itempic J_ItemImg">
																		</a>
																	</div>
																	<div class="item-info">
																		<div class="item-basic-info">
																			<a href="#">
																				<p>${gsOrder.gsName}</p>
																				<p class="info-little">${gsOrder.parameter}</p>
																			</a>
																		</div>
																	</div>
																</li>
																<li class="td td-price">
																	<div class="item-price">
																		${gsOrder.price/gsOrder.gsNumber}</div>
																</li>
																<li class="td td-number">
																	<div class="item-number">
																		<span>×</span>${gsOrder.gsNumber}
																	</div>
																</li>
																<li class="td td-operation">
																	<div class="item-operation"></div>
																</li>
															</ul>

														</div>
														<div class="order-right">
															<li class="td td-amount">
																<div class="item-amount">合计：${gsOrder.price}</div>
															</li>
															<div class="move-right">
																<li class="td td-status">
																	<div class="item-status">
																		<p class="Mystatus">
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
																		</p>
																	</div>
																</li>
																<li class="td td-change">
																	<div class="am-btn am-btn-danger anniu">
																		<c:if test="${gsOrder.upDownShelf == 1}">
																			<a href="javascript:void(0);" style="color: #fff;">待发货</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 0}">
																			<a style="color: #fff;"
																				href="${ctx}/shoppingmall/goods/user/goAlipays?orderId=${gsOrder.id}&sum=${gsOrder.gsNumber}">去付款</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 2}">
																			<a style="color: #fff;"
																				href="${ctx}/shoppingmall/goods/user/shouh?id=${gsOrder.id}">确定收货</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 3}">
																			<a style="color: #fff;" href="javascript:void(0);">订单完成</a>
																		</c:if>
																	</div>
																</li>
															</div>
														</div>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="am-tab-panel am-fade" id="tab4">
								<div class="order-top">
									<div class="th th-item">
										<td class="td-inner">商品</td>
									</div>
									<div class="th th-price">
										<td class="td-inner">单价</td>
									</div>
									<div class="th th-number">
										<td class="td-inner">数量</td>
									</div>
									<div class="th th-operation">
										<td class="td-inner">商品操作</td>
									</div>
									<div class="th th-amount">
										<td class="td-inner">合计</td>
									</div>
									<div class="th th-status">
										<td class="td-inner">交易状态</td>
									</div>
									<div class="th th-change">
										<td class="td-inner">交易操作</td>
									</div>
								</div>

								<div class="order-main">
									<div class="order-list">
										<c:forEach items="${gsOrderLists}" var="gsOrder" varStatus="i">
											<c:if test="${gsOrder.upDownShelf == 2}">
												<!--交易成功-->
												<div class="order-status5">
													<div class="order-title">
														<div class="dd-num" style="width: 70%; max-width: 600px">
															订单编号：<a href="javascript:;">${gsOrder.id}</a>
														</div>
														<span>成交时间：<fmt:formatDate
																value="${gsOrder.createDate}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</span>
													</div>
													<div class="order-content">
														<div class="order-left">

															<ul class="item-list">
																<li class="td td-item">
																	<div class="item-pic">
																		<a href="#" class="J_MakePoint"> <img
																			src="${fns:getConfig('wy.file.http.path')}${gsOrder.image}"
																			class="itempic J_ItemImg">
																		</a>
																	</div>
																	<div class="item-info">
																		<div class="item-basic-info">
																			<a href="#">
																				<p>${gsOrder.gsName}</p>
																				<p class="info-little">${gsOrder.parameter}</p>
																			</a>
																		</div>
																	</div>
																</li>
																<li class="td td-price">
																	<div class="item-price">
																		${gsOrder.price/gsOrder.gsNumber}</div>
																</li>
																<li class="td td-number">
																	<div class="item-number">
																		<span>×</span>${gsOrder.gsNumber}
																	</div>
																</li>
																<li class="td td-operation">
																	<div class="item-operation"></div>
																</li>
															</ul>

														</div>
														<div class="order-right">
															<li class="td td-amount">
																<div class="item-amount">合计：${gsOrder.price}</div>
															</li>
															<div class="move-right">
																<li class="td td-status">
																	<div class="item-status">
																		<p class="Mystatus">
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
																		</p>
																	</div>
																</li>
																<li class="td td-change">
																	<div class="am-btn am-btn-danger anniu">
																		<c:if test="${gsOrder.upDownShelf == 1}">
																			<a href="javascript:void(0);" style="color: #fff;">待发货</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 0}">
																			<a style="color: #fff;"
																				href="${ctx}/shoppingmall/goods/user/goAlipays?orderId=${gsOrder.id}&sum=${gsOrder.gsNumber}">去付款</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 2}">
																			<a style="color: #fff;"
																				href="${ctx}/shoppingmall/goods/user/shouh?id=${gsOrder.id}">确定收货</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 3}">
																			<a style="color: #fff;" href="javascript:void(0);">订单完成</a>
																		</c:if>
																	</div>
																</li>
															</div>
														</div>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>

							<div class="am-tab-panel am-fade" id="tab5">
								<div class="order-top">
									<div class="th th-item">
										<td class="td-inner">商品</td>
									</div>
									<div class="th th-price">
										<td class="td-inner">单价</td>
									</div>
									<div class="th th-number">
										<td class="td-inner">数量</td>
									</div>
									<div class="th th-operation">
										<td class="td-inner">商品操作</td>
									</div>
									<div class="th th-amount">
										<td class="td-inner">合计</td>
									</div>
									<div class="th th-status">
										<td class="td-inner">交易状态</td>
									</div>
									<div class="th th-change">
										<td class="td-inner">交易操作</td>
									</div>
								</div>

								<div class="order-main">
									<div class="order-list">
										<!--不同状态的订单	-->
										<c:forEach items="${gsOrderLists}" var="gsOrder" varStatus="i">
											<c:if test="${gsOrder.upDownShelf == 3}">
												<!--交易成功-->
												<div class="order-status5">
													<div class="order-title">
														<div class="dd-num" style="width: 70%; max-width: 600px">
															订单编号：<a href="javascript:;">${gsOrder.id}</a>
														</div>
														<span>成交时间：<fmt:formatDate
																value="${gsOrder.createDate}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</span>
													</div>
													<div class="order-content">
														<div class="order-left">

															<ul class="item-list">
																<li class="td td-item">
																	<div class="item-pic">
																		<a href="#" class="J_MakePoint"> <img
																			src="${fns:getConfig('wy.file.http.path')}${gsOrder.image}"
																			class="itempic J_ItemImg">
																		</a>
																	</div>
																	<div class="item-info">
																		<div class="item-basic-info">
																			<a href="#">
																				<p>${gsOrder.gsName}</p>
																				<p class="info-little">${gsOrder.parameter}</p>
																			</a>
																		</div>
																	</div>
																</li>
																<li class="td td-price">
																	<div class="item-price">
																		${gsOrder.price/gsOrder.gsNumber}</div>
																</li>
																<li class="td td-number">
																	<div class="item-number">
																		<span>×</span>${gsOrder.gsNumber}
																	</div>
																</li>
																<li class="td td-operation">
																	<div class="item-operation"></div>
																</li>
															</ul>

														</div>
														<div class="order-right">
															<li class="td td-amount">
																<div class="item-amount">合计：${gsOrder.price}</div>
															</li>
															<div class="move-right">
																<li class="td td-status">
																	<div class="item-status">
																		<p class="Mystatus">
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
																		</p>
																	</div>
																</li>
																<li class="td td-change">
																	<div class="am-btn am-btn-danger anniu">
																		<c:if test="${gsOrder.upDownShelf == 1}">
																			<a href="javascript:void(0);" style="color: #fff;">待发货</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 0}">
																			<a style="color: #fff;"
																				href="${ctx}/shoppingmall/goods/user/goAlipays?orderId=${gsOrder.id}&sum=${gsOrder.gsNumber}">去付款</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 2}">
																			<a style="color: #fff;"
																				href="${ctx}/shoppingmall/goods/user/shouh?id=${gsOrder.id}">确定收货</a>
																		</c:if>
																		<c:if test="${gsOrder.upDownShelf == 3}">
																			<a style="color: #fff;" href="javascript:void(0);">订单完成</a>
																		</c:if>
																	</div>
																</li>
															</div>
														</div>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</div>

								</div>

							</div>
						</div>

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