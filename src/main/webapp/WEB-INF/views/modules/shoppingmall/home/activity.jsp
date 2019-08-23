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
		<script   src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
    	<script   src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js"></script>
    	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
       <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/img.css" />
	</head>

	<body>


		<%@ include file="/WEB-INF/views/include/index.jsp"%>
            <b class="line"></b>
			<div class="listMain">

				<!--分类-->
			<div class="nav-table">
					   <div class="long-title"><span class="all-goods">全部分类</span></div>
					  
			</div>
			
			<c:if test="${gsNavigation.id != null }">
			<div id="ss" hidden="hidden">
				${gsNavigation.gsContent}
			</div>
			<div id="sss" style="max-width:1200px;margin: 20px auto;">
				
			</div>
			<div style="max-width:1200px;margin: 20px auto;text-align: center">
				<c:if test="${gsNavigation.gsofficeId != null }">
				<a href="${ctx}/shoppingmall/goods/gindex/list?gsofficeId=${gsNavigation.gsofficeId}" style="font-size:24px;">>>进入该商店<<</a></li>				
			</c:if>
			</div>
			</c:if>
			<c:if test="${gsActivity.id != null }">
			<div id="ss" hidden="hidden">
				${gsActivity.gsContent}
			</div>
			<div id="sss" style="max-width:1200px;margin: 20px auto;">
			</div>
			</c:if>
			
			<script type="text/javascript">
				$(function(){
					debugger
					$("#sss").html($("#ss").text())
				});
			</script>
	</body>
	
</html>