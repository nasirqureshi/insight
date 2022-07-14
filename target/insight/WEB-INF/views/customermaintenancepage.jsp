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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<body bgcolor="#e0e0e0"
	style="word-wrap: break-word; white-space: normal; font-size: 0.7em;">
	<!-- START HEADER -->
	<header id="header" class="page-topbar">
		<!-- start header nav-->
		<div class="navbar-fixed">
			<form class="col s12" role="navigation" method="post" id="navform"
				name="navform" action="/insight/nav">
				<input style="font-weight: bold;" type="hidden" id="navmode"
					name="navmode" value="logout">
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
			id="customermaintenanceform" name="customermaintenanceform"
			action="/insight/parts/customermaintenancesearch">
			<div class="row">
				<h5 class="light col s12">Customer Maintenance</h5>
			</div>
			<div class="row">
				<div class="input-field col s3">
					<input style="font-weight: bold;" id="searchcustomerid"
						name="searchcustomerid" value="${searchcustomerid}" type="text"><label
						for="searchcustomerid"><strong>Search Customer #</strong></label>
				</div>
				<div class="col s4">
					<button
						class="btn-floating btn-large waves-effect waves-light  blue"
						type="submit" id="customermaintenancesearchBtn"
						name="customermaintenancesearchBtn"
						value="customermaintenancesearch">
						<i class="mdi-action-search right"></i>
					</button>
				</div>
				<div class="col s4">
					<input type="hidden" id="customermaintenancemode"
						name="customermaintenancemode" value="">
				</div>
				<div class="col s1">
					<c:choose>
						<c:when test="${customermaintenancemode eq 'create'}">
							<button
								class="btn-floating btn-large waves-effect waves-light  blue"
								type="submit" id="customermaintenancecreateBtn"
								name="customermaintenancecreateBtn"
								value="customermaintenancecreate">
								<i class="mdi-content-add-circle-outline right"></i>
							</button>
						</c:when>
						<c:otherwise>
							<button
								class="btn-floating btn-large waves-effect waves-light  blue"
								type="submit" id="customermaintenanceupdateBtn"
								name="customermaintenanceupdateBtn"
								value="customermaintenanceupdate">
								<i class="mdi-content-save right"></i>
							</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="row">
				<div class="col s6">
					<div class="card grey lighten-2">
						<div class="card-panel">
							<div class="row">
								<div class="input-field col s4">
									<input style="font-weight: bold;" id="customerid"
										name="customerid" value="${customer.customerid}" type="text"
										size="10"><label for="customerid"><strong>Customer
											ID</strong></label>
								</div>
								<div class="input-field col s8">
									<input style="font-weight: bold;" id="companyname"
										name="companyname" value="${customer.companyname}" type="text"><label
										for="companyname"><strong>Company Name</strong></label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s12">
									<input style="font-weight: bold;" id="address1" name="address1"
										value="${customer.address1}" type="text"><label
										for="address1"><strong>Address 1</strong></label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s12">
									<input style="font-weight: bold;" id="address2" name="address2"
										value="${customer.address2}" type="text"><label
										for="address2"><strong>Address 2</strong></label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s6">
									<input style="font-weight: bold;" id="town" name="town"
										value="${customer.town}" type="text"><label for="town"><strong>City</strong></label>
								</div>
								<div class="input-field col s6">
									<input style="font-weight: bold;" id="st" name="st"
										value="${customer.st}" type="text"><label for="st"><strong>State</strong></label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s6">
									<input style="font-weight: bold;" id="rte" name="rte"
										value="${customer.rte}" type="text"><label for="rte"><strong>Route</strong></label>
								</div>
								<div class="input-field col s6">
									<input style="font-weight: bold;" id="zip" name="zip"
										value="${customer.zip}" type="text"><label for="zip"><strong>Zip</strong></label>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col s6">
					<div class="card grey lighten-2">
						<div class="card-panel">
							<div class="row">
								<div class="input-field col s6">
									<input style="font-weight: bold;" id="contactname"
										name="contactname" value="${customer.contactname}" type="text"><label
										for="contactname"><strong>Customer Name</strong></label>
								</div>
								<div class="input-field col s6">
									<input style="font-weight: bold;" id="contacttitle"
										name="contacttitle" value="${customer.contacttitle}"
										type="text"><label for="contacttitle"><strong>Customer
											Title</strong></label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s3">
									<input style="font-weight: bold;" id="ph" name="ph"
										value="${customer.ph}" type="text"><label for="ph"><strong>Phone</strong></label>
								</div>
								<div class="input-field col s3">
									<input style="font-weight: bold;" id="fax" name="fax"
										value="${customer.fax}" type="text"><label for="fax"><strong>Fax</strong></label>
								</div>
								<div class="input-field col s6">
									<input style="font-weight: bold;" id="emailaddress"
										name="emailaddress" value="${customer.emailaddress}"
										type="text"><label for="emailaddress"><strong>Email
											Address</strong></label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s4">
									<input style="font-weight: bold;" id="creditbalance"
										name="creditbalance" value="${customer.creditbalance}"
										type="text"><label for="creditbalance"><strong>Balance</strong></label>
								</div>
								<div class="input-field col s4">
									<input style="font-weight: bold;" id="creditlimit"
										name="creditlimit" value="${customer.creditlimit}" type="text"><label
										for="creditlimit"><strong>Limit</strong></label>
								</div>
								<div class="input-field col s4">
									<input style="font-weight: bold;" id="customerlevel"
										name="customerlevel" value="${customer.customerlevel}"
										type="text"><label for="customerlevel"><strong>Level</strong></label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s6">
									<input style="font-weight: bold;" id="accountopenedby"
										name="accountopenedby" value="${customer.accountopenedby}"
										type="text"><label for="accountopenedby"><strong>Opened
											By</strong></label>
								</div>
								<div class="input-field col s6">
									<input style="font-weight: bold;" id="accountreferredby"
										name="accountreferredby" value="${customer.accountreferredby}"
										type="text"><label for="accountreferredby"><strong>Referred
											By</strong></label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s12">
									<input style="font-weight: bold;" id="notes" name="notes"
										value="${customer.notes}" type="text"><label
										for="notes"><strong>Notes</strong></label>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col s12">
					<div class="card grey lighten-2">
						<div class="card-panel">
							<div class="row">
								<div class="input-field col s2">
									<input id="openaccountdate" name="openaccountdate"
										value="<fmt:formatDate value='${customer.openaccountdate}' pattern="MM-dd-yyyy" />"
										type="text"> <label for="openaccountdate"><strong>Open
											Account Date(MM-dd-yyyy)</strong></label>
								</div>
								<div class="col s3">
									<label><strong>Terms</strong></label> <select id="terms"
										name="terms" class="browser-default">
										<option value="${customer.terms}" selected>${customer.terms}</option>
										<c:forEach var="terms" items="${paymentlistdd}">
											<option value="${terms.key}">${terms.key}</option>
										</c:forEach>
									</select>
								</div>
								<div class="input-field col s2">
									<input style="font-weight: bold;" id="taxid" name="taxid"
										value="${customer.taxid}" type="text" size="1"><label
										for="taxid"><strong>Tax ID (Y/N)</strong></label>
								</div>
								<div class="input-field col s2">
									<input style="font-weight: bold;" id="taxidnumber"
										name="taxidnumber" value="${customer.taxidnumber}" type="text"><label
										for="taxidnumber"><strong>Tax ID Number</strong></label>
								</div>
								<div class="input-field col s2">
									<input id="taxidexpireon" name="taxidexpireon"
										value="<fmt:formatDate value='${customer.taxidexpireon}' pattern="MM-dd-yyyy" />"
										type="text"> <label for="taxidexpireon"><strong>Tax
											Expire Date(MM-dd-yyyy)</strong></label>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="divider"></div>
			<div class="card    blue-grey lighten-4">
				<div class="card-content">
					<div class="row">
						<div class="col s2 input-field">
							<input type="date" value="${taxexpiredate}" name="taxexpiredate"
								id="taxexpiredate" /> <label for="taxexpiredate" class="active"><strong>Tax
									Expire Date</strong> </label>
						</div>
						<div class="col s1">
							<button
								class="btn-floating btn-large waves-effect waves-light blue"
								type="submit" id="taxexpiredateBtn" name="taxexpiredateBtn"
								value="taxexpiredate">
								<i class="mdi-action-search right"></i>
							</button>
						</div>
					</div>
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

							var cb = '<c:out value="${bg}"/>';
							$('nav').css('background-color', cb);

							$(".button-collapse").sideNav();
							$(".dropdown-button").dropdown();
							$('select').material_select();

							$("#navlogout").click(function() {
								$('input[name=navmode]').val('logout');
								$("#navform").submit();
							});

							$("#navhome").click(function() {
								$('input[name=navmode]').val('home');
								$("#navform").submit();
							});

							$("#customermaintenancecreateBtn")
									.click(
											function() {
												$("#customermaintenanceform")
														.attr("action",
																"/insight/customers/customermaintenancecreate");
												$("#customermaintenanceform")
														.submit();
											});

							$("#customermaintenanceupdateBtn")
									.click(
											function() {
												$("#customermaintenanceform")
														.attr("action",
																"/insight/customers/customermaintenanceupdate");
												$("#customermaintenanceform")
														.submit();
											});

							$("#customermaintenancesearchBtn")
									.click(
											function() {
												$("#customermaintenanceform")
														.attr("action",
																"/insight/customers/customermaintenancesearch");
												$("#customermaintenanceform")
														.submit();
											});

							$("#customermaintenanceform").submit(function() {
								var isFormValid = true;
								return isFormValid;
							});
						});
	</script>
</body>
</html>