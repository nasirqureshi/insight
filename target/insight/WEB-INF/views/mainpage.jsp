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
<title>Main</title>
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
			<div class="container  blue-grey lighten-5">
				<form class="col s12" role="form" method="post"
					action="/insight/main">
					<div class="row">
						<i class="material-icons store">Home</i>
					</div>
					<c:if test="${not empty user}">
						<div class="card-panel  blue-grey lighten-4">
							<div class="card-content">
								<span class="card-title">Parts</span>
								<div class="row">
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="invoicespage">
												<i class="large mdi-communication-quick-contacts-dialer"></i>
											</button>
										</span>
										<p>
											<strong>Invoices</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="invoicesmainpage">
												<i class="large mdi-communication-quick-contacts-dialer"></i>
											</button>
										</span>
										<p>
											<strong>Invoices Return</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="utilitiespage">
												<i class="large mdi-maps-local-mall"></i>
											</button>
										</span>
										<p>
											<strong>Utilities</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="partshistorypage">
												<i class="large mdi-maps-local-mall"></i>
											</button>
										</span>
										<p>
											<strong>Parts History</strong>
										</p>
									</div>
									<div class="col s2">

										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="customerupdatepage">
												<i class="mdi-social-people"></i>
											</button>
										</span>
										<p>
											<strong>Customer Update</strong>
										</p>

									</div>

									<c:if test="${user.actualrole ne 'sales'}">
										<div class="col s2">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="partmaintenancepage">
													<i class="mdi-action-settings"></i>
												</button>
											</span>
											<p>
												<strong>Parts Maintenance</strong>
											</p>
										</div>
									</c:if>
									<c:if
										test="${(user.actualrole == 'purchasing')||(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
										<div class="col s2">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="reorderlevelqtytoorderpage">
													<i class="large mdi-action-add-shopping-cart"></i>
												</button>
											</span>
											<p>
												<strong>Reorder Level - Qty to Order</strong>
											</p>
										</div>
									</c:if>
									<c:if
										test="${(user.actualrole == 'purchasing')||(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
										<div class="col s2">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="createvendororderpage">
													<i class="large mdi-action-note-add"></i>
												</button>
											</span>
											<p>
												<strong>Create Order</strong>
											</p>
										</div>
									</c:if>
									<c:if
										test="${(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
										<div class="col s2">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="updatepartcostanddiscountpricepage">
													<i class="mdi-notification-sync"></i>
												</button>
											</span>
											<p>
												<strong>Update Parts Price (Cost, Discount)</strong>
											</p>
										</div>
									</c:if>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="addvendorpartpage">
												<i class="large mdi-content-add-box"></i>
											</button>
										</span>
										<p>
											<strong>VendorPart+</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="partlinkoemsearchpage">
												<i class="mdi-action-view-week"></i>
											</button>
										</span>
										<p>
											<strong>PartLink OEM Search</strong>
										</p>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<div class="row"></div>
					<c:if
						test="${(user.actualrole == 'purchasing') || (user.actualrole == 'admin') || (user.actualrole == 'branchmanager') || (user.actualrole == 'superadmin')}">
						<div class="card-panel  blue-grey lighten-4">
							<div class="card-content">
								<span class="card-title">Orders</span>
								<div class="row">
									<div class="col s2">
										<span><button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="existingorderpage">
												<i class="large mdi-maps-local-grocery-store"></i>
											</button></span>
										<p>
											<strong>EditOrder</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="addtocontainerpage">
												<i class="large mdi-maps-directions-ferry"></i>
											</button>
										</span>
										<p>
											<strong>Container+</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="scanorderpage">
												<i class="large mdi-device-gps-fixed"></i>
											</button>
										</span>
										<p>
											<strong>ScanOrder*</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="vendoritemsuploadpage">
												<i class="large mdi-file-file-upload"></i>
											</button>
										</span>
										<p>
											<strong>Upload Vendor Items</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="addtoorderpage">
												<i class="large mdi-maps-directions-ferry"></i>
											</button>
										</span>
										<p>
											<strong>Add to Order</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="comparepricepage">
												<i class="large mdi-file-file-upload"></i>
											</button>
										</span>
										<p>
											<strong>Compare price</strong>
										</p>
									</div>
									<div class="col s2">
										<c:if
											test="${(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="salesanalysispage">
													<i class="large mdi-social-poll"></i>
												</button>
											</span>
											<p>
												<strong>Sales By Category</strong>
											</p>
										</c:if>
									</div>
									<div class="col s2">
										<span><button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="createordernopage">
												<i class="large mdi-av-playlist-add"></i>
											</button></span>
										<p>
											<strong>Create Order #</strong>
										</p>
									</div>
								</div>
								<div class="row">
									<div class="col s2">
										<span><button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="purchasingfunctionpage">
												<i class="large mdi-action-add-shopping-cart"></i>
											</button></span>
										<p>
											<strong>Purchasing Functions</strong>
										</p>
									</div>
									<div class="col s2">
										<c:if
											test="${(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="deadinventorypage">
													<i class="large mdi-social-poll"></i>
												</button>
											</span>
											<p>
												<strong>Dead Inventory</strong>
											</p>
										</c:if>
									</div>
									<div class="col s2">
										<c:if
											test="${(user.actualrole == 'purchasing')||(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect" value="formatelabel">
													<i class="large mdi-action-swap-vert-circle"></i>
												</button>
											</span>
											<p>
												<strong>Formate Label</strong>
											</p>
										</c:if>
									</div>
									<div class="col s2">
										<c:if
											test="${(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'purchasing') || (user.actualrole == 'superadmin') }">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect" value="localorderspage">
													<i class="large mdi-action-shopping-basket"></i>
												</button>
											</span>
											<p>
												<strong>Local Orders</strong>
											</p>
										</c:if>
									</div>
									<div class="col s2">
										<c:if
											test="${(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'purchasing') || (user.actualrole == 'superadmin') }">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="vendorpricecomparisonpage">
													<i class="mdi-action-view-list"></i>
												</button>
											</span>
											<p>
												<strong>Vendor Price</strong>
											</p>
										</c:if>
									</div>
									<div class="col s2">
										<c:if
											test="${(user.actualrole == 'purchasing')||(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="updatepartsprice">
													<i class="mdi-notification-sync"></i>
												</button>
											</span>
											<p>
												<strong>Update Vendor Parts</strong>
											</p>
										</c:if>
									</div>
								</div>
								<div class="row"></div>
								<div class="row">
									<div class="col s2">
										<c:if
											test="${(user.actualrole == 'purchasing')||(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect" value="procurementpage">
													<i class="mdi-image-healing"></i>
												</button>
											</span>
											<p>
												<strong>Procurement</strong>
											</p>
										</c:if>
									</div>
									<div class="col s2">
										<c:if
											test="${(user.actualrole == 'purchasing')||(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="customermaintenancepage">
													<i class="mdi-social-people"></i>
												</button>
											</span>
											<p>
												<strong>Customer Maintenance</strong>
											</p>
										</c:if>
									</div>
									<div class="col s2">
										<c:if
											test="${(user.actualrole == 'purchasing')||(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="inventorytransferpage">
													<i class="mdi-notification-sync"></i>
												</button>
											</span>
											<p>
												<strong>Inventory Transfer</strong>
											</p>
										</c:if>
									</div>
									<div class="col s2">
										<c:if
											test="${(user.actualrole == 'purchasing')||(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="containertransferpage">
													<i class="mdi-notification-sync"></i>
												</button>
											</span>
											<p>
												<strong>Container Transfer</strong>
											</p>
										</c:if>
									</div>
									<div class="col s2">
										<c:if
											test="${(user.actualrole == 'purchasing')||(user.actualrole == 'branchmanager') || (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect" value="vendorsalespage">
													<i class="mdi-notification-sync"></i>
												</button>
											</span>
											<p>
												<strong>Vendor Sales</strong>
											</p>
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<div class="row"></div>
					<c:if
						test="${ (user.actualrole == 'admin') || (user.actualrole == 'superadmin')}">
						<div class="card-panel  blue-grey lighten-4">
							<div class="card-content">
								<span class="card-title">Analytics</span>
								<div class="row">
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="analyticspage">
												<i class="large mdi-editor-insert-chart"></i>
											</button>
										</span>
										<p>
											<strong>Analytics</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="partsfixpage">
												<i class="large mdi-maps-local-pharmacy"></i>
											</button>
										</span>
										<p>
											<strong>Parts Fix</strong>
										</p>
									</div>
									<c:if test="${ (user.actualrole == 'superadmin') }">
										<div class="col s2">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect" value="salesreportpage">
													<i class="mdi-action-assessment"></i>
												</button>
											</span>
											<p>
												<strong>Sales Reports</strong>
											</p>
										</div>
									</c:if>
									<c:if test="${ (user.actualrole == 'superadmin') }">
										<div class="col s2">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="salesdetailspage">
													<i class="mdi-action-assessment"></i>
												</button>
											</span>
											<p>
												<strong>Sales Details</strong>
											</p>
										</div>
										<div class="col s2">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect" value="stockauditpage">
													<i class="mdi-action-assessment"></i>
												</button>
											</span>
											<p>
												<strong>Stock Audit Report</strong>
											</p>
										</div>
									</c:if>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="saleshistorypage">
												<i class="mdi-action-assessment"></i>
											</button>
										</span>
										<p>
											<strong>Sales Analysis History</strong>
										</p>
									</div>
									<c:if
										test="${(user.actualrole == 'admin') || (user.actualrole == 'salessupervisor') || (user.actualrole == 'superadmin')}">
										<div class="col s2">
											<span>
												<button
													class="btn-floating btn-large waves-effect waves-light blue"
													type="submit" name="mainpageselect"
													value="routeSaleAnalysispage">
													<i class="mdi-action-assessment"></i>
												</button>
											</span>
											<p>
												<strong>Sales Analysis By Route</strong>
											</p>
										</div>
									</c:if>
									<div class="col s2"></div>
								</div>
							</div>
						</div>
					</c:if>
					<div class="row"></div>
					<c:if
						test="${(user.actualrole == 'warehouse') || (user.actualrole == 'admin') || (user.actualrole == 'salessupervisor') || (user.actualrole == 'superadmin')}">
						<div class="card-panel  blue-grey lighten-4">
							<div class="card-content">
								<span class="card-title">Ware House</span>
								<div class="row">
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light purple darken-4"
												type="submit" name="mainpageselect"
												value="invoicenotdeliveredpage">
												<i class="large mdi-maps-local-shipping"></i>
											</button>
										</span>
										<p>
											<strong>Invoice Not Delivered</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light purple darken-4"
												type="submit" name="mainpageselect" value="redeliverypage">
												<i class="mdi-maps-directions"></i>
											</button>
										</span>
										<p>
											<strong>Add to Re-Delivery</strong>
										</p>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<div class="row"></div>
					<c:if
						test="${ (user.actualrole == 'admin') || (user.actualrole == 'superadmin') || (user.actualrole == 'accounting') }">
						<div class="row"></div>
						<div class="card-panel  blue-grey lighten-4">
							<div class="card-content">
								<span class="card-title">Accounting</span>
								<div class="row">
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="enteramountpage">
												<i class="mdi-editor-attach-money"></i>
											</button>
										</span>
										<p>
											<strong>Enter Amounts</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="bouncecheckpage">
												<i class="mdi-editor-attach-money"></i>
											</button>
										</span>
										<p>
											<strong>Bounced Checks</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="writeoffpage">
												<i class="mdi-editor-attach-money"></i>
											</button>
										</span>
										<p>
											<strong>Write Off</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="closeinvoicespage">
												<i class="mdi-editor-attach-money"></i>
											</button>
										</span>
										<p>
											<strong>Close Invoices</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="custhistorypage">
												<i class="mdi-editor-attach-money"></i>
											</button>
										</span>
										<p>
											<strong>Customer History</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="accountingreports">
												<i class="mdi-editor-attach-money"></i>
											</button>
										</span>
										<p>
											<strong>Accounting Reports</strong>
										</p>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<div class="row"></div>
					<c:if test="${user.actualrole == 'superadmin' }">
						<div class="row"></div>
						<div class="card-panel  blue-grey lighten-4">
							<div class="card-content">
								<span class="card-title">Maintenance</span>
								<div class="row">
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="superadminpage">
												<i class="mdi-notification-sync"></i>
											</button>
										</span>
										<p>
											<strong>Maintenance</strong>
										</p>
									</div>
									<div class="col s2">
										<span><button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="partmakerpage">
												<i class="large mdi-communication-vpn-key"></i>
											</button></span>
										<p>
											<strong>Part Maker</strong>
										</p>
									</div>
									<div class="col s2">
										<span><button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect" value="containerpage">
												<i class="large mdi-maps-directions-transit"></i>
											</button></span>
										<p>
											<strong>Final Container</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="initiateorderpage">
												<i class="large mdi-maps-local-gas-station"></i>
											</button>
										</span>
										<p>
											<strong>Initiate Order</strong>
										</p>
									</div>
									<div class="col s2">
										<span>
											<button
												class="btn-floating btn-large waves-effect waves-light blue"
												type="submit" name="mainpageselect"
												value="uploadnewpartspage">
												<i class="mdi-editor-publish"></i>
											</button>
										</span>
										<p>
											<strong>Upload New Parts(Excel)</strong>
										</p>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</form>
			</div>
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
		});
	</script>
</body>
</html>
