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
<title>Close Invoices</title>
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
			<form:form cssClass="col s12" method="post" id="utilform"
				action="/insight/accounting/enteramount">
				<input type="hidden" name="utilitiesmode" id="utilitiesmode"
					value="" />
				<div class="row">
					<p class="caption">Payments Maintenance</p>
				</div>
				<div class="card  blue-grey lighten-4">
					<div class="card-content">
						<div class="row">
							<div class="input-field col s2">
								<input id="invoiceno" name="invoiceno" value="" type="text"
									required> <label for="invoiceno"> <strong>Invoice
										No: </strong></label>
							</div>
							<div class="col s1">
								<button
									class="btn-floating btn-large waves-effect waves-light  blue"
									type="submit" id="invcheck" name="invcheck" value="invcheck">
									<i class="mdi-action-search right"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</form:form>
			<c:if test="${not empty invList}">
				<form:form cssClass="col s12" method="post" id="eaform"
					name="eaform" commandName="invList"
					action="/insight/accounting/addPayments">
					<input type="hidden" name="operation" id="operation" value="" />
					<div class="card-panel   indigo lighten-5">
						<div class="card-content">
							<div class="row">
								<div class="col s12">
									<h5 class="light col s12">Payments Maintenance</h5>
									<table class="striped">
										<thead>
											<tr>
												<th>Invoice#</th>
												<th>Customer</th>
												<th>Total Credit or Balance</th>
												<th>Paid Till Now</th>
												<th>Current Balance</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td style="word-wrap: break-word; white-space: normal;"><c:out
														value="${invList.invoiceNumber}" /></td>
												<td style="word-wrap: break-word; white-space: normal;"><c:out
														value="${ invList.companyName}" /> <form:hidden
														path="companyName" /></td>
												<td style="word-wrap: break-word; white-space: normal;"><c:out
														value="${invList.creditBalance}" /> <form:hidden
														path="creditBalance" /></td>
												<td style="word-wrap: break-word; white-space: normal;"><c:out
														value="${invList.appliedAmount }" /> <form:hidden
														path="appliedAmount" /></td>
												<td style="word-wrap: break-word; white-space: normal;"><c:out
														value="${invList.balance }" /> <form:hidden
														path="balance" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="card  blue-grey lighten-4">
						<div class="card-content">
							<div class="row">
								<div class="input-field col s2">
									<form:hidden path="InvoiceNumber" />
									<form:hidden path="appliedDate" />
									<input type="text" id="payingAmount" required="required"
										name="payingAmount"
										<c:if test="${invList.balance < 0.0}">value="${invList.balance }"</c:if> />
									<label for="payingAmount"> <strong>Payment
											Amount: </strong></label> <span class="help-block has-error"> <form:errors
											path="payingAmount" cssClass="error" />
									</span>
								</div>
								<div class="input-field col s2">
									<form:input id="paymentType" path="paymentType" />
									<label for="paymentType"><strong> Check No: </strong></label>
								</div>
								<span class="help-block has-error"> <form:errors
										path="paymentType" cssClass="error" />
								</span>
								<div class="row"></div>
								<div class="col s1">
									<button class="btn waves-effect waves-light" type="button"
										id="addpaymentsbtn" name="addpaymentsbtn" value="addpayments">&nbsp;Save</button>
								</div>
							</div>
						</div>
						<c:if test="${not empty appAmtList}">
							<jsp:useBean id="now" class="java.util.Date" scope="request" />
							<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"
								var="currDate" />
							<div class="card-panel   indigo lighten-5">
								<div class="col s12">
									<h5 class="light col s12">Payments History</h5>
									<table class="striped">
										<thead>
											<tr>
												<th>Invoice#</th>
												<th>Applied Date</th>
												<th>Payment Type</th>
												<th>Amount</th>
												<th>User Name</th>
												<th>Payment Time</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${appAmtList }" var="amtList">
												<tr>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${amtList.invoiceNumber}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${ amtList.appliedDate}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${ amtList.paymentType}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${amtList.appliedAmount}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${amtList.userName }" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${amtList.paymentTime }" /></td>
													<c:if test="${ currDate eq amtList.appliedDate}">
														<td>
															<button type="button"
																onclick="$.delPayment('${ amtList.invoiceNumber }', '${ amtList.appliedDate }', '${ amtList.appliedAmount }', '${ amtList.paymentType}')">
																Delete</button>
														</td>
													</c:if>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</c:if>
					</div>
				</form:form>
			</c:if>
		</div>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/jquery.1.10.2.min.js" />"></script>
	<script
		src="<c:url value="/resources/jquery/jquery.autocomplete.min.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
                $(document).ready(function() {
                	
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

                    $("#invcheck").click(function() {

                        $('input[name=utilitiesmode]').val('invcheck');
                        $("#utilform").submit();

                    });
                    $("#addpaymentsbtn").click(function() {
                        $('input[name=operation]').val('addpayments');
                        $("#eaform").submit();

                    });

                    $("#addpayments").submit(function() {
                        var isFormValid = true;

                        if ($('#utilitiesmode').val() == 'invcheck') {
                            if ($('#invoiceno').val() == '') {
                                alert('invoice no cannot be blank');
                                isFormValid = false;
                            }

                        }
                        return isFormValid;
                    });
                });
                $.extend({
                    delPayment: function(invno, invdate, apamt, paymentType) {
                        $('input[name=operation]').val('delPayment');
                        $('input[name=InvoiceNumber]').val(invno);
                        $('input[name=appliedDate]').val(invdate);
                        $('input[name=payingAmount]').val(apamt);
                        $('input[name=paymentType]').val(paymentType);
                        $("#eaform").submit();
                    }
                });
            <c:if test="${not empty error}">
                  alert("${error}");
            </c:if>
        </script>
</body>
</html>
