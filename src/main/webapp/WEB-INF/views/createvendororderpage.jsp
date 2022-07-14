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
<title>Create Vendor Order</title>
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

/* Added for Radio*/
/* Radio Buttons
   ========================================================================== */
[type="radio"]:not (:checked ), [type="radio"]:checked {
	position: absolute;
	left: -9999px;
	opacity: 0;
}

[type="radio"]:not (:checked )+label, [type="radio"]:checked+label {
	position: relative;
	padding-left: 35px;
	cursor: pointer;
	display: inline-block;
	height: 25px;
	line-height: 25px;
	font-size: 1rem;
	transition: .28s ease;
	/* webkit (konqueror) browsers */
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

[type="radio"]+label:before, [type="radio"]+label:after {
	content: '';
	position: absolute;
	left: 0;
	top: 0;
	margin: 4px;
	width: 16px;
	height: 16px;
	z-index: 0;
	transition: .28s ease;
}

/* Unchecked styles */
[type="radio"]:not (:checked )+label:before, [type="radio"]:not (:checked
	 )+label:after, [type="radio"]:checked+label:before, [type="radio"]:checked+label:after,
	[type="radio"].with-gap:checked+label:before, [type="radio"].with-gap:checked+label:after
	{
	border-radius: 50%;
}

[type="radio"]:not (:checked )+label:before, [type="radio"]:not (:checked
	 )+label:after {
	border: 2px solid #5a5a5a;
}

[type="radio"]:not (:checked )+label:after {
	-webkit-transform: scale(0);
	transform: scale(0);
}

/* Checked styles */
[type="radio"]:checked+label:before {
	border: 2px solid transparent;
}

[type="radio"]:checked+label:after, [type="radio"].with-gap:checked+label:before,
	[type="radio"].with-gap:checked+label:after {
	border: 2px solid #26a69a;
}

[type="radio"]:checked+label:after, [type="radio"].with-gap:checked+label:after
	{
	background-color: #26a69a;
}

[type="radio"]:checked+label:after {
	-webkit-transform: scale(1.02);
	transform: scale(1.02);
}

/* Radio With gap */
[type="radio"].with-gap:checked+label:after {
	-webkit-transform: scale(0.5);
	transform: scale(0.5);
}

/* Focused styles */
[type="radio"].tabbed:focus+label:before {
	box-shadow: 0 0 0 10px rgba(0, 0, 0, 0.1);
}

/* Disabled Radio With gap */
[type="radio"].with-gap:disabled:checked+label:before {
	border: 2px solid rgba(0, 0, 0, 0.26);
}

[type="radio"].with-gap:disabled:checked+label:after {
	border: none;
	background-color: rgba(0, 0, 0, 0.26);
}

/* Disabled style */
[type="radio"]:disabled:not (:checked )+label:before, [type="radio"]:disabled:checked+label:before
	{
	background-color: transparent;
	border-color: rgba(0, 0, 0, 0.26);
}

[type="radio"]:disabled+label {
	color: rgba(0, 0, 0, 0.26);
}

[type="radio"]:disabled:not (:checked )+label:before {
	border-color: rgba(0, 0, 0, 0.26);
}

[type="radio"]:disabled:checked+label:after {
	background-color: rgba(0, 0, 0, 0.26);
	border-color: #BDBDBD;
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
					modelAttribute="vendorOrder" id="createvendororderform"
					action="/insight/orders/createvendororder"
					enctype="multipart/form-data">
					<div class="row">
						<h5 class="light col s12">Create Order</h5>
					</div>
					<c:if test="${not empty messages and messages!=''}">
						<div class="row">
							<div class="col s12">
								<div class="card grey">
									<div class="card-content white-text">
										<div class="row">
											<h6 class="light col s12">Info</h6>
										</div>
										<!-- c:forEach items="$ {messages}" var="msg"-->
										<p>
											<c:out value="${messages}" />
										</p>
										<!-- /c:forEach-->
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<div class="card  blue-grey lighten-4">
						<div class="card-content">
							<div class="row">
								<div class="col s4">
									<label style="color: #00b0ff"><strong>Location</strong></label>
									<select name="location" id="location" class="browser-default">
										<c:forEach items="${locationList}" var="obj">
											<option value="${obj.key}" title="${obj.value}"
												<c:if test="${not empty vendorOrder.location and vendorOrder.location == obj.key}"> selected="selected"</c:if>>${obj.value}</option>
										</c:forEach>
									</select>
								</div>
								<div class="input-field col s4">
									<input style="font-weight: bold;" id="orderNo" name="orderNo"
										value="${vendorOrder.orderNo}" type="text"> <label
										for="orderNo"><strong>Order No</strong></label>
								</div>
								<div class="input-field col s4">
									<input style="font-weight: bold;" id="supplierId"
										name="supplierId" value="${vendorOrder.supplierId}"
										type="text"> <label for="supplierId"><strong>Supplier
											Id</strong></label>
								</div>
							</div>
							<div class="row">
								<div class="col s4">
									<div class="col">
										<label
											style="color: #00b0ff; font-size: 1rem; cursor: text; -webkit-transition: 0.2s ease-out; -moz-transition: 0.2s ease-out; -o-transition: 0.2s ease-out; -ms-transition: 0.2s ease-out; transition: 0.2s ease-out;"><strong>By
												Whose No to Create Order&nbsp;&nbsp;&nbsp;</strong> </label>
									</div>
									<div class="col">
										<p>
											<input name="byWhosNoToCreateOrder" class="with-gap"
												id="byWhosNoToCreateOrder_O" type="radio" value="O"
												${vendorOrder.byWhosNoToCreateOrder=='O'?'checked':''} /> <label
												for="byWhosNoToCreateOrder_O"
												style="color: #00b0ff; font-size: 1rem; cursor: text; -webkit-transition: 0.2s ease-out; -moz-transition: 0.2s ease-out; -o-transition: 0.2s ease-out; -ms-transition: 0.2s ease-out; transition: 0.2s ease-out;"><strong>By
													Our Nos&nbsp;&nbsp;&nbsp;</strong> </label>
										</p>
										<p>
											<input name="byWhosNoToCreateOrder" class="with-gap"
												id="byWhosNoToCreateOrder_V" type="radio" value="V"
												${vendorOrder.byWhosNoToCreateOrder=='V'?'checked':''} /> <label
												for="byWhosNoToCreateOrder_V"
												style="color: #00b0ff; font-size: 1rem; cursor: text; -webkit-transition: 0.2s ease-out; -moz-transition: 0.2s ease-out; -o-transition: 0.2s ease-out; -ms-transition: 0.2s ease-out; transition: 0.2s ease-out;"><strong>Vendor
													Nos</strong> </label>
										</p>
									</div>
								</div>
								<div class="col s4">
									<div class="col">
										<label
											style="color: #00b0ff; font-size: 1rem; cursor: text; -webkit-transition: 0.2s ease-out; -moz-transition: 0.2s ease-out; -o-transition: 0.2s ease-out; -ms-transition: 0.2s ease-out; transition: 0.2s ease-out;"><strong>Does
												Input File has Qty&nbsp;&nbsp;&nbsp;</strong> </label>
									</div>
									<div class="col">
										<p>
											<input name="doesInputFileHasQty" class="with-gap"
												id="doesInputFileHasQty_Y" type="radio" value="Y"
												${vendorOrder.doesInputFileHasQty=='Y'?'checked':''} /> <label
												for="doesInputFileHasQty_Y"
												style="color: #00b0ff; font-size: 1rem; cursor: text; -webkit-transition: 0.2s ease-out; -moz-transition: 0.2s ease-out; -o-transition: 0.2s ease-out; -ms-transition: 0.2s ease-out; transition: 0.2s ease-out;"><strong>Yes&nbsp;&nbsp;&nbsp;</strong>
											</label>
										</p>
										<p>
											<input name="doesInputFileHasQty" class="with-gap"
												id="doesInputFileHasQty_N" type="radio" value="N"
												${vendorOrder.doesInputFileHasQty=='N'?'checked':''} /> <label
												for="doesInputFileHasQty_N"
												style="color: #00b0ff; font-size: 1rem; cursor: text; -webkit-transition: 0.2s ease-out; -moz-transition: 0.2s ease-out; -o-transition: 0.2s ease-out; -ms-transition: 0.2s ease-out; transition: 0.2s ease-out;"><strong>No</strong>
											</label>
										</p>
									</div>
								</div>
								<div class="file-field input-field col s4">
									<div class="btn waves-light blue">
										<span>Select Input File</span> <input name="file" type="file"
											class="file" id="file" />
									</div>
									<div class="file-path-wrapper">
										<input class="file-path validate" type="text">
									</div>
								</div>
							</div>
							<div class="row"></div>
							<div class="row center-align">
								<!--div class="col s4">
                                        <label style="color: #00b0ff"><strong>Select Input File</strong></label> 
                                        <input name="file" type="file" class="file" id="file"/>
                                    </div-->
								<div class="col s12">
									<button class="btn waves-effect waves-light" type="button"
										id="submitBtn" name="submitBtn" value="submitBtn">&nbsp;Submit</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
            $(document).ready(function () {

                var cb = '<c:out value="${bg}"/>';
                $('nav').css('background-color', cb);
                $(".button-collapse").sideNav();
                $(".dropdown-button").dropdown();
                $('select').material_select();

                $("#navlogout").click(function () {
                    $('input[name=navmode]').val('logout');
                    $("#navform").submit();
                });

                $("#navhome").click(function () {
                    $('input[name=navmode]').val('home');
                    $("#navform").submit();
                });

                $("#submitBtn").click(function () {
                    $("#createvendororderform").submit();
                });


                $("#createvendororderform").submit(function () {
                    var isFormValid = true;
                    return isFormValid;
                });
            });
        </script>
</body>
</html>