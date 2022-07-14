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
	border-bottom: 1.3px solid #00b0ff;
	box-shadow: 0 1px 0 0 #00b0ff;
}
/* valid color */
.input-field input[type=text].valid {
	border-bottom: 1.3px solid #00b0ff;
	box-shadow: 0 1px 0 0 #00b0ff;
}
/* invalid color */
.input-field input[type=text].invalid {
	border-bottom: 1.3px solid #00b0ff;
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
<body
	style="word-wrap: break-word; white-space: normal; font-size: 0.7em;">
	<!-- Start Page Loading -->
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
	<form role="form" method="post" id="invoiceform" action="">
		<div class="card  blue-grey lighten-4">
			<div class="card-content">
				<div class="row">
					<div class="col s5">
						<div class="row">
							<div class="input-field col s6">
								<input id="searchcustomernumber" name="searchcustomernumber"
									value="${searchcustomernumber}" type="text"> <label
									for="searchcustomernumber">Customer No</label>
							</div>
							<div class="col s6">
								<button class="btn waves-effect waves-light" type="submit"
									id="invcreatebtn" name="invcreatebtn" value="invcreate">&nbsp;Create
									New Invoice</button>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col s5">
						<div class="row">
							<div class="input-field col s6">
								<input id="searchinvoicenumber" name="searchinvoicenumber"
									value="${searchinvoicenumber}" type="text"> <label
									for="searchinvoicenumber">Invoice No</label>
							</div>
							<div class="col s6">
								<div class="row">
									<button class="btn waves-effect waves-light" type="submit"
										id="inveditbtn" name="inveditbtn" value="invedit">Edit</button>
									<button class="btn waves-effect waves-light" type="submit"
										id="invreturnbtn" name="invreturnbtn" value="invreturn">Return
										Invoice</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
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

                //$('select').not('.disabled').material_select();

                $('html, body').animate({
                    scrollTop: $(document).height()
                }, 'slow');

                $("#navlogout").click(function() {
                    $('input[name=navmode]').val('logout');
                    $("#navform").submit();
                });

                $("#navhome").click(function() {
                    $('input[name=navmode]').val('home');
                    $("#navform").submit();
                });

                $("#searchcustomernumber").keypress(function(event) {
                    if (event.which == 13) {
                        event.preventDefault();
                        $("#invcreatebtn").click();
                    }
                });
                $("#searchinvoicenumber").keypress(function(event) {
                    if (event.which == 13) {
                        event.preventDefault();
                    }
                });

                $("#invreturnbtn").click(function() {
                    if ($('#searchinvoicenumber').val() == '') {
                        alert('invoice no cannot be blank');
                        isFormValid = false;
                    } else {
                        isFormValid = true;
                        $("#invoiceform")
                                .attr(
                                        "action",
                                        "/insight/invoice/"
                                        + "returninvoicemain");
                        $("#invoiceform").submit();
                    }
                });

                $("#invoiceform").submit(function() {
                    return isFormValid;
                });
            });
            <c:if test="${not empty errMsg}">
            alert('${errMsg}');
            </c:if>
        </script>
</body>
</html>