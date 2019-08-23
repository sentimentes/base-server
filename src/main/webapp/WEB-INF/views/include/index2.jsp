<%@ page contentType="text/html;charset=UTF-8" %><meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<div class="mt-logo">
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
						<div class="menu-hd"><a id="mc-menu-hd" href="${ctx}/shoppingmall/goods/gindex/shopcart" target="_top"><i class="am-icon-shopping-cart  am-icon-fw"></i><span>购物车</span><strong id="J_MiniCartNum" class="h"></strong></a></div>
					</div>
					<div class="topMessage favorite">
						<div class="menu-hd"><a href="${ctx}/shoppingmall/goods/gindex/list?ss=2" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>退出该商店</span></a></div>
				</ul>
						</div>

						<!--悬浮搜索框-->

						<div class="nav white">
							
							<div class="logoBig" >
								<li style="font-size: 36px;color: red; margin-top: 20px; float: right;">${office.name}</li>
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
				</div>