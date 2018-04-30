<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/layout/head.jsp"%>


<div class="jumbotron">
	<ul>
		<li>page = ${page}</li>
		<li>orderBy = ${orderBy}</li>
		<li>size = ${param.size}</li>
	</ul>
</div>
<c:if test="${operationDone!=null}">
	<div class="alert alert-success" role="alert">
		<span class="glyphicon 	glyphicon glyphicon-ok" aria-hidden="true"></span>
		Operacja zakończona sukcesem
	</div>
</c:if>
<table class="table table-hover table-striped">
	<thead>
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Quantity</th>
			<th>Price</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="p" items="${products}">
			<tr>
				<td>${p.id}</td>
				<td>${p.name}</td>
				<td>${p.quantity}</td>
				<td>${p.price}</td>
				<td><a href="${p.id}/delete"> <i
						class=" glyphicon glyphicon-remove-circle"></i></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<%@ include file="/WEB-INF/pages/layout/footer.jsp"%>

