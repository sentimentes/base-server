<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>特价商品类型管理管理</title>
	<meta name="decorator" content="default"/>
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
		<li><a href="${ctx}/shoppingmall/goods/tjgsType/">特价商品类型管理列表</a></li>
		<li class="active"><a href="${ctx}/shoppingmall/goods/tjgsType/form?id=${tjgsType.id}">特价商品类型管理<shiro:hasPermission name="shoppingmall:goods:tjgsType:edit">${not empty tjgsType.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shoppingmall:goods:tjgsType:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tjgsType" action="${ctx}/shoppingmall/goods/tjgsType/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">类型名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>必填字段</span> 	
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">office_id：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${tjgsType.office.id}" labelName="office.name" labelValue="${tjgsType.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">up_down_shelf：</label>
			<div class="controls">
				<form:input path="upDownShelf" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">展览图片：</label>
			<div class="controls">
				<form:input path="image" name="image" class="text" maxlength="255" readonly="true" />
			    <input type="file" id="imageTemp" name="imageTemp" class="button" value="选择文件" accept=".png,.jpg" />
		    <c:if test="${not empty tjgsType.image}">
			<a id="myImg" onclick="ss();">浏览图片</a>
			<div id="myModal" class="modal" style=" width: 50%;height: 50%;margin:0 auto;z-index:9999" >
  				<span class="close" onclick="document.getElementById('myModal').style.display='none'">&times;</span>
				 <c:set value="${fn:split(tjgsType.image,',')}" var="imgArray" />
	    	    	<c:forEach items="${imgArray}" var="img">
	    	     		<img  class="modal-content" id="img01" style="width:27%;margin:0 auto;float:left;padding-left:50px;"  src="${fns:getConfig('wy.file.http.path')}${img}">
	    	  		</c:forEach>
			</div>
			</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>必填字段</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shoppingmall:goods:tjgsType:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>