<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page language="java" trimDirectiveWhitespaces="true"%>
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
	<!-- Header -->
	<div class="row">
		<div class="navbar-fixed">
			<form class="col s12" role="navigation" method="post" id="navform"
				name="navform" action="/insight/nav">
				<input type="hidden" id="navmode" name="navmode" value="logout">
				<nav>
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
						</ul>
					</div>
				</nav>
			</form>
		</div>
		<!-- /Header -->
		<div class="row">
			<div class="container  blue-grey lighten-5">
				<form method="post" class="col s12" id="addvendorpartform"
					action="/insight/orders/addvendorpart">
					<div class="row">
						<h5 class="light col s12">Vendor Part</h5>
					</div>
					<div class="row"></div>
					<div class="card  blue-grey lighten-4  z-depth-3">
						<div class="card-content">
							<div class="row">
								<div class="col s12">
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
										<input id="addvendorpartsearchtxt"
											name="addvendorpartsearchtxt" value="${partno}" type="text"><label
											for="addvendorpartsearchtxt"><strong>Part #</strong></label>
									</div>
									<div class="col s2">
										<button
											class="btn-floating btn-large waves-effect waves-light  blue"
											type="submit" id="addvendorpartbtn" name="addvendorpartbtn"
											value="partnosearch">
											<i class="mdi-action-search right"></i>
										</button>
									</div>
									<div class="input-field col s2">
										<input id="vendornosearchtxt" name="vendornosearchtxt"
											value="${vendorpartno}" type="text"><label
											for="vendornosearchtxt"><strong>VendorPart #</strong></label>
									</div>
									<div class="col s2">
										<button
											class="btn-floating btn-large waves-effect waves-light  blue"
											type="submit" id="addvendorpartbtn" name="addvendorpartbtn"
											value="vendorpartsearch">
											<i class="mdi-action-search right"></i>
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col s12">
							<div class="input-field col s2">
								<input id="partno" name="partno" value="${vendoritems.partno}"
									type="text"><label for="partno"><strong>Part
										#</strong></label>
							</div>
							<div class="input-field col s2">
								<input name="vendorpartno" id="vendorpartno"
									value="${vendoritems.vendorpartno}" type="text"><label
									for="vendorpartno"><strong>VendorPart #</strong></label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s10">
							<input id="itemdesc1" name="itemdesc1"
								value="${vendoritems.itemdesc1}" type="text"><label
								for="itemdesc1"><strong>itemdesc1</strong></label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s10">
							<input id="itemdesc2" name="itemdesc2"
								value="${vendoritems.itemdesc2}" type="text"><label
								for="itemdesc2"><strong>itemdesc2</strong></label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s3">
							<input id="plno" name="plno" value="${vendoritems.plno}"
								type="text"><label for="plno"><strong>plno</strong></label>
						</div>
						<div class="input-field col s3">
							<input id="oemno" name="oemno" value="${vendoritems.oemno}"
								type="text"><label for="oemno"><strong>oemno</strong></label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s3">
							<input id="sellingrate" name="sellingrate"
								value="${vendoritems.sellingrate}" type="text"><label
								for="sellingrate"><strong>sellingrate</strong></label>
						</div>
						<div class="input-field col s3">
							<input id="noofpieces" name="noofpieces"
								value="${vendoritems.noofpieces}" type="text"><label
								for="noofpieces"><strong>noofpieces</strong></label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s3">
							<input id="itemsize" name="itemsize"
								value="${vendoritems.itemsize}" type="text"><label
								for="itemsize"><strong>itemsize</strong></label>
						</div>
						<div class="input-field col s3">
							<input id="itemsizeunits" name="itemsizeunits"
								value="${vendoritems.itemsizeunits}" type="text"><label
								for="itemsizeunits"><strong>itemsizeunits</strong></label>
						</div>
					</div>
					<c:if
						test="${(user.actualrole == 'purchasing') || (user.actualrole == 'admin') || (user.actualrole == 'branchmanager')  || (user.actualrole == 'superadmin') }">
						<div class="row">
							<div class="col s2">
								<button class="btn waves-effect waves-light" type="submit"
									id="addvendorpartbtn" name="addvendorpartbtn"
									value="savevendorpartno">
									&nbsp;Save Or Update <i class="mdi-content-add-circle right"></i>
								</button>
							</div>
						</div>
					</c:if>
				</form>
			</div>
		</div>
	</div>
	<!-- /container -->
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
		$(document).ready(function() {
			
			var cb = '<c:out value="${bg}"/>';
			$('nav').css('background-color', cb);
			
			$(".button-collapse").sideNav();
			$(".dropdown-button").dropdown();
			$('select').material_select();

			$('html, body').animate({
				scrollTop : $(document).height()
			}, 'fast');

			$("#navlogout").click(function() {
				$('input[name=navmode]').val('logout');
				$("#navform").submit();
			});

			$("#navhome").click(function() {
				$('input[name=navmode]').val('home');
				$("#navform").submit();
			});
			
			$("#vendorpartno").keypress(function(event) {
				if (event.which == 13) {
					event.preventDefault();
					$('input[name=addtocontainermode]').val('vendorpartsearch');
					$("#addvendorpartbtn").click();
				}
			});

			

			$("#addvendorpartsearchtxt").keypress(function(event) {
				if (event.which == 13) {
					event.preventDefault();
					$("#addvendorpartbtn").click();
				}
			});

			$("#addvendorpartbtn").click(function() {
				$("html, body").animate({
					scrollTop : 0
				}, "fast");
				$("#addvendorpartform").submit();
			});

			$("#addvendorpartform").submit(function() {
				var isFormValid = true;
				/* if ($('#addvendorpartbtn').val() == 'existingordersearch') {
					if ($('#addvendorpartsearchtxt').length) {
						if ($('#addvendorpartsearchtxt').val() == '') {
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
