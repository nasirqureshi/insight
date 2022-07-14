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
				id="salesanalysisform" name="salesanalysisform"
				action="/insight/report/salesDetailsRpt">
				<div class="row">
					<h5 class="light col s12">Sales Details</h5>
				</div>
				<div class="card  blue-grey lighten-4">
					<div class="card-content">
						<div class="row">
							<div class="col s2">
								<input value="${requestScope.startDate}" min="2000-01-01"
									max="2030-01-01" name="startDate" id="analyticsfromdate"
									type="date">
							</div>
							<div class="col s2">
								<input value="${requestScope.endDate}" min="2000-01-01"
									max="2030-01-01" name="endDate" id="endDate" type="date">
							</div>
							<div class="col s3">
								<label><strong>Select SalesPerson</strong></label> <select
									id="spList" name="salesperson" multiple>
									<option value="" selected>All SalesPerson</option>
									<c:forEach var="sp" items="${spList}">
										<option value="${sp.username}">${sp.username}</option>
									</c:forEach>
								</select>
								<!--input type="text"
                                            id="ssalesperson" name="salesperson"
                                            class="browser-default"-->
							</div>
							<div class="col s2">
								<span><button
										class="btn-floating btn-large waves-effect waves-light blue"
										type="submit" id="salesanalysisBtn" name="salesanalysisBtn"
										value="categorysales">
										<i class="mdi-action-assignment-turned-in right"></i>
									</button></span>
							</div>
							<div class="col s2">
								<c:if test="${not empty salesList}">
									<input type="hidden" name="rptPath"
										value="//sales//salesDetails_${sysdate}.pdf" />
									<span><button
											class="btn-floating btn-large waves-effect waves-light blue"
											type="submit" id="saveanalysisBtn" name="saveanalysisBtn"
											value="downloadRpt">
											<i class="mdi-content-save right"></i>
										</button></span>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col s12">
						<c:if test="${not empty salesGrpList}">
							<h3>Daily Summary Sales Report</h3>
							<table class="responsive-table">
								<thead>
									<tr>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>Sales Person</strong>
										</th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>Total Invoices</strong>
										</th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>Invoice Total</strong>
										</th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>Discount</strong>
										</th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>Tax</strong>
										</th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>Net Amount</strong>
										</th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>Average</strong>
										</th>
									</tr>
								</thead>
								<tbody>
									<c:set var="totinv" value="0" />
									<c:set var="totamt" value="0" />
									<c:set var="totdisc" value="0" />
									<c:set var="tottax" value="0" />
									<c:set var="totnetamt" value="0" />
									<c:set var="totavg" value="0" />
									<c:set var="len" value="0" />
									<c:forEach items="${salesGrpList}" var="salesgrp">
										<tr>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
												<strong><c:out value="${salesgrp.SalesPerson1}" /></strong>
											</td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
												<strong><c:out value="${salesgrp.totalinv}" /></strong>
											</td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
												<strong><c:out value="${salesgrp.NetAmount}" /></strong>
											</td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
												<strong><c:out value="${salesgrp.subTotalDiscount}" /></strong>
											</td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
												<strong><c:out value="${salesgrp.subTotalTax}" /></strong>
											</td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
												<strong><c:out value="${salesgrp.netTotalAmount}" /></strong>
											</td>
											<td
												style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
												<strong><c:out value="${salesgrp.avgTotal}" /></strong>
											</td>
										</tr>
										<c:set var="len" value="${len+1}" />
										<c:set var="totinv" value="${totinv+salesgrp.totalinv}" />
										<c:set var="totamt" value="${totamt+salesgrp.NetAmount}" />
										<c:set var="totdisc"
											value="${totdisc+salesgrp.subTotalDiscount}" />
										<c:set var="tottax" value="${tottax+salesgrp.subTotalTax}" />
										<c:set var="totnetamt"
											value="${totnetamt+salesgrp.netTotalAmount}" />
										<c:set var="totavg" value="${totavg+salesgrp.avgTotal}" />
									</c:forEach>
									<tr>
										<td
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>Total:</strong>
										</td>
										<td
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>${totinv}</strong>
										</td>
										<td
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>${totamt}</strong>
										</td>
										<td
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>${totdisc}</strong>
										</td>
										<td
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>${tottax}</strong>
										</td>
										<td
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>${totnetamt}</strong>
										</td>
										<td
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;">
											<strong>${totavg/len}</strong>
										</td>
									</tr>
								</tbody>
							</table>
						</c:if>
					</div>
				</div>
				<div class="row">
					<div class="col s12">
						<c:if test="${not empty salesList}">
							<h3>Daily Detailed Sales Report</h3>
							<table class="responsive-table">
								<thead>
									<tr>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>Order
												Date Time</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>Invoice
												Number</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>Customer
												ID</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>Customer
												Name</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>Net
												Inv Amount</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>Order
												Discount</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>Tax</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>Total
												Amout</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>Returned
												Invoice</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>Net
												Credit_Amount</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>CR
												Discount</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>CR
												Tax</strong></th>
										<th
											style="word-wrap: break-word; white-space: normal; font-size: 1.7em; color: white; border: 0.3px solid grey; border-collapse: collapse; background-color: #1E90FF;"><strong>Total
												Credit Amount</strong></th>
									</tr>
								</thead>
								<tbody>
									<c:set var="sp" value="" />
									<c:forEach items="${salesList}" var="sales">
										<c:choose>
											<c:when test="${sales.SalesPerson1 ne sp}">
												<tr>
													<td colspan="13"
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: grey;">${sales.SalesPerson1}</td>
												</tr>
												<c:set var="sp" value="${sales.SalesPerson1}" />
											</c:when>
											<c:otherwise>
												<tr>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.Order_Date_Time}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.InvoiceNumber}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.CustomerID}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.Cusomer_Name}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.Net_Inv_Amount}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.OrderDiscount}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.Tax}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.Total_Amout}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.ReturnedInvoice}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.Net_Credit_Amount}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.CR_DISCOUNT}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.CR_TAX}" /></strong>
													</td>
													<td
														style="word-wrap: break-word; white-space: normal; font-size: 1.6em; border: 0.1px solid grey; border-collapse: collapse; background-color: #F0F8FF;">
														<strong><c:out value="${sales.CR_DISCOUNT}" /></strong>
													</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script>
            $(document).ready(
                    
                    function() {
                        
                        var cb = '<c:out value="${bg}"/>';
                        $('nav').css('background-color', cb);

                        $(".button-collapse").sideNav();
                        $(".dropdown-button").dropdown();
                        $('select').material_select();

                        $('.collapsible').collapsible({
                            accordion: true
                                    // A setting that changes the collapsible behavior to expandable instead of the default accordion style
                        });

                        if (datefield.type != "date") { //if browser doesn't support input type="date", initialize date picker widget:
                            jQuery(function($) { //on document.ready
                                $('#startDate').datepicker();
                                $('#endDate').datepicker();
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

                        $("#salesanalysisBtn").click(
                                function() {
                                    $("#salesanalysisform").attr("action",
                                            "/insight/report/salesDetailsRpt");
                                    $("html, body").animate({
                                        scrollTop: 0
                                    }, "fast");
                                    $("#salesanalysisform").submit();
                                });

                        $("#saveanalysisBtn").click(
                                function() {

                                    $("#salesanalysisform").attr("action",
                                            "/insight/report/downloadRpt");
                                    $("#salesanalysisform").attr("target", "_blank");
                                    $("html, body").animate({
                                        scrollTop: 0
                                    }, "fast");
                                    $("#salesanalysisform").submit();

                                });

                        $("#salesanalysisform").submit(function() {
                            var isFormValid = true;
                            /* if ($('#existingorderbtn').val() == 'existingordersearch') {
                             if ($('#existingordernosearchtxt').length) {
                             if ($('#existingordernosearchtxt').val() == '') {
                             alert('search ORDER NO cannot be blank');
                             isFormValid = false;
                             }
                             }
                             } */

                            return isFormValid;
                        });
         
                    });
                    $(document).ready(function() {
         $('select').material_select();
      });
        </script>
</body>
</html>
