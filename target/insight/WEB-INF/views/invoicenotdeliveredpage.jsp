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
		<form class="col s12" role="form" method="post"
			id="invoicenotdeliveredform"
			action="/insight/warehouse/invoicenotdelivered">
			<div class="row">
				<h5 class="light col s12">Invoice Not Delivered</h5>
			</div>
			<div class="row">
				<input type="hidden" id="invoicenotdeliveredmode"
					name="invoicenotdeliveredmode" value="">
			</div>
			<div class="row">
				<input type="hidden" id="routefilename" name="routefilename"
					value="${routefilename}">
			</div>
			<div class="row">
				<div class="card blue-grey lighten-5">
					<div class="card-content">
						<div class="row">
							<div class="col s1">
								<label><strong>Route</strong></label><select id="routeselected"
									name="routeselected" class="browser-default">
									<option value="${routeselected}" selected>${routeselected}</option>
									<c:forEach var="route" items="${routelistdd}">
										<option value="${route}">${route}</option>
									</c:forEach>
								</select>
							</div>
							<div class="input-field col s2">
								<input id="wildtext" name="wildtext" value="" type="text">
								<label for="wildtext"><strong>Search(Inv/Cust/Notes)</strong></label>
							</div>
							<div class="col s1">
								<button class="btn-floating btn waves-effect waves-light  blue"
									type="submit" id="routesearchbtn" name="routesearchbtn"
									value="routesearch">
									<i class="mdi-action-search right"></i>
								</button>
							</div>
							<div class="col s1">
								<c:if test="${not empty invoicenotdeliveredlist}">
									<button class="btn-floating btn waves-effect waves-light  blue"
										type="submit" id="saveinvoicebtn" name="saveinvoicebtn"
										value="saveinvoice">
										<i class="mdi-content-save right"></i>
									</button>
								</c:if>
							</div>
							<div class="col s2">
								<label><strong>Select Driver</strong></label> <select
									id="driverselected" name="driverselected"
									class="browser-default">
									<option value="${driverselected}" selected>${driverselected}</option>
									<c:forEach var="driver" items="${driverlistdd}">
										<option value="${driver.key}">${driver.key}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col s1">
								<label><strong>AM/PM</strong></label> <select id="ampmselected"
									name="ampmselected" class="browser-default">
									<option value="${ampmselected}" selected>${ampmselected}</option>
									<option value="AM">AM</option>
									<option value="PM">PM</option>
								</select>
							</div>
							<div class="col s1">
								<label><strong>For Route</strong></label><select id="forroute"
									name="forroute" class="browser-default">
									<option value="${forroute}" selected>${forroute}</option>
									<c:forEach var="route" items="${routelistdd}">
										<option value="${route}">${route}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col s1">
								<c:if test="${not empty invoicenotdeliveredlist}">
									<button class="btn-floating btn waves-effect waves-light  blue"
										type="submit" id="createroutebtn" name="createroutebtn"
										value="createroute">
										<i class="mdi-action-language"></i>
									</button>
								</c:if>
							</div>
							<div class="col s1">
								<c:if test="${routefilename ne ''}">
									<button class="btn-floating btn waves-effect waves-light  blue"
										type="submit" id="printroutebtn" name="printroutebtn"
										value="printroute">
										<i class="mdi-maps-local-print-shop right"></i>
									</button>
								</c:if>
							</div>
							<div class="col s1">
								<c:if test="${not empty invoicenotdeliveredlist}">
									<h3 class="header">${fn:length(invoicenotdeliveredlist)}</h3>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="divider"></div>
			<div class="row">
				<div class="col s12">
					<c:if test="${not empty invoicenotdeliveredlist}">
						<p>
							<label><strong><input type="checkbox"
									id="checkAll" />select all</strong></label>
						</p>
						<table class="striped">
							<thead>
								<tr>
									<th><strong>Invoicenumber</strong></th>
									<th><strong>Date/Time</strong></th>
									<th><strong>Customer</strong></th>
									<th><strong>Company</strong></th>
									<th><strong>Sales</strong></th>
									<th><strong>Delivered</strong></th>
									<th width="35%"><strong>Notes</strong></th>
									<th><strong>Region</strong></th>
									<th><strong>Select</strong></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${invoicenotdeliveredlist}" var="notdelivered">
									<tr>
										<td style="word-wrap: break-word; white-space: normal;"><strong><input
												type="text" value="${notdelivered.value.invoicenumber}"
												name="invoicenumber" id="invoicenumber" readonly="readonly"
												style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
										<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
													value="${notdelivered.value.invoicetime}" /></strong></td>
										<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
													value="${notdelivered.value.customerid}" /></strong></td>
										<td style="word-wrap: break-word; white-space: normal;"><c:out
												value="${notdelivered.value.companyname}" /></td>
										<td style="word-wrap: break-word; white-space: normal;"><c:out
												value="${notdelivered.value.salesperson}" /></td>
										<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
													value="${notdelivered.value.isdelivered}" /></strong></td>
										<td width="35%"
											style="word-wrap: break-word; white-space: normal;"><strong><input
												type="text" value="${notdelivered.value.notes}" name="notes"
												id="notes"
												style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
										<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
													value="${notdelivered.value.region}" /></strong></td>
										<td><input type="checkbox"
											value="${notdelivered.value.invoicenumber}"
											id="invoiceselect" name="invoiceselect" /> <label
											for="invoiceselect"><strong>${notdelivered.value.invoicenumber}</strong></label></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>
		</form>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
		$(document)
				.ready(
						function() {

							$('select').material_select();

							var cb = '<c:out value="${bg}"/>';
							$('nav').css('background-color', cb);

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

							$("#checkAll").change(
									function() {
										$("input:checkbox").prop('checked',
												$(this).prop("checked"));
									});

							$("#printroutebtn")
									.click(
											function() {
												$("#invoicenotdeliveredform")
														.attr("action",
																"/insight/warehouse/printroute");
												$("html, body").animate({
													scrollTop : 0
												}, "fast");
												$("#invoicenotdeliveredform")
														.submit();
											});

							$("#createroutebtn")
									.click(
											function() {
												if ($('#ampmselected').val() == '') {
													alert('AM/PM cannot be blank');
													isFormValid = false;
												} else if ($('#driverselected')
														.val() == '') {
													alert('Please select a driver');
													isFormValid = false;
												} else if ($('#forroute').val() == '') {
													alert('Please select a route#');
													isFormValid = false;
												} else {
													$(
															"#invoicenotdeliveredform")
															.attr("action",
																	"/insight/warehouse/createroute");
													$("html, body").animate({
														scrollTop : 0
													}, "fast");
													$(
															"#invoicenotdeliveredform")
															.submit();
												}
											});

							$("#saveinvoicebtn")
									.click(
											function() {
												$("#invoicenotdeliveredform")
														.attr("action",
																"/insight/warehouse/saveinvoicenotes");
												$("html, body").animate({
													scrollTop : 0
												}, "fast");
												$("#invoicenotdeliveredform")
														.submit();
											});

							$("#wildtext")
									.keypress(
											function(event) {
												if (event.which == 13) {
													event.preventDefault();
													if ($('#wildtext').val() == '') {
														alert('invoice number  cannot be blank');
														isFormValid = false;
													} else {
														$(
																"#invoicenotdeliveredform")
																.attr("action",
																		"/insight/warehouse/getwildsearch");
														$("html, body")
																.animate(
																		{
																			scrollTop : 0
																		},
																		"fast");
														$(
																"#invoicenotdeliveredform")
																.submit();
													}
												}
											});

							$("#routesearchbtn")
									.click(
											function() {

												$("#invoicenotdeliveredform")
														.attr("action",
																"/insight/warehouse/getinvoices");
												$("html, body").animate({
													scrollTop : 0
												}, "fast");
												$("#invoicenotdeliveredform")
														.submit();

											});

						});
	</script>
</body>
</html>
