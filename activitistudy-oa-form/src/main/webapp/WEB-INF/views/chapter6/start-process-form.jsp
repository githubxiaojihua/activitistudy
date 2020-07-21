<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.activiti.engine.form.*, org.apache.commons.lang3.*"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/global.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/include-base-styles.jsp"%>
<title>启动流程</title>
<script type="text/javascript" src="${ctx }/js/common/jquery.js"></script>
<script type="text/javascript" src="${ctx }/js/common/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${ctx }/js/common/bootstrap-datepicker.js"></script>
<script type="text/javascript">
	$(function() {
		$('.datepicker').datepicker();
	});
</script>
</head>
<body>

	<form
		action="${ctx }/chapter6/process-instance/start/${processDefinitionId}"
		class="form-horizontal" method="post">
		<!-- 遍历开启表单中的属性 -->
		<c:forEach items="${startFormData.formProperties}" var="fp">
			<!-- 将fp设置到pageContext中方便后面调用 -->
			<c:set var="fpo" value="${fp}" />
			<%
		        /**
		        	只有像ID，name这样每一个表单字段都会有的会设置属性才可以直接使用EL表达式获取，
		        	像datePattern这种不是必须设置的属性需要调用其FormType.getInfomation来获取
		        	而EL表达式不支持调用方法，所以这里需要进行转化
		        */
				//1、得到每个字段的FormType
				FormType type = ((FormProperty) pageContext.getAttribute("fpo")).getType();
				//2、设置需要读取的属性
				String[] keys = { "datePattern" };
				for (String key : keys) {
					//3、通过getInformation来获取相应的属性，并设置到pageContext中
					pageContext.setAttribute(key, ObjectUtils.toString(type.getInformation(key)));
				}
			%>
			<div class="control-group">
			    <!-- 组建form表单 -->
				<%-- 文本或者数字类型 --%>
				<c:if test="${fp.type.name == 'string' || fp.type.name == 'long'}">
					<label class="control-label" for="${fp.id}">${fp.name}:</label>
					<div class="controls">
						<input type="text" id="${fp.id}" name="${fp.id}"
							data-type="${fp.type.name}" value="" />
					</div>
				</c:if>

				<%-- 日期 --%>
				<c:if test="${fp.type.name == 'date'}">
					<label class="control-label" for="${fp.id}">${fp.name}:</label>
					<div class="controls">
						<input type="text" id="${fp.id}" name="${fp.id}"
							class="datepicker" data-type="${fp.type.name}"
							data-date-format="${fn:toLowerCase(datePattern)}" />
					</div>
				</c:if>
			</div>
		</c:forEach>

		<%-- 按钮区域 --%>
		<div class="control-group">
			<div class="controls">
				<a href="javascript:history.back();" class="btn"><i
					class="icon-backward"></i>返回列表</a>
				<button type="submit" class="btn">
					<i class="icon-play"></i>启动流程
				</button>
			</div>
		</div>
	</form>

</body>
</html>