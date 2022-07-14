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
		<div class="row">
			<form class="col s12" role="form" method="post"
				id="createneworderform" action="/insight/orders/createneworder">
				<div class="row">
					<h5 class="light col s12">Create Order</h5>
				</div>
				<div class="row"></div>
				<div class="card  blue-grey lighten-4  z-depth-3">
					<div class="card-content">
						<div class="row">
							<div class="col s3">
								<label><strong>Select Vendor</strong></label> <select
									id="vendorselected" name="vendorselected"
									class="browser-default">
									<option value="${vendorselected}" selected>${vendorselected}</option>
									<c:forEach var="vendor" items="${vendorlistdd}">
										<option value="${vendor.key}">${vendor.key}</option>
									</c:forEach>
								</select>
							</div>
							<div class="input-field col s2">
								<input id="createnewordernosearchtxt"
									name="createnewordernosearchtxt" value="${orderno}" type="text"
									required> <label for="createnewordernosearchtxt"><strong>Order
										#</strong> </label>
							</div>
							<div class="col s1">
								<button
									class="btn-floating btn-large waves-effect waves-light  blue"
									type="submit" id="createneworderbtn" name="createneworderbtn"
									value="createneworder">
									<i class="mdi-av-playlist-add right"></i>
								</button>
							</div>
							<c:if test="${not empty requestScope.vendororder}">
								<div class="col s6">
									<table class="striped">
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
								</div>
							</c:if>
						</div>
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

			//$('select').not('.disabled').material_select();

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

			$("#createnewordernosearchtxt").keypress(function(event) {
				if (event.which == 13) {
					event.preventDefault();
					$("#createneworderbtn").click();
				}
			});

			$("#createneworderbtn").click(function() {
				$("html, body").animate({
					scrollTop : 0
				}, "fast");
				$("#createneworderform").submit();
			});

			$("#createneworderform").submit(function() {
				var isFormValid = true;
				 if ($('#createneworderbtn').val() == 'createneworder') {
					if ($('#createnewordernosearchtxt').length) {
						if ($('#createnewordernosearchtxt').val() == '') {
							alert('search ORDER NO cannot be blank');
							isFormValid = false;
						}
					}
				} 

				return isFormValid;
			});
		});
	</script>
</body>
</html>
