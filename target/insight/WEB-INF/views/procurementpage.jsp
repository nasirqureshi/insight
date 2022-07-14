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
<script type="text/javascript">
	var datefield = document.createElement("input")
	datefield.setAttribute("type", "date")
	if (datefield.type != "date") { //if browser doesn't support input type="date", load files for jQuery UI Date Picker
		document
				.write('<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />\n')
		document
				.write('<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"><\/script>\n')
		document
				.write('<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"><\/script>\n')
	}
</script>
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
			<form class="col s12" role="form" method="post" id="procurementform"
				name="procurementform" action="/insight/orders/procurement">
				<div class="row">
					<h5 class="light col s12">Procurement</h5>
				</div>
				<div class="card  blue-grey lighten-4">
					<div class="card-content">
						<div class="row">
							<div class="col s1">
								<label><strong>Target Month</strong></label> <input
									value="${requestScope.analyticsfromdate}" min="2000-01-01"
									max="2030-01-01" name="analyticsfromdate"
									id="analyticsfromdate" type="date">
							</div>
							<div class="col s2">
								<label><strong>Select Subcategory</strong></label> <select
									id="subcategoryselected" name="subcategoryselected"
									class="browser-default">
									<option value="${subcategoryselected}" selected>${subcategoryselected}</option>
									<c:forEach var="subcategory" items="${subcategorylistdd}">
										<option value="${subcategory.key}">${subcategory.key}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col s1">
								<label><strong>Select OrderType</strong></label> <select
									id="ordertypeselected" name="ordertypeselected"
									class="browser-default">
									<option value="${ordertypeselected}" selected>${ordertypeselected}</option>
									<c:forEach var="ordertype" items="${ordertypelistdd}">
										<option value="${ordertype}">${ordertype}</option>
									</c:forEach>
								</select>
							</div>
							<div class="input-field col s1">
								<input id="stocklimit" name="stocklimit" type="text"
									class="validate" value="${stocklimit}"> <label
									for="stocklimit"><strong>Stock less than</strong></label>
							</div>
							<div class="input-field col s1">
								<input id="onorderlimit" name="onorderlimit" type="text"
									class="validate" value="${onorderlimit}"> <label
									for="onorderlimit"><strong>OnOrder less than</strong></label>
							</div>
							<div class="input-field col s1">
								<input id="orderlimit" name="orderlimit" type="text"
									class="validate" value="${orderlimit}"> <label
									for="orderlimit"><strong>Order Limit</strong></label>
							</div>
							<div class="col s1">
								<span><button
										class="btn-floating btn waves-effect waves-light blue"
										type="submit" id="procurementBtn" name="procurementBtn"
										value="procurement">
										<i class="mdi-action-find-in-page  right">Search</i>
									</button></span>
							</div>
							<div class="col s1">
								<c:if test="${not empty requestScope.procurementpartslist}">
									<span><button
											class="btn-floating btn waves-effect waves-light blue"
											type="submit" id="saveprocurementBtn"
											name="saveprocurementBtn" value="saveprocurement">
											<i class="mdi-content-save right">Save</i>
										</button></span>
								</c:if>
							</div>
							<div class="col s1">
								<c:if test="${not empty requestScope.procurementpartslist}">
									<span><button
											class="btn-floating btn waves-effect waves-light blue"
											type="submit" id="getprojectionBtn" name="getprojectionBtn"
											value="getprojectionBtn">
											<i class="mdi-av-equalizer right">Sales</i>
										</button></span>
								</c:if>
							</div>
							<div class="col s1">
								<h3 class="header">${listsize}</h3>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${not empty requestScope.procurementpartslist}">
					<div class="card-panel z-depth-3">
						<div class="card-content">
							<div class="row">
								<div class="col s12">
									<table class="striped">
										<thead>
											<tr>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">&nbsp;partno</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">manufacturername</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">makemodelname</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">year</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">capa</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">type</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">partdescription</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">stck</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">ord</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">re</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">sfty</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">ordqty</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">dpi</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">plink</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">buy</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">sell</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">%</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">#1yrbck</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">#2yrbck</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.1px light grey; border-collapse: collapse; background-color: #42426F;">#3yrbck</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${requestScope.procurementpartslist}"
												var="procurement">
												<tr>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.2em; border: 0.1px light grey; border-collapse: collapse; color: red;"><strong><c:out
																value="${procurement.partno}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.9em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.manufacturername}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.9em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.makemodelname}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.9em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.year}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.9em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.capa}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.9em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.ordertype}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.9em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.partdescription}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.2em; border: 0.1px light grey; color: green; border-collapse: collapse;"><strong><c:out
																value="${procurement.unitsinstock}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.2em; border: 0.1px light grey; color: orange; border-collapse: collapse;"><strong><c:out
																value="${procurement.unitsonorder}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.2em; border: 0.1px light grey; color: blue; border-collapse: collapse;"><strong><c:out
																value="${procurement.reorderlevel}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.2em; border: 0.1px light grey; color: darkgrey; border-collapse: collapse;"><strong><c:out
																value="${procurement.safetyquantity}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.2em; border: 0.1px light grey; color: red; border-collapse: collapse;"><strong><c:out
																value="${procurement.quantitytoorder}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.9em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.dpinumber}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.9em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.keystonenumber}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.9em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.actualprice}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.9em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.costprice}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 0.9em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.percent}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.sales1yearback}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.sales2yearback}" /></strong></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px light grey; border-collapse: collapse;"><strong><c:out
																value="${procurement.sales3yearback}" /></strong></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<div class="card  blue-grey lighten-4">
					<div class="card-content">
						<div class="row">
							<div class="col s12">
								<c:if test="${not empty requestScope.procurementpartslist}">
									<div class="col s2">
										<span>
											<button class="btn waves-effect waves-light" type="submit"
												id="createoverseasorderBtn" name="createoverseasorderBtn"
												value="createoverseasorder">Create Overseas Order</button>
										</span>
									</div>
									<div class="col s2">
										<span>
											<button class="btn waves-effect waves-light" type="submit"
												id="createeagleeyesorderBtn" name="createeagleeyesorderBtn"
												value="createeagleeyesorder">Create Eagle Eyes
												Order</button>
										</span>
									</div>
								</c:if>
								<div class="col s1">
									<div class="card blue-grey lighten-4">
										<div class="card-content">
											<label><strong>Branch</strong></label> <select
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
								<div class="col s2">
									<span>
										<button class="btn waves-effect waves-light" type="submit"
											id="getpartsfrombranchBtn" name="getpartsfrombranchBtn"
											value="getpartsfrombranch">Parts From Other Branch</button>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
		$(document).ready(
				function() {

					var cb = '<c:out value="${bg}"/>';
					$('nav').css('background-color', cb);

					$(".button-collapse").sideNav();
					$(".dropdown-button").dropdown();
					$('select').material_select();

					$('.collapsible').collapsible({
						accordion : true
					// A setting that changes the collapsible behavior to expandable instead of the default accordion style
					});

					if (datefield.type != "date") { //if browser doesn't support input type="date", initialize date picker widget:
						jQuery(function($) { //on document.ready
							$('#analyticsfromdate').datepicker();
							$('#analyticstodate').datepicker();
						})
					}

					$("#navlogout").click(function() {
						$('input[name=navmode]').val('logout');
						$("#navform").submit();
					});

					$("#navhome").click(function() {
						$('input[name=navmode]').val('home');
						$("#navform").submit();
					});

					$("#procurementBtn").click(
							function() {
								$("#procurementform").attr("action",
										"/insight/orders/procurement");
								$("html, body").animate({
									scrollTop : 0
								}, "fast");
								$("#procurementform").submit();
							});

					$("#saveprocurementBtn").click(
							function() {

								$("#procurementform").attr("action",
										"/insight/orders/saveprocurement");
								$("html, body").animate({
									scrollTop : 0
								}, "fast");
								$("#procurementform").submit();

							});

					$("#getprojectionBtn").click(
							function() {

								$("#procurementform").attr("action",
										"/insight/orders/getprojection");
								$("html, body").animate({
									scrollTop : 0
								}, "fast");
								$("#procurementform").submit();

							});

					$("#procurementform").submit(function() {
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
