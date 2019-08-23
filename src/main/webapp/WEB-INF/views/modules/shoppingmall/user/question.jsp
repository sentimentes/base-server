<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">

		<title>安全问题</title>
		<%@ include file="/WEB-INF/views/include/head.jsp"%>
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/css/personal.css" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/static/css/stepstyle.css" rel="stylesheet" type="text/css">

		<script src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/jquery.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/js/amazeui.js" type="text/javascript"></script>

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
					<div class="am-cf am-padding">
						<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">设置安全问题</strong> / <small>Set&nbsp;Safety&nbsp;Question</small></div>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red; font-size: 18px;">${message}</span>
					</div>
					<hr/>
					<!--进度条-->
					<div class="m-progress">
						<div class="m-progress-list">
							<span class="step-1 step">
                                <em class="u-progress-stage-bg"></em>
                                <i class="u-stage-icon-inner">1<em class="bg"></em></i>
                                <p class="stage-name">设置安全问题</p>
                            </span>
							<span class="step-${ss} step">
                                <em class="u-progress-stage-bg"></em>
                                <i class="u-stage-icon-inner">2<em class="bg"></em></i>
                                <p class="stage-name">完成</p>
                            </span>
							<span class="u-progress-placeholder"></span>
						</div>
						<div class="u-progress-bar total-steps-2">
							<div class="u-progress-bar-inner"></div>
						</div>
					</div>
					
					<form:form id="searchForm" modelAttribute="gsUser" action="${ctx}/shoppingmall/goods/user/answerSave?ss=${ss}" method="post" class="am-form am-form-horizontal">
						<div class="am-form-group select">
							<label for="user-question1" class="am-form-label">问题一</label>
							<div class="am-form-content">
								
								<form:input id="user-answer1" path="passwordOne" type="text" placeholder="请输入安全问题" maxlength="64"  style="height: 35px;" />
							</div>
						</div>
						<div class="am-form-group">
							<label for="user-answer1" class="am-form-label">答案</label>
							<div class="am-form-content">
								<form:input id="user-answer1" path="answerOne" placeholder="请输入安全问题答案" type="text" maxlength="64"  style="height: 35px;"/>
							</div>
						</div>
						<div class="am-form-group select">
							<label for="user-question2" class="am-form-label">问题二</label>
							<div class="am-form-content">
						
							<form:input id="user-answer1" path="passwordTwo"  placeholder="请输入安全问题  " type="text" maxlength="64"   style="height: 35px;"/>
							</div>
						</div>
						<div class="am-form-group">
							<label for="user-answer2" class="am-form-label">答案</label>
							<div class="am-form-content">
								<form:input id="user-answer2" path="answerTwo"  placeholder="请输入安全问题答案" type="text" maxlength="64"  style="height: 35px;"/>
							</div>
						</div>
						<div class="info-btn">
							<input id="btnSubmit" class="am-btn am-btn-danger" type="submit" value="保存修改" />
						</div>

					</form:form>

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