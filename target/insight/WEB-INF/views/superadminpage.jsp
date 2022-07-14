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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="msapplication-tap-highlight" content="no">
<meta name="description"
	content="Materialize is a Material Design Admin Template,It's modern, responsive and based on Material Design by Google. ">
<meta name="keywords"
	content="materialize, admin template, dashboard template, flat admin template, responsive admin template,">
<title>Materialize - Material Design Admin Template</title>
<!-- Favicons-->
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/main.jpg"
	sizes="32x32">
<!-- Favicons-->
<link rel="apple-touch-icon-precomposed"
	href="images/favicon/apple-touch-icon-152x152.png">
<!-- For iPhone -->
<meta name="msapplication-TileColor" content="#00bcd4">
<meta name="msapplication-TileImage"
	content="images/favicon/mstile-144x144.png">
<!-- For Windows Phone -->
<!-- CORE CSS-->
<link
	href="${pageContext.request.contextPath}/resources/materialize/css/materialize.css"
	type="text/css" rel="stylesheet" media="screen,projection">
<link
	href="${pageContext.request.contextPath}/resources/materialize/css/style.css"
	type="text/css" rel="stylesheet" media="screen,projection">
<link
	href="${pageContext.request.contextPath}/resources/materialize/css/layouts/style-fullscreen.css"
	type="text/css" rel="stylesheet" media="screen,projection">
<link
	href="${pageContext.request.contextPath}/resources/materialize/css/custom/custom.css"
	type="text/css" rel="stylesheet" media="screen,projection">
<link
	href="${pageContext.request.contextPath}/resources/materialize/js/plugins/perfect-scrollbar/perfect-scrollbar.css"
	type="text/css" rel="stylesheet" media="screen,projection">
<link
	href="${pageContext.request.contextPath}/resources/materialize/js/plugins/jvectormap/jquery-jvectormap.css"
	type="text/css" rel="stylesheet" media="screen,projection">
<link
	href="${pageContext.request.contextPath}/resources/materialize/js/plugins/chartist-js/chartist.min.css"
	type="text/css" rel="stylesheet" media="screen,projection">
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
<body bgcolor="#f4f6f8">
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
						</ul>
					</div>
				</nav>
			</form>
		</div>
	</header>
	<!-- END HEADER -->
	<div class="divider"></div>
	<!-- form start -->
	<form role="form" method="post" id="superadminform" action="">
		<!-- main -->
		<div id="main">
			<!-- wrapper -->
			<div class="wrapper">
				<!--start content -->
				<div class="divider"></div>
				<section id="address">
					<!--card widgets start-->
					<div id="address" class="section">
						<div class="row">
							<div class="col s6">
								<div id="profile-card" class="card">
									<div class="card-content">
										<div class="row"></div>
									</div>
								</div>
							</div>
							<div class="col s6">
								<div id="profile-card" class="card">
									<div class="card-content">
										<div class="row"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
				<section id="button">
					<div class="row"></div>
				</section>
			</div>
			<!-- wrapper -->
		</div>
		<!-- main -->
	</form>
	<!-- form end -->
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/jquery-1.11.2.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/materialize.js" />"></script>
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/prism/prism.js" />"></script> --%>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/perfect-scrollbar/perfect-scrollbar.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/custom-script.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/chartjs/chart.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/sparkline/jquery.sparkline.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/sparkline/sparkline-script.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins/jvectormap/vectormap-script.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/plugins.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/custom-script.js" />"></script>
	<script>
		$(document).ready(function() {

			var cb = '<c:out value="${bg}"/>';
			$('nav').css('background-color', cb);
			$(".button-collapse").sideNav();
			$(".dropdown-button").dropdown();
			$('select').material_select();
			$('.tooltipped').tooltip({
				delay : 50
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

			$("#superadminform").submit(function() {
				return isFormValid;
			});

		});
	</script>
</body>
</html>