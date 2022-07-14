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
	style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 0.7em;">
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
			<form class="col s12" role="form" method="post" id="utilform"
				action="/insight/customers/customerupdate">
				<div class="row">
					<input type="hidden" id="customerupdatemode"
						name="customerupdatemode" value="">
				</div>
				<div class="card blue-grey lighten-4 z-depth-3">
					<div class="card-content">
						<div class="row">
							<div class="col s2">
								<h5 class="breadcrumbs-title">Customer Update</h5>
							</div>
							<div class="input-field col s2">
								<input id="customersearchno" name="customersearchno"
									value="${customersearchno}" type="text"> <label
									for="customersearchno"><strong>CUSTOMER #</strong> </label>
							</div>
							<div class="col s1">
								<button
									class="btn-floating btn-large waves-effect waves-light blue"
									type="submit" id="customersearchbtn" name="customersearchbtn"
									value="customersearchbtn">
									<i class="mdi-action-search right"></i>
								</button>
							</div>
							<div class="col s4">
								<h5 class="breadcrumbs-title"></h5>
							</div>
							<c:if test="${customerupdatemode == 'update'}">
								<div class="col s1">
									<button
										class="btn-floating btn-large waves-effect waves-light blue"
										type="submit" id="customerupdatebtn" name="customerupdatebtn"
										value="customerupdatebtn">
										<i class="mdi-content-save  right"></i>
									</button>
								</div>
							</c:if>
						</div>
					</div>
				</div>
				<div class="divider"></div>


				<div class="card-panel z-depth-3">
					<div class="card-content">
						<div class="row">
							<h5 class="breadcrumbs-title">Customer Details</h5>
						</div>
						<div class="row">
							<div class="col s12">
								<div class="input-field col s4">
									<input style="font-weight: bold; background-color: #d3d3d3;"
										id="contactname" name="contactname"
										value="${updatecustomer.companyname}" type="text" readonly><label
										for="contactname"><strong>Company Name</strong></label>
								</div>
								<div class="input-field col s4">
									<input style="font-weight: bold; background-color: #d3d3d3;"
										id="contactname" name="contactname"
										value="${updatecustomer.contactname}" type="text" readonly><label
										for="contactname"><strong>Customer Name</strong></label>
								</div>
								<div class="input-field col s4">
									<input style="font-weight: bold; background-color: #d3d3d3;"
										id="contacttitle" name="contacttitle"
										value="${updatecustomer.contacttitle}" type="text" readonly><label
										for="contacttitle"><strong>Customer Title</strong></label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s2">
									<input style="font-weight: bold;" id="ph" name="ph"
										value="${updatecustomer.ph}" type="text"><label
										for="ph"><strong>Phone</strong></label>
								</div>
								<div class="input-field col s2">
									<input style="font-weight: bold;" id="cell" name="cell"
										value="${updatecustomer.cell}" type="text"><label
										for="cell"><strong>cell</strong></label>
								</div>
								<div class="input-field col s2">
									<input style="font-weight: bold;" id="fax" name="fax"
										value="${updatecustomer.fax}" type="text"><label
										for="fax"><strong>Fax</strong></label>
								</div>
								<div class="input-field col s6">
									<input style="font-weight: bold;" id="emailaddress"
										name="emailaddress" value="${updatecustomer.emailaddress}"
										type="text"><label for="emailaddress"><strong>Email
											Address</strong></label>
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
		$(document)
				.ready(
						function() {
							$('input[name=customerupdatemode]')
							.val('search');
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

							$("#customersearchno").keypress(function(event) {
								if (event.which == 13) {
									event.preventDefault();
									$("#customersearchbtn").click();
								}
							});
							
							$("#customerupdatebtn").click(
									function() {
										
										$('input[name=customerupdatemode]')
												.val('update');
										$("#utilform").attr(
												"action",
												"/insight/customers/"
														+ "customerupdateupdate");
										$("#utilform").submit();

									});

							$("#customersearchbtn").click(
									function() {
										
										$('input[name=customerupdatemode]')
												.val('search');
										$("#utilform").attr(
												"action",
												"/insight/customers/"
														+ "customerupdatesearch");
										$("#utilform").submit();

									});

							$("#utilform")
									.submit(
											function() {
												var isFormValid = true;
												if ($('#customerupdatemode')
														.val() == 'search') {
													if ($('#customersearchno').val() == '') {
														alert('partno cannot be blank');
														$('input[name=customerupdatemode]')
														.val('search');
														isFormValid = false;
													}

												}

												return isFormValid;
											});
						});
	</script>
</body>
</html>
