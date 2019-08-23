<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<title>商品页面</title>

		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="${pageContext.request.contextPath}/static/css/optstyle.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/basic/js/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/basic/js/quick_links.js"></script>

		<script type="text/javascript" src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.imagezoom.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.flexslider.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/list.js"></script>
	<script type="text/javascript">
			function updateSort() {
		    	$("#searchForm").attr("action", "${ctx}/shoppingmall/goods/gindex/savecard");
		    	$("#searchForm").submit();
			}	
			function updateSorts() {
		    	$("#searchForm").attr("action", "${ctx}/shoppingmall/goods/gindex/jiesuans");
		    	$("#searchForm").submit();
			}	
	</script>
	</head>

	<body>


		<%@ include file="/WEB-INF/views/include/index.jsp"%>
            <b class="line"></b>
			<div class="listMain">
				<!--分类-->
			<div class="nav-table">
					
				</div>
				<ol class="am-breadcrumb am-breadcrumb-slash">
					<li><a href="#">首页</a></li>
					<li><a href="#">分类</a></li>
					<li class="am-active">内容</li>
				</ol>
				<script type="text/javascript">
					$(function() {});
					$(window).load(function() {
						$('.flexslider').flexslider({
							animation: "slide",
							start: function(slider) {
								$('body').removeClass('loading');
							}
						});
					});
				</script>
				<div class="scoll">
					<section class="slider">
						<div class="flexslider">
							<ul class="slides">
								<li>
									<img src="${fns:getConfig('wy.file.http.path')}${gsGoods.image}"  />
								</li>
								<li>
									<img src="${fns:getConfig('wy.file.http.path')}${gsGoods.image}"  />
								</li>
								<li>
									<img src="${fns:getConfig('wy.file.http.path')}${gsGoods.image}" />
								</li>
							</ul>
						</div>
					</section>
				</div>

				
				<!--放大镜-->

				<div class="item-inform">
					<div class="clearfixLeft" id="clearcontent">

						<div class="box">
							<script type="text/javascript">
								$(document).ready(function() {
									$(".jqzoom").imagezoom();
									$("#thumblist li a").click(function() {
										$(this).parents("li").addClass("tb-selected").siblings().removeClass("tb-selected");
										$(".jqzoom").attr('src', $(this).find("img").attr("mid"));
										$(".jqzoom").attr('rel', $(this).find("img").attr("big"));
									});
								});
							</script>
							<div class="tb-booth tb-pic tb-s310">
								<a href="${pageContext.request.contextPath}/static/images/01.jpg"><img src="${fns:getConfig('wy.file.http.path')}${gsGoods.image}"  /></a>
							</div>
						</div>

						<div class="clear"></div>
					</div>

					<div class="clearfixRight">

						<!--规格属性-->
						<!--名称-->
						<div class="tb-detail-hd">
							<h1>${gsGoods.name}</h1>
						</div>
						<div class="tb-detail-list">
							<!--价格-->
							<span style="color: red; font-size: 18px;">${message}</span>
							<div class="tb-detail-price">
							
								<c:if test="${gsGoods.salePrice != gsGoods.price}">
								<li class="price iteminfo_price">
									<dt>促销价</dt>
									<dd><em>¥</em><b class="sys_item_price">${gsGoods.salePrice}</b>  </dd>                                 
								</li>
								<li class="price iteminfo_mktprice">
									<dt>原价</dt>
									<dd><em>¥</em><b class="sys_item_mktprice">${gsGoods.price}</b></dd>									
								</li>
								</c:if> 
								<c:if test="${gsGoods.salePrice == gsGoods.price}">
								<li class="price iteminfo_price">
									<dt>商品价格</dt>
									<dd><em>¥</em><b class="sys_item_price">${gsGoods.salePrice}</b>  </dd>                                 
								</li>
								</c:if> 
								<div class="clear"></div>
							</div>


							<!--各种规格-->
							<dl class="iteminfo_parameter sys_item_specpara">
								<dt class="theme-login"><div class="cart-title">可选规格<span class="am-icon-angle-right"></span></div></dt>
								<dd>
									<!--操作页面-->
									<div class="theme-popover-mask"></div>
 									<% int ss=0; %>
									<div class="theme-popover">
										<div class="theme-span"></div>
										<div class="theme-poptit">
											<a href="javascript:;" title="关闭" class="close">×</a>
										</div>
										<div class="theme-popbod dform">
											<form:form id="searchForm" modelAttribute="gsShoppingCart" action="${ctx}/shoppingmall/goods/gindex/savecard" method="post" class="breadcrumb form-search">
												<form:input path="gsId" type="hidden" htmlEscape="false" maxlength="64" class="input-medium" value="${gsGoods.id}"/>
												<div class="theme-signin-left">
												
													<c:forEach items="${gsMenuSmallList}" var="gsMenuSmall" varStatus="i">
													 <form:input path="parameter" id="sssq${i.index}" type="hidden" maxlength="64"  value=""/>
													<c:if test="${gsMenuSmall.gsMenuId == gsGoods.menuId}">
													<div class="theme-options">
													 <input type="hidden" id="sssw<%=ss %>" value="${gsMenuSmall.name}:">
													 <input type="hidden" id="sss<%=ss %>" value="<%=ss %>">
													
														<div class="cart-title">${gsMenuSmall.name}</div>
														<ul id="test">
														 <% int s=0; %>
														<c:forEach items="${gsTypeList}" var="gsType" varStatus="j">
															 <c:if test="${gsMenuSmall.name == gsType.smName}">
															 <% s++;%>
															<c:if test="<%=s<2 %>">
															<li id="<%=ss %>" class="sku-line  selected<%=ss %>">${gsType.vaName}</li>
															 </c:if> 
															 <c:if test="<%=s>=2 %>">
															<li id="<%=ss %>" class="sku-line ">${gsType.vaName}</li>
															 </c:if> 
															  </c:if> 
															</c:forEach>
														</ul>
														
							<script type="text/javascript">
								$(document).ready(function() {
										var q5=$("#sss<%=ss %>").val();
										var q6=$("#sssw<%=ss %>").val();
										var tt=$(".selected"+q5).html();
										$("#sssq<%=ss %>").val(q6+tt);
										/* alert(); */
										 $('li').click(function(){
											
											 tt=$(this).html();
												var rr=this.id;
											var q6=$("#sssw"+rr).val();
											 $("#sssq"+rr).val(q6+tt);
										}) ;
										$(".theme-options").each(function() {
										var i = $(this);
										var p = i.find("ul>li");
										p.click(function() {
											if (!!$(this).hasClass("selected<%=ss %>")) {
												$(this).removeClass("selected<%=ss %>");
											} else {
												$(this).addClass("selected<%=ss %>").siblings("li").removeClass("selected<%=ss %>");
											}

										})
									})
									
								});
							</script>
							 <% ss++;%>
											</div>
										</c:if>			
							</c:forEach>
													<div class="theme-options">
														<div class="cart-title number">数量</div>
														<dd>
															<input id="min" class="am-btn am-btn-default" name="" type="button" value="-" />
														 	<form:input id="text_box" path="number"  type="text" maxlength="64"  value="1" style="width:30px;"/>
															<input id="add" class="am-btn am-btn-default" name="" type="button" value="+" />
															<span id="Stock" class="tb-hidden">库存<span class="stock">${gsGoods.number}</span>件</span>
														</dd>

													</div>
													<div class="clear"></div>

													<div class="btn-op">
														<div class="btn am-btn am-btn-warning">确认</div>
														<div class="btn close am-btn am-btn-warning">取消</div>
													</div>
												</div>
												<div class="theme-signin-right">
													<div class="img-info">
														<img src="../images/songzi.jpg" />
													</div>
													<div class="text-info">
														<span class="J_Price price-now">¥39.00</span>
														<span id="Stock" class="tb-hidden">库存<span class="stock">${gsGoods.number}</span>件</span>
													</div>
												</div>

												</form:form>
										</div>
									</div>

								</dd>
							</dl>
							
							
						</div>

						<div class="pay">
							<div class="pay-opt">
							<a href="${ctx}/shoppingmall/goods/gindex"><span class="am-icon-home am-icon-fw">返回首页</span></a>
							
							</div>
							<li>
								<div class="clearfix tb-btn tb-btn-buy theme-login">
								<!-- 	<a id="LikBuy" title="点此按钮到下一步确认购买信息" href="#">立即购买</a> -->   
						<input id="btnSubmit2" class="btn btn-primary"  onclick="updateSort();" type="button" title="加入购物车" value="加入购物车" style="width: 98px;
						border: 1px solid #F03726; background-color: #FFEDED; color: #F03726;height:30px;line-height: 30px;"/>
								</div>
							</li>
							<li>
								<div class="clearfix tb-btn tb-btn-basket theme-login">
									<!-- <a id="LikBasket" title="加入购物车" href="#"><i></i>加入购物车</a> -->
							<input id="LikBuy"  type="button" value="立即购买" onclick="updateSorts();" title="立即购买" style="background-color:#F03726;color:#FFF;width: 98px;
							float: left;height: 30px;border: 1px solid #F03726;"/>
								</div>
							</li>
						</div>

					</div>

					<div class="clear"></div>

				</div>

				
							
				<!-- introduce-->
				
				<div class="introduce">
					
					<div class="introduceMain">
						<div class="am-tabs" data-am-tabs>
							<ul class="am-avg-sm-3 am-tabs-nav am-nav am-nav-tabs">
								<li class="am-active">
									<a href="#">
										<span class="index-needs-dt-txt">宝贝详情</span></a>
								</li>
							</ul>
							<div class="am-tabs-bd">

								<div class="am-tab-panel am-fade am-in am-active">
									<div class="details">
										<div class="attr-list-hd after-market-hd">
											<h4>商品细节</h4>
											<a href="${ctx}/shoppingmall/goods/gindex/list?gsofficeId=${gsGoods.office.id}" style="font-size:24px;">进入该商店</a></li>
										</div>
										<div class="twlistNews">
											
										<div id="ss" hidden="hidden">
											${gsGoods.details}
										</div>
										<div id="sss" style="max-width:1200px;margin: 20px auto;">
										</div>
										<script>
										$(function(){
										$("#sss").html($("#ss").text())
											});
										</script>
									</div>
									<div class="clear"></div>
								</div>
							</div>

						</div>

						<div class="clear"></div>

					<div class="footer">

					<div class="footer-bd" >
						<p style="margin: 26px 413px;">
							<em>© 2019 商城 www.leixoaming.cn All Rights Reserved</em>
						</p>
					</div>
				</div>
					</div>

				</div>
		
			</div>
		

	</body>

</html>