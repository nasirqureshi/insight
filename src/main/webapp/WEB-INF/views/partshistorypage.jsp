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
<title>Parts History</title>
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
			<form class="col s12" role="form" method="post" id="utilform"
				action="/insight/parts/partshistory">
				<div class="row">
					<h5 class="light col s12">Part History</h5>
				</div>
				<div class="row">
					<input type="hidden" id="partshistorymode" name="partshistorymode"
						value="">
				</div>
				<div class="card    blue-grey lighten-4">
					<div class="card-content">
						<div class="row">
							<div class="input-field col s2">
								<input id="partshistorypartno" name="partshistorypartno"
									value="" type="text" required> <label
									for="partshistorypartno"><strong>Part #</strong></label>
							</div>
							<div class="col s1">
								<button
									class="btn-floating btn-large waves-effect waves-light blue"
									type="submit" id="partshistory" name="partshistory"
									value="partshistory">
									<i class="mdi-action-search right"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="divider"></div>
				<div class="row">
					<c:if test="${not empty requestScope.partslist}">
						<div class="col s12">
							<div class="card	white">
								<div class="card-content">
									<table class="responsive-table">
										<thead>
											<tr>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>Partno</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>InterChange</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>Make</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>Model</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>Description</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>Yearfrom</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>YearTo</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>Stock</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>Order</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>ReOrder</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>Partlink</strong></th>
												<c:if
													test="${(user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
													<th
														style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>BuyingPrice</strong></th>
												</c:if>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>SellingPrice</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #35586C;"><strong>Location</strong></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${partslist}" var="part">
												<tr>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${part.partno}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${part.interchangeno}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${part.make}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${part.model}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${part.partdescription}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${part.yearfrom}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${part.yearto}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${part.unitsinstock}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${part.unitsonorder}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${part.reorderlevel}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${part.keystonenumber}" /></strong></td>
													<c:if
														test="${(user.actualrole == 'admin') || (user.actualrole == 'superadmin') }">
														<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																	value="${part.actualprice}" /></strong></td>
													</c:if>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${part.costprice}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${part.location}" /></strong></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="divider"></div>
					</c:if>
				</div>
				<div class="row">
					<c:if test="${not empty requestScope.futureorderslist}">
						<div class="col s12">
							<div class="card	white">
								<div class="card-content">
									<table class="responsive-table">
										<thead>
											<tr>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #36648B;"><strong>Partno</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #36648B;"><strong>Orderno</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #36648B;"><strong>Est.
														Arrival</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #36648B;"><strong>Quantity</strong></th>
												<c:if
													test="${(user.actualrole == 'admin') || (user.actualrole == 'warehouse') || (user.actualrole == 'superadmin') }">
													<th
														style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #36648B;"><strong>CompanyName</strong></th>
													<th
														style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #36648B;"><strong>Price</strong></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${futureorderslist}" var="futureorder">
												<tr>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${futureorder.partno}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${futureorder.orderno}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${futureorder.estimatedarrivaldate}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${futureorder.quantity}" /></strong></td>
													<c:if
														test="${(user.actualrole == 'admin')  || (user.actualrole == 'warehouse') || (user.actualrole == 'superadmin')}">
														<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																	value="${futureorder.companyname}" /></strong></td>
														<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																	value="${futureorder.price}" /></strong></td>
													</c:if>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="divider"></div>
					</c:if>
				</div>
				<div class="row">
					<c:if test="${not empty requestScope.processedorderslist}">
						<div class="col s12">
							<div class="card	white">
								<div class="card-content">
									<table class="responsive-table">
										<thead>
											<tr>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #104E8B;"><strong>Partno</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #104E8B;"><strong>Orderno</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #104E8B;"><strong>Inventory
														Done Dt.</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #104E8B;"><strong>ItemDesc1</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #104E8B;"><strong>ItemDesc2</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #104E8B;"><strong>Quantity</strong></th>
												<c:if
													test="${(user.actualrole == 'admin')  || (user.actualrole == 'warehouse') || (user.actualrole == 'superadmin')}">
													<th
														style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #104E8B;"><strong>CompanyName</strong></th>
													<th
														style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #104E8B;"><strong>Price</strong></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${processedorderslist}"
												var="processedorder">
												<tr>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${processedorder.partno}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${processedorder.orderno}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${processedorder.inventorydonedate}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${processedorder.itemdesc1}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${processedorder.itemdesc2}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${processedorder.quantity}" /></strong></td>
													<c:if
														test="${(user.actualrole == 'admin')  || (user.actualrole == 'warehouse') || (user.actualrole == 'superadmin')}">
														<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																	value="${processedorder.companyname}" /></strong></td>
														<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																	value="${processedorder.price}" /></strong></td>
													</c:if>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="divider"></div>
					</c:if>
				</div>
				<div class="row">
					<div class="divider"></div>
					<c:if test="${not empty requestScope.localorderslist}">
						<div class="col s8">
							<div class="card	white">
								<div class="card-content">
									<table class="responsive-table">
										<thead>
											<tr>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #6996AD;"><strong>Partno</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #6996AD;"><strong>Invoice</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #6996AD;"><strong>Date</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #6996AD;"><strong>Company</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #6996AD;"><strong>Quantity</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #6996AD;"><strong>Price</strong></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${localorderslist}" var="localorder">
												<tr>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${localorder.partno}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${localorder.invoiceno}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${localorder.dateentered}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${localorder.companyname}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${localorder.quantity}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${localorder.price}" /></strong></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty requestScope.invoicedetailslist}">
						<div class="col s4">
							<div class="card	white">
								<div class="card-content">
									<table class="responsive-table">
										<thead>
											<tr>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #6996AD;"><strong>Partno</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #6996AD;"><strong>Invoice</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #6996AD;"><strong>Quantity</strong></th>
												<th
													style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #6996AD;"><strong>Soldprice</strong></th>
												<c:if
													test="${(user.actualrole == 'admin')  || (user.actualrole == 'warehouse') || (user.actualrole == 'superadmin')}">
													<th
														style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #6996AD;"><strong>Price</strong></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${invoicedetailslist}" var="invoicedetails">
												<tr>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${invoicedetails.partnumber}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${invoicedetails.invoicenumber}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${invoicedetails.quantity}" /></strong></td>
													<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																value="${invoicedetails.soldprice}" /></strong></td>
													<c:if
														test="${(user.actualrole == 'admin')  || (user.actualrole == 'warehouse') || (user.actualrole == 'superadmin')}">
														<td style="word-wrap: break-word; white-space: normal;"><strong><c:out
																	value="${invoicedetails.actualprice}" /></strong></td>
													</c:if>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="divider"></div>
					</c:if>
				</div>
			</form>
		</div>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
		$(document).ready(
				function() {
					
					var cb = '<c:out value="${bg}"/>';
					$('nav').css('background-color', cb);
					
					$("#navlogout").click(function() {
						$('input[name=navmode]').val('logout');
						$("#navform").submit();
					});

					$("#navhome").click(function() {
						$('input[name=navmode]').val('home');
						$("#navform").submit();
					});

					$("#partshistorypartno").keypress(function(event) {
						if (event.which == 13) {
							event.preventDefault();
							$("#partshistory").click();
						}
					});

					$("#partshistory").click(
							function() {

								$('input[name=partshistorymode]').val(
										'partshistory');
								$("#utilform").attr("action",
										"/insight/parts/" + "partshistory");
								$("#utilform").submit();

							});

					$("#utilform").submit(function() {
						var isFormValid = true;
						if ($('#partshistorymode').val() == 'partshistory') {
							if ($('#partshistorypartno').val() == '') {
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
