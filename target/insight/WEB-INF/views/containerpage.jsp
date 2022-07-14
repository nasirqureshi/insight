<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" manifest="/not-existing.appcache">
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<!--<![endif]-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/${appcss}" />
<title>Utilities</title>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<style type="text/css">
/* label color */
.input-field label {
	color: #00b0ff;
}
/* label focus color */
.input-field input[type=text]:focus+label {
	color: #00b0ff;
}
/* label underline focus color */
.input-field input[type=text]:focus {
	border-bottom: 1px solid #00b0ff;
	box-shadow: 0 1px 0 0 #00b0ff;
}
/* valid color */
.input-field input[type=text].valid {
	border-bottom: 1px solid #00b0ff;
	box-shadow: 0 1px 0 0 #00b0ff;
}
/* invalid color */
.input-field input[type=text].invalid {
	border-bottom: 1px solid #00b0ff;
	box-shadow: 0 1px 0 0 #00b0ff;
}
/* icon prefix focus color */
.input-field .prefix.active {
	color: #00b0ff;
}
</style>
<c:choose>
	<c:when test="${branch == 'CHS'}">
		<c:set value="#4885ed" var="bg"></c:set>
	</c:when>
	<c:when test="${branch == 'GRS'}">
		<c:set value="#4A777A" var="bg"></c:set>
	</c:when>
	<c:when test="${branch == 'MPS'}">
		<c:set value="#8d96cd" var="bg"></c:set>
	</c:when>
	<c:otherwise>
		<c:set value="#000000" var="bg"></c:set>
	</c:otherwise>
</c:choose>
</head>
<body bgcolor="#f4f6f8"
	style="word-wrap: break-word; white-space: normal; font-size: 0.8em;">
	<!-- START HEADER -->
	<header id="header" class="page-topbar">
		<!-- start header nav-->
		<div class="navbar-fixed">
			<form class="col s12" role="navigation" method="post" id="navform"
				name="navform" action="/insight/nav">
				<input type="hidden" id="navmode" name="navmode" value="logout">
				<nav class="navbar-color">
					<div class="nav-wrapper">
						<a href="#" class="brand-logo right">&nbsp;${branch}</a>
						<ul class="left hide-on-med-and-down">
							<li class="active"><a id="navhome" href="#">Home</a></li>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">Status <b class="caret"></b></a>
								<ul class="dropdown-menu">
								</ul></li>
							<li><a id="User:" href="#">${user.username}</a></li>
							<li><a id="Date:" href="#">${sysdate}</a></li>
							<li class="active"><a id="navlogout" href="#">Logout</a></li>
							<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
							<li></li>
						</ul>
					</div>
				</nav>
			</form>
		</div>
	</header>
	<!-- END HEADER -->
	<div class="row">
		<div class="row">
			<form class="col s12" role="form" method="post"
				id="containerorderform" action="/insight/container/getorder">
				<div class="card  blue-grey lighten-4 z-depth-3">
					<div class="card-content">
						<div class="row">
							<div class="col s3">
								<h5 class="light col s12">Final Container</h5>
								<input type="hidden" id="containermode" name="containermode"
									value="getorder">
							</div>
							<div class="col s2">
								<label><strong>Select Vendor</strong></label> <select
									id="vendorselected" name="vendorselected"
									class="browser-default">
									<option value="${vendorselected}" selected>${vendorselected}</option>
									<c:forEach var="vendor" items="${vendorlistdd}">
										<option value="${vendor.key}">${vendor.key}</option>
									</c:forEach>
								</select>
							</div>
							<div class="input-field col s2">
								<input id="containerordernosearchtxt"
									name="containerordernosearchtxt" value="${orderno}" type="text"
									required> <label for="containerordernosearchtxt"><strong>Order
										#</strong> </label>
							</div>
							<div class="col s1">
								<button
									class="btn-floating btn-large waves-effect waves-light  blue"
									type="submit" id="getcontainerorderbtn"
									name="getcontainerorderbtn" value="getorder">
									<i class="mdi-action-search right"></i>
								</button>
							</div>
							<c:if test="${not empty requestScope.vendororder}">
								<c:if test="${not empty requestScope.containerorderdetailslist}">
									<c:if test="${fn:length(containerorderdetailslist) gt 1}">
										<c:if test="${containermode == 'getorder'}">
											<div class="col s3">
												<span>
													<button class="btn waves-effect waves-light" type="submit"
														id="updatevendorpricesbtn" name="updatevendorpricesbtn"
														value="updatevendorprices">Update Prices to
														Vendors</button>
												</span>
											</div>
										</c:if>
									</c:if>
								</c:if>
								<c:if test="${containermode == 'updatevendorprices'}">
									<div class="col s3">
										<span>
											<button class="btn waves-effect waves-light" type="submit"
												id="dofinalbtn" name="dofinalbtn" value="dofinal">Do
												Final</button>
										</span>
									</div>
								</c:if>
							</c:if>
						</div>
					</div>
				</div>
				<c:if test="${not empty requestScope.vendororder}">
					<div class="card-panel   indigo lighten-5  z-depth-3">
						<div class="card-content">
							<div class="row">
								<div class="col s12">
									<table>
										<thead>
											<tr>
												<th
													style="border: 0px; color: #000000; background-color: transparent;"><strong>Order
														#</strong></th>
												<th
													style="border: 0px; color: #000000; background-color: transparent;"><strong>Supplier
														ID</strong></th>
												<th
													style="border: 0px; color: #000000; background-color: transparent;"><strong>Container
														#</strong></th>
												<th
													style="border: 0px; color: #000000; background-color: transparent;"><strong>Order
														Date</strong></th>
												<th
													style="border: 0px; color: #000000; background-color: transparent;"><strong>Order
														Status</strong></th>
												<th
													style="border: 0px; color: #000000; background-color: transparent;"><strong>Total
														Items</strong></th>
												<th
													style="border: 0px; color: #000000; background-color: transparent;"><strong>Order
														Total</strong></th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td
													style="border: 0px; color: #404040; background-color: transparent;"><strong>${vendororder.orderno}</strong></td>
												<td
													style="border: 0px; color: #404040; background-color: transparent;"><strong>${vendororder.supplierid}</strong></td>
												<td
													style="border: 0px; color: #404040; background-color: transparent;"><strong>${vendororder.containerno}</strong></td>
												<td
													style="border: 0px; color: #404040; background-color: transparent;"><strong>${vendororder.orderdate}</strong></td>
												<td
													style="border: 0px; color: #404040; background-color: transparent;"><strong>${vendororder.orderstatus}</strong></td>
												<td
													style="border: 0px; color: #404040; background-color: transparent;"><strong>${vendororder.totalitems}</strong></td>
												<td
													style="border: 0px; color: #404040; background-color: transparent;"><strong>${vendororder.ordertotal}</strong></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${containermode == 'dofinal'}">
					<div class="row">
						<div class="col s6 offset-s3">
							<div class="card-panel">
								<h5 class="header2">Final Steps For Order</h5>
								<div class="divider"></div>
								<div class="row">
									<div class="input-field col s6">
										<input id="orderdiscount" name="orderdiscount" type="text">
										<label class="active" for="orderdiscount"><strong>Order
												Discount</strong> </label>
									</div>
									<div class="col s6">
										<p>
											<input class="filled-in" id="filled-in-box" type="checkbox"
												checked="checked"> <label for="filled-in-box"><strong>Find
													Errors</strong> </label>
										</p>
									</div>
								</div>
								<div class="row">
									<div class="input-field col s6">
										<input id="stickercharges" name="stickercharges" type="text">
										<label class="active" for="stickercharges"><strong>Sticker
												Charges</strong> </label>
									</div>
									<div class="col s6">
										<p>
											<input class="with-gap" name="updateinventory" type="radio"
												id="updateinventory" value="updateinventory"
												${updateinventory=='updateinventory'?'checked':''} /> <label
												for="updateinventory"><strong>Update
													Inventory</strong></label>
										</p>
									</div>
								</div>
								<div class="row">
									<div class="input-field col s6">
										<input id="customs" name="customs" type="text"> <label
											class="active" for="customs"><strong>Customs</strong></label>
									</div>
									<div class="col s6">
										<p>
											<input class="with-gap" name="calculateprices" type="radio"
												id="calculateprices" value="calculateprices"
												${calculateprices=='calculateprices'?'checked':''} /> <label
												for="calculateprices"><strong>Calculate
													Prices</strong></label>
										</p>
									</div>
								</div>
								<div class="row">
									<div class="input-field col s6">
										<input id="freight" name="freight" type="text"> <label
											class="active" for="freight"><strong>Freight</strong></label>
									</div>
									<div class="col s6"></div>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${containermode != 'dofinal'}">
					<c:if test="${not empty requestScope.containerorderdetailslist}">
						<div class="row">
							<div class="col s12">
								<table class="highlight">
									<thead>
										<tr>
											<th width="7%">&nbsp;Part&nbsp;#</th>
											<th width="15%">&nbsp;Vendorpartno#</th>
											<th>Desc1</th>
											<th>Desc2</th>
											<th>Capa</th>
											<th>St</th>
											<th>Or</th>
											<th>Re</th>
											<th>Type</th>
											<th width="10%">&nbsp;&nbsp;Price</th>
											<th width="10%">&nbsp;&nbsp;Quantity</th>
											<th>Tot</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${requestScope.containerorderdetailslist}"
											var="vendororderitems">
											<tr>
												<td width="7%"><strong><input type="text"
														value="${vendororderitems.partno}" name="partno"
														id="partno"
														style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
												<td width="15%"><strong><input type="text"
														value="${vendororderitems.vendorpartno}"
														name="vendorpartno" id="vendorpartno"
														style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
												<td><strong><c:out
															value="${vendororderitems.description1}" /></strong></td>
												<td><c:out value="${vendororderitems.description2}" /></td>
												<td><c:out value="${vendororderitems.capa}" /></td>
												<td><c:out value="${vendororderitems.unitsinstock}" /></td>
												<td><c:out value="${vendororderitems.unitsonorder}" /></td>
												<td><c:out value="${vendororderitems.reorderlevel}" /></td>
												<td><c:out value="${vendororderitems.ordertype}" /></td>
												<td width="10%"><strong><input type="text"
														value="${vendororderitems.price}" name="price" id="price"
														style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
												<td width="10%"><strong><input type="text"
														value="${vendororderitems.quantity}" name="quantity"
														id="quantity"
														style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
												<td><c:out value="${vendororderitems.totalcost}" /></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
				</c:if>
			</form>
		</div>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
		$(document)
				.ready(
						function() {

							var cb = '<c:out value="${bg}"/>';
							$('nav').css('background-color', cb);

							$(".button-collapse").sideNav();
							$(".dropdown-button").dropdown();
							$('select').material_select();

							//$('select').not('.disabled').material_select();

							$('html, body').animate({
								scrollTop : $(document).height()
							}, 'slow');

							$("#navlogout").click(function() {
								$('input[name=navmode]').val('logout');
								$("#navform").submit();
							});

							$("#navhome").click(function() {
								$('input[name=navmode]').val('home');
								$("#navform").submit();
							});

							$("#containerordernosearchtxt").keypress(
									function(event) {
										if (event.which == 13) {
											event.preventDefault();
											$('input[name=containermode]').val(
													'getorder');
											$("#containerorderbtn").click();
										}
									});

							//dofinal
							$("#dofinalbtn").click(
									function() {

										$('input[name=containermode]').val(
												'dofinal');

										$("#containerorderform").attr("action",
												"/insight/container/dofinal");
										$("html, body").animate({
											scrollTop : 0
										}, "fast");
										$("#containerorderform").submit();
									});

							//updatevendorprices
							$("#updatevendorpricesbtn")
									.click(
											function() {

												$('input[name=containermode]')
														.val(
																'updatevendorprices');

												$("#containerorderform")
														.attr("action",
																"/insight/container/updatevendorprices");
												$("html, body").animate({
													scrollTop : 0
												}, "fast");
												$("#containerorderform")
														.submit();
											});

							$("#containerorderform").submit(function() {

								var isFormValid = true;

								/* if ($('#existingorderbtn').val() == 'existingordersearch') {
									if ($('#existingordernosearchtxt').length) {
										if ($('#existingordernosearchtxt').val() == '') {
											alert('search ORDER NO cannot be blank');
											isFormValid = false;
										}
									}
								} */

								return isFormValid;
							});
						});
	</script>
</body>
</html>
