<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>付款成功页面</title>
<link rel="stylesheet"  type="text/css" href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css"/>
<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/basic/css/demo.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/sustyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/basic/js/jquery-1.7.min.js"></script>
</head>
<body>


<%@ include file="/WEB-INF/views/include/index.jsp"%>



<div class="take-delivery">
 <div class="status">
   <!-- <h2>您已成功付款</h2> -->
   <div class="successInfo">
     <ul>
       <li>付款金额<em>${gsOrder.price}</em></li>
       <div class="user-info">
         <p>收货人：${gsOrder.pepole}</p>
         <p>联系电话：${gsOrder.phone}</p>
         <p>收货地址：${gsOrder.upDownShelf}</p>
       </div>
             请认真核对您的收货信息，如有错误请联系客服
                               
     </ul>
     <div class="option">
       <span class="info">您可以</span>
        <a href="${pageContext.request.contextPath}/static/person/order.html" class="J_MakePoint">查看<span>已买到的宝贝</span></a>
        <a href="${pageContext.request.contextPath}/static/person/orderinfo.html" class="J_MakePoint">查看<span>交易详情</span></a>
     </div>
    </div>
  </div>
  <a href="${ctx}/shoppingmall/goods/user/goAlipay?orderId=118467986e694dea9730dd03076e0b91">付款</a>
</div>


<div class="footer">

					<div class="footer-bd" >
						<p style="margin: 26px 413px;">
							<em>© 2019 商城 www.leixoaming.cn All Rights Reserved</em>
						</p>
					</div>
				</div>


</body>
</html>
