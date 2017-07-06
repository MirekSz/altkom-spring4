<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/layout/head.jsp"%>
<div class="container" ng-app="cart">
	<div class="row" ng-controller="CartController" ng-cloak>
		<center ng-if="orderNo">
			<i>
				<h1 style="color: green" >
					<span class="glyphicon glyphicon-ok "></span> Order confirmed:
					{{orderNo}} no.
				</h1>
			</i>
		</center>
		<div class="col-sm-12 col-md-10 col-md-offset-1">
			<form ng-model-options="{ debounce: 250 }"> 
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Product</th>
							<th>Quantity</th>
							<th class="text-center">Price</th>
							<th class="text-center">Total</th>
							<th> </th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="item in items">
							<td class="col-sm-8 col-md-6">
								<div class="media">
									<a class="thumbnail pull-left" href="#"> <img
										ng-src="${contextPath}/product/img/{{item.id}}"
										style="width: 60px; height: 60px;">
									</a>
									<div class="media-body">
										<h4 class="media-heading">
											<select class="form-control"
												style="color: #428bca; font-weight: bold" ng-model="item.id">
												<option ng-repeat="product in products "
													value="{{product.id}}">{{product.name}}</option>
											</select>
										</h4>
										<h5 class="media-heading">
											by <a href="#">Brand name</a>
										</h5>
										<div ng-if="item.avaliable >= 10">
											<span>Status: </span><span class="text-success"><strong>In
													Stock</strong></span>
										</div>
										<div ng-if="item.avaliable < 10">
											<span>Status: </span><span class="text-warning"><strong>Last
													items in Stock</strong></span>
										</div>
									</div>
								</div>
							</td>
							<td class="col-sm-1 col-md-1" style="text-align: center"><input
								type="number" class="form-control" ng-model="item.quantity"></td>
							<td class="col-sm-1 col-md-1 text-center"><strong>
									{{item.price}}</strong></td>
							<td class="col-sm-1 col-md-1 text-center"><strong>
									{{item.sum | number:2}}</strong></td>
							<td class="col-sm-1 col-md-1">
								<button type="button" class="btn btn-danger"
									ng-click="remove(item)">
									<span class="glyphicon glyphicon-remove"></span> Remove
								</button>
							</td>
						</tr>
						<tr>
							<td>
								<button type="button" class="btn btn-primary"
									ng-click="addItem()">
									Add product <span class="glyphicon glyphicon-plus"></span>
								</button> 
							</td>
							<td> </td>
							<td> </td>
							<td><h4>Netto</h4>
								<h4>Vat</h4>
								<h3>Total</h3></td>
							<td class="text-right"><h4>
									<strong>{{netto| number:2}}</strong>
								</h4>
								<h4>
									<strong>{{vat| number:2}}</strong>
								</h4>
								<h3>
									<strong>{{total| number:2}}</strong>
								</h3></td>
						</tr>
						<tr>
							<td> </td>
							<td> </td>
							<td> </td>
							<td>
								<button type="button" class="btn btn-default">
									<span class="glyphicon glyphicon-shopping-cart"></span>
									Continue Shopping
								</button>
							</td>
							<td>
								<button type="button" class="btn btn-success"
									ng-click="process()">
									Checkout <span class="glyphicon glyphicon-play"></span>
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/pages/layout/footer.jsp"%>

