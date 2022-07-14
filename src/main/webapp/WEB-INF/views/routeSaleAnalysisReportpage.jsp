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
<title>Route Sale Analysis</title>
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
				id="routeSaleAnalysisForm"
				action="/insight/report/routeSaleAnalysis">
				<!--div class="row">
                            <h5 class="light col s12">Sale Analysis By Route</h5>
                    </div-->
				<div class="card  blue-grey lighten-4">
					<div class="card-content">
						<div class="row">
							<div class="col s3">
								<label><strong>Select Route</strong></label> <select
									id="routselected" name="routselected" class="browser-default">
									<c:forEach var="route" items="${routelistdd}">
										<option value="${route}"
											<c:if test="${routselected eq route}">selected="selected"</c:if>>${route}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col s1">
								<button
									class="btn-floating btn-large waves-effect waves-light  blue"
									type="submit" id="getexistingorderbtn"
									name="getexistingorderbtn" value="getexistingorder">
									<i class="mdi-action-search right"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${not empty requestScope.routeSale}">
					<div class="row">
						<div class="col s12">
							<c:set var="yearsList" value="${routeSale.yearsList}" />
							<c:set var="dataList" value="${routeSale.dataList}" />
							<c:set var="summary" value="${routeSale.summary}" />
							<table class="bordered striped">
								<thead>
									<tr>
										<th style="padding: 4px 0px;"></th>
										<th style="padding: 4px 0px;"></th>
										<c:forEach items="${yearsList}" var="yr">
											<th style="padding: 4px 0px;"><c:out value="${yr}" /></th>
										</c:forEach>
										<!--th style="padding: 4px 0px;">Avg</th-->
									</tr>
								</thead>
								<tbody>
									<!--c:forEach var="entry" items="$ {dataMap}"-->
									<c:forEach items="${dataList}" var="monthlySaleData">
										<tr style="border-style: solid; border-color: black;">
											<!-- style="border-style: solid; border-color: black; border-bottom: none;" -->
											<td rowspan="4"
												style="border-style: solid; border-color: black; padding: 4px 0px 0px 4px; font-weight: bold;">${monthlySaleData.month}</td>
											<td style="padding: 4px 0px 0px 4px; font-weight: bold;">Sale</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y1Sale}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y2Sale}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y3Sale}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y4Sale}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y5Sale}</td>
										</tr>
										<tr style="border-right-style: solid; border-color: black;">
											<td style="padding: 4px 0px 0px 4px; font-weight: bold;">Tax</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y1Tax}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y2Tax}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y3Tax}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y4Tax}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y5Tax}</td>
										</tr>
										<tr style="border-right-style: solid; border-color: black;">
											<td style="padding: 4px 0px 0px 4px; font-weight: bold;">Discount</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y1Discount}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y2Discount}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y3Discount}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y4Discount}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y5Discount}</td>
										</tr>
										<!--tr>
                                                <td style="padding: 4px 0px 0px 4px;font-weight: bold;">Expenses</td>
                                                <td style="padding: 4px 0px;">$ {monthlySaleData.y1Expenses}</td>
                                                <td style="padding: 4px 0px;">$ {monthlySaleData.y2Expenses}</td>
                                                <td style="padding: 4px 0px;">$ {monthlySaleData.y3Expenses}</td>
                                                <td style="padding: 4px 0px;">$ {monthlySaleData.y4Expenses}</td>
                                                <td style="padding: 4px 0px;">$ {monthlySaleData.y5Expenses}</td>
                                            </tr-->
										<tr style="border-right-style: solid; border-color: black;">
											<td style="padding: 4px 0px 0px 4px; font-weight: bold;">Net</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y1Net}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y2Net}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y3Net}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y4Net}</td>
											<td style="padding: 4px 0px;">${monthlySaleData.y5Net}</td>
										</tr>
									</c:forEach>
									<tr style="border-style: solid; border-color: black;">
										<!-- style="border-style: solid; border-color: black; border-bottom: none;" -->
										<td rowspan="4"
											style="border-style: solid; border-color: black; padding: 4px 0px 0px 4px; font-weight: bold;">Total</td>
										<td style="padding: 4px 0px 0px 4px; font-weight: bold;">Sale</td>
										<td style="padding: 4px 0px;">${summary.y1Sale}</td>
										<td style="padding: 4px 0px;">${summary.y2Sale}</td>
										<td style="padding: 4px 0px;">${summary.y3Sale}</td>
										<td style="padding: 4px 0px;">${summary.y4Sale}</td>
										<td style="padding: 4px 0px;">${summary.y5Sale}</td>
									</tr>
									<tr style="border-right-style: solid; border-color: black;">
										<td style="padding: 4px 0px 0px 4px; font-weight: bold;">Tax</td>
										<td style="padding: 4px 0px;">${summary.y1Tax}</td>
										<td style="padding: 4px 0px;">${summary.y2Tax}</td>
										<td style="padding: 4px 0px;">${summary.y3Tax}</td>
										<td style="padding: 4px 0px;">${summary.y4Tax}</td>
										<td style="padding: 4px 0px;">${summary.y5Tax}</td>
									</tr>
									<tr style="border-right-style: solid; border-color: black;">
										<td style="padding: 4px 0px 0px 4px; font-weight: bold;">Discount</td>
										<td style="padding: 4px 0px;">${summary.y1Discount}</td>
										<td style="padding: 4px 0px;">${summary.y2Discount}</td>
										<td style="padding: 4px 0px;">${summary.y3Discount}</td>
										<td style="padding: 4px 0px;">${summary.y4Discount}</td>
										<td style="padding: 4px 0px;">${summary.y5Discount}</td>
									</tr>
									<!--tr>
                                                <td style="padding: 4px 0px;font-weight: bold;">Expenses</td>
                                                <td style="padding: 4px 0px;">$ {summary.y1Expenses}</td>
                                                <td style="padding: 4px 0px;">$ {summary.y2Expenses}</td>
                                                <td style="padding: 4px 0px;">$ {summary.y3Expenses}</td>
                                                <td style="padding: 4px 0px;">$ {summary.y4Expenses}</td>
                                                <td style="padding: 4px 0px;">$ {summary.y5Expenses}</td>
                                            </tr-->
									<tr style="border-right-style: solid; border-color: black;">
										<td
											style="padding: 4px 0px 0px 4px; font-weight: bold; border-bottom-style: solid; border-color: black;">Net</td>
										<td
											style="padding: 4px 0px; border-bottom-style: solid; border-color: black;">${summary.y1Net}</td>
										<td
											style="padding: 4px 0px; border-bottom-style: solid; border-color: black;">${summary.y2Net}</td>
										<td
											style="padding: 4px 0px; border-bottom-style: solid; border-color: black;">${summary.y3Net}</td>
										<td
											style="padding: 4px 0px; border-bottom-style: solid; border-color: black;">${summary.y4Net}</td>
										<td
											style="padding: 4px 0px; border-bottom-style: solid; border-color: black;">${summary.y5Net}</td>
									</tr>
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

});
        </script>
</body>
</html>
