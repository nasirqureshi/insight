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

/* Start by setting display:none to make this hidden.
   Then we position it in relation to the viewport window
   with position:fixed. Width, height, top and left speak
   for themselves. Background we set to 80% white with
   our animation centered, and no-repeating */
.modal {
	display: none;
	position: fixed;
	z-index: 1000;
	top: 0;
	left: 0;
	height: 100%;
	width: 100%;
	background: rgba(255, 255, 255, .8)
		url('http://i.stack.imgur.com/FhHRx.gif') 50% 50% no-repeat;
}

/* When the body has the loading class, we turn
   the scrollbar off with overflow:hidden */
body.loading {
	overflow: hidden;
}

/* Anytime the body has the loading class, our
   modal element will be visible */
body.loading .modal {
	display: block;
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
	style="word-wrap: break-word; white-space: normal; font-size: 0.7em;">
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
				id="purchasingfunctionform" action="/insight/purchasing/order">
				<div class="row">
					<h5 class="light col s12">Purchasing Function</h5>
				</div>
				<div class="card">
					<div class="card-content yellow lighten-4">
						<div class="row">
							<h5 class="header center">${message}</h5>
						</div>
					</div>
				</div>
				<div class="card">
					<div class="card-content">
						<div class="row">
							<div class="col s3">
								<label><strong>Select Vendor</strong></label> <select
									id="vendorselected" name="vendorselected"
									class="browser-default">
									<option value="${vendorselected}" selected>${vendorselected}</option>
									<c:forEach var="vendor" items="${vendorlistdd}">
										<option
											style="word-wrap: break-word; white-space: normal; font-size: 0.8em;"
											value="${vendor.key}">${vendor.key}</option>
									</c:forEach>
								</select>
							</div>
							<div class="input-field col s2">
								<input id="ordernosearchtxt" name="ordernosearchtxt"
									value="${orderno}"
									style="word-wrap: break-word; white-space: normal; font-size: 1.5em; font-weight: bold;"
									type="text" required> <label for="ordernosearchtxt"><strong>Order
										#</strong> </label>
							</div>
							<div class="col s1">
								<button
									class="btn-floating btn-large waves-effect waves-light blue"
									type="submit" id="purchasingfunctionbtn"
									name="purchasingfunctionbtn" value="getvendororder">
									<i class="mdi-action-search right"></i>
								</button>
							</div>
							<div class="col s5">
								<c:if test="${not empty requestScope.vendororder}">
									<table>
										<thead>
											<tr>
												<th><strong>Order #</strong></th>
												<th><strong>Supplier ID</strong></th>
												<th><strong>Order Date</strong></th>
												<th><strong>Order Status</strong></th>
												<th><strong>Total Items</strong></th>
												<th><strong>Order Total</strong></th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><strong>${vendororder.orderno}</strong></td>
												<td><strong>${vendororder.supplierid}</strong></td>
												<td><strong>${vendororder.orderdate}</strong></td>
												<td><strong>${vendororder.orderstatus}</strong></td>
												<td><strong>${vendororder.totalitems}</strong></td>
												<td><strong>${vendororder.ordertotal}</strong></td>
											</tr>
										</tbody>
									</table>
								</c:if>
							</div>
							<div class="col s1">
								<div class="card brown lighten-5">
									<div class="card-content">
										<label><strong>Select Branch</strong></label> <select
											id="branchselected" name="branchselected"
											class="browser-default">
											<option value="${branchselected}" selected>${branchselected}</option>
											<c:forEach var="b" items="${branchlistdd}">
												<option value="${b.key}">${b.key}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						<c:if test="${not empty requestScope.vendororder}">
							<div class="row">
								<div class="col s10">
									<div class="card  blue-grey lighten-4">
										<div class="card-content">
											<div class="row">
												<div class="col s3">
													<span>
														<button class="btn tooltipped" type="submit"
															id="purchasingfunctionbtn" name="purchasingfunctionbtn"
															value="removeunitsonorder" data-position="top"
															data-delay="25"
															data-tooltip="remove units on order from parts">RMV-(unitsonorder)</button>
													</span>
												</div>
												<div class="col s3">
													<span>
														<button class="btn tooltipped" type="submit"
															id="purchasingfunctionbtn" name="purchasingfunctionbtn"
															value="addunitsonorder" data-position="top"
															data-delay="25"
															data-tooltip="add units on order from parts">ADD-(unitsonorder)</button>
													</span>
												</div>
												<div class="col s3">
													<span>
														<button class="btn tooltipped" type="submit"
															id="purchasingfunctionbtn" name="purchasingfunctionbtn"
															value="updatepricesonorder" data-position="top"
															data-delay="25" data-tooltip="update prices to the order">UpdatePricesOnOrder</button>
													</span>
												</div>
												<div class="col s3">
													<span>
														<button class="btn tooltipped" type="submit"
															id="purchasingfunctionbtn" name="purchasingfunctionbtn"
															value="printpricesonorder" data-position="top"
															data-delay="25" data-tooltip="print prices to the order">PrintPricesOnOrder</button>
													</span>
												</div>
											</div>
											<div class="row">
												<div class="col s3">
													<span>
														<button class="btn tooltipped" type="submit"
															id="purchasingfunctionbtn" name="purchasingfunctionbtn"
															value="makefinalonorder" data-position="top"
															data-delay="25" data-tooltip="make final to the order">Make
															Final</button>
													</span>
												</div>
												<div class="col s3">
													<span>
														<button class="btn tooltipped" type="submit"
															id="purchasingfunctionbtn" name="purchasingfunctionbtn"
															value="removefinalonorder" data-position="top"
															data-delay="25" data-tooltip="remove final to the order">Remove
															Final</button>
													</span>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col s2">
									<div class="card  deep-purple lighten-5">
										<div class="card-content">
											<div class="row">
												<div class="col s12">
													<span>
														<button class="btn tooltipped" type="submit"
															id="purchasingfunctionbtn" name="purchasingfunctionbtn"
															value="copyorder" data-position="top" data-delay="25"
															data-tooltip="copies order to the specified branch">Copy
															Order</button>
													</span>
												</div>
											</div>
											<c:if test="${vendororder.orderstatus ne 'REMOVED' }">
												<div class="row">
													<div class="col s12">
														<span>
															<button class="btn tooltipped" type="submit"
																id="purchasingfunctionbtn" name="purchasingfunctionbtn"
																value="removepartlist" data-position="top"
																data-delay="25"
																data-tooltip="resets units in stock from partlist to the specified branch">Remove
																Partlist</button>
														</span>
													</div>
												</div>
												<!--div class="row">
													<div class="col s12">
														<span>
															<button class="btn tooltipped" type="submit"
																id="purchasingfunctionbtn" name="purchasingfunctionbtn"
																value="removefax" data-position="top" data-delay="25"
																data-tooltip="resets units in stock from fax to the specified branch">
																Remove Partlist</button>
														</span>
													</div>
												</div-->
											</c:if>
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</div>
				</div>
				<c:if test="${not empty requestScope.vendororderitemsdetailslist}">
					<div class="card">
						<div class="card-content">
							<div class="row">
								<div class="col s12">
									<table class="striped">
										<thead>
											<tr>
												<th width="7%">&nbsp;Part&nbsp;#</th>
												<th width="15%">&nbsp;Vendorpartno#</th>
												<th>Make</th>
												<th>Model</th>
												<th>Partdescription</th>
												<th>Year</th>
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
											<c:forEach
												items="${requestScope.vendororderitemsdetailslist}"
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
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.8em;"><strong><c:out
																value="${vendororderitems.manufacturername}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.8em;"><c:out
															value="${vendororderitems.makemodelname}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.8em;"><c:out
															value="${vendororderitems.partdescription}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.8em;"><c:out
															value="${vendororderitems.year}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.8em;"><c:out
															value="${vendororderitems.capa}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.8em;"><c:out
															value="${vendororderitems.unitsinstock}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.8em;"><c:out
															value="${vendororderitems.unitsonorder}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.8em;"><c:out
															value="${vendororderitems.reorderlevel}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.8em;"><c:out
															value="${vendororderitems.ordertype}" /></td>
													<td width="10%"><strong><input type="text"
															value="${vendororderitems.price}" name="price" id="price"
															style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
													<td width="10%"><strong><input type="text"
															value="${vendororderitems.quantity}" name="quantity"
															id="quantity"
															style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
													<td><c:out value="${vendororderitems.totalprice}" /></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</form>
		</div>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
		$body = $("body");

		$(document).on({
			ajaxStart : function() {
				$body.addClass("loading");
			},
			ajaxStop : function() {
				$body.removeClass("loading");
			}
		});
	</script>
	<script>
		$(document).ready(function() {

			var cb = '<c:out value="${bg}"/>';
			$('nav').css('background-color', cb);

			$(".button-collapse").sideNav();
			$(".dropdown-button").dropdown();
			$('select').material_select();

			//$('select').not('.disabled').material_select();

			$('.tooltipped').tooltip({
				delay : 25
			});

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

			$("#ordernosearchtxt").keypress(function(event) {
				if (event.which == 13) {
					event.preventDefault();
					$("#purchasingfunctionbtn").click();
				}
			});

			$("#purchasingfunctionbtn").click(function() {
				$("html, body").animate({
					scrollTop : 0
				}, "fast");
				$("#purchasingfunctionform").submit();
			});

			$("#purchasingfunctionform").submit(function() {
				var isFormValid = true;

				return isFormValid;
			});
		});
	</script>
	<div class="modal">
		<!-- Place at bottom of page -->
	</div>
</body>
</html>
