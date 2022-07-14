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
				id="inventorytransferform" modelAttribute="vendorOrder"
				action="/insight/orders/inventorypartstransfermap">
				<div class="card blue-grey lighten-4 z-depth-3">
					<div class="card-content">
						<div class="row">

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
							<div class="input-field col s1">
								<input id="stocklimit" name="stocklimit" type="text"
									class="validate" value="${stocklimit}"> <label
									for="stocklimit"><strong>Stock greater than</strong></label>
							</div>
							<div class="col s1">
								<label><strong>Year</strong></label><select id="selectYear"
									name="selectYear" class="browser-default"
									style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black;">
									<option value="${selectYear}" selected>${selectYear}</option>
									<c:forEach var="year" items="${yearslistdd}">
										<option value="${year}">${year}</option>
									</c:forEach>
								</select>
							</div>
							<div class="input-field col s1">
								<input id="factor" name="factor" type="text" class="validate"
									value="${factor}"> <label for="stocklimit"><strong>Percent
										Buffer</strong></label>
							</div>
							<div class="col s1">
								<label><strong>Select Branch</strong></label> <select
									id="branchselected" name="branchselected"
									class="browser-default" multiple>
									<option value="${branchselected}" selected>${branchselected}</option>
									<c:forEach var="b" items="${branchlistdd}">
										<option value="${b.key}">${b.key}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col s1">
								<label><strong>Make</strong></label><select id="makeselected"
									name="makeselected" class="browser-default"
									style="word-wrap: break-word; white-space: normal; font-size: 1.0em; font-weight: bold; color: black;">
									<option value="${makeselected}" selected>${makeselected}</option>
									<c:forEach var="make" items="${makelistdd}">
										<option value="${make.key}">${make.key}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col s3">
								<span class="card-title">Feedback</span>
								<h6 class="header">${feedback}</h6>
							</div>
							<div class="col s1">
								<h3 class="header">${listsize}</h3>
							</div>
							<div class="col s1">
								<button
									class="btn-floating btn-large waves-effect waves-light  blue"
									type="submit" id="searchpartsfortransferbtn"
									name="searchpartsfortransferbtn"
									value="searchpartsfortransferbtn">
									<i class="mdi-action-search right"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
				<c:set var="chcontains" value="false" />
				<c:set var="grcontains" value="false" />
				<c:set var="mpcontains" value="false" />
				<c:set var="nycontains" value="false" />

				<c:choose>
					<c:when test="${branchselected eq 'ALL'}">
						<c:forEach var="item" items="${branchlistdd}">
							<c:if test="${item.key eq 'CHS'}">
								<c:set var="chcontains" value="true" />
							</c:if>
							<c:if test="${item.key eq 'GRS'}">
								<c:set var="grcontains" value="true" />
							</c:if>
							<c:if test="${item.key eq 'MPS'}">
								<c:set var="mpcontains" value="true" />
							</c:if>

						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="l" items="${branchselected}">
							<c:if test="${l eq 'CHS'}">
								<c:set var="chcontains" value="true" />
							</c:if>
							<c:if test="${l eq 'GRS'}">
								<c:set var="grcontains" value="true" />
							</c:if>
							<c:if test="${l eq 'MPS'}">
								<c:set var="mpcontains" value="true" />
							</c:if>

						</c:forEach>
					</c:otherwise>
				</c:choose>
				<div class="row">
					<div class="col s12">
						<c:if test="${not empty partstransferdetailslist}">
							<table class="striped">
								<thead class="fixed">
									<tr>
										<th><strong>Partno</strong></th>
										<th><strong>MakeModel</strong></th>
										<th><strong>Partdescription</strong></th>
										<th><strong>YearFrom</strong></th>
										<th><strong>YearTo</strong></th>
										<th><strong>Stock</strong></th>
										<th><strong>OnOrder</strong></th>
										<th><strong>ReOrder</strong></th>
										<th><strong>Safety</strong></th>
										<th><strong>Loc</strong></th>
										<c:if test="${chcontains eq 'true'}">
											<th>CHSt</th>
											<th>CHSf</th>
											<th>CH Need</th>
										</c:if>
										<c:if test="${grcontains eq 'true'}">
											<th>GRSt</th>
											<th>GRSf</th>
											<th>GR Need</th>
										</c:if>
										<c:if test="${mpcontains eq 'true'}">
											<th>MPSt</th>
											<th>MPSf</th>
											<th>MP Need</th>
										</c:if>

									</tr>
								</thead>
								<tbody>
									<c:forEach items="${partstransferdetailslist}"
										var="transferparts">
										<tr>
											<td><strong><input type="text"
													value="${transferparts.partno}" name="partno" id="partno"
													style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
											<td style="word-wrap: break-word; white-space: normal;"><c:out
													value="${transferparts.manufacturername}${' '}${transferparts.makemodelname}" /></td>
											<td style="word-wrap: break-word; white-space: normal;"><c:out
													value="${transferparts.partdescription}" /></td>
											<td style="word-wrap: break-word; white-space: normal;"><c:out
													value="${transferparts.yearfrom}" /></td>
											<td style="word-wrap: break-word; white-space: normal;"><c:out
													value="${transferparts.yearto}" /></td>
											<td
												style="word-wrap: break-word; white-space: normal; background-color: #C5D3DA; color: #000000;"><p
													class="flow-text">
													<strong><c:out
															value="${transferparts.unitsinstock}" /></strong>
												</p></td>
											<td
												style="word-wrap: break-word; white-space: normal; background-color: #C5D3DA; color: #000000;"><p
													class="flow-text">
													<c:out value="${transferparts.unitsonorder}" />
												</p></td>
											<td
												style="word-wrap: break-word; white-space: normal; background-color: #C5D3DA; color: #000000;"><p
													class="flow-text">
													<c:out value="${transferparts.reorderlevel}" />
												</p></td>
											<td
												style="word-wrap: break-word; white-space: normal; background-color: #C5D3DA; color: #000000;"><p
													class="flow-text">
													<strong><c:out
															value="${transferparts.safetyquantity}" /></strong>
												</p></td>
											<td style="word-wrap: break-word; white-space: normal;"><c:out
													value="${transferparts.location}" /></td>

											<c:if test="${chcontains eq 'true'}">
												<td
													style="word-wrap: break-word; white-space: normal; background-color: #EEFFFF; color: #000000;"><p
														class="flow-text">
														<strong><c:out
																value="${transferparts.chunitsinstock}" /></strong>
													</p></td>
												<td
													style="word-wrap: break-word; white-space: normal; background-color: #EEEEFF; color: #010100;"><p
														class="flow-text">
														<strong><c:out
																value="${transferparts.chsafetyquantity}" /></strong>
													</p></td>
												<td><strong><input type="text"
														value="${transferparts.chneed}" name="chneed" id="chneed"
														style="word-wrap: break-word; white-space: normal; color: #F44336; background-color: #FFFFEE;" /></strong></td>
											</c:if>

											<c:if test="${grcontains eq 'true'}">
												<td
													style="word-wrap: break-word; white-space: normal; background-color: #EEFFFF; color: #000000;"><p
														class="flow-text">
														<strong><c:out
																value="${transferparts.grunitsinstock}" /></strong>
													</p></td>
												<td
													style="word-wrap: break-word; white-space: normal; background-color: #EEEEFF; color: #010100;"><p
														class="flow-text">
														<strong><c:out
																value="${transferparts.grsafetyquantity}" /></strong>
													</p></td>
												<td><strong><input type="text"
														value="${transferparts.grneed}" name="grneed" id="grneed"
														style="word-wrap: break-word; white-space: normal; color: #F44336; background-color: #FFFFEE;" /></strong></td>
											</c:if>

											<c:if test="${mpcontains eq 'true'}">
												<td
													style="word-wrap: break-word; white-space: normal; background-color: #EEFFFF; color: #000000;"><p
														class="flow-text">
														<strong><c:out
																value="${transferparts.mpunitsinstock}" /></strong>
													</p></td>
												<td
													style="word-wrap: break-word; white-space: normal; background-color: #EEEEFF; color: #010100;"><p
														class="flow-text">
														<strong><c:out
																value="${transferparts.mpsafetyquantity}" /></strong>
													</p></td>
												<td><strong><input type="text"
														value="${transferparts.mpneed}" name="mpneed" id="mpneed"
														style="word-wrap: break-word; white-space: normal; color: #F44336; background-color: #FFFFEE;" /></strong></td>
											</c:if>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
				</div>
				<div class="row">
					<div class="col s6">
						<button
							class="btn-floating btn-large waves-effect waves-light  blue"
							type="submit" id="createpartsfortransferbtn"
							name="createpartsfortransferbtn"
							value="createpartsfortransferbtn">
							<i class="large mdi-action-add-shopping-cart right"></i>
						</button>
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

							var cb = '<c:out value="${bg}"/>';
							$('nav').css('background-color', cb);
							$(".button-collapse").sideNav();
							$(".dropdown-button").dropdown();
							$('select').material_select();
							$('.tooltipped').tooltip({
								delay : 50
							});

							$('.collapsible').collapsible({
								accordion : true
							// A setting that changes the collapsible behavior to expandable instead of the default accordion style
							});

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

							$("#scanordernosearchtxt").keypress(
									function(event) {
										if (event.which == 13) {
											event.preventDefault();
											$("#scanorderbtn").click();
										}
									});

							$("#searchpartsfortransferbtn")
									.click(
											function() {

												$("#inventorytransferform")
														.attr("action",
																"/insight/orders/searchpartsfortransferbtn");
												$("html, body").animate({
													scrollTop : 0
												}, "fast");
												$("#inventorytransferform")
														.submit();
											});

							$("#createpartsfortransferbtn")
									.click(
											function() {

												$("#inventorytransferform")
														.attr("action",
																"/insight/orders/createpartsfortransferbtn");
												$("html, body").animate({
													scrollTop : 0
												}, "fast");
												$("#inventorytransferform")
														.submit();
											});

							$("#scanorderform").submit(function() {
								var isFormValid = true;
								/* if ($('#scanorderbtn').val() == 'scanordersearch') {
									if ($('#scanordernosearchtxt').length) {
										if ($('#scanordernosearchtxt').val() == '') {
											alert('search ORDER NO cannot be blank');
											isFormValid = false;
										}
									}
								} */

								return isFormValid;
							});
						});
	</script>
	<div class="modal">
		<!-- Place at bottom of page -->
	</div>
</body>
</html>
