<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<title>购物车页面</title>

<link
	href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/static/basic/css/demo.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/cartstyle.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/optstyle.css"
	rel="stylesheet" type="text/css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery.js"></script>

</head>
<script type="text/javascript">
			function updateSort() {
		    	$("#searchForm").attr("action", "${ctx}/shoppingmall/goods/gindex/jiesuan");
		    	$("#searchForm").submit();
			}	
			function selectAllDels() {
				var allCheckBoxs = document.getElementsByName("idList");
				var desc = document.getElementById("allChecked");
				var selectOrUnselect = false;
				for (var i = 0; i < allCheckBoxs.length; i++) {
					if (allCheckBoxs[i].checked) {
						selectOrUnselect = true;
						break;
					}
				}
				if (selectOrUnselect) {
					_allUnchecked(allCheckBoxs);
				} else {
					_allchecked(allCheckBoxs);
				}
			}
			function _allchecked(allCheckBoxs) {
				for (var i = 0; i < allCheckBoxs.length; i++) {
					allCheckBoxs[i].checked = true;
				}
			}
			function _allUnchecked(allCheckBoxs) {
				for (var i = 0; i < allCheckBoxs.length; i++) {
					allCheckBoxs[i].checked = false;
				}
			}
	</script>
	
<body>

	<div class="hmtop">
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
						<div class="menu-hd"><a id="mc-menu-hd" href="${ctx}/shoppingmall/goods/gindex/shopcart" target="_top"><i class="am-icon-shopping-cart  am-icon-fw"></i><span>购物车</span><strong id="J_MiniCartNum" class="h">0</strong></a></div>
					</div>
					<div class="topMessage favorite">
						<div class="menu-hd"><a href="${ctx}/shoppingmall/goods/gindex/list?ss=2" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>退出该商店</span></a></div>
				</ul>
				</div>
				<!--悬浮搜索框-->
				<div class="nav white">
					<div class="logo" style="font-size: 15px;padding: 5px;color: red;">${office.name}</div>
					<div class="logoBig" >
						<li style="font-size: 36px;color: red; margin-top: 34px; float: right;">${office.name}</li>
					</div>

					<div class="search-bar pr">
						<a name="index_none_header_sysc" href="#"></a>
						<form:form id="inputForm" modelAttribute="gsGoods" action="${ctx}/shoppingmall/goods/gindex/goodsSame" method="post" class="form-horizontal">
							<form:input path="name" id="searchInput" name="index_none_header_sysc" type="text" placeholder="搜索" autocomplete="off" />
							<input id="ai-topsearch" class="submit am-btn" value="搜索" index="1" type="submit">
						</form:form>
					</div>
				</div>

				<div class="clear"></div>
			</div>
			

	<!--购物车 -->
	<div class="concent">
		<div id="cartTable">
			<div class="cart-table-th">
			<span style="color: red; font-size: 18px;">${message}</span>
				<div class="wp">
					<div class="th th-chk">
						<div id="J_SelectAll1" class="select-all J_SelectAll"></div>
					</div>
					<div class="th th-item">
						<div class="td-inner">商品信息</div>
					</div>
					<div class="th th-price">
						<div class="td-inner">单价</div>
					</div>
					<div class="th th-amount">
						<div class="td-inner">数量</div>
					</div>
					<div class="th th-sum">
						<div class="td-inner">金额</div>
					</div>
					<div class="th th-op">
						<div class="td-inner">操作</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>

			<tr class="item-list">
				<div class="bundle  bundle-last ">
					<div class="bundle-main">
					<form:form id="searchForm" modelAttribute="GsShoppingCart" action="${ctx}/shoppingmall/goods/gindex/jiesuan" method="post" class="breadcrumb form-search">
						<c:forEach items="${gsShoppingCartList}" var="gsShoppingCart"
							varStatus="i">
							<ul class="item-content clearfix">
							
								<li class="td td-chk">
									<div class="cart-checkbox" >
									<input class="check" id="J_CheckBox_170037950254" name="idList" value="${gsShoppingCart.id}" type="checkbox"/>
										<label for="J_CheckBox_170037950254"></label>
									</div>
								</li>

								<li class="td td-item">
									<div class="item-pic">
										<a href="#" target="_blank"
											data-title="${gsShoppingCart.gsName}" class="J_MakePoint"
											data-point="tbcart.8.12"> <img
											src="${fns:getConfig('wy.file.http.path')}${gsShoppingCart.image}"
											class="itempic J_ItemImg" style="width: 100%; height: 100%;"></a>
									</div>
									<div class="item-info">
										<div class="item-basic-info">
											<a href="#" target="_blank" title="${gsShoppingCart.gsName}"
												class="item-title J_MakePoint" data-point="tbcart.8.11">${gsShoppingCart.gsName}</a>
										</div>
									</div>
								</li>
								<li class="td td-info">
									<div class="item-props">
										<span class="sku-line">${gsShoppingCart.parameter}</span>
									</div>
								</li>
								<li class="td td-price">
									<div class="item-price price-promo-promo">
										<div class="price-content">
											<div class="price-line">
												<em class="price-original" style="left: 80px;">${gsShoppingCart.price}</em>
											</div>
											<div class="price-line" >
												<sapn class="J_Price price-now" tabindex="0"  id="price${i.index}">${gsShoppingCart.salePrice}</span>
											</div>
										</div>
									</div>
								</li>
								<li class="td td-amount">
									<div class="amount-wrapper ">
										<div class="item-amount ">
											<div class="sl">
												<input class="min am-btn" name="" type="button" value="-" onclick="jian${i.index}();"/>
												<input class="text_box" name="" type="text"
													value="${gsShoppingCart.number}" style="width: 30px;" id="numbers${i.index}"/>
											    <input class="add am-btn" name="" type="button" value="+" onclick="jia${i.index}();"/>
											</div>
										</div>
									</div>
								</li>

								<li class="td td-sum">
									<div class="td-inner">
										<em tabindex="0" class="J_ItemSum number" id="jiage${i.index}">${gsShoppingCart.number*gsShoppingCart.salePrice}</em>
									</div>
								</li>
								<li class="td td-op">
									<div class="td-inner">
										<a class="delete" href="${ctx}/shoppingmall/goods/user/deleteShoppingCart?id=${gsShoppingCart.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a>
										
									</div>
								</li>
							</ul>
							
							<script type="text/javascript">
    function jia${i.index}() {
    	debugger
    	 var q5=$("#price${i.index}").text();
    	 var q6=$("#numbers${i.index}").val();
    	 var q7=q5*(parseInt(q6)+1);
   	 	$("#jiage${i.index}").text(q7);
       }
    	function jian${i.index}() {
    	debugger
    	 var q5=$("#price${i.index}").text();
    	 var q6=$("#numbers${i.index}").val();
    	 var q8=parseInt(q6);
    	 if(q8>=2){
    		var q7=q5*(q8-1); 
    	 }else{
    		 var q7=q5*q8; 
    	 }
    	 
   	 $("#jiage${i.index}").text(q7);
     }
</script>
						</c:forEach>
						</form:form>
					</div>
				</div>
			</tr>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>

		<!-- <div class="float-bar-wrapper">
			<div class="float-bar-right">
				<div class="amount-sum">
					<div class="arrow-box">
						<span class="selected-items-arrow"></span> <span class="arrow"></span>
					</div>
				</div>
				<div class="btn-area">
				  <input id="J_Go" class="btn btn-primary"  onclick="updateSort();" type="button" title="结&nbsp;算" value="结&nbsp;算" style="margin-right: 149px;width: 97px;border: 1px solid #FFEDED; background-color: #F03726; color: #FFEDED; height: 48px;line-height: 48px"/>
				<a  onclick="updateSort();" id="J_Go" class="submit-btn submit-btn-disabled" aria-label="请注意如果没有选择宝贝，将无法结算">
								<span>结&nbsp;算</span></a>
				</div>
			</div>

		</div> -->
		<div class="float-bar-wrapper">
					<div id="J_SelectAll2" class="select-all J_SelectAll">
						<div class="cart-checkbox">
							<input class="check-all check"  id="allChecked" onClick="selectAllDels()" name="select-all" value="true" type="checkbox">
							<label for="J_SelectAllCbx2"></label>
							
						</div>
						<span>全选</span>
					</div>
					
					<div class="float-bar-right">
						
						
						<div class="btn-area">
				  		<a  onclick="updateSort();" id="J_Go" class="submit-btn submit-btn-disabled" aria-label="请注意如果没有选择宝贝，将无法结算">
							<span>结&nbsp;算</span></a>
				</div>
					</div>

				</div>
		<div class="footer">

					<div class="footer-bd" >
						<p style="margin: 26px 326px; width: 100%;">
							<em>© 2019 商城 www.leixoaming.cn All Rights Reserved</em>
						</p>
					</div>
				</div>

	</div>

	<!--引导 -->
	<div class="navCir">
			<li class="active"><a href="${ctx}/shoppingmall/goods/gindex"><i class="am-icon-home "></i>首页</a></li>
			<li><a href="${ctx}/shoppingmall/goods/gindex/shopcart"><i class="am-icon-shopping-basket"></i>购物车</a></li>	
			<li><a href="${ctx}/shoppingmall/goods/user/people"><i class="am-icon-user"></i>我的</a></li>					
		</div>

</body>

</html>