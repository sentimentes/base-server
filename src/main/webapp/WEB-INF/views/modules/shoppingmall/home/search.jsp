<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<title>搜索页面</title>
		<link href="${pageContext.request.contextPath}/static/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/static/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
		<link href="${pageContext.request.contextPath}/static/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css" />
		<script src="${pageContext.request.contextPath}/static/common/jeesite.js" type="text/javascript"></script>
		<link href="${pageContext.request.contextPath}/static/common/jeesite.css" type="text/css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/static/basic/css/demo.css" rel="stylesheet" type="text/css" />

		<link href="${pageContext.request.contextPath}/static/css/seastyle.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/static/basic/js/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/script.js"></script>
	</head>
	<script type="text/javascript">
function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
</script>
	<body>

		<%@ include file="/WEB-INF/views/include/index.jsp"%>
			<b class="line"></b>
           <div class="search">
			<div class="search-list">
			<div class="nav-table">
					   <div class="long-title"><span class="all-goods">全部分类</span></div>
			</div>
					<div class="am-g am-g-fixed">
						<div class="am-u-sm-12 am-u-md-12">
	                  	<div class="theme-popover">		
	                  	</br></br>												
							<ul class="select" style="margin-top: 17px;">
								<div class="clear"></div>
								<li class="select-list">
									<dl id="select1">
										<dt class="am-badge am-round">分类</dt>	
										 <div class="dd-conent">
										 <c:if test="${ TjgsId != null}">										
											<dd class="select-all <c:if test="${gsMenuId == null }">selected</c:if>"><a href="${ctx}/shoppingmall/goods/gindex/tjgsTypelist?id=${TjgsId}">全部</a></dd>
											  <c:forEach items="${gsMenuList}" var="gsMenu" varStatus="i" >
											<dd class="<c:if test="${gsMenuId == gsMenu.id }">selected</c:if>"><a href="${ctx}/shoppingmall/goods/gindex/tjgsTypelist?id=${TjgsId}&gsMenuId=${gsMenu.id}&name=${gsMenu.spType1}">${gsMenu.spType1}</a></dd>
											</c:forEach>
											</c:if>
										<c:if test="${TjgsId == null }">				
											<dd class="select-all <c:if test="${id == null }">selected</c:if>"><a href="${ctx}/shoppingmall/goods/gindex/gsMenuList?id=">全部</a></dd>
											  <c:forEach items="${gsMenuList}" var="gsMenu" varStatus="i" >
											<dd class="<c:if test="${id == gsMenu.id }">selected</c:if>"><a href="${ctx}/shoppingmall/goods/gindex/gsMenuList?id=${gsMenu.id}&name=${gsMenu.spType1}">${gsMenu.spType1}</a></dd>
											</c:forEach>
										 </c:if>
										 </div>
									</dl>
								</li>      
							</ul>
							</br>
							<div class="clear"></div>
                        </div>
							<div class="search-content">
							<form:form id="searchForm" modelAttribute="gsMenu" action="${ctx}/shoppingmall/goods/gindex/gsMenuList?id=${id}" method="post" class="breadcrumb form-search">
								<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
								<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
								</form:form>
								<c:if test="${gsTjgsList[0] != null }">
								<ul class="am-avg-sm-2 am-avg-md-3 am-avg-lg-5 boxes">
									  <c:forEach items="${gsTjgsList}" var="gsTjgs" varStatus="i" >
									<li>
										<div class="i-pic limit">
												<a href="${ctx}/shoppingmall/goods/gindex/goodsone?id=${gsTjgs.gsId} "><img style="height: 218px;" src="${fns:getConfig('wy.file.http.path')}${gsTjgs.image}" /></a>										
											<p class="title fl">${gsTjgs.gsName}</p>
											<p class="number fl" style="font-size: 14px;">
												<b>原价¥</b>
												<strong><s>${gsTjgs.price}</s></strong>
											</p>
											<p class="price fl" style="font-size: 14px;">
												促销价¥<span>${gsTjgs.salePrice}</span>
											</p>
										</div>
									</li>
									</c:forEach>
								</ul>
								</c:if>
								
								<c:if test="${page.list[0] != null}">
								<ul class="am-avg-sm-2 am-avg-md-3 am-avg-lg-5 boxes">
									  <c:forEach items="${page.list}" var="gsGoods" varStatus="i" >
									<li>
										<div class="i-pic limit">
											<a href="${ctx}/shoppingmall/goods/gindex/goodsone?id=${gsGoods.id} "><img style="height: 218px;" src="${fns:getConfig('wy.file.http.path')}${gsGoods.image}" /></a>			
											<p class="title fl">${gsGoods.name}</p>
											<p class="number fl">
												<b>原价¥</b>
												<strong><s>${gsGoods.price}</s></strong>
											</p>
											<p class="price fl">
												促销价¥<span>${gsGoods.salePrice}</span>
											</p>
										</div>
									</li>
									</c:forEach>
								</ul>
								<div class="pagination" style="margin-bottom: 50px;">${page}</div>
								</c:if>
							</div>
						
							<div class="clear"></div>
							

						</div>
					</div>
				</div>

			</div>

		<div class="navCir">
			<li class="active"><a href="${ctx}/shoppingmall/goods/gindex"><i class="am-icon-home "></i>首页</a></li>
			<li><a href="${ctx}/shoppingmall/goods/gindex/shopcart"><i class="am-icon-shopping-basket"></i>购物车</a></li>	
			<li><a href="${ctx}/shoppingmall/goods/user/people"><i class="am-icon-user"></i>我的</a></li>					
		</div>
		
	</body>

</html>