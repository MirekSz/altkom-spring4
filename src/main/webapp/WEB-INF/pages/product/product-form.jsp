<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/layout/head.jsp"%>
<h3>Product form</h3>
<form:form class="form-horizontal" modelAttribute="product"
	action="${contextPath}/product/save">

	<form:hidden path="id" />
	<div class="form-group">
		<label class="col-sm-2 control-label">Name</label>
		<div class="col-sm-8">
			<form:input path="name" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Quantity</label>
		<div class="col-sm-8">
			<form:input path="quantity" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Price</label>
		<div class="col-sm-8">
			<form:input path="price" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">Description</label>
		<div class="col-sm-8">
			<form:textarea path="description" class="form-control" />
		</div>
	</div>
	<button type="submit" class="btn btn-primary pull-right">
		<i class=" glyphicon glyphicon-ok"></i> Save
	</button>
</form:form>
<%@ include file="/WEB-INF/pages/layout/footer.jsp"%>

