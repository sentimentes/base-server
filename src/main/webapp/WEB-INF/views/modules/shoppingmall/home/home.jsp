<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
		
		<title>首页</title>
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/css/hmstyle.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/static/css/skin.css" rel="stylesheet" type="text/css" />
		<script src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/amazeui.min.js"></script>
	</head>

	<body>
		<%@ include file="/WEB-INF/views/include/index.jsp"%>
			<div class="banner">
                      <!--轮播 -->
						<div class="am-slider am-slider-default scoll" data-am-flexslider id="demo-slider-0">
							<ul class="am-slides">
							<c:forEach items="${gsNavigationList}" var="gsNavigation" varStatus="i">
							<li class="banner${i.index+1}"><a href="${ctx}/shoppingmall/goods/gindex/navigation?id=${gsNavigation.id}"><img src="${fns:getConfig('wy.file.http.path')}${gsNavigation.image}" /></a></li>
							</c:forEach>
							</ul>
						</div>
						<div class="clear"></div>	
			</div>
			<div class="shopNav">
				<div class="slideall" style="max-width: 1311px;">
					
					   <div class="long-title"><span class="all-goods">全部分类</span></div>
					  
						<!--侧边导航 -->
						<div id="nav" class="navfull">
							<div class="area clearfix">
								<div class="category-content" id="guide_2">
									<div class="category">
										<ul class="category-list" id="js_climit_li">
											<c:forEach items="${gsMenuList}" var="gsMenu" varStatus="i">
											<li class="appliance js_toggle relative ">
												<div class="category-info">
													<h3 class="category-name b-category-name"><i><img src="${pageContext.request.contextPath}/static/images/cake.png"></i><a class="ml-22" title="${gsMenu.spType1}" href="${ctx}/shoppingmall/goods/gindex/gsMenuList?id=${gsMenu.id}&name=${gsMenu.spType1}">${gsMenu.spType1}</a></h3>
													<em>&gt;</em></div>
											<b class="arrow"></b>	
											</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
						</div>
						
						
						<!--轮播-->
						
						<script type="text/javascript">
							(function() {
								$('.am-slider').flexslider();
							});
							$(document).ready(function() {
								$("li").hover(function() {
									$(".category-content .category-list li.first .menu-in").css("display", "none");
									$(".category-content .category-list li.first").removeClass("hover");
									$(this).addClass("hover");
									$(this).children("div.menu-in").css("display", "block")
								}, function() {
									$(this).removeClass("hover")
									$(this).children("div.menu-in").css("display", "none")
								});
							})
						</script>
					<!--走马灯 -->
					<div class="marqueen" style="right: -43px;">
						<span class="marqueen-title">商城头条</span>
						<div class="demo">

						<ul>
						<div class="mod-vip">
							<div class="m-baseinfo">
								<a href="${ctx}/shoppingmall/goods/user/people">
								<c:if test="${gsUser.image != null}">
								<img src="${fns:getConfig('wy.file.http.path')}${gsUser.image}">
								</c:if>
									<c:if test="${gsUser.image == null}">
									<img src="${pageContext.request.contextPath}/static/images/getAvatar.do.jpg">
									</c:if>
								</a>
								<em style="top:-5px">
									<c:if test="${sessionScope.name != null}">
									<span class="s-name"><c:if test="${gsDj.name != null}">
									${gsDj.name}
									</c:if>	
									<c:if test="${gsDj.name == null}">
									会员	
									</c:if>	:${sessionScope.name}</span>	<br>
									积分：<c:if test="${sessionScope.jifei != null}">
									${sessionScope.jifei}
									</c:if>	
									<c:if test="${sessionScope.jifei == null}">
									0
									</c:if>	<br>
									折扣：<c:if test="${gsDj.zk != null}">
									${gsDj.zk}
									</c:if>	
									<c:if test="${gsDj.zk == null}">
									1
									</c:if>	
									</c:if>	
									<c:if test="${sessionScope.name == null}">
									<span class="s-name">请登录</span>	
									</c:if>					
								</em>
						</div>
						<c:if test="${sessionScope.name == null}">
							<div class="member-logout">
								<a class="am-btn-warning btn" href="${ctx}/shoppingmall/goods/user/">登录</a>
								<a class="am-btn-warning btn" href="${ctx}/shoppingmall/goods/user/register">注册</a>
							</div>
							<div class="clear"></div>
						</c:if>		
						</div>	
						<c:forEach items="${gsActivityList}" var="gsActivity" varStatus="i">
							<li><a  href="${ctx}/shoppingmall/goods/gindex/activity?id=${gsActivity.id}"><span>[<c:if test="${gsActivity.types == 1}">&nbsp;公告</c:if>
							<c:if test="${gsActivity.types == 2}">特惠&nbsp;</c:if>]</span>&nbsp;${gsActivity.name}</a></li>
						</c:forEach>
						</ul>
                        <div class="advTip"><img src="${pageContext.request.contextPath}/static/images/advTip.jpg"/></div>
						</div>
					</div>
					<div class="clear"></div>
				</div>
				<script type="text/javascript">
					if ($(window).width() < 640) {
						function autoScroll(obj) {
							$(obj).find("ul").animate({
								marginTop: "-39px"
							}, 500, function() {
								$(this).css({
									marginTop: "0px"
								}).find("li:first").appendTo(this);
							})
						}
						$(function() {
							setInterval('autoScroll(".demo")', 3000);
						})
					}
				</script>
			</div>
			<div class="shopMainbg">
				<div class="shopMain" id="shopmain">

					<!--今日推荐 -->

					<div class="am-g am-g-fixed recommendation">
						<div class="clock am-u-sm-3" ">
							<img src="${pageContext.request.contextPath}/static/images/2016.png "></img>
							<p>今日<br>推荐商家</p>
						</div>
						<c:forEach items="${officeList}" var="office" varStatus="i" >
						<c:if test="${i.index<3}">
						<div class="am-u-sm-4 am-u-lg-3 ">
							<div class="info ">
								<h3>${office.address}</h3>
								<h4>${office.name}</h4>
							</div>
							<a  href="${ctx}/shoppingmall/goods/gindex/list?gsofficeId=${office.id}">
							<div class="recommendationMain three">
								<img src="${pageContext.request.contextPath}/static/images/tj1${i.index+1}.jpg "></img>
							</div>
							</a>
						</div>
						</c:if>
						</c:forEach>
					</div>
					<div class="clear "></div>
					<!--热门活动 -->
					
					<div class="am-container activity ">
						<div class="shopTitle ">
							<h4>活动商品</h4>
							<h3>每期活动 优惠享不停 </h3>
						<!-- 	<span class="more ">
                              <a href="# ">全部活动<i class="am-icon-angle-right" style="padding-left:10px ;" ></i></a>
                        </span> -->
						</div>
					  <div class="am-g am-g-fixed ">
					  <c:forEach items="${tjgsTypeList}" var="tjgsType" varStatus="i" >
						<c:if test="${i.index<4}">
						<div class="am-u-sm-3 ">
						<a  href="${ctx}/shoppingmall/goods/gindex/tjgsTypelist?id=${tjgsType.id}">
							<div class="icon-sale <c:if test="${i.index+1==1}">one</c:if>
							<c:if test="${i.index+1==2}">two</c:if>
							<c:if test="${i.index+1==3}">three</c:if>
							<c:if test="${i.index+1==4}"></c:if>"></div>	
								<h4>${tjgsType.name}</h4>							
							<div class="activityMain ">
								<img src="${fns:getConfig('wy.file.http.path')}${tjgsType.image}" ></img>
							</div>
							<!-- <div class="info ">
								<h3>春节送礼优选</h3>
							</div>	 -->	
						</a>												
						</div>
						</c:if>
						</c:forEach>
					  </div>
                   </div>
					<div class="clear "></div>
  <div id="f1">
					<!--甜点-->
					
					<c:forEach items="${gsMenuList}" var="gsMenu" varStatus="i">
											
					<div id="f${i.index+1}">
					<!--甜点-->
					<div class="am-container ">
						<div class="shopTitle ">
							<h4>${gsMenu.spType1}</h4>
							<span class="more ">
                    <a class="ml-22" title="${gsMenu.spType1}" href="${ctx}/shoppingmall/goods/gindex/gsMenuList?id=${gsMenu.id}&name=${gsMenu.spType1}">更多商品</a><i class="am-icon-angle-right" style="padding-left:10px ;" ></i>
                        </span>
						</div>
					</div>

					<div class="am-g am-g-fixed floodFour">
						<div class="am-u-sm-5 am-u-md-4 text-one list ">
						
							<div class="word">
								<% int s=0; %>
								<c:forEach items="${gsMenuValueList}" var="gsMenuValue" varStatus="k">
								<c:if test="${gsMenuValue.gsMenuId==gsMenu.id}">
								<% s++;%>
								<c:if test="<%=s<7 %>">
									<a class="outer" href="javacript:void(0);"><span class="inner"><b class="text">${gsMenuValue.name}</b></span></a>
								</c:if>
								</c:if>
								</c:forEach>	
															
							</div>
							<%-- <span style="    float: left; margin: 70px; font-size: 30px; width: 100px;">${gsMenu.spType1}</span> --%>
						</div>
							<% int s1=0; %>
							<c:forEach items="${gsGoodsList}" var="gsGoods" varStatus="j">
							
							<c:if test="${fn:containsIgnoreCase(gsGoods.menuName, gsMenu.spType1)}">
							<% s1++;%>
							<c:if test="<%= s1<9 %>">
							<div class="am-u-sm-6  text-two
							<%-- <c:if test="<%= s1==2 %>">am-u-sm-7 am-u-md-4 text-two</c:if>
							<c:if test="<%= s1==3 %>">am-u-sm-3 am-u-md-2 text-three big</c:if>
							<c:if test="<%= s1==4 %>">am-u-sm-3 am-u-md-2 text-three sug</c:if>		
							<c:if test="<%= s1==5 %>">am-u-sm-3 am-u-md-2 text-three</c:if>
							<c:if test="<%= s1==6 %>">am-u-sm-3 am-u-md-2 text-three last big</c:if>--%>"> 
								<div class="outer-con ">
									<div class="title ">
										${gsGoods.name}
									</div>									
									<div class="sub-title ">
									<c:if test="${gsGoods.salePrice == null}">	
										${gsGoods.price}
										</c:if>
										<c:if test="${gsGoods.salePrice != null}">	
										${gsGoods.salePrice}
										</c:if>
									</div>
									<!-- <i class="am-icon-shopping-basket am-icon-md  seprate"></i> -->
<!-- leixiaoming 商品详情 -->			</div>
								<a href="${ctx}/shoppingmall/goods/gindex/goodsone?id=${gsGoods.id} "><img src="${fns:getConfig('wy.file.http.path')}${gsGoods.image}" /></a>
							</div>
							</c:if>
							</c:if>
								</c:forEach>

					</div>
                 <div class="clear "></div>  
                 </div>
                    </c:forEach>
					<div class="footer">

					<div class="footer-bd" >
						<p style="margin: 26px 413px;">
							<em>© 2019 商城 www.leixoaming.cn All Rights Reserved</em>
						</p>
					</div>
				</div>

		</div>
		</div>
		<div class="navCir">
			<li class="active" style="width: 25%;"><a href="${ctx}/shoppingmall/goods/gindex"><i class="am-icon-home "></i>首页</a></li>
			<li style="width: 25%;"><a href="${ctx}/shoppingmall/goods/gindex/shopcart"><i class="am-icon-shopping-basket"></i>购物车</a></li>
			<li style="width: 25%;"><a href="${ctx}/shoppingmall/goods/gindex/list?ss=2"><i class="am-icon-list"></i>退出该商城</a></li>	
			<li style="width: 25%;"><a href="${ctx}/shoppingmall/goods/user/people"><i class="am-icon-user"></i>我的</a></li>					
		</div>
		


		
		<script>
			window.jQuery || document.write('<script src="basic/js/jquery.min.js "><\/script>');
		</script>
		<script type="text/javascript " src="${pageContext.request.contextPath}/static/basic/js/quick_links.js "></script>
	</body>

</html>