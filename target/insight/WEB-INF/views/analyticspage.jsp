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
			<form class="col s12" role="form" method="post" id="analyticsform"
				name="analyticsform" action="/insight/admin/getanalysis">
				<div class="row">
					<h5 class="light col s12">Analytics</h5>
				</div>

				<div class="card-content">
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
						<div class="col s2">
							<input type="checkbox" class="filled-in" id="extended"
								name="extended" value="extended"> <label for="extended"><strong>extended
									search(includes archives)</strong> </label>
						</div>
						<div class="input-field col s4">
							<textarea id="feedback" name="feedback"
								class="materialize-textarea">${feedback}</textarea>
							<label for="feedback"><strong>Feedback</strong></label>
						</div>
					</div>
				</div>

				<ul class="collapsible" data-collapsible="accordion">
					<li>
						<div class="collapsible-header">
							<i class="mdi-editor-attach-money"></i>Cost Of Goods
						</div>
						<div class="collapsible-body">
							<div class="row"></div>
							<div class="row">
								<c:if test="${not empty requestScope.costofgoodstotal}">
									<div class="col s10">
										<table class="striped">
											<thead>
												<tr>
													<th><strong>Total Invoices</strong></th>
													<th><strong>Our Price</strong></th>
													<th><strong>Sold Price</strong></th>
													<th><strong>Gross</strong></th>
													<th><strong>Discount</strong></th>
													<th><strong>Margin</strong></th>
													<th><strong>Percent</strong></th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td><strong>${costofgoodstotal.totalitems}</strong></td>
													<td><strong>${costofgoodstotal.totalourprice}</strong></td>
													<td><strong>${costofgoodstotal.totalsoldprice}</strong></td>
													<td><strong>${costofgoodstotal.totalgross}</strong></td>
													<td><strong>${costofgoodstotal.totaldiscount}</strong></td>
													<td><strong>${costofgoodstotal.totalmargin}</strong></td>
													<td><strong>${costofgoodstotal.totalpercent}</strong></td>
												</tr>
											</tbody>
										</table>
									</div>
								</c:if>
								<div class="col s2">
									<span><button
											class="btn-floating btn-large waves-effect waves-light blue"
											type="submit" id="analyticspageselect"
											name="analyticspageselect" value="costofgoodstotal">
											<i class="mdi-action-assignment-turned-in right"></i>
										</button></span>
								</div>
							</div>
							<div class="row"></div>
						</div>
					</li>
					<li>
						<div class="collapsible-header">
							<i class="mdi-editor-insert-chart"></i>Sales Analysis
						</div>
						<div class="collapsible-body">
							<div class="row"></div>
							<div class="row">
								<div class="col s2">
									<label><strong>Select Vendor</strong></label> <select
										id="duration" name="duration" class="browser-default">
										<option value="${duration}" selected>${duration}</option>
										<option>Yearly</option>
										<option>Monthly</option>
										<option>Weekly</option>
									</select>
								</div>
								<div class="col s3">
									<span><button
											class="btn-floating btn-large waves-effect waves-light blue"
											type="submit" id="analyticspageselect"
											name="analyticspageselect" value="salescustomer">
											<i class="mdi-social-people right"></i>
										</button></span>
									<p>By Customer</p>
								</div>
								<div class="col s3">
									<span><button
											class="btn-floating btn-large waves-effect waves-light blue"
											type="submit" id="analyticspageselect"
											name="analyticspageselect" value="salescategory">
											<i class="mdi-image-style right"></i>
										</button></span>
									<p>By Category</p>
								</div>
							</div>
							<div class="row">
								<c:if test="${analyticspageselect == 'salescategory' }">
									<div class="row">
										<div class="col s12">
											<c:if test="${duration == 'Yearly' }">
												<c:if test="${not empty yearsubcategorylist}">
													<table class="striped">
														<thead>
															<tr>
																<th><strong>Year</strong></th>
																<th><strong>Subcategory</strong></th>
																<th><strong>Count</strong></th>
																<th><strong>OurPrice</strong></th>
																<th><strong>SalesPrice</strong></th>
																<th><strong>Margin</strong></th>
																<th><strong>Percent</strong></th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${yearsubcategorylist}" var="yearsales">
																<tr>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.yr}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.subcategory}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.cnt}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><c:out
																			value="${yearsales.ourprice}" /></td>
																	<td style="word-wrap: break-word; white-space: normal;"><c:out
																			value="${yearsales.salesprice}" /></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.margin}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.prcnt}" /></strong></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</c:if>
											</c:if>
											<c:if test="${duration == 'Monthly' }">
												<c:if test="${not empty monthsubcategorylist}">
													<table class="striped">
														<thead>
															<tr>
																<th><strong>Year</strong></th>
																<th><strong>Month</strong></th>
																<th><strong>Subcategory</strong></th>
																<th><strong>Count</strong></th>
																<th><strong>OurPrice</strong></th>
																<th><strong>SalesPrice</strong></th>
																<th><strong>Margin</strong></th>
																<th><strong>Percent</strong></th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${monthsubcategorylist}"
																var="yearsales">
																<tr>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.yr}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.mnth}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.subcategory}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.cnt}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><c:out
																			value="${yearsales.ourprice}" /></td>
																	<td style="word-wrap: break-word; white-space: normal;"><c:out
																			value="${yearsales.salesprice}" /></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.margin}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.prcnt}" /></strong></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</c:if>
											</c:if>
											<c:if test="${duration == 'Weekly' }">
												<c:if test="${not empty weeksubcategorylist}">
													<table class="striped">
														<thead>
															<tr>
																<th><strong>Year</strong></th>
																<th><strong>Month</strong></th>
																<th><strong>Week</strong></th>
																<th><strong>Subcategory</strong></th>
																<th><strong>Count</strong></th>
																<th><strong>OurPrice</strong></th>
																<th><strong>SalesPrice</strong></th>
																<th><strong>Margin</strong></th>
																<th><strong>Percent</strong></th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${weeksubcategorylist}" var="yearsales">
																<tr>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.yr}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.mnth}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.wk}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.subcategory}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.cnt}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><c:out
																			value="${yearsales.ourprice}" /></td>
																	<td style="word-wrap: break-word; white-space: normal;"><c:out
																			value="${yearsales.salesprice}" /></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.margin}" /></strong></td>
																	<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																				value="${yearsales.prcnt}" /></strong></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</c:if>
											</c:if>
										</div>
									</div>
								</c:if>
								<c:if test="${analyticspageselect == 'salescustomer' }">
									<div class="row">
										<div class="col s12">
											<div class="well bs-component">
												<c:if test="${duration == 'Yearly' }">
													<c:if test="${not empty yearcustomerlist}">
														<table class="striped">
															<thead>
																<tr>
																	<th><strong>Year</strong></th>
																	<th><strong>Customer</strong></th>
																	<th><strong>Count</strong></th>
																	<th><strong>OurPrice</strong></th>
																	<th><strong>SalesPrice</strong></th>
																	<th><strong>Margin</strong></th>
																	<th><strong>Percent</strong></th>
																</tr>
															</thead>
															<tbody>
																<c:forEach items="${yearcustomerlist}" var="yearsales">
																	<tr>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.yr}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.companyname}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.cnt}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><c:out
																				value="${yearsales.ourprice}" /></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><c:out
																				value="${yearsales.salesprice}" /></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.margin}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.prcnt}" /></strong></td>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</c:if>
												</c:if>
												<c:if test="${duration == 'Monthly' }">
													<c:if test="${not empty monthcustomerlist}">
														<table class="striped">
															<thead>
																<tr>
																	<th><strong>Year</strong></th>
																	<th><strong>Month</strong></th>
																	<th><strong>Customer</strong></th>
																	<th><strong>Count</strong></th>
																	<th><strong>OurPrice</strong></th>
																	<th><strong>SalesPrice</strong></th>
																	<th><strong>Margin</strong></th>
																	<th><strong>Percent</strong></th>
																</tr>
															</thead>
															<tbody>
																<c:forEach items="${monthcustomerlist}" var="yearsales">
																	<tr>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.yr}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.mnth}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.companyname}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.cnt}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><c:out
																				value="${yearsales.ourprice}" /></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><c:out
																				value="${yearsales.salesprice}" /></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.margin}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.prcnt}" /></strong></td>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</c:if>
												</c:if>
												<c:if test="${duration == 'Weekly' }">
													<c:if test="${not empty weekcustomerlist}">
														<table class="striped">
															<thead>
																<tr>
																	<th><strong>Year</strong></th>
																	<th><strong>Month</strong></th>
																	<th><strong>Week</strong></th>
																	<th><strong>Customer</strong></th>
																	<th><strong>Count</strong></th>
																	<th><strong>OurPrice</strong></th>
																	<th><strong>SalesPrice</strong></th>
																	<th><strong>Margin</strong></th>
																	<th><strong>Percent</strong></th>
																</tr>
															</thead>
															<tbody>
																<c:forEach items="${weekcustomerlist}" var="yearsales">
																	<tr>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.yr}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.mnth}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.wk}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.companyname}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.cnt}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><c:out
																				value="${yearsales.ourprice}" /></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><c:out
																				value="${yearsales.salesprice}" /></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.margin}" /></strong></td>
																		<td
																			style="word-wrap: break-word; white-space: normal;"><strong><c:out
																					value="${yearsales.prcnt}" /></strong></td>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</c:if>
												</c:if>
											</div>
										</div>
									</div>
								</c:if>
							</div>
							<div class="row"></div>
						</div>
					</li>
					<li>
						<div class="collapsible-header">
							<i class="mdi-social-pages"></i>Price Analysis
						</div>
						<div class="collapsible-body">
							<div class="row"></div>
							<div class="row">
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
											type="submit" id="analyticspageselect"
											name="analyticspageselect" value="subcategorydetails">
											<i class="mdi-toggle-check-box right"></i>
										</button></span>
									<p>Local Scan</p>
								</div>
								<div class="col s2">
									<span><button
											class="btn-floating btn-large waves-effect waves-light blue"
											type="submit" id="analyticspageselect"
											name="analyticspageselect" value="subcategoryallbranch">
											<i class="mdi-toggle-check-box right"></i>
										</button></span>
									<p>All Branch Scan</p>
								</div>
								<div class="col s2">
									<c:if test="${not empty subcatallbranchlist}">
										<span><button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" id="analyticspageselect"
												name="analyticspageselect" value="saveallbranch">
												<i class="mdi-content-save"></i>
											</button></span>
										<p>Save To Excel</p>
									</c:if>
								</div>
							</div>
							<div class="row">
								<div class="col s12">
									<c:if test="${analyticspageselect == 'subcategoryallbranch' }">
										<c:if test="${not empty subcatallbranchlist}">
											<table class="striped">
												<thead>
													<tr>
														<th><strong>Partno</strong></th>
														<th><strong>Make</strong></th>
														<th><strong>Model</strong></th>
														<th><strong>Partdescription</strong></th>
														<th><strong>Chicago</strong></th>
														<th><strong>GrandRapids</strong></th>
														<th><strong>Melrose</strong></th>
														<th><strong>Total</strong></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${subcatallbranchlist}" var="allbranch">
														<tr>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${allbranch.partno}" /></strong></td>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${allbranch.manufacturername}" /></strong></td>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${allbranch.makemodelname}" /></strong></td>
															<td style="word-wrap: break-word; white-space: normal;"><c:out
																	value="${allbranch.partdescription}" /></td>
															<td style="word-wrap: break-word; white-space: normal;"><c:out
																	value="${allbranch.chicagoquantity}" /></td>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${allbranch.grandrapidsquantity}" /></strong></td>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${allbranch.melrosequantity}" /></strong></td>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${allbranch.total}" /></strong></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
									</c:if>
									<c:if test="${analyticspageselect == 'subcategorydetails' }">
										<c:if test="${not empty subcatdetailslist}">
											<table class="striped">
												<thead>
													<tr>
														<th><strong>Partno</strong></th>
														<th><strong>OrderType</strong></th>
														<th><strong>Count</strong></th>
														<th><strong>ActualPrice</strong></th>
														<th><strong>CostPrice</strong></th>
														<th><strong>PartMargin</strong></th>
														<th><strong>PartPercent</strong></th>
														<th><strong>OurPrice</strong></th>
														<th><strong>SalesPrice</strong></th>
														<th><strong>Margin</strong></th>
														<th><strong>Percent</strong></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${subcatdetailslist}" var="yearsales">
														<tr>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${yearsales.partno}" /></strong></td>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${yearsales.ordertype}" /></strong></td>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${yearsales.cnt}" /></strong></td>
															<td style="word-wrap: break-word; white-space: normal;"><c:out
																	value="${yearsales.actualprice}" /></td>
															<td style="word-wrap: break-word; white-space: normal;"><c:out
																	value="${yearsales.costprice}" /></td>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${yearsales.partmargin}" /></strong></td>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${yearsales.partprcnt}" /></strong></td>
															<td style="word-wrap: break-word; white-space: normal;"><c:out
																	value="${yearsales.ourprice}" /></td>
															<td style="word-wrap: break-word; white-space: normal;"><c:out
																	value="${yearsales.salesprice}" /></td>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${yearsales.margin}" /></strong></td>
															<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																		value="${yearsales.prcnt}" /></strong></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
									</c:if>
								</div>
							</div>
						</div>
					</li>
				</ul>
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
			
			$('.collapsible').collapsible({
				accordion : true
			// A setting that changes the collapsible behavior to expandable instead of the default accordion style
			});

			if (datefield.type != "date") { //if browser doesn't support input type="date", initialize date picker widget:
				jQuery(function($) { //on document.ready
				//	$('#analyticsfromdate').datepicker();
				//	$('#analyticstodate').datepicker();
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
		});
	</script>
</body>
</html>
