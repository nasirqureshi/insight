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
				id="existingorderform"
				action="/insight/existingorders/getaddtoorder">
				<input type="hidden" id="btnval" name="btnval" value="" />
				<div class="row">
					<h5 class="light col s12">Add To Order</h5>
				</div>
				<div class="row"></div>
				<div class="card  blue-grey lighten-4">
					<div class="card-content">
						<div class="row">
							<div class="col s2">
								<input value="${requestScope.fromdate}" min="2000-01-01"
									name="fromdate" id="fromdate" type="date">
							</div>
							<div class="col s2">
								<input value="${requestScope.todate}" min="2000-01-01"
									name="todate" id="todate" type="date">
							</div>
							<div class="col s1">
								<button
									class="btn-floating btn-large waves-effect waves-light  blue"
									type="submit" id="getexistingorderbtn"
									name="getexistingorderbtn" value="getexistingorder">
									<i class="mdi-action-search right"></i>
								</button>
							</div>
							<div class="col s1">
								<c:if test="${not empty requestScope.addtoorderlist}">
									<span><button
											class="btn-floating btn-large waves-effect waves-light blue"
											type="submit" id="getexistingorderbtn1"
											name="getexistingorderbtn" value="saveexcel">
											<i class="mdi-content-save right"></i>
										</button></span>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${not empty requestScope.addtoorderlist}">
					<div class="row">
						<div class="col s12">
							<table class="bordered striped">
								<thead>
									<tr>
										<th width="7%">&nbsp;Part&nbsp;#</th>
										<th>Make</th>
										<th>Model</th>
										<th>Part description</th>
										<th>Year</th>
										<th>Capa</th>
										<th>St</th>
										<th>Or</th>
										<th>Re</th>
										<th>Sfty</th>
										<th>Type</th>
										<th width="10%">Cost&nbsp;Price</th>
										<th width="10%">&nbsp;&nbsp;Quantity</th>
										<th>Tot</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${requestScope.addtoorderlist}"
										var="vendororderitems">
										<tr>
											<td width="7%"><strong><input type="text"
													value="${vendororderitems.partno}" name="partno"
													id="partno"
													style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
											<td><strong><c:out
														value="${vendororderitems.manufacturername}" /></strong></td>
											<td><c:out value="${vendororderitems.makemodelname}" /></td>
											<td><c:out value="${vendororderitems.partdescription}" /></td>
											<td><c:out value="${vendororderitems.year}" /></td>
											<td><c:out value="${vendororderitems.capa}" /></td>
											<td><c:out value="${vendororderitems.unitsinstock}" /></td>
											<td><c:out value="${vendororderitems.unitsonorder}" /></td>
											<td><c:out value="${vendororderitems.reorderlevel}" /></td>
											<td><c:out value="${vendororderitems.safetyquantity}" /></td>
											<td><c:out value="${vendororderitems.ordertype}" /></td>
											<td width="10%"><strong><input type="text"
													value="${vendororderitems.price}" name="price" id="price"
													style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
											<td width="10%"><strong><input type="text"
													value="${vendororderitems.qty}" name="quantity"
													id="quantity"
													style="border: 0px; color: #F44336; background-color: transparent;" /></strong></td>
											<td><c:out value="${vendororderitems.totalprice}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
			</form>
		</div>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
            $(document).ready(
                    function () {

                        var cb = '<c:out value="${bg}"/>';
                        $('nav').css('background-color', cb);

                        $(".button-collapse").sideNav();
                        $(".dropdown-button").dropdown();
                        $('select').material_select();

                        //$('select').not('.disabled').material_select();

                        $('html, body').animate({
                            scrollTop: $(document).height()
                        }, 'slow');

                        $("#navlogout").click(function () {
                            $('input[name=navmode]').val('logout');
                            $("#navform").submit();
                        });

                        $("#navhome").click(function () {
                            $('input[name=navmode]').val('home');
                            $("#navform").submit();
                        });

                        $("#getexistingorderbtn").click(function () {

                            $("#existingorderform").attr("action", "/insight/existingorders/getaddtoorder");
                            
                            $("html, body").animate({
                                scrollTop: 0
                            }, "fast");
                            $("#existingorderform").submit();
                        });
                        $("#getexistingorderbtn1").click(function () {
                            
                            //$("#existingorderform").attr("action", "/insight/existingorders/getaddtoorder");
                            $("#btnval").val( "saveexcel");
                            
                            $("html, body").animate({
                                scrollTop: 0
                            }, "fast");
                            $("#existingorderform").submit();
                        });

                        //resetunitonorderbtn                        

                        $("#existingorderform").submit(
                                function () {
                                    
                                    var isFormValid = true;

                                    return isFormValid;
                                });
                    });
        </script>
</body>
</html>
