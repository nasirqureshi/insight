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
<title>Main</title>
<link
	href="${pageContext.request.contextPath}/resources/materialize/css/materialize.css"
	type="text/css" rel="stylesheet" media="screen,projection">
<link
	href="${pageContext.request.contextPath}/resources/materialize/css/style.css"
	type="text/css" rel="stylesheet" media="screen,projection">
<link
	href="${pageContext.request.contextPath}/resources/materialize/js/plugins/prism/prism.css"
	type="text/css" rel="stylesheet" media="screen,projection">
<link
	href="${pageContext.request.contextPath}/resources/materialize/js/plugins/perfect-scrollbar/perfect-scrollbar.css"
	type="text/css" rel="stylesheet" media="screen,projection">
<link
	href="${pageContext.request.contextPath}/resources/materialize/js/plugins/chartist-js/chartist.min.css"
	type="text/css" rel="stylesheet" media="screen,projection">
<link
	href="${pageContext.request.contextPath}/resources/materialize/css/custom/custom.css"
	type="text/css" rel="stylesheet" media="screen,projection">
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
				id="initiateorderform" name="initiateorderform"
				action="/insight/initiateorders/getorderdetails">
				<div class="row">
					<h5 class="light col s12">Initiate Orders</h5>
				</div>
				<div class="row">
					<div class="col s2">
						<input value="${requestScope.analyticsfromdate}" min="2000-01-01"
							max="2030-01-01" name="analyticsfromdate" id="analyticsfromdate"
							type="date">
					</div>
					<div class="col s2">
						<input value="${requestScope.analyticstodate}" min="2000-01-01"
							max="2030-01-01" name="analyticstodate" id="analyticstodate"
							type="date">
					</div>
					<div class="col s1">
						<label><strong>Catgry</strong></label> <select
							id="categoryselected" name="categoryselected"
							class="browser-default">
							<option value="${categoryselected}" selected>${categoryselected}</option>
							<c:forEach var="category" items="${categorylistdd}">
								<option value="${category.key}">${category.key}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col s1">
						<label><strong>Subctgry</strong></label> <select
							id="relsubcategoryselected" name="relsubcategoryselected"
							class="browser-default">
							<option value="${relsubcategoryselected}" selected>${relsubcategoryselected}</option>
							<c:forEach var="relsubcategory" items="${relsubcategorylist}">
								<option value="${relsubcategory.key}">${relsubcategory.key}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col s1">
						<label><strong>Make</strong></label> <select id="makeselected"
							name="makeselected" class="browser-default">
							<option value="${makeselected}" selected>${makeselected}</option>
							<c:forEach var="make" items="${makelistdd}">
								<option value="${make.key}">${make.key}</option>
							</c:forEach>
						</select>
					</div>
					<div class="input-field col s1">
						<input placeholder="50" name="stocklimit" id="stocklimit"
							type="text" class="validate" value="50"> <label
							for="stocklimit" class="active"><strong>stock
								less than</strong></label>
					</div>
					<div class="input-field col s1">
						<input placeholder="500" name="listlimit" id="listlimit"
							type="text" class="validate" value="-1"> <label
							for="listlimit" class="active"><strong>list
								limit</strong></label>
					</div>
					<div class="col s1">
						<p>
							<label><strong><input type="checkbox"
									id="avoidcapa" name="avoidcapa" checked="checked" />avoid capa</strong></label>
						</p>
					</div>
					<div class="col s1">
						<p>
							<input name="typeoforderrdio" type="radio" id="taiwanrdio"
								value="taiwanrdio" ${typeoforderrdio=='taiwanrdio'?'checked':''} />
							<label for="taiwanrdio"><strong>Taiwan</strong></label>
						</p>
						<p>
							<input name="typeoforderrdio" type="radio" id="eagleeyeorder"
								value="eagleeyeorder"
								${typeoforderrdio=='eagleeyeorder'?'checked':''} /> <label
								for="eagleeyeorder"><strong>Eagle Eye</strong></label>
						</p>
						<p>
							<input name="typeoforderrdio" type="radio" id="localorder"
								value="localorder" ${typeoforderrdio=='localorder'?'checked':''} />
							<label for="test2"><strong>Local</strong></label>
						</p>
					</div>
					<div class="col s1">
						<button
							class="btn-floating btn-large waves-effect waves-light  blue"
							type="submit" id="searchorderpartsbtn" name="searchorderpartsbtn"
							value="">
							<i class="mdi-action-search right"></i>
						</button>
					</div>
				</div>
				<div class="divider"></div>
			</form>
		</div>
	</div>

	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/jquery-1.11.2.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/materialize.js" />"></script>
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/prism/prism.js" />"></script> --%>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/perfect-scrollbar/perfect-scrollbar.min.js" />"></script>
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/chartist-js/chartist.min.js" />"></script> --%>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/custom-script.js" />"></script>
	<script>
		$(document)
				.ready(
						function() {

							var cb = '<c:out value="${bg}"/>';
							$('nav').css('background-color', cb);

							$(".button-collapse").sideNav();
							$(".dropdown-button").dropdown();
							$('select').material_select();
							$('.collapsible').collapsible({
								accordion : true
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

							$("#searchorderpartsbtn")
									.click(
											function() {

												$("#initiateorderform")
														.attr("action",
																"/insight/initiateorders/searchpartorder");
												$("html, body").animate({
													scrollTop : 0
												}, "fast");
												$("#initiateorderform")
														.submit();

											});

							$('#categoryselected')
									.change(
											function() {
												var selectedValue = $(this)
														.val();
												if (selectedValue.length > 0) {
													$("#initiateorderform")
															.attr("action",
																	"/insight/initiateorders/getsubcategorylist");
													$("html, body").animate({
														scrollTop : 0
													}, "fast");
													$("#initiateorderform")
															.submit();
												}
											});

							$("#initiateorderform").submit(function() {
								var isFormValid = true;
								if ($('#factor').val() == '') {
									alert('factor cannot be blank');
									isFormValid = false;
								}

								return isFormValid;
							});
						});
	</script>
</body>
</html>
