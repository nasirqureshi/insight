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
				id="salesanalysisform" name="salesanalysisform"
				action="/insight/orders/salesanalysis">
				<div class="row">
					<h5 class="light col s12">Sales Analysis</h5>
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
										type="submit" id="salesanalysisBtn" name="salesanalysisBtn"
										value="categorysales">
										<i class="mdi-action-assignment-turned-in right"></i>
									</button></span>
							</div>
							<div class="col s2">
								<c:if test="${not empty categorysaleslist}">
									<span><button
											class="btn-floating btn-large waves-effect waves-light blue"
											type="submit" id="saveanalysisBtn" name="saveanalysisBtn"
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
							<table class="responsive-table">
								<thead>
									<tr>
										<th colspan="2"
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>partno</strong></th>
										<th colspan="4"
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>make</strong></th>
										<th colspan="4"
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>model</strong></th>
										<th colspan="8"
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>partdescription</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>sold</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>type</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>stock</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>order</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>re</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>buy</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>sell</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>%</strong></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${categorysaleslist}" var="salescategory">
										<tr>
											<c:forEach items="${salescategory.partsmonthlysaleslist}"
												var="monthlysales">
												<td
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; border: 0.1px solid grey; color: red; border-collapse: collapse; background-color: #fffde7;"><strong><c:out
															value="${fn:substring(monthlysales.yearmonth,0,4)}-${monthlysales.monthval}->${monthlysales.salescount}" /></strong></td>
											</c:forEach>
										</tr>
										<tr>
											<td colspan="2"
												style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.partno}" /></strong></td>
											<td colspan="4"
												style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.manufacturername}" /></strong></td>
											<td colspan="4"
												style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.makemodelname}" /></strong></td>
											<td colspan="8"
												style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.partdescription}" /></strong></td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.totalsold}" /></strong></td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.ordertype}" /></strong></td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.unitsinstock}" /></strong></td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.unitsonorder}" /></strong></td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.reorderlevel}" /></strong></td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.buyingprice}" /></strong></td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.sellingprice}" /></strong></td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.1em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;"><strong><c:out
														value="${salescategory.percent}" /></strong></td>
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

					$("#salesanalysisBtn").click(
							function() {
								$("#salesanalysisform").attr("action",
										"/insight/orders/salesanalysis");
								$("html, body").animate({
									scrollTop : 0
								}, "fast");
								$("#salesanalysisform").submit();
							});

					$("#saveanalysisBtn").click(
							function() {

								$("#salesanalysisform").attr("action",
										"/insight/orders/saveanalysis");
								$("html, body").animate({
									scrollTop : 0
								}, "fast");
								$("#salesanalysisform").submit();

							});

					$("#salesanalysisform").submit(function() {
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
