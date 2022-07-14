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
			<form class="col s12" role="form" method="post"
				id="deadinventoryform" name="deadinventoryform"
				action="/insight/orders/deadinventory">
				<div class="row">
					<h5 class="light col s12">Dead Inventory</h5>
				</div>
				<div class="card  blue-grey lighten-4">
					<div class="card-content">
						<div class="row">
							<div class="col s2">
								<input value="${requestScope.analyticsfromdate}"
									min="2000-01-01" max="2030-01-01" name="analyticsfromdate"
									id="analyticsfromdate" type="date">
							</div>
							<div class="col s2">
								<input value="${requestScope.analyticstodate}" min="2000-01-01"
									max="2030-01-01" name="analyticstodate" id="analyticstodate"
									type="date">
							</div>
							<div class="col s3">
								<label><strong>Select Subcategory</strong></label> <select
									id="subcategoryselected" name="subcategoryselected"
									class="browser-default">
									<option value="${subcategoryselected}" selected>${subcategoryselected}</option>
									<c:forEach var="subcategory" items="${subcategorylistdd}">
										<option value="${subcategory.key}">${subcategory.key}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col s2">
								<span><button
										class="btn-floating btn-large waves-effect waves-light blue"
										type="submit" id="deadinventoryBtn" name="deadinventoryBtn"
										value="categorysales">
										<i class="mdi-action-assignment-turned-in right"></i>
									</button></span>
							</div>
							<div class="col s2">
								<c:if test="${not empty categorysaleslist}">
									<span><button
											class="btn-floating btn-large waves-effect waves-light blue"
											type="submit" id="deadinventoryBtn" name="deadinventoryBtn"
											value="savesales">
											<i class="mdi-content-save right"></i>
										</button></span>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col s12">
						<c:if test="${not empty categorysaleslist}">
							<table class="striped">
								<thead>
									<tr>
										<th><strong>Partno</strong></th>
										<th><strong>Make</strong></th>
										<th><strong>Model</strong></th>
										<th><strong>Partdescription</strong></th>
										<th><strong>Totalsold</strong></th>
										<th><strong>Type</strong></th>
										<th><strong>UnitsinStock</strong></th>
										<th><strong>UnitsonOrder</strong></th>
										<th><strong>ReOrder</strong></th>
										<th><strong>Buy</strong></th>
										<th><strong>Sell</strong></th>
										<th><strong>%</strong></th>
										<th><strong>from</strong></th>
										<th><strong>to</strong></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${categorysaleslist}" var="salescategory">
										<tr>
											<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
														value="${salescategory.partno}" /></strong></td>
											<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
														value="${salescategory.manufacturername}" /></strong></td>
											<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
														value="${salescategory.makemodelname}" /></strong></td>
											<td style="word-wrap: break-word; white-space: normal;"><c:out
													value="${salescategory.partdescription}" /></td>
											<td style="word-wrap: break-word; white-space: normal;"><c:out
													value="${salescategory.totalsold}" /></td>
											<td style="word-wrap: break-word; white-space: normal;"><c:out
													value="${salescategory.ordertype}" /></td>
											<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
														value="${salescategory.unitsinstock}" /></strong></td>
											<td style="word-wrap: break-word; white-space: normal;"><c:out
													value="${salescategory.unitsonorder}" /></td>
											<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
														value="${salescategory.reorderlevel}" /></strong></td>
											<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
														value="${salescategory.buyingprice}" /></strong></td>
											<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
														value="${salescategory.sellingprice}" /></strong></td>
											<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
														value="${salescategory.percent}" /></strong></td>
											<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
														value="${salescategory.yearfrom}" /></strong></td>
											<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
														value="${salescategory.yearto}" /></strong></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
		$(document).ready(function() {
			
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
			
			$("#deadinventoryBtn").click(function() {
				$("html, body").animate({
					scrollTop : 0
				}, "fast");
				$("#deadinventoryform").submit();
			});

			$("#deadinventoryform").submit(function() {
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
