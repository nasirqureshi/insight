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
<title>Bounced Check</title>
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
			<form:form cssClass="col s12" method="post" id="bcform" name="bcform"
				commandName="bouncedchecks"
				action="/insight/accounting/bouncedchecks">
				<input type="hidden" name="operation" id="operation" value="" />
				<div class="row">
					<p class="caption">Bounced Checks Maintenance</p>
				</div>
				<div class="card  blue-grey lighten-4  z-depth-3">
					<div class="card-content">
						<div class="row">
							<div class="input-field col s2">
								<form:input path="checkid" />
								<label for="checkid"> <strong>Check Id: </strong></label> <span
									class="help-block has-error"> <form:errors
										path="checkid" cssClass="error" />
								</span>
							</div>
							<div class="input-field col s2">
								<input type="text" name="customerId" id="customerId"
									<c:if test='${not empty BCList }'>value="<c:out value='${BCList[0].customerid }'/>" </c:if>
									<c:if test='${empty BCList and not empty bouncedchecks and not empty bouncedchecks.customerid }'>value="<c:out value='${ bouncedchecks.customerid }'/>" </c:if> />
								<label for="customerid"> <strong>Customer Id: </strong></label>
								<span class="help-block has-error"> <form:errors
										path="customerid" cssClass="error" />
								</span>
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
				<div class="card-panel   indigo lighten-5  z-depth-3">
					<div class="card-content">
						<div class="row">
							<div class="col s12">
								<table class="striped">
									<tbody>
										<tr>
											<td>Company Name</td>
											<td style="word-wrap: break-word; white-space: normal;"><input
												type="text" name="companyName" readonly
												<c:if test='${not empty BCList }'>value="<c:out value='${BCList[0].companyname }'/>" </c:if>
												<c:if test='${empty BCList and not empty bouncedchecks and not empty bouncedchecks.companyname }'>value="<c:out value='${ bouncedchecks.companyname }'/>" </c:if> />
											</td>
											<td>Credit Balance</td>
											<td style="word-wrap: break-word; white-space: normal;"><input
												type="text" name="creditBalance" readonly
												<c:if test='${not empty BCList }'>value="<c:out value='${BCList[0].creditbalance }'/>" </c:if>
												<c:if test='${empty BCList and not empty bouncedchecks and not empty bouncedchecks.creditbalance }'>value="<c:out value='${ bouncedchecks.creditbalance }'/>" </c:if> />
											</td>
										</tr>
										<tr>
											<td>Check No:</td>
											<td style="word-wrap: break-word; white-space: normal;"><form:input
													path="checkno" /> <span class="help-block has-error">
													<form:errors path="checkno" cssClass="error" />
											</span></td>
										</tr>
										<tr>
											<td>Check Date</td>
											<td style="word-wrap: break-word; white-space: normal;"><input
												type="date" class="datepicker" name="checkDate"
												id="checkDate"
												value='<fmt:formatDate value="${ bouncedchecks.checkdate }" pattern="yyyy-MM-dd" />' />
												<span class="help-block has-error"> <form:errors
														path="checkdate" cssClass="error" />
											</span></td>
										</tr>
										<tr>
											<td>Amount Bounced</td>
											<td style="word-wrap: break-word; white-space: normal;"><form:input
													path="bouncedamount" /> <span class="help-block has-error">
													<form:errors path="bouncedamount" cssClass="error" />
											</span></td>
											<td>Returns Check Fee:</td>
											<td>25</td>
										</tr>
										<tr>
											<td>Amount Paid</td>
											<td style="word-wrap: break-word; white-space: normal;"><form:input
													path="paidamount" /> <span class="help-block has-error">
													<form:errors path="paidamount" cssClass="error" />
											</span></td>
										</tr>
										<tr>
											<td>Balance</td>
											<td style="word-wrap: break-word; white-space: normal;"><form:input
													path="balance" /> <span class="help-block has-error">
													<form:errors path="balance" cssClass="error" />
											</span></td>
										</tr>
										<tr>
											<td>Is Cleared</td>
											<td style="word-wrap: break-word; white-space: normal;"><form:checkbox
													path="iscleared" value="Y" /> <span
												class="help-block has-error"> <form:errors
														path="iscleared" cssClass="error" />
											</span></td>
										</tr>
										<tr>
											<td colspan="2"></td>
										</tr>
									</tbody>
								</table>
								<div class="row">
									<div class="col s2">
										<button class="btn waves-effect waves-light" type="submit"
											id="invclear" name="invclear" value="invclear">&nbsp;Clear</button>
									</div>
									<div class="col s2">
										<c:choose>
											<c:when test="${empty bouncedchecks.checkid}">
												<button class="btn waves-effect waves-light" type="submit"
													id="invsave" name="invsave" value="invsave">&nbsp;Save</button>
											</c:when>
											<c:otherwise>
												<button class="btn waves-effect waves-light" type="submit"
													id="invupdate" name="invupdate" value="invupdate">&nbsp;Change</button>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<c:if test="${not empty BCList }">
									<table>
										<tr>
											<th>Check Id</th>
											<th>Check Date</th>
											<th>Check No</th>
											<th>Amount Bounced</th>
											<th>Is cleared</th>
										</tr>
										<c:forEach items="${BCList }" var="bc">
											<tr>
												<td><c:out value="${ bc.checkid }"></c:out></td>
												<td><c:out value="${bc.checkdate }"></c:out></td>
												<td><c:out value="${ bc.checkno }"></c:out></td>
												<td><c:out value="${ bc.bouncedamount }"></c:out></td>
												<td><c:out value="${ bc.iscleared }"></c:out></td>
												<td>
													<button type="button" id="invedtibc" name="invedtibc"
														onclick="$.editBC('${ bc.checkid }', '${ bc.customerid }')">Edit</button>
												</td>
											</tr>
										</c:forEach>
									</table>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
	$(document).ready(function() {
		
		var cb = '<c:out value="${bg}"/>';
		$('nav').css('background-color', cb);
		
		$('.datepicker').pickadate({
			selectMonths : false,
			selectYears : 15,
			format : 'yyyy-mm-dd'
			//format : 'mm-dd-yyyy'
                        //yyyy-MM-dd
		// Creates a dropdown of 15 years to control year
		});

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
			$('input[name=operation]').val('getBC');
			$("#bcform").submit();

		});
		$("#invsave").click(function() {
			$('input[name=operation]').val('save');
			$("#bcform").submit();

		});
		$("#invupdate").click(function() {
			$('input[name=operation]').val('update');
			$("#bcform").submit();

		});
		$("#invclear").click(function() {			
			$('input[name=operation]').val('clear');
			$("#bcform").submit();

		});

		$("#bcform").submit(function() {
			var isFormValid = true;

			if ($('#operation').val() == 'invcheck') {
				if ($('#checkId').val() == '') {
					alert('check Id cannot be blank');
					isFormValid = false;
				}

			}
			return isFormValid;
		});
	});
	$.extend({
		editBC : function(checkId, custId) {
			$('input[name=operation]').val('getBC');
			$('input[name=checkId]').val(checkId);
			$('input[name=customerId]').val(custId);
			$("#bcform").submit();
		}
	});
	<c:if test="${not empty error}">
                                        alert("${error}");
                                    </c:if>
</script>
</body>
</html>
