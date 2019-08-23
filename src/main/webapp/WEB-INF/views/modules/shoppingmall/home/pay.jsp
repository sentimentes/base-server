<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0 ,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<title>结算页面</title>
		<link href="${pageContext.request.contextPath}/static/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/css/cartstyle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/css/jsstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/address.js"></script>
		
	</head>

	<body>

		<%@ include file="/WEB-INF/views/include/index.jsp"%>
			<div class="concent">
				<!--地址 -->
				<div class="paycont">
					<div class="address">
						<h3>确认收货地址 </h3>
						<div class="control">
							<div class="tc-btn createAddr theme-login am-btn am-btn-danger">使用新地址</div>
						</div>
						<div class="clear"></div>
						<ul>
							<div class="per-border"></div>
							<c:forEach items="${gsAddressList}" var="gsAddress" varStatus="i">
							<li class="user-addresslist 
							<c:if test="${i.index==0}">
							defaultAddr
							</c:if> 
							">
							<input type="hidden" value="${gsAddress.id}" id="ids${i.index+1}">
								<div class="address-left">
									<div class="user ">

										<span class="buy-address-detail">   
                   <span class="buy-user" id="buy-user${i.index+1}">${gsAddress.name} </span>
										<span class="buy-phone" id="buy-phone${i.index+1}">${gsAddress.phone}</span>
										</span>
									</div>
									<div class="default-address ">
										<span class="buy-line-title buy-line-title-type">收货地址：</span>
										<span class="buy--address-detail">
								   <span class="province" id="province${i.index+1}">${gsAddress.upDownShelf}</span>
										</span>
										</span>
									</div>
									<c:if test="${i.index==0}">
									<ins class="deftip">默认地址</ins>
									</c:if> 
								</div>
								
								<div class="clear"></div>
								<div class="new-addr-btn">
									<form:form id="listForm" modelAttribute="gsAddress"  action="${ctx}/shoppingmall/goods/gindex/addtrssDel?id=${gsAddress.id}" method="post" class="am-form am-form-horizontal" >
									<form:input path="idList" type="hidden" htmlEscape="false" maxlength="64" class="input-medium" value="${idlist}"/>
									<a href="#" class="hidden">设为默认</a>
									<span class="new-addr-bar hidden">|</span>
									<div class="tc-btn createAddr theme-login${i.index+1} am-btn am-btn-danger">编辑</div>
									<input id="btnSubmit" class="am-btn am-btn-danger" type="submit" value="删除" onclick="return confirmx('确认要删除该用户管理吗？)"/>
									</form:form>
								</div>
								<div class="theme-popover" style="height:320px;">		
			</div>
					<script type="text/javascript">
									$(document).ready(function($) {
									var addressid=$(".defaultAddr").find("input").val()
									$("#adderssid").val(addressid);
									var logname=$(".selected").find("input").val()
									$("#logid").val(logname);
									var $ww = $(window).width();
									$('.theme-login${i.index+1}').click(function() {
										$(document.body).css("overflow","hidden");
										var name=$("#buy-user${i.index+1}").text();
										var ids=$("#ids${i.index+1}").val();
										var phone=$("#buy-phone${i.index+1}").text();
										var province=$("#province${i.index+1}").text();
										$("#names").val(name);
										$("#phones").val(phone);
										$("#intro").text(province);
										$("#ids").val(ids);
										
										$('.theme-popover-mask').show();
										$('.theme-popover-mask').height($(window).height());
										$('.theme-popover').slideDown(200);
										
									})
									$('.theme-poptit .close,.btn-op .close').click(function() {
										$(document.body).css("overflow","visible");			
										$('.theme-popover-mask').hide();
										$('.theme-popover').slideUp(200);
									})
								}); 
					</script>
							</li>
							</c:forEach>
							
							<div class="per-border"></div>
						</ul>
							
						<div class="clear"><span style="color: red;">${message}</span></div>
					</div>
					<!--物流 -->
					<div class="logistics">
						<h3>选择物流方式</h3>
						<ul class="op_express_delivery_hot">
						<c:forEach items="${gsLogisticsList}" var="gsLogistics"  varStatus="h">
							<li data-value="yuantong" class="OP_LOG_BTN  <c:if test="${h.index==0 }">
								selected
							</c:if> "><input type="hidden" value="${gsLogistics.name}"><i class="c-gap-right" style="background-position:0px 
							<c:if test="${gsLogistics.name =='圆通'}">
								-468px	
							</c:if> 
							<c:if test="${gsLogistics.name =='申通'}">
								-1008px	
							</c:if> 
							<c:if test="${gsLogistics.name =='韵达'}">
								-576px	
							</c:if> 
							<c:if test="${gsLogistics.name =='中通'}">
								-324px	
							</c:if> 
							<c:if test="${gsLogistics.name =='顺丰'}">
								-180px	
							</c:if> 
							"></i>${gsLogistics.name}
							
							<span></span></li>
						</c:forEach>
						</ul>
					</div>
					<div class="clear"></div>
					<!--订单 -->
					
			<form:form id="listForm" modelAttribute="gsOrder"  action="${ctx}/shoppingmall/goods/user/orderSave?" method="post" class="am-form am-form-horizontal" >
			<form:hidden path="addressId"  id="adderssid" />
			<form:hidden path="logisticsName"  id="logid" />
					<div class="concent">
						<div id="payTable">
							<h3>确认订单信息</h3>
							<div class="cart-table-th">
								<div class="wp">

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
									<div class="th th-oplist">
										<div class="td-inner">配送方式</div>
									</div>
								</div>
							</div>
							<div class="clear"></div>
							<tr class="item-list">
								<div class="bundle  bundle-last">
									<div class="bundle-main">
									<% int sum=0; %>
							<c:forEach items="${gsShoppingCartList}" var="gsShoppingCart" varStatus="i">
							<input type="hidden"  name="gsShoppingCartList[${i.index}].gsId"  value="${gsShoppingCart.gsId}" />
							<input type="hidden"  name="gsShoppingCartList[${i.index}].id"  value="${gsShoppingCart.id}" />
							<input type="hidden" name="gsShoppingCartList[${i.index}].parameter"  value="${gsShoppingCart.parameter}" />
							<input type="hidden" name="gsShoppingCartList[${i.index}].gsName"  value="${gsShoppingCart.gsName}" />
							<input type="hidden" id="pricess${i.index}" name="gsShoppingCartList[${i.index}].salePrice"  value="${gsShoppingCart.salePrice}" />
							<ul class="item-content clearfix">
								<% sum++; %>
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
											<div class="price-line">
												<sapn class="J_Price price-now"  id="price${i.index}">${gsShoppingCart.salePrice}</span>
											</div>
										</div>
									</div>
								</li>
								
								<li class="td td-amount">
									<div class="amount-wrapper ">
										<div class="item-amount ">
										<span class="phone-title">购买数量</span>
											<div class="sl">
												<input class="min am-btn" name="" type="button" value="-" onclick="jian${i.index}();" style="float: left;    margin-left: 57px;"/>
												<input class="text_box" name="gsShoppingCartList[${i.index}].number" type="text"
													value="${gsShoppingCart.number}" style="width: 30px;float: left;" id="numbers${i.index}"/>
											    <input class="add am-btn" name="" type="button" value="+" onclick="jia${i.index}();" style="float: left;"/>
											</div>
										</div>
									</div>
								</li>

								<li class="td td-sum">
									<div class="td-inner">
										<em tabindex="0" class="J_ItemSum number" id="jiage${i.index}">${gsShoppingCart.number*gsShoppingCart.salePrice}</em>
									</div>
								</li>
								<li class="td td-oplist">
												<div class="td-inner">
													<span class="phone-title">配送方式</span>
													<div class="pay-logis">
														包邮
													</div>
												</div>
								</li>
							</ul>
							
									<script type="text/javascript">
									  function jia${i.index}() {
									  	 var sum=$("#sum").text();
									  	 var q5=$("#price${i.index}").text();
									  	 var q6=$("#numbers${i.index}").val();
									  	 var q7=q5*(parseFloat(q6)+1);
									 	 $("#jiage${i.index}").text(q7);
									 	 $("#pricess${i.index}").val(q7);
									 	 $("#sum").text(parseFloat(sum)+parseFloat(q5));
									$("#J_ActualFee").text(parseFloat(sum)+parseFloat(q5));
									     }
									  	function jian${i.index}() {
									  	 var sum=$("#sum").text();
									  	 var q5=$("#price${i.index}").text();
									  	 var q6=$("#numbers${i.index}").val();
									  	 var q8=parseInt(q6);
									  	 if(q8>=2){
									  		var q7=q5*(q8-1);
									  		 $("#sum").text(parseFloat(sum)-parseFloat(q5));
									  		 $("#J_ActualFee").text(parseFloat(sum)-parseFloat(q5));
									  	 }else{
									  		 var q7=q5*q8; 
									  	 }
									 	 $("#jiage${i.index}").text(q7);
									 	 $("#pricess${i.index}").val(q7);
									   }
									</script>
						</c:forEach>
								<div class="clear"></div>
								</div>
							</tr>							
							<div class="clear"></div>
							</div>					
							</div>
							<div class="clear"></div>
							</div>
							<!--含运费小计 -->
							<div class="buy-point-discharge ">
								
								<p class="price g_price ">
									合计（含运费） <span>¥</span><em class="pay-sum" id=sum></em>
								</p>
							</div>
							<script type="text/javascript">
									$(document).ready(function() {
										var sum=0;
										for(i=0;i<<%=sum%>;i++){
											 var q5=$("#price"+i).text();
											 sum+=parseInt(q5)
										}
										 $("#sum").text(sum);
										 $("#J_ActualFee").text(sum);
									});
							</script>
							<!--信息 -->
							<div class="order-go clearfix">
								<div class="pay-confirm clearfix">
									<div class="box">
										<div tabindex="0" id="holyshit267" class="realPay"><em class="t">实付款：</em>
											<span class="price g_price ">
                                    <span>¥</span> <em class="style-large-bold-red " id="J_ActualFee"></em>
											</span>
										</div>
									</div>

									<div id="holyshit269" class="submitOrder">
										<div class="go-btn-wrap">
											<input id="btnSubmit" class="am-btn am-btn-danger" type="submit" value="提交订单" />
										</div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
						<div class="clear"></div>
					</form:form>
				<div class="footer">

					<div class="footer-bd" >
						<p style="margin: 26px 413px;">
							<em>© 2019 商城 www.leixoaming.cn All Rights Reserved</em>
						</p>
					</div>
			</div>
			</div>
			<div class="theme-popover-mask"></div>
			<div class="theme-popover" style="height:320px;">

				<!--标题 -->
				<div class="am-cf am-padding" >
					<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">新增地址</strong> / <small>Add address</small></div>
				</div>
				<hr/>

				<div class="am-u-md-12">
					<form:form id="listForm" modelAttribute="gsAddress"  action="${ctx}/shoppingmall/goods/gindex/addressSave?ss=${ss}&number=${number}&parameter=${parameter}" method="post" class="am-form am-form-horizontal" >
						<form:input path="idList" type="hidden" htmlEscape="false" maxlength="64" class="input-medium" value="${idlist}"/>
						<form:hidden path="id"  id="ids"/>
						<div class="am-form-group">
							<label for="user-name" class="am-form-label">收货人</label>
							<div class="am-form-content">
								<form:input path="name" htmlEscape="false" maxlength="255" type="text" id="names" placeholder="收货人" class="input-xlarge required" autocomplete="off"/>
							</div>
						</div>

						<div class="am-form-group">
							<label for="user-phone" class="am-form-label">手机号码</label>
							<div class="am-form-content">
								<form:input path="phone"  id="phones" placeholder="手机号必填" type="text" htmlEscape="false" maxlength="255" class="input-xlarge required" autocomplete="off"/>
							</div>
						</div>
						<div class="am-form-group">
							<label for="user-intro" class="am-form-label">详细地址</label>
							<div class="am-form-content">
								<form:textarea  rows="3" id="intro" placeholder="输入详细地址" path="upDownShelf" htmlEscape="false" maxlength="255" class="input-xlarge required" autocomplete="off"/>
								<small>100字以内写出你的详细地址...</small>
							</div>
						</div>

						<div class="am-form-group theme-poptit">
							<div class="am-u-sm-9 am-u-sm-push-3">
							<input id="btnSubmit" class="am-btn am-btn-danger" type="submit" value="保 存"/>
							<div class="am-btn am-btn-danger close">取消</div>
							</div>
						</div>
					</form:form>
				</div>
			</div>

			<div class="clear"></div>
	</body>

</html>