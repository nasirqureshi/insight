<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" manifest="/not-existing.appcache">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="msapplication-tap-highlight" content="no">
<title>Invoice</title>
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
<link href="${pageContext.request.contextPath}/resources/style/main.css"
	type="text/css">
<script
	src="${pageContext.request.contextPath}/resources/jquery/jquery.1.10.2.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/jquery/jquery.autocomplete.min.js"></script>
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
<style>
input[type=text] {
	margin: 0 0 0 0px !important;
}

.input-field {
	margin: 0 0 0 0px !important;
}

/* Remove default checkbox */
[type="checkbox"]:not (:checked ), [type="checkbox"]:checked {
	position: absolute;
	left: -9999px;
	opacity: 0;
}

[type="checkbox"] {
	/* checkbox aspect */
	
}

[type="checkbox"]+label {
	position: relative;
	padding-left: 35px;
	cursor: pointer;
	display: inline-block;
	height: 25px;
	line-height: 25px;
	font-size: 1.0em;
	-webkit-user-select: none;
	/* webkit (safari, chrome) browsers */
	-moz-user-select: none;
	/* mozilla browsers */
	-khtml-user-select: none;
	/* webkit (konqueror) browsers */
	-ms-user-select: none;
	/* IE10+ */
}

[type="checkbox"]+label:before, [type="checkbox"]:not (.filled-in )+label:after
	{
	content: '';
	position: absolute;
	top: 0;
	left: 0;
	width: 18px;
	height: 18px;
	z-index: 0;
	border: 2px solid #5a5a5a;
	border-radius: 1px;
	margin-top: 2px;
	transition: .2s;
}

[type="checkbox"]:not (.filled-in )+label:after {
	border: 0;
	-webkit-transform: scale(0);
	transform: scale(0);
}

[type="checkbox"]:not (:checked ):disabled+label:before {
	border: none;
	background-color: rgba(0, 0, 0, 0.26);
}

[type="checkbox"].tabbed:focus+label:after {
	-webkit-transform: scale(1);
	transform: scale(1);
	border: 0;
	border-radius: 50%;
	box-shadow: 0 0 0 10px rgba(0, 0, 0, 0.1);
	background-color: rgba(0, 0, 0, 0.1);
}

[type="checkbox"]:checked+label:before {
	top: -4px;
	left: -5px;
	width: 12px;
	height: 22px;
	border-top: 2px solid transparent;
	border-left: 2px solid transparent;
	border-right: 2px solid #26a69a;
	border-bottom: 2px solid #26a69a;
	-webkit-transform: rotate(40deg);
	transform: rotate(40deg);
	-webkit-backface-visibility: hidden;
	backface-visibility: hidden;
	-webkit-transform-origin: 100% 100%;
	transform-origin: 100% 100%;
}

[type="checkbox"]:checked:disabled+label:before {
	border-right: 2px solid rgba(0, 0, 0, 0.26);
	border-bottom: 2px solid rgba(0, 0, 0, 0.26);
}

/* Indeterminate checkbox */
[type="checkbox"]:indeterminate+label:before {
	top: -11px;
	left: -12px;
	width: 10px;
	height: 22px;
	border-top: none;
	border-left: none;
	border-right: 2px solid #26a69a;
	border-bottom: none;
	-webkit-transform: rotate(90deg);
	transform: rotate(90deg);
	-webkit-backface-visibility: hidden;
	backface-visibility: hidden;
	-webkit-transform-origin: 100% 100%;
	transform-origin: 100% 100%;
}

[type="checkbox"]:indeterminate:disabled+label:before {
	border-right: 2px solid rgba(0, 0, 0, 0.26);
	background-color: transparent;
}

[type="checkbox"].filled-in+label:after {
	border-radius: 2px;
}

[type="checkbox"].filled-in+label:before, [type="checkbox"].filled-in+label:after
	{
	content: '';
	left: 0;
	position: absolute;
	/* .1s delay is for check animation */
	transition: border .25s, background-color .25s, width .20s .1s, height
		.20s .1s, top .20s .1s, left .20s .1s;
	z-index: 1;
}

[type="checkbox"].filled-in:not (:checked )+label:before {
	width: 0;
	height: 0;
	border: 3px solid transparent;
	left: 6px;
	top: 10px;
	-webkit-transform: rotateZ(37deg);
	transform: rotateZ(37deg);
	-webkit-transform-origin: 20% 40%;
	transform-origin: 100% 100%;
}

[type="checkbox"].filled-in:not (:checked )+label:after {
	height: 20px;
	width: 20px;
	background-color: transparent;
	border: 2px solid #5a5a5a;
	top: 0px;
	z-index: 0;
}

[type="checkbox"].filled-in:checked+label:before {
	top: 0;
	left: 1px;
	width: 8px;
	height: 13px;
	border-top: 2px solid transparent;
	border-left: 2px solid transparent;
	border-right: 2px solid #fff;
	border-bottom: 2px solid #fff;
	-webkit-transform: rotateZ(37deg);
	transform: rotateZ(37deg);
	-webkit-transform-origin: 100% 100%;
	transform-origin: 100% 100%;
}

[type="checkbox"].filled-in:checked+label:after {
	top: 0;
	width: 20px;
	height: 20px;
	border: 2px solid #26a69a;
	background-color: #26a69a;
	z-index: 0;
}

[type="checkbox"].filled-in.tabbed:focus+label:after {
	border-radius: 2px;
	border-color: #5a5a5a;
	background-color: rgba(0, 0, 0, 0.1);
}

[type="checkbox"].filled-in.tabbed:checked:focus+label:after {
	border-radius: 2px;
	background-color: #26a69a;
	border-color: #26a69a;
}

.autocomplete-suggestions {
	border: 1px solid #999;
	background: #FFF;
	overflow: auto;
}

.autocomplete-suggestion {
	padding: 5px 5px;
	white-space: nowrap;
	overflow: hidden;
	font-size: 1.0em;
}

.autocomplete-selected {
	background: #F0F0F0;
}

.autocomplete-suggestions strong {
	font-weight: bold;
	color: black;
	color: #3399FF;
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
<body bgcolor="#ffffff">
	<script type="text/javascript"
		src="<c:url value="/resources/materialize/js/materialize.js" />"></script>
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
	<!-- START HEADER -->
	<header id="htm_header" class="page-topbar">
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
							<li><a id="user" href="#">${user.username}</a></li>
							<li><a id="date" href="#">${sysdate}</a></li>
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
	<form role="form" method="post" id="invoiceform" action="">
		<!-- main -->
		<div id="main">
			<!-- wrapper -->
			<div class="wrapper">
				<c:if test="${(invoicestatus.invoicemessage ne '')}">
					<div class="row" style="margin: 0 0 0 0px !important;">
						<div class="col s6 offset-s3">
							<span style="color: #ff0000; font-weight: bold; color: black;"
								class="card-title">${invoicestatus.invoicemessage}</span>
						</div>
					</div>
				</c:if>
				<!--start customer -->
				<section id="customer">
					<!--card widgets start-->
					<div id="customer_parts" class="section"
						style="padding: 0 0 0 0px !important; margin: 0 0 0 0px !important;">
						<div class="row" style="margin: 0 0 0 0px !important;">
							<div class="col s10">
								<div id="profile-card" class="grey lighten-3 card">
									<div class="card-content">
										<div class="input-field col s2">
											<input id="searchcustomernumber" name="searchcustomernumber"
												value="${searchcustomernumber}" class="validate" type="text"
												style="font-size: 1.0em; font-weight: bold; color: black; color: red;"
												placeholder="Search Customer #"> <label
												for="searchcustomernumber"><strong>Search
													Customer #</strong></label>
										</div>
										<c:if test="${not empty customer}">
											<div class="col s3">
												<span
													style="font-size: 1.0em; font-weight: bold; color: black;">${customer.companyname}&nbsp;</span>
											</div>
											<div class="col s1">
												<span
													style="font-size: 1.0em; font-weight: bold; color: black;">${customer.customerid}&nbsp;</span>
											</div>
											<div class="col s1">
												<span
													style="font-size: 1.0em; font-weight: bold; color: black;">${customer.terms}&nbsp;</span>
											</div>
											<div class="col s1">
												<span
													style="font-size: 1.0em; font-weight: bold; color: black;">${customer.taxid}&nbsp;</span>
											</div>
											<div class="col s1">
												<span
													style="font-size: 1.0em; font-weight: bold; color: black;">${customer.creditbalance}&nbsp;</span>
											</div>
											<div class="col s1">
												<span
													style="font-size: 1.0em; font-weight: bold; color: black;">${customer.paymentterms}&nbsp;</span>
											</div>
										</c:if>
									</div>
								</div>
							</div>
							<div class="col s2">
								<div id="profile-card" class="grey lighten-2 card">
									<div class="card-content">
										<div class="input-field col s12">
											<input id="searchinvoicenumber" name="searchinvoicenumber"
												value="${searchinvoicenumber}" type="text" class="validate"
												style="font-size: 1.0em; font-weight: bold; color: black; color: red;"
												placeholder="Search Invoice"> <label
												for="searchcustomernumber"><strong>Search
													Invoice#</strong></label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--customer row end -->
					</div>
				</section>
				<!--end section customer -->
				<!--start section address -->
				<section id="address">
					<c:if test="${not empty requestScope.customer}">
						<div class="row" style="margin: 0 0 0 0px !important;">
							<div class="col s6">
								<div id="profile-card" class="grey lighten-4 card"
									style="margin: 5px !important;">
									<div class="card-content">
										<div class="row">
											<div class="input-field col s4">
												<input class="validate" id="billAttention"
													name="billAttention"
													value="${displayinvoice.billAttention}"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													placeholder="Bill Attention" type="text">
											</div>
											<div class="input-field col s4">
												<input class="validate" id="ba_addr1" name="ba_addr1"
													value="${billtoaddress.addr1}" placeholder="Address 1"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
											<div class="input-field col s4">
												<input class="validate" id="ba_addr2" name="ba_addr2"
													value="${billtoaddress.addr2}" placeholder="Address 2"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
										</div>
										<div class="row">
											<div class="input-field col s4">
												<input class="validate" id="ba_city" name="ba_city"
													value="${billtoaddress.city}" placeholder="City"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
											<div class="input-field col s2">
												<input class="validate" id="ba_state" name="ba_state"
													value="${billtoaddress.state}" placeholder="State"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
											<div class="input-field col s2">
												<input class="validate" id="ba_zip" name="ba_zip"
													value="${billtoaddress.postalcode}" placeholder="Zip"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
											<div class="input-field col s2">
												<input class="validate" id="ba_region" name="ba_region"
													value="${billtoaddress.region}" placeholder="Region"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
											<div class="input-field col s2">
												<input class="validate" id="ba_country" name="ba_country"
													value="${billtoaddress.country}" placeholder="Country"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col s1">
								<input type="checkbox" id="billsameship" name="billsameship"
									value="billsameship"> <label for="billsameship">Bill
									Same As Ship</label>
							</div>
							<div id="profile-card-ship" class="col s5">
								<div id="profile-card" class="grey lighten-4 card"
									style="margin: 5px !important;">
									<div class="card-content">
										<div class="row">
											<div class="input-field col s6">
												<input class="validate" id="sa_addr1" name="sa_addr1"
													value="${shiptoaddress.addr1}" placeholder="Address 1"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
											<div class="input-field col s6">
												<input class="validate" id="sa_addr2" name="sa_addr2"
													value="${shiptoaddress.addr2}" placeholder="Address 2"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
										</div>
										<div class="row">
											<div class="input-field col s4">
												<input class="validate" id="sa_city" name="sa_city"
													value="${shiptoaddress.city}" placeholder="City"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
											<div class="input-field col s2">
												<input class="validate" id="sa_state" name="sa_state"
													value="${shiptoaddress.state}" placeholder="State"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
											<div class="input-field col s2">
												<input class="validate" id="sa_zip" name="sa_zip"
													value="${shiptoaddress.postalcode}" placeholder="Zip"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
											<div class="input-field col s2">
												<input class="validate" id="sa_region" name="sa_region"
													value="${shiptoaddress.region}" placeholder="Region"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
											<div class="input-field col s2">
												<input class="validate" id="sa_country" name="sa_country"
													value="${shiptoaddress.country}" placeholder="Country"
													style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black;"
													type="text">
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</section>
				<!--end section address -->
				<!--start section parts -->
				<section id="parts">
					<div class="row" style="margin: 0 0 0 0px !important;">
						<div class="col s1">
							<div id="profile-card" class="grey lighten-2 card">
								<c:if
									test="${(not empty requestScope.customer) && (invoicemode ne 'error')}">
									<div class="row" style="margin: 0 0 0 0px !important;">
										<div class="card-content">
											<input id="partnoinvoicedetails" name="partnoinvoicedetails"
												value="${partnoinvoicedetails}" type="text"
												placeholder="Add Part#"
												style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black; color: red;">
										</div>
									</div>
								</c:if>
							</div>
						</div>
						<c:if
							test="${(not empty requestScope.customer) && (invoicemode ne 'proceed')}">
							<div class="col s11">
								<div id="profile-card" class="grey lighten-2 card">
									<div class="row" style="margin: 0 0 0 0px !important;">
										<div class="card-content">
											<div class="col s1">
												<label><strong>Year</strong></label><select
													id="yearselected" name="yearselected"
													class="browser-default"
													style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black;">
													<option value="${yearselected}" selected>${yearselected}</option>
													<c:forEach var="year" items="${yearslistdd}">
														<option value="${year}">${year}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col s3">
												<label><strong>Make</strong></label><select
													id="makeselected" name="makeselected"
													class="browser-default"
													style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black;">
													<option value="${makeselected}" selected>${makeselected}</option>
													<c:forEach var="make" items="${makelistdd}">
														<option value="${make.key}">${make.key}</option>
													</c:forEach>
												</select>
											</div>
											<c:if test="${not empty requestScope.relmakemodellist}">
												<div class="col s3">
													<label><strong>Model</strong></label><select
														id="modelselected" name="modelselected"
														class="browser-default"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black;">
														<option value="${modelselected}" selected>${modelselected}</option>
														<c:forEach var="model" items="${relmakemodellist}">
															<option value="${model.key}">${model.key}</option>
														</c:forEach>
													</select>
												</div>
											</c:if>
											<c:if test="${not empty requestScope.relpartslist}">
												<div class="col s5">
													<label><strong>Parts</strong></label><select
														id="partselected" name="partselected"
														class="browser-default"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black;">
														<option value="${partselected}" selected>${partselected}</option>
														<c:forEach var="part" items="${relpartslist}">
															<option id="${part.key}" value="${part.value}">${part.value}</option>
														</c:forEach>
													</select>
												</div>
											</c:if>
										</div>
									</div>
									<c:if test="${not empty requestScope.displaypart}">
										<div class="row" style="margin: 0 0 0 0px !important;">
											<div class="card-content">
												<div class="input-field col s1">
													<input disabled value="${displaypart.partno}" min="0"
														name="dpf_partno" id="dpf_partno" type="text"
														style="word-wrap: break-word; white-space: normal; font-weight: bold; color: black; border: none;">
													<label for="dpf_partno"><strong>Part #</strong></label>
												</div>
												<div class="input-field col s1">
													<input disabled value="${displaypart.interchangeno}"
														min="0" name="dpf_interchangeno" id="dpf_interchangeno"
														type="text"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black; border: none;">
													<label for="dpf_interchangeno"><strong>Int
															#</strong></label>
												</div>
												<div class="input-field col s1">
													<input disabled value="${displaypart.keystonenumber}"
														min="0" name="dpf_plink" id="dpf_plink" type="text"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black; border: none;">
													<label for="dpf_plink"><strong>Plink</strong></label>
												</div>
												<div class="input-field col s1">
													<input disabled value="${displaypart.oemnumber}" min="0"
														name="dpf_oem" id="dpf_oem" type="text"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black; border: none;">
													<label for="dpf_oem"><strong>OEM</strong></label>
												</div>
												<div class="input-field col s1">
													<input disabled value="${displaypart.dpinumber}" min="0"
														name="dpf_dpi" id="dpf_dpi" type="text"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black; border: none;">
													<label for="dpf_dpi"><strong>DPI</strong></label>
												</div>
												<div class="input-field col s1">
													<input disabled value="${displaypart.unitsinstock}" min="0"
														name="dpf_unitsinstock" id="dpf_unitsinstock" type="text"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black; border: none;">
													<label for="dpf_unitsinstock"><strong>ST</strong></label>
												</div>
												<div class="input-field col s1">
													<input disabled value="${displaypart.unitsonorder}" min="0"
														name="dpf_unitsonorder" id="dpf_unitsonorder" type="text"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black; border: none;">
													<label for="dpf_unitsonorder"><strong>ORD</strong></label>
												</div>
												<div class="input-field col s1">
													<input disabled value="${displaypart.reorderlevel}" min="0"
														name="dpf_reorderlevel" id="dpf_reorderlevel" type="text"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black; border: none;">
													<label for="dpf_reorderlevel"><strong>RO</strong></label>
												</div>
												<div class="input-field col s1">
													<input disabled value="${displaypart.listprice}" min="0"
														name="dpf_listprice" id="dpf_listprice" type="text"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black; border: none;">
													<label for="dpf_listprice"><strong>List</strong></label>
												</div>
												<div class="input-field col s1">
													<input disabled value="${displaypart.calculatedprice}"
														min="0" name="dpf_customerprice" id="dpf_customerprice"
														type="text"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black; color: red; border: none;">
													<label for="dpf_customerprice"><strong>Sell</strong></label>
												</div>
												<div class="input-field col s1">
													<input disabled value="${displaypart.location}" min="0"
														name="dpf_location" id="dpf_location" type="text"
														style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black; border: none;">
													<label for="dpf_location"><strong>Loc</strong></label>
												</div>
												<div class="col s1">
													<button
														class="btn-floating btn waves-effect waves-light  blue"
														type="submit" id="addpartstoinvoicebtn"
														name="addpartstoinvoicebtn" value="${displaypart.partno}">
														<i class="mdi-content-add-circle right"></i>
													</button>
												</div>
											</div>
										</div>
									</c:if>
									<p></p>
								</div>
							</div>
						</c:if>
					</div>
				</section>
				<!--end section parts -->
				<c:if test="${not empty requestScope.customer}">
					<!--start section invoice -->
					<section id="invoice">
						<div id="invoice" class="section"
							style="padding: 0 0 0 0px !important; margin: 0 0 0 0px !important;">
							<c:if
								test="${not empty requestScope.displayinvoice.displayinvoicedetailslist}">
								<div class="row" style="margin: 0 0 0 0px !important;">
									<div class="invoice-table">
										<div class="row">
											<div class="col s12 m12 l12">
												<table class="striped">
													<thead>
														<tr>
															<th></th>
															<th>Make</th>
															<th width="20%">Model</th>
															<th width="30%">Part description</th>
															<th>Year</th>
															<th>St</th>
															<th>Re</th>
															<th>Or</th>
															<th>Sell</th>
															<th>List</th>
															<th>Loc</th>
															<th>Qty</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach
															items="${requestScope.displayinvoice.displayinvoicedetailslist}"
															var="dpinvoicedetails">
															<tr>
																<td><strong><input type="text"
																		value="${dpinvoicedetails.partnumber}"
																		name="dplistpartnumber"
																		id="${dpinvoicedetails.partnumber}"
																		style="word-wrap: break-word; white-space: normal; font-size: 1.2em; font-weight: bold; color: black; color: green;" /></strong></td>
																<td width="10%"><input type="text"
																	value="${dpinvoicedetails.manufacturername}"
																	name="dplistmanufacturername"
																	id="dplistmanufacturername"
																	style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black; border-style: hidden;"
																	disabled /></td>
																<td width="20%"><input type="text"
																	value="${dpinvoicedetails.makemodelname}"
																	name="dplistmakemodelname" id="dplistmakemodelname"
																	style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black; border-style: hidden;"
																	disabled /></td>
																<td width="30%"><input type="text"
																	value="${dpinvoicedetails.partdescription}"
																	name="dplistpartdescription" id="dplistpartdescription"
																	style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black; border-style: hidden;"
																	disabled /></td>
																<td><input type="text" name="dplistyear"
																	value="${dpinvoicedetails.year}" id="dplistyear"
																	style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black; border-style: hidden;"
																	disabled /></td>
																<td><input type="text"
																	value="${dpinvoicedetails.unitsinstock}"
																	name="dplistunitsinstock" id="dplistunitsinstock"
																	style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black; border-style: hidden;"
																	disabled /></td>
																<td><input type="text"
																	value="${dpinvoicedetails.reorderlevel}"
																	name="dplistreorderlevel" id="dplistreorderlevel"
																	style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black; border-style: hidden;"
																	disabled /></td>
																<td><input type="text"
																	value="${dpinvoicedetails.unitsonorder}"
																	name="dplistunitsonorder" id="dplistunitsonorder"
																	style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black; border-style: hidden;"
																	disabled /></td>
																<td><input type="text"
																	value="${dpinvoicedetails.soldprice}"
																	name="dplistsoldprice" id="dplistsoldprice"
																	style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black; color: red; border-style: hidden;" /></td>
																<td><input type="text"
																	value="${dpinvoicedetails.listprice}"
																	name="dplistlistprice" id="dplistlistprice"
																	style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black; border-style: hidden;"
																	disabled /></td>
																<td><input type="text"
																	value="${dpinvoicedetails.location}"
																	name="dplistlocation" id="dplistlocation"
																	style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black; border-style: hidden;"
																	disabled /></td>
																<td><input type="text"
																	value="${dpinvoicedetails.quantity}"
																	name="dplistquantity" id="dplistquantity"
																	style="word-wrap: break-word; white-space: normal; font-size: 0.8em; font-weight: bold; color: black; border-style: hidden;" /></td>
																<td>
																	<button
																		class="btn-floating btn waves-effect waves-light  blue"
																		type="submit"
																		id="rempart${dpinvoicedetails.partnumber}"
																		name="removepartstoinvoice"
																		value="${dpinvoicedetails.partnumber}">
																		<i class="mdi-content-remove-circle right"></i>
																	</button>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</c:if>
							<div class="row" style="margin: 0 0 0 0px !important;">
								<div class="col s12">
									<div id="profile-card" class="grey lighten-1 card">
										<div class="card-content">
											<div class="col s1">
												<h5 class="blue-text invoice-text">${displayinvoice.invoicenumber}</h5>
											</div>
											<%-- <div class="input-field col s1">
											<input id="displayinvoicenumber" name="displayinvoicenumber"
												value="${displayinvoice.invoicenumber}" type="text"
												style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black;">
											<label for="displayinvoicenumber"><strong>Invoice</strong></label>
										</div> --%>
											<div class="input-field col s2">
												<input id="displayorderdate" name="displayorderdate"
													value="<fmt:formatDate value='${displayinvoice.orderdate}' pattern="MM-dd-yyyy" />"
													type="text"
													style="word-wrap: break-word; white-space: normal; font-size: 1.3em; font-weight: bold; color: black;">
												<label for="displayorderdate"><strong>Order
														Date</strong></label>
											</div>
											<div class="input-field col s1">
												<input id="displayterms" name="displayterms"
													value="${displayinvoice.paymentterms}" type="text"
													style="word-wrap: break-word; white-space: normal; font-size: 1.3em; font-weight: bold; color: black;">
												<label for="displayterms"><strong>Terms</strong></label>
											</div>
											<div class="input-field col s1">
												<input id="displaysalesperson" name="displaysalesperson"
													value="${displayinvoice.salesperson}" type="text"
													style="word-wrap: break-word; white-space: normal; font-size: 1.3em; font-weight: bold; color: black;">
												<label for="displaysalesperson"><strong>Sales</strong></label>
											</div>
											<div class="input-field col s1">
												<input id="displayinvoicetotal" name="displayinvoicetotal"
													value="${displayinvoice.invoicetotal}" type="text"
													style="word-wrap: break-word; white-space: normal; font-size: 1.3em; font-weight: bold; color: black;">
												<label for="displayinvoicetotal"><strong>Invoice
														Total</strong></label>
											</div>
											<div class="input-field col s1">
												<input id="displaytax" name="displaytax"
													value="${displayinvoice.tax}" type="text"
													style="word-wrap: break-word; white-space: normal; font-size: 1.3em; font-weight: bold; color: black;">
												<label for="displaytax"><strong>Tax</strong></label>
											</div>
											<div class="input-field col s1">
												<input id="displaydiscount" name="displaydiscount"
													value="${displayinvoice.discount}" type="text"
													style="word-wrap: break-word; white-space: normal; font-size: 1.3em; font-weight: bold; color: black;">
												<label for="displaydiscount"><strong>Disc</strong></label>
											</div>
											<div class="input-field col s1">
												<input id="displaynewapplied" name="displaynewapplied"
													value="${displayinvoice.balance}" type="text"
													style="word-wrap: break-word; white-space: normal; font-size: 1.3em; font-weight: bold; color: black;">
												<label for="displaynewapplied"><strong>Amount
														Owed</strong></label>
											</div>
											<div class="col s1">
												<select id="shipvia" name="shipvia" class="browser-default">
													<option value="${shipvia}"
														style="word-wrap: break-word; white-space: normal; font-size: 1.3em; font-weight: bold; color: black;"
														selected>${shipvia}</option>
													<c:forEach var="ship" items="${shippinglistdd}">
														<option
															style="word-wrap: break-word; white-space: normal; font-size: 1.3em; font-weight: bold; color: black;"
															value="${ship.key}">${ship.key}</option>
													</c:forEach>
												</select> <label class="active"><strong>Ship Type</strong></label>
											</div>
											<div class="col s1">
												<p>
													<button
														class="btn-floating btn waves-effect waves-light blue"
														type="submit" id="updateinvoicebtn"
														name="updateinvoicebtn" value="updateinvoice">
														<i class="mdi-action-done   right"></i>
													</button>
												</p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</section>
					<!--end section invoice -->
				</c:if>
			</div>
			<!-- wrapper ends-->
		</div>
		<!-- main ends-->
		<section id="button">
			<div class="row">
				<c:if test="${(requestScope.buttonlogic.createinvoice == 1)}">
					<div class="col s2">
						<button class="waves-effect waves-light btn blue-grey lighten-1"
							type="submit" id="createnewinvoicebtn" name="createnewinvoicebtn"
							value="createnewinvoice">
							<i class="mdi-content-send  right"></i>Create
						</button>
					</div>
				</c:if>
				<c:if test="${(requestScope.buttonlogic.modifyinvoice == 1)}">
					<div class="col s2">
						<button class="waves-effect waves-light btn blue-grey lighten-1"
							type="submit" id="modifyinvoicebtn" name="modifyinvoicebtn"
							value="modifyinvoice">
							<i class="mdi-content-send  right"></i>Modify
						</button>
					</div>
				</c:if>
				<c:if test="${(requestScope.buttonlogic.archiveinvoice == 1)}">
					<div class="col s2">
						<button class="waves-effect waves-light btn  blue-grey lighten-1"
							type="submit" id="archiveinvoicebtn" name="archiveinvoicebtn"
							value="archiveinvoice">
							<i class="mdi-content-content-copy  right"></i>Archive
						</button>
					</div>
				</c:if>
				<c:if test="${(requestScope.buttonlogic.printinvoice == 1)}">
					<div class="col s2">
						<button class="waves-effect waves-light btn  blue-grey lighten-1"
							type="button" id="printinvoicebtn" name="printinvoicebtn"
							value="printinvoice">
							<i class="mdi-action-print  right"></i>Print
						</button>
					</div>
				</c:if>
				<c:if test="${(requestScope.buttonlogic.returninvoice == 1)}">
					<div class="col s2">
						<button class="waves-effect waves-light btn blue-grey lighten-1"
							type="submit" id="returninvoicebtn" name="returninvoicebtn"
							value="returninvoice">
							<i class="mdi-content-undo    right"></i>Return
						</button>
					</div>
				</c:if>
				<c:if test="${(requestScope.buttonlogic.resetinvoice == 1)}">
					<div class="col s2">
						<button class="waves-effect waves-light btn red darken-1"
							type="submit" id="resetinvoicebtn" name="resetinvoicebtn"
							value="resetinvoice">
							<i class="mdi-action-cached   right"></i>Reset
						</button>
					</div>
				</c:if>
			</div>
		</section>
		<p>
			<input type="hidden" id="selectpartnumber" name="selectpartnumber"
				value="{selectpartnumber}">
		</p>
	</form>
	<!-- form end -->
	<form method="post" id="printForm" name="printForm"
		action="/insight/invoice/printinvoice" target="_blank">
		<input type="hidden" name="invPrintName" value="${printFileName}" />
	</form>
	<script>
		$(document)
				.ready(
						function() {

							var cb = '<c:out value="${bg}"/>';
							$('nav').css('background-color', cb);
							$(".button-collapse").sideNav();
							$(".dropdown-button").dropdown();
							$('select').material_select();
							$('.tooltipped').tooltip({
								delay : 50
							});
							$("#createnewinvoicebtn").click(
									function() {

										var selectedValue = $(this).val();
										event.preventDefault();
										isFormValid = true;
										$("#invoiceform").attr(
												"action",
												"/insight/invoice/"
														+ "createnewinvoice");
										$("#invoiceform").submit();
									});
							$("#updateinvoicebtn").click(
									function() {

										var selectedValue = $(this).val();
										event.preventDefault();
										isFormValid = true;
										$("#invoiceform").attr(
												"action",
												"/insight/invoice/"
														+ "updateinvoice");
										$("#invoiceform").submit();
									});
							$("#modifyinvoicebtn")
									.click(
											function() {
												event.preventDefault();
												isFormValid = true;
												$("#invoiceform")
														.attr("action",
																"/insight/invoice/modifyinvoice");
												$("#invoiceform").submit();
											});
							$("#invcreatebtn").click(
									function() {
										event.preventDefault();
										isFormValid = true;
										$("#invoiceform").attr("action",
												"/insight/invoice/invcreate");
										$("#invoiceform").submit();
									});
							$("#resetinvoicebtn")
									.click(
											function() {
												event.preventDefault();
												isFormValid = true;
												$("#invoiceform")
														.attr("action",
																"/insight/invoice/resetinvoice");
												$("#invoiceform").submit();
											});
							$("#printinvoicebtn").click(function() {
								$("#printForm").submit();
							});
							$("#partnoinvoicedetails")
									.keypress(
											function(event) {

												if (event.which == 13) {
													event.preventDefault();
													if ($(
															'#partnoinvoicedetails')
															.val() == '') {
														alert('partno cannot be blank');
														isFormValid = false;
													} else {
														var selectedValue = $(
																this).val();
														$(
																'input[name=selectpartnumber]')
																.val(
																		selectedValue);
														event.preventDefault();
														isFormValid = true;
														$("#invoiceform")
																.attr(
																		"action",
																		"/insight/invoice/"
																				+ "searchpartstoinvoicedetails");
														$("#invoiceform")
																.submit();
													}
												}
											});

							$("#searchcustomernumber")
									.keypress(
											function(event) {

												if (event.which == 13) {
													event.preventDefault();
													if ($(
															'#searchcustomernumber')
															.val() == '') {
														alert('partno cannot be blank');
														isFormValid = false;
													} else {
														isFormValid = true;
														$("#invoiceform")
																.attr(
																		"action",
																		"/insight/invoice/"
																				+ "searchcustomernumber");
														$("#invoiceform")
																.submit();
													}
												}
											});
							$('#searchcustomernumber')
									.autocomplete(
											{
												serviceUrl : '${pageContext.request.contextPath}/invoice/getAllCustomer',
												paramName : "custSearch",
												delimiter : ",",
												onSelect : function(suggestion) {
													cityID = suggestion.data;
													cityId = cityID;
													jQuery(
															"#searchcustomernumber")
															.val(cityID);
													isFormValid = true;
													$("#invoiceform")
															.attr(
																	"action",
																	"/insight/invoice/"
																			+ "searchcustomernumber");
													$("#invoiceform").submit();
													return false;
												},
												transformResult : function(
														response) {

													return {

														suggestions : $
																.map(
																		$
																				.parseJSON(response),
																		function(
																				item) {
																			return {
																				value : item.customerid
																						+ ' '
																						+ item.companyname,
																				data : item.customerid
																			};
																		})

													};

												}
											});

							if ($('#searchinvoicenumber').val() == '') {
								$('input[name=searchinvoicenumber]').val('0');
							}
							if ($('#searchcustomernumber').val() == '') {
								$('input[name=searchcustomernumber]').val(
										'1111111111');
							}

							$("#navlogout").click(function() {
								$('input[name=navmode]').val('logout');
								$("#navform").submit();
							});
							$("#navhome").click(function() {
								$('input[name=navmode]').val('home');
								$("#navform").submit();
							});
							$("#addlinetoinvoicedetailsBtn")
									.click(
											function() {

												var selectedValue = $(this)
														.val();
												$(
														'input[name=selectpartnumber]')
														.val(selectedValue);
												event.preventDefault();
												isFormValid = true;
												$("#invoiceform")
														.attr(
																"action",
																"/insight/invoice/"
																		+ "addlinetoinvoicedetails");
												$("#invoiceform").submit();
											});
							$("#createinvoicebtn").click(
									function() {
										$('#addlinetoinvoicedetailsBtn').prop(
												'disabled', true);
										var selectedValue = $(this).val();
										//alert(selectedValue);
										isFormValid = true;
										$("#invoiceform").attr(
												"action",
												"/insight/invoice/"
														+ "createinvoice");
										$("#invoiceform").submit();
									});
							$(document)
									.on(
											'click',
											'button[id^="rempart"]',
											function() {
												var selectedValue = $(this)
														.val();
												$(
														'input[name=selectpartnumber]')
														.val(selectedValue);
												event.preventDefault();
												isFormValid = true;
												$("#invoiceform")
														.attr(
																"action",
																"/insight/invoice/"
																		+ "removepartstoinvoice");
												$("#invoiceform").submit();
											});
							$("#addpartstoinvoicebtn").click(
									function() {
										var selectedValue = $(this).val();
										/* $('html, body').animate({
										 scrollTop: 0
										 }, 'slow'); */

										isFormValid = true;
										$("#invoiceform").attr(
												"action",
												"/insight/invoice/"
														+ "addpartstoinvoice");
										$("#invoiceform").submit();
									});
							$('#makeselected')
									.change(
											function() {

												isFormValid = false;
												var selectedValue = $(this)
														.val();
												if (selectedValue.length > 0) {
													isFormValid = true;
													$("#invoiceform")
															.attr("action",
																	"/insight/invoice/getallmodels");
													/* $("html, body").animate({
														scrollTop : 100
													}, "fast"); */
													$("#invoiceform").submit();
												}
											});
							$('#modelselected')
									.change(
											function() {

												isFormValid = false;
												var selectedValue = $(this)
														.val();
												if (selectedValue.length > 0) {
													isFormValid = true;
													$("#invoiceform")
															.attr("action",
																	"/insight/invoice/getallpartsfrommodel");
													/* $("html, body").animate({
														scrollTop : 100
													}, "fast"); */
													$("#invoiceform").submit();
												}
											});
							$('#yearselected').change(
									function() {

										isFormValid = false;
										var selectedValue = $(this).val();
										isFormValid = true;
										$("#invoiceform").attr("action",
												"/insight/invoice/resetyear");
										/* $("html, body").animate({
											scrollTop : 100
										}, "fast"); */
										$("#invoiceform").submit();
									});
							$('#partselected')
									.change(
											function() {

												isFormValid = false;
												var selectedValue = $(this)
														.find('option:selected')
														.attr('id');
												$(
														'input[name=selectpartnumber]')
														.val(selectedValue);
												isFormValid = true;
												$("#invoiceform")
														.attr("action",
																"/insight/invoice/getdetailsofselectedpart");
												/* $("html, body").animate({
													scrollTop : 100
												}, "fast"); */
												$("#invoiceform").submit();
											});
							$("#searchinvoicenumber")
									.keypress(
											function(event) {

												if (event.which == 13) {
													event.preventDefault();
													isFormValid = false;
													var selectedValue = $(this)
															.val();
													//alert(selectedValue);
													if (selectedValue.length > 0) {
														isFormValid = true;
														$("#invoiceform")
																.attr("action",
																		"/insight/invoice/searchinvoice");
														/* $("html, body")
																.animate(
																		{
																			scrollTop : 0
																		},
																		"fast"); */
														$("#invoiceform")
																.submit();
													}

												}
											});
							$('#billsameship').change(function() {

								if ($(this).attr('checked')) {
									$(this).val('TRUE');
									$('#profile-card-ship').toggle('show');
								} else {
									$(this).val('FALSE');
									$('#profile-card-ship').toggle('hide');
								}

							});
							$("#invoiceform").submit(function() {
								return isFormValid;
							});
						});
	</script>
</body>
</html>