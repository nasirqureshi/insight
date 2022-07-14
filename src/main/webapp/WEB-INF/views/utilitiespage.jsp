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
				action="/insight/parts/utilities">
				<div class="row">
					<input type="hidden" id="utilitiesmode" name="utilitiesmode"
						value="">
				</div>
				<div class="card blue-grey lighten-4 z-depth-3">
					<div class="card-content">
						<div class="row">
							<div class="col s2">
								<h5 class="breadcrumbs-title">Utilities</h5>
							</div>
							<div class="input-field col s2">
								<input id="stockcheckpartno" name="stockcheckpartno"
									value="${stockcheckpartno}" type="text"> <label
									for="stockcheckpartno"><strong>Part #</strong> </label>
							</div>

							<div class="col s1">
								<button
									class="btn-floating btn-large waves-effect waves-light blue"
									type="submit" id="stockcheck" name="stockcheck"
									value="stockcheck">
									<i class="mdi-action-search right"></i>
								</button>
							</div>
							<div class="col s4">
								<h5 class="breadcrumbs-title"></h5>
							</div>

							<div class="input-field col s2">
								<input id="locationpartno" name="locationpartno"
									value="${locationpartno}" type="text"> <label
									for="locationpartno"><strong>Location Update</strong> </label>
							</div>
							<c:if
								test="${ (user.actualrole == 'admin') || (user.actualrole == 'superadmin') || (user.actualrole == 'warehouse') }">
								<c:if test="${not empty stockchecks}">
									<div class="col s1">
										<button
											class="btn-floating btn-large waves-effect waves-light blue"
											type="submit" id="locationupdate" name="locationupdate"
											value="locationupdate">
											<i class="mdi-content-save  right"></i>
										</button>
									</div>
								</c:if>
							</c:if>

						</div>
					</div>
				</div>
				<div class="divider"></div>
				<c:if test="${utilitiespageselect == 'stockcheck'}">
					<div class="card-panel z-depth-3">
						<div class="card-content">
							<div class="row">
								<h5 class="breadcrumbs-title">Stock across branches</h5>
							</div>
							<div class="row">
								<div class="col s12">
									<c:if test="${not empty stockchecks}">
										<table class="striped">
											<thead>
												<tr>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">Branch</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">Part
														#</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">Inter
														#</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">Make</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">Model</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">Part
														Description</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">St</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">Or</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">RO</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">Sfty</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">Rtn</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">type</th>
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #4a4a4a;">Loc</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="stock" items="${stockchecks}">
													<tr>
														<td class="flow-text"
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><strong><c:out
																	value="${stock.branch}" /></strong></td>
														<c:choose>
															<c:when test="${stock.pricelock == 'Y'}">
																<td
																	style="word-wrap: break-word; white-space: normal; font-weight: bold; background-color: #94ffff;"><c:out
																		value="${stock.partno}" /></td>
															</c:when>
															<c:otherwise>
																<td
																	style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
																		value="${stock.partno}" /></td>
															</c:otherwise>
														</c:choose>
														<td
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
																value="${stock.interchangepartno}" /></td>
														<td
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
																value="${stock.manufacturername}" /></td>
														<td
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
																value="${stock.makemodelname}" /></td>
														<td
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
																value="${stock.partdescription}" /></td>
														<td class="flow-text"
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><strong><c:out
																	value="${stock.unitsinstock}" /></strong></td>
														<td
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
																value="${stock.unitsonorder}" /></td>
														<td
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
																value="${stock.reorderlevel}" /></td>
														<td
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
																value="${stock.safetyquantity}" /></td>
														<td
															style="word-wrap: break-word; white-space: normal; font-weight: bold; border: 0px; color: #F44336; background-color: transparent;"><c:out
																value="${stock.returncount}" /></td>
														<td
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
																value="${stock.ordertype}" /></td>
														<td
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
																value="${stock.location}" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty nsfpart}">
					<div class="card-panel z-depth-3">
						<div class="card-content">
							<div class="row">
								<h5 class="breadcrumbs-title">NSF</h5>
							</div>
							<div class="row">
								<div class="col s12">
									<table class="bordered">
										<thead>
											<tr>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">NSF
													Part #</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">Inter
													#</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">Make</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">Model</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">Part
													Description</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">St</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">Or</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">RO</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">Sfty</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">Rtrn</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">type</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.3em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #007FFF;">Loc</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><STRONG><c:out
															value="${nsfpart.partno}" /></STRONG></td>
												<td
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
														value="${nsfpart.interchangeno}" /></td>
												<td
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
														value="${nsfpart.manufacturername}" /></td>
												<td
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
														value="${nsfpart.makemodelname}" /></td>
												<td
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
														value="${nsfpart.partdescription}" /></td>
												<td class="flow-text"
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><strong><c:out
															value="${nsfpart.unitsinstock}" /></strong></td>
												<td
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
														value="${nsfpart.unitsonorder}" /></td>
												<td
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
														value="${nsfpart.reorderlevel}" /></td>
												<td
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
														value="${nsfpart.safetyquantity}" /></td>
												<td
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
														value="${nsfpart.returncount}" /></td>
												<td
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
														value="${nsfpart.ordertype}" /></td>
												<td
													style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
														value="${nsfpart.location}" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<div class="divider"></div>
				<c:if test="${not empty partsmonthlysales}">
					<div class="card-panel z-depth-3">
						<div class="card-content">
							<div class="row">
								<div class="col s12">
									<div class="row">
										<h5 class="breadcrumbs-title">Parts Sales History</h5>
									</div>
									<table class="responsive-table">
										<tbody>
											<tr>
												<c:forEach items="${partsmonthlysales}" var="monthlysales">
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 0.8em; border: 0.1px solid grey; color: red; border-collapse: collapse; background-color: #fffde7;"><strong><c:out
																value="${fn:substring(monthlysales.yearmonth,0,4)}-${monthlysales.monthval}->${monthlysales.salescount}" /></strong></td>
												</c:forEach>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<div class="divider"></div>
				<c:if test="${not empty partslinklist}">
					<div class="card-panel z-depth-3">
						<div class="card-content">
							<div class="row">
								<div class="col s12">
									<div class="row">
										<h5 class="breadcrumbs-title">Parts Link</H5>
									</div>
									<table class="striped">
										<thead>
											<tr>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #506987;">Partslink
													#</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #506987;">Make</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #506987;">Model</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #506987;">Yearfrom</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #506987;">Yearto</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #506987;">Desc</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #506987;">Desc2</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #506987;">OEM</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #506987;">Notes</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="pl" items="${partslinklist}">
												<tr>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${pl.plink}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${pl.make}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${pl.model}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${pl.y1}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${pl.y2}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${pl.pname}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${pl.mvariables}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${pl.oem}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${pl.notes}" /></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<div class="divider"></div>
				<c:if test="${not empty stockchecksdetails}">
					<div class="card-panel z-depth-3">
						<div class="card-content">
							<div class="row">
								<div class="col s12">
									<div class="row">
										<h5 class="breadcrumbs-title">Vendor Details</h5>
									</div>
									<table class="striped">
										<thead>
											<tr>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #42426F;">Vendor
													Name</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #42426F;">Vendor
													Part #</th>
												<th
													style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #42426F;">Notes</th>
												<c:if
													test="${(user.actualrole == 'purchasing') || (user.actualrole == 'admin') || (user.actualrole == 'branchmanager') || (user.actualrole == 'superadmin')}">
													<th
														style="word-wrap: break-word; white-space: normal; font-weight: bold; font-size: 1.1em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #42426F;">Vendor
														Price</th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="stockdetails" items="${stockchecksdetails}">
												<tr>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${stockdetails.companyname}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${stockdetails.vendorpartno}" /></td>
													<td
														style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
															value="${stockdetails.comments}" /></td>
													<c:if
														test="${(user.actualrole == 'purchasing') || (user.actualrole == 'admin') || (user.actualrole == 'branchmanager') || (user.actualrole == 'superadmin')}">
														<td
															style="word-wrap: break-word; white-space: normal; font-weight: bold;"><c:out
																value="${stockdetails.sellingrate}" /></td>
													</c:if>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
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

			$("#navlogout").click(function() {
				$('input[name=navmode]').val('logout');
				$("#navform").submit();
			});

			$("#navhome").click(function() {
				$('input[name=navmode]').val('home');
				$("#navform").submit();
			});

			$("#stockcheckpartno").keypress(function(event) {
				if (event.which == 13) {
					event.preventDefault();
					$("#stockcheck").click();
				}
			});

			$("#stockcheck").click(function() {

				$('input[name=utilitiesmode]').val('stockcheck');
				$("#utilform").attr("action", "/insight/parts/" + "utilities");
				$("#utilform").submit();

			});
			
			$("#locationupdate").click(function() {

				$('input[name=utilitiesmode]').val('locationupdate');
				$("#utilform").attr("action", "/insight/parts/" + "utilities");
				$("#utilform").submit();

			});

			$("#utilform").submit(function() {
				var isFormValid = true;
				if ($('#utilitiesmode').val() == 'stockcheck') {
					if ($('#stockcheckpartno').val() == '') {
						alert('partno cannot be blank');
						isFormValid = false;
					}

				}
				
				return isFormValid;
			});
		});
	</script>
</body>
</html>
