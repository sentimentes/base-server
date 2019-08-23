<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">

		<title>地址管理</title>

		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/css/personal.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/css/addstyle.css" rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/jquery.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/amazeui.js"></script>

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

					<div class="user-address">
						<!--标题 -->
						<div class="am-cf am-padding">
							<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">地址管理</strong> / <small>Address&nbsp;list</small></div>
						</div>
						<hr/>
						<ul class="am-avg-sm-1 am-avg-md-3 am-thumbnails">
					<c:forEach items="${gsAddressList}" var="gsAddress" varStatus="i">
							<li class="user-addresslist " style="margin-left: 62px;margin-top: 10px;">
								<p class="new-tit new-p-re">
									<span class="new-txt">${gsAddress.name} </span>
									<span class="new-txt-rd2">${gsAddress.phone}</span>
									
								</p>
								<div class="new-mu_l2a new-p-re">
									<p class="new-mu_l2cw">
										<span class="title">地址：</span>
										 <span class="province">${gsAddress.upDownShelf}</span>
								</div>
								<div class="new-addr-btn">
									<a href="${ctx}/shoppingmall/goods/user/addressup?id=${gsAddress.id}"><i class="am-icon-edit"></i>编辑</a>
									<span class="new-addr-bar">|</span>
									<a href="${ctx}/shoppingmall/goods/gindex/addtrssDels?id=${gsAddress.id}" onclick="return confirmx('确认要删除该用户管理吗？)"><i class="am-icon-trash"></i>删除</a>
								</div>
							</li>
						</c:forEach>
						</ul>
						<div class="clear"></div>
						<a class="new-abtn-type" data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0}">添加新地址</a>
						<!--例子-->
						<div class="am-modal am-modal-no-btn" id="doc-modal-1">

							<div class="add-dress">

								<!--标题 -->
								<div class="am-cf am-padding">
									<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">${not empty gsAddress.id?'修改':'新增'  }地址</strong> / <small>${not empty gsAddress.id?'update':'add'}&nbsp;address</small></div>
								</div>
								<hr/>
								<div class="am-u-md-12 am-u-lg-8" style="margin-top: 20px;">
									
								<form:form id="listForm" modelAttribute="gsAddress"  action="${ctx}/shoppingmall/goods/gindex/addressSaves" method="post" class="am-form am-form-horizontal" >
										<form:hidden path="id"  id="ids"/>
										<div class="am-form-group">
											<label for="user-name" class="am-form-label">收货人</label>
											<div class="am-form-content">
												<form:input path="name" htmlEscape="false" maxlength="255" type="text" id="user-name" placeholder="收货人" class="input-xlarge required" autocomplete="off"/>
											</div>
										</div>
										<div class="am-form-group">
											<label for="user-phone" class="am-form-label">手机号码</label>
											<div class="am-form-content">
												<form:input path="phone"  id="user-phone" placeholder="手机号必填" type="text" htmlEscape="false" maxlength="255" class="input-xlarge required" autocomplete="off"/>
											</div>
										</div>
					
										<div class="am-form-group">
											<label for="user-intro" class="am-form-label">详细地址</label>
											<div class="am-form-content">
												<form:textarea  rows="3" id="user-intro" placeholder="输入详细地址" path="upDownShelf" htmlEscape="false" maxlength="255" class="input-xlarge required" autocomplete="off"/>
												<small>100字以内写出你的详细地址</small>
											</div>
										</div>
										<div class="am-form-group">
											<div class="am-u-sm-9 am-u-sm-push-3">
												<input id="btnSubmit" class="am-btn am-btn-danger" type="submit" value="保 存"/>
											</div>
										</div>
									</form:form>
								</div>

							</div>

						</div>

					</div>

					<script type="text/javascript">
						$(document).ready(function() {							
							$(".new-option-r").click(function() {
								$(this).parent('.user-addresslist').addClass("defaultAddr").siblings().removeClass("defaultAddr");
							});
							
							var $ww = $(window).width();
							if($ww>640) {
								$("#doc-modal-1").removeClass("am-modal am-modal-no-btn")
							}
							
						})
					</script>

					<div class="clear"></div>

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