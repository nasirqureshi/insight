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
<title>Formate Label</title>
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
					modelAttribute="labelfile" id="formatelabelform"
					action="/insight/orders/formatelabel" enctype="multipart/form-data">
					<div class="row">
						<h5 class="light col s12">Upload Label</h5>
					</div>
					<div class="row">
						<div class="col s12">
							<div class="card grey">
								<div class="card-content white-text">
									<span class="card-title">Info</span>
									<p>${message}</p>
								</div>
							</div>
						</div>
					</div>
					<div class="card  blue-grey lighten-4">
						<div class="card-content">
							<div class="row">
								<div class="col s3">
									<input name="files[0]" type="file" class="file" />
								</div>
								<div class="col s2">
									<button
										class="btn-floating btn-large waves-effect waves-light red"
										type="button" id="labelUploadBtn" name="labelUploadBtn"
										value="formatelabel">
										<i class="mdi-file-file-upload right"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
				</form>
				<form class="col s12" role="form" method="post"
					id="downloadReportForm"
					action="/insight/orders/downloadLabelReport">
					<c:if test="${not empty downloadFileName}">
						<div class="card  blue-grey lighten-4">
							<div class="card-content">
								<div class="row">
									<input name="downloadFileName" type="hidden"
										value="${downloadFileName}" />
									<div class="col s3">Download Report</div>
								</div>
								<div class="row">
									<div class="col s2">
										<button
											class="btn-floating btn-large waves-effect waves-light red"
											type="button" id="labelDownloadBtn" name="labelDownloadBtn"
											value="formatelabel">
											<i class="mdi-file-file-download right"></i>
										</button>
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

                $("#labelUploadBtn").click(function() {
                    $("#formatelabelform").submit();
                });
                $("#labelDownloadBtn").click(function() {
                    $("#downloadReportForm").submit();
                });

                $("#formatelabelform").submit(function() {
                    var isFormValid = true;
                    return isFormValid;
                });
            });
        </script>
</body>
</html>