<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>导航广告管理管理</title>
<meta name="decorator" content="default"/>
	<script   src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
    <script   src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
      <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/img.css" />
		<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
		
		var modalImg = document.getElementById("img01");
	    var captionText = document.getElementById("caption");
	    function ss(){
	    	myModal.style.display = "block";
	        captionText.innerHTML = this.alt;
	    }
	    // 获取 <span> 元素，设置关闭按钮
	    var span = document.getElementsByClassName("close")[0];
	     
	    // 当点击 (x), 关闭弹窗
	    span.onclick = function() { 
	    	myModal.style.display = "none";
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shoppingmall/goods/gsNavigation/">导航广告管理列表</a></li>
		<li class="active"><a href="${ctx}/shoppingmall/goods/gsNavigation/form?id=${gsNavigation.id}">导航广告管理<shiro:hasPermission name="shoppingmall:goods:gsNavigation:edit">${not empty gsNavigation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shoppingmall:goods:gsNavigation:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="gsNavigation" action="${ctx}/shoppingmall/goods/gsNavigation/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>必填字段</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展览图片：</label>
			<div class="controls">
				<form:input path="image" name="image" class="text" maxlength="255" readonly="true" />
			    <input type="file" id="imageTemp" name="imageTemp" class="button" value="选择文件" accept=".png,.jpg" />
		    <c:if test="${not empty gsNavigation.image}">
			<a id="myImg" onclick="ss();">浏览图片</a>
			<div id="myModal" class="modal" style=" width: 50%;height: 50%;margin:0 auto;z-index:9999" >
  				<span class="close" onclick="document.getElementById('myModal').style.display='none'">&times;</span>
				 <c:set value="${fn:split(gsNavigation.image,',')}" var="imgArray" />
	    	    	<c:forEach items="${imgArray}" var="img">
	    	     		<img  class="modal-content" id="img01" style="width:27%;margin:0 auto;float:left;padding-left:50px;"  src="${fns:getConfig('wy.file.http.path')}${img}">
	    	  		</c:forEach>
			</div>
			</c:if>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">商店id：</label>
			<div class="controls">
				<sys:treeselect id="office" name="gsofficeId" value="${gsNavigation.gsofficeId}" labelName="office.name" labelValue="${gsNavigation.gsofficeName}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">上下架：</label>
			<div class="controls">
				<form:input path="upDownShelf" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>必填字段</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详情：</label>
			<div class="controls">
				<form:textarea id="newsContent" name="gsContent" htmlEscape="false"  path="gsContent" rows="4"  class="input-large" style="width:900px;height:800px;"/>
				<p><span class="help-inline">（选填）请输入内容，可以图文混排</span></p>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shoppingmall:goods:gsNavigation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('newsContent');
	</script>
</body>
</html>