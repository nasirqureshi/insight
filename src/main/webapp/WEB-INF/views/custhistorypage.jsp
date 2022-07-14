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
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/${appcss}" />
<title>Customer History</title>
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
			<form class="col s12" method="post" id="custform"
				action="/insight/accounting/custhistory">
				<div class="row">
					<p class="caption">Customer History</p>
				</div>
				<div class="row">
					<input type="hidden" id="utilitiesmode" name="utilitiesmode"
						value="">
				</div>
				<div class="card  blue-grey lighten-4">
					<div class="card-content">
						<div class="row">
							<div class="input-field col s2">
								<input id="customerId" name="customerId" value="" type="text"
									required> <label for="customerId"><strong>
										Customer Id </strong> </label>
							</div>
						</div>
						<div class="row">
							<div class="col s3">
								<input value="" min="2000-01-01" max="2030-01-01"
									name="startDate" id="startDate" type="date">
							</div>
							<div class="col s3">
								<input value="" min="2000-01-01" max="2030-01-01" name="endDate"
									id="endDate" type="date">
							</div>
							<div class="col s3">
								<span><button
										class="btn-floating btn-large waves-effect waves-light blue"
										type="submit" value="writeoffreport">
										<i class="mdi-toggle-check-box right"></i>
									</button> </span>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${not empty custList}">
					<div class="card-panel   indigo lighten-5">
						<div class="card-content">
							<div class="row">
								<div class="col s12">
									<h5 class="light col s12">Customer Details</h5>
									<table class="striped">
										<thead>
											<tr>
												<th>CUSTOMER ID</th>
												<th>COMPANY NAME</th>
												<th>TERMS</th>
												<th>TOTAL PURCHASE</th>
												<th>CUR. BALANCE</th>
												<th>WRITE OFF</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td style="word-wrap: break-word; white-space: normal;"><c:out
														value="${ custList.customerId }" /></td>
												<td style="word-wrap: break-word; white-space: normal;"><c:out
														value="${custList.companyName}" /></td>
												<td style="word-wrap: break-word; white-space: normal;"><c:out
														value="${custList.terms}" /></td>
												<td style="word-wrap: break-word; white-space: normal;"><c:out
														value="${custList.totalPurchase}" /></td>
												<td style="word-wrap: break-word; white-space: normal;"><c:out
														value="${custList.currBalance}" /></td>
												<td style="word-wrap: break-word; white-space: normal;"><c:out
														value="${custList.writeBalance}" /></td>
											</tr>
										</tbody>
									</table>
									<c:if test="${not empty custList.bclist}">
										<h5 class="light col s12">Bounced Checks Details</h5>
										<table class="striped">
											<thead>
												<tr>
													<th>Chk Id</th>
													<th>Entered Date</th>
													<th>Check No Dt</th>
													<th>Amount</th>
													<th>CUR. BALANCE</th>
													<th>Payment Details</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="bc" items="${custList.bclist}"
													varStatus="loop">
													<tr>
														<td style="word-wrap: break-word; white-space: normal;"><c:out
																value="${ bc.checkId }" /></td>
														<td style="word-wrap: break-word; white-space: normal;"><c:out
																value="${bc.enteredDate}" /></td>
														<td style="word-wrap: break-word; white-space: normal;"><c:out
																value="${bc.checkNo} / ${bc.checkDate}" /></td>
														<td style="word-wrap: break-word; white-space: normal;"><c:out
																value="${bc.bouncedAmount}" /></td>
														<td style="word-wrap: break-word; white-space: normal;"><c:out
																value="${bc.balance}" /></td>
														<td style="word-wrap: break-word; white-space: normal;"><c:out
																value="${bc.paymentDetails }" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
									<c:if test="${not empty custList.invlist}">
										<h5 class="light col s12">Invoice Details</h5>
										<table class="striped">
											<thead>
												<tr>
													<th>INVOICE #</th>
													<th>INV DATE</th>
													<th>INV TOTAL</th>
													<th>REMARKS</th>
													<th>PAYMENT DETAILS</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="inv" items="${custList.invlist}"
													varStatus="loop">
													<tr>
														<td style="word-wrap: break-word; white-space: normal;"><c:out
																value="${ inv.invoicenumber }" /></td>
														<td style="word-wrap: break-word; white-space: normal;"><c:out
																value="${inv.orderdate}" /></td>
														<td style="word-wrap: break-word; white-space: normal;"><c:out
																value="${inv.invoicetotal}" /></td>
														<td style="word-wrap: break-word; white-space: normal;"><c:out
																value="${inv.notes}" /></td>
														<td style="word-wrap: break-word; white-space: normal;"><c:out
																value="${inv.paymentDetails}" /></td>
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
			</form>
		</div>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/jquery.1.10.2.min.js" />"></script>
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

		$("#stockcheck").click(function() {

			$('input[name=utilitiesmode]').val('stockcheck');
			$("#custform").submit();

		});

		$("#custform").submit(function() {
			var isFormValid = true;
			if ($('#utilitiesmode').val() == 'stockcheck') {
				if ($('#customerId').val() == '') {
					alert('Customer Id cannot be blank');
					isFormValid = false;
				}

			}
			return isFormValid;
		});
	});
	<c:if test="${not empty error}">
                                        alert("${error}");
                                    </c:if>
</script>
</body>
</html>
