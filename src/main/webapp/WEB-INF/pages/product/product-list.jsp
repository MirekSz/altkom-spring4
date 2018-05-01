<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="altkom" tagdir="/WEB-INF/tags/altkom"  %>
<%@ include file="/WEB-INF/pages/layout/head.jsp"%>
<c:set var="currentPage" value="${param.page!=null?param.page:1}" />


<a href="new" class="btn btn-primary "> <i
	class=" glyphicon glyphicon-plus"></i> Add new product
</a>
<div class="col-xs-4 pull-right">
	<div class="input-group">
		<input type="text" class="form-control searcher"
			placeholder="Search..."> <span class="input-group-btn">
			<button class="btn btn-default" type="button">
				<i class="glyphicon glyphicon-send" style="height: 20px"></i>
			</button>
		</span>
	</div>
</div>

<table class="table table-hover table-striped">
	<thead>
		<tr>
			<th>Id</th>
			<altkom:column name="name" title="product.name"></altkom:column>
			<th><spring:message code="product.quantity" /></th>
			<th><spring:message code="product.price" /></th>
			<th><spring:message code="product.actions" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="p" items="${products}">
			<tr>
				<td>${p.id}<img src="img/${p.id}" style="width:30px;height:30px" onerror="this.style='display:none'"/></td>
				<td>${p.name}</td>
				<td>${p.quantity}</td>
				<td>${p.price}</td>
				<td><a href="${p.id}/edit"> <i
						class=" glyphicon glyphicon-pencil"></i></a> <a href="${p.id}/delete">
						<i class=" glyphicon glyphicon-remove-circle"></i>
				</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<nav aria-label="Page navigation" class="pull-right">
	<ul class="pagination">
		<li><a href="?page=${currentPage-1}" aria-label="Previous"> <span
				aria-hidden="true">&laquo;</span>
		</a></li>
		<li><a href="#">${currentPage}</a></li>
		<li><a href="?page=${currentPage+1}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>
<%@ include file="/WEB-INF/pages/layout/footer.jsp"%>

