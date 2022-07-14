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
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/${appcss}" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/jquery/jquery.jqplot.css" />" />
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

table.borderedAll>tbody>tr {
	/*border-bottom: 1px solid #d0d0d0;*/
	
}

table.borderedAll>tbody>tr>td {
	border: 1px solid black;
	padding: 2px 2px;
	font-weight: bold;
}

table.borderedAll>tbody>tr>td.noBorder {
	border: 0px;
}

table.borderedAll>thead>tr>th {
	border: 1px solid black;
	padding: 9px 2px;
}

table.borderedAll>thead>tr>th.noBorder {
	border: 0px;
}

table.borderedAll>thead {
	border: 0px;
}

/*.row {
                margin-bottom: auto;
            }

            html {
                line-height: normal;
            }

            .card .card-content {
                padding-top: 2px;
            }

            .card-panel {
                padding: 0px;
            }*/
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
<body
	style="word-wrap: break-word; white-space: normal; font-size: 0.7em;">
	<div id="loader-wrapper">
		<div id="loader"></div>
		<div class="loader-section section-left"></div>
		<div class="loader-section section-right"></div>
	</div>
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
						</ul>
					</div>
				</nav>
			</form>
		</div>
	</header>
	<div class="row">
		<form:form method="post" id="invoiceform" name="invoiceform"
			commandName="invoice" action="/insight/invoice/returninvoiceactions">
			<form:hidden path="invoicenumber" />
			<form:hidden path="returnedinvoice" />
			<form:hidden path="customerid" />
			<input type="hidden" name="partToAdd" id="partToAdd" />
			<input type="hidden" name="partToRemove" id="partToRemove" />
			<input type="hidden" name="operation" id="operation" />
			<div class="card  grey lighten-4">
				<div class="card-content">
					<div class="row">
						<div class="col s12">
							<table class="borderedAll">
								<thead>
									<tr>
										<th><strong>Invoice No</strong></th>
										<th><strong>Customer</strong></th>
										<th><strong>Invoice Date</strong></th>
										<th><strong>Terms</strong></th>
										<th><strong>Sales Person</strong></th>
										<th><strong>Shipping Method</strong></th>
										<th><strong>Return Invoice No</strong></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><c:out value="${invoice.invoicenumber}" /></td>
										<td><c:out value="${invoice.customerName}" /> - <c:out
												value="${invoice.customerid}" /></td>
										<td><fmt:formatDate pattern="MM-dd-yyyy"
												value="${invoice.orderdate}" /></td>
										<td><c:out value="${invoice.paymentterms}" /></td>
										<td><c:out value="${invoice.salesperson}" /></td>
										<td><form:select path="shipvia"
												cssClass="browser-default">
												<form:option value="" label="--- Select ---" />
												<form:options items="${shipviadd}" />
											</form:select></td>
										<td><c:out value="${invoice.returnedinvoice}" /></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="card  grey lighten-4">
				<div class="card-content">
					<div class="row">
						<div class="col s6">
							<span class="card-title">Original Invoice Items</span>
						</div>
						<c:if test="${empty originalinvoiceItems}">
							<!--div class="col s6">
                                    <button class="btn waves-effect waves-light" type="submit" id="inveditbtn" name="inveditbtn" value="invedit">Show Original Invoice Items</button>
                                </div-->
						</c:if>
					</div>
					<c:if test="${not empty originalinvoiceItems}">
						<div class="row">
							<div class="col s12">
								<table class="borderedAll">
									<thead>
										<tr>
											<th><strong>Part No</strong></th>
											<th><strong>Make/Model</strong></th>
											<th><strong>Part Description</strong></th>
											<th><strong>Year</strong></th>
											<th><strong>Stock</strong></th>
											<th><strong>Cost</strong></th>
											<th><strong>List</strong></th>
											<th><strong>Quantity</strong></th>
											<th class="noBorder" style="width: 5%"></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${originalinvoiceItems}"
											var="orginvoicedetails" varStatus="idx">
											<tr>
												<td><c:out value="${orginvoicedetails.partnumber}" /></td>
												<td><c:out value="${orginvoicedetails.makemodelname}" /></td>
												<td><c:out value="${orginvoicedetails.partdescription}" /></td>
												<td><c:out value="${orginvoicedetails.year}" /></td>
												<td><c:out value="${orginvoicedetails.unitsinstock}" /></td>
												<td><c:out value="${orginvoicedetails.soldprice}" /></td>
												<td><c:out value="${orginvoicedetails.listprice}" /></td>
												<td><c:out value="${orginvoicedetails.quantity}" /></td>
												<td class="noBorder"><c:if
														test="${empty removeSaveBtn or removeSaveBtn ne 'remove'}">
														<a class="btn-floating btn waves-effect waves-light green"
															onclick="addPartToInvoice('${orginvoicedetails.partnumber}')"><i
															class="mdi-content-add"></i></a>
													</c:if></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
				</div>
			</div>
			<div class="card  grey lighten-4">
				<div class="card-content">
					<div class="row">
						<div class="col s6">
							<span class="card-title">Invoice Details</span>
						</div>
					</div>
					<div class="row">
						<div class="col s12">
							<table class="borderedAll">
								<thead>
									<tr>
										<th><strong>Part No</strong></th>
										<th><strong>Make/Model</strong></th>
										<th><strong>Part Description</strong></th>
										<th><strong>Year</strong></th>
										<th><strong>Stock</strong></th>
										<th><strong>Cost</strong></th>
										<th><strong>List</strong></th>
										<th style="width: 8%"><strong>Quantity</strong></th>
										<th class="noBorder" style="width: 5%;"></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${invoice.displayinvoicedetailslist}"
										var="invoicedetails" varStatus="idx">
										<tr>
											<td><form:hidden
													path="displayinvoicedetailslist[${idx.index}].partnumber" />
												<form:hidden
													path="displayinvoicedetailslist[${idx.index}].actualprice" />
												<c:out value="${invoicedetails.partnumber}" /></td>
											<td><form:hidden
													path="displayinvoicedetailslist[${idx.index}].makemodelname" />
												<c:out value="${invoicedetails.makemodelname}" /></td>
											<td><form:hidden
													path="displayinvoicedetailslist[${idx.index}].partdescription" />
												<c:out value="${invoicedetails.partdescription}" /></td>
											<td><form:hidden
													path="displayinvoicedetailslist[${idx.index}].year" /> <c:out
													value="${invoicedetails.year}" /></td>
											<td><form:hidden
													path="displayinvoicedetailslist[${idx.index}].unitsinstock" />
												<c:out value="${invoicedetails.unitsinstock}" /></td>
											<td><form:hidden
													path="displayinvoicedetailslist[${idx.index}].soldprice" />
												<c:out value="${invoicedetails.soldprice}" /></td>
											<td><form:hidden
													path="displayinvoicedetailslist[${idx.index}].listprice" />
												<c:out value="${invoicedetails.listprice}" /></td>
											<td><form:input
													path="displayinvoicedetailslist[${idx.index}].quantity"
													cssStyle="margin: 0 0 2px 0;" /></td>
											<td class="noBorder"><c:if
													test="${empty removeSaveBtn or removeSaveBtn ne 'remove'}">
													<a class="btn-floating btn waves-effect waves-light green"
														onclick="removePartFromInvoice('${invoicedetails.partnumber}')"><i
														class="mdi-action-delete"></i></a>
												</c:if></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="row">
						<div class="col s12">
							<table class="borderedAll">
								<thead>
									<tr>
										<th><strong>Invoice Total</strong></th>
										<th><strong>Invoice Tax</strong></th>
										<th><strong>Discount</strong></th>
										<th><strong>Amount Owed</strong></th>
										<th class="noBorder" style="width: 5%;"></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><form:hidden path="invoicetotal" /> <c:out
												value="${invoice.invoicetotal}" /></td>
										<td><form:hidden path="tax" /> <c:out
												value="${invoice.tax}" /></td>
										<td><form:hidden path="discount" /> <c:out
												value="${invoice.discount}" /></td>
										<td><form:hidden path="balance" /> <c:out
												value="${invoice.balance}" /></td>
										<td class="noBorder"></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="col s12">
							<c:if test="${empty removeSaveBtn or removeSaveBtn ne 'remove'}">
								<button class="btn waves-effect waves-light" type="button"
									id="saveInvoiceBtn" name="saveInvoiceBtn"
									value="saveInvoiceBtn">Save Invoice</button>
							</c:if>
							<button class="btn waves-effect waves-light" type="button"
								id="backBtn" name="backBtn" value="backBtn">Back</button>
						</div>
					</div>
				</div>
			</div>
		</form:form>
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
                                                            $('.tooltipped').tooltip({
                                                                delay: 50
                                                            });
                                                            $('html, body').animate({scrollTop: $(document).height()}, 'fast');

                                                            $("#navlogout").click(function() {
                                                                $('input[name=navmode]').val('logout');
                                                                $("#navform").submit();
                                                            });

                                                            $("#navhome").click(function() {
                                                                $('input[name=navmode]').val('home');
                                                                $("#navform").submit();
                                                            });
                                                            $("#saveInvoiceBtn").click(function() {
                                                                $("#operation").val('saveInvoice');
                                                                $("#invoiceform").submit();
                                                            });
                                                            $("#backBtn").click(function() {
                                                                $("#operation").val('back');
                                                                $("#invoiceform").submit();
                                                            });
                                                        });
                                                        function addPartToInvoice(partNo) {
                                                            $("#partToAdd").val(partNo);
                                                            $("#operation").val('addItem');
                                                            //$("#invoiceform").attr('action', '/insight/invoice/returninvoiceactions');
                                                            $("#invoiceform").submit();
                                                        }
                                                        function removePartFromInvoice(partNo) {
                                                            $("#partToRemove").val(partNo);
                                                            $("#operation").val('removeItem');
                                                            //$("#invoiceform").attr('action', '/insight/invoice/returninvoiceremoveitem');
                                                            $("#invoiceform").submit();
                                                        }
            <c:if test="${not empty errMsg}">
                                                        alert('${errMsg}');
            </c:if>
        </script>
</body>
</html>
