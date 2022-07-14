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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/${appcss}" />
<title>ReOrderLevel - Quantity To Order</title>
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

/*Added for Checkbox*/
/* Remove default checkbox */
[type="checkbox"]:not (:checked ), [type="checkbox"]:checked {
	position: absolute;
	left: -9999px;
	opacity: 0;
}

[type="checkbox"] {
	/* checkbox aspect */
	
}

[type="checkbox"]+label {
	position: relative;
	padding-left: 35px;
	cursor: pointer;
	display: inline-block;
	height: 25px;
	line-height: 25px;
	font-size: 1rem;
	-webkit-user-select: none;
	/* webkit (safari, chrome) browsers */
	-moz-user-select: none;
	/* mozilla browsers */
	-khtml-user-select: none;
	/* webkit (konqueror) browsers */
	-ms-user-select: none;
	/* IE10+ */
}

[type="checkbox"]+label:before, [type="checkbox"]:not (.filled-in )+label:after
	{
	content: '';
	position: absolute;
	top: 0;
	left: 0;
	width: 18px;
	height: 18px;
	z-index: 0;
	border: 2px solid #5a5a5a;
	border-radius: 1px;
	margin-top: 2px;
	transition: .2s;
}

[type="checkbox"]:not (.filled-in )+label:after {
	border: 0;
	-webkit-transform: scale(0);
	transform: scale(0);
}

[type="checkbox"]:not (:checked ):disabled+label:before {
	border: none;
	background-color: rgba(0, 0, 0, 0.26);
}

[type="checkbox"].tabbed:focus+label:after {
	-webkit-transform: scale(1);
	transform: scale(1);
	border: 0;
	border-radius: 50%;
	box-shadow: 0 0 0 10px rgba(0, 0, 0, 0.1);
	background-color: rgba(0, 0, 0, 0.1);
}

[type="checkbox"]:checked+label:before {
	top: -4px;
	left: -5px;
	width: 12px;
	height: 22px;
	border-top: 2px solid transparent;
	border-left: 2px solid transparent;
	border-right: 2px solid #26a69a;
	border-bottom: 2px solid #26a69a;
	-webkit-transform: rotate(40deg);
	transform: rotate(40deg);
	-webkit-backface-visibility: hidden;
	backface-visibility: hidden;
	-webkit-transform-origin: 100% 100%;
	transform-origin: 100% 100%;
}

[type="checkbox"]:checked:disabled+label:before {
	border-right: 2px solid rgba(0, 0, 0, 0.26);
	border-bottom: 2px solid rgba(0, 0, 0, 0.26);
}

/* Indeterminate checkbox */
[type="checkbox"]:indeterminate+label:before {
	top: -11px;
	left: -12px;
	width: 10px;
	height: 22px;
	border-top: none;
	border-left: none;
	border-right: 2px solid #26a69a;
	border-bottom: none;
	-webkit-transform: rotate(90deg);
	transform: rotate(90deg);
	-webkit-backface-visibility: hidden;
	backface-visibility: hidden;
	-webkit-transform-origin: 100% 100%;
	transform-origin: 100% 100%;
}

[type="checkbox"]:indeterminate:disabled+label:before {
	border-right: 2px solid rgba(0, 0, 0, 0.26);
	background-color: transparent;
}

[type="checkbox"].filled-in+label:after {
	border-radius: 2px;
}

[type="checkbox"].filled-in+label:before, [type="checkbox"].filled-in+label:after
	{
	content: '';
	left: 0;
	position: absolute;
	/* .1s delay is for check animation */
	transition: border .25s, background-color .25s, width .20s .1s, height
		.20s .1s, top .20s .1s, left .20s .1s;
	z-index: 1;
}

[type="checkbox"].filled-in:not (:checked )+label:before {
	width: 0;
	height: 0;
	border: 3px solid transparent;
	left: 6px;
	top: 10px;
	-webkit-transform: rotateZ(37deg);
	transform: rotateZ(37deg);
	-webkit-transform-origin: 20% 40%;
	transform-origin: 100% 100%;
}

[type="checkbox"].filled-in:not (:checked )+label:after {
	height: 20px;
	width: 20px;
	background-color: transparent;
	border: 2px solid #5a5a5a;
	top: 0px;
	z-index: 0;
}

[type="checkbox"].filled-in:checked+label:before {
	top: 0;
	left: 1px;
	width: 8px;
	height: 13px;
	border-top: 2px solid transparent;
	border-left: 2px solid transparent;
	border-right: 2px solid #fff;
	border-bottom: 2px solid #fff;
	-webkit-transform: rotateZ(37deg);
	transform: rotateZ(37deg);
	-webkit-transform-origin: 100% 100%;
	transform-origin: 100% 100%;
}

[type="checkbox"].filled-in:checked+label:after {
	top: 0;
	width: 20px;
	height: 20px;
	border: 2px solid #26a69a;
	background-color: #26a69a;
	z-index: 0;
}

[type="checkbox"].filled-in.tabbed:focus+label:after {
	border-radius: 2px;
	border-color: #5a5a5a;
	background-color: rgba(0, 0, 0, 0.1);
}

[type="checkbox"].filled-in.tabbed:checked:focus+label:after {
	border-radius: 2px;
	background-color: #26a69a;
	border-color: #26a69a;
}
</style>
<!--script type="text/javascript">
            var datefield = document.createElement("input")
            datefield.setAttribute("type", "date")
            if (datefield.type != "date") { //if browser doesn't support input type="date", load files for jQuery UI Date Picker
                document
                        .write('<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />\n')
                document
                        .write('<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"><\/script>\n')
                document
                        .write('<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"><\/script>\n')
            }
        </script-->
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
								<ul class="dropdown-menu"></ul></li>
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
				id="reorderLevelAnalysisform" name="reorderLevelAnalysisform"
				action="/insight/reorderLevel/getQtyToOrder">
				<!--div class="row">
                        <h5 class="light col s12">Sales Analysis</h5>
                    </div-->
				<div class="card  blue-grey lighten-4">
					<div class="card-content">
						<div class="row">
							<h6 class="col s12">
								Current Cycle (
								<fmt:formatDate
									value="${reorderLevelData.currentCycleStartDate}"
									pattern="MM-dd-yyyy" />
								to
								<fmt:formatDate value="${ reorderLevelData.currentCycleEndDate}"
									pattern="MM-dd-yyyy" />
								) : Target Cycle (
								<fmt:formatDate value="${reorderLevelData.targetCycleStartDate}"
									pattern="MM-dd-yyyy" />
								to
								<fmt:formatDate value="${ reorderLevelData.targetCycleEndDate}"
									pattern="MM-dd-yyyy" />
								)
							</h6>
						</div>
						<div class="row">
							<!--div class="input-field col s3">
                                    <label for="c1SQ_Multiplier"><strong>Current Cycle SQ Multiplier</strong> </label>   
                                    <input type="text" id="c1SQ_Multiplier" name="c1SQ_Multiplier" value="$ {reorderLevelData.currentCycleSQMultiplier}">

                                </div>
                                <div class="input-field col s3">
                                    <input type="text" id="c2SQ_Multiplier" name="c2SQ_Multiplier" value="$ {reorderLevelData.targetCycleSQMultiplier}">
                                    <label for="c2SQ_Multiplier"><strong>Target Cycle SQ Multiplier</strong> </label>   
                                </div>
                                <div class="input-field col s3">
                                    <input type="text" id="c1DemandFactor" name="c1DemandFactor" value="$ {reorderLevelData.currentCycleDemandFactor}">
                                    <label for="c1DemandFactor"><strong>Current Cycle Demand Factor</strong> </label>   
                                </div>
                                <div class="input-field col s3">
                                    <input type="text" id="c2DemandFactor" name="c2DemandFactor" value="$ {reorderLevelData.targetCycleDemandFactor}">
                                    <label for="c2DemandFactor"><strong>Target Cycle Demand Factor</strong> </label>   
                                </div-->
							<div class="input-field col s3">
								<input type="text" id="c2SQ_Multiplier" name="c2SQ_Multiplier"
									value="${reorderLevelData.targetCycleSQMultiplier}"> <label
									for="c2SQ_Multiplier"><strong>SQ Multiplier</strong> </label>
							</div>
							<div class="input-field col s3">
								<input type="text" id="c2DemandFactor" name="c2DemandFactor"
									value="${reorderLevelData.targetCycleDemandFactor}"> <label
									for="c2DemandFactor"><strong>Demand Factor</strong> </label>
							</div>
							<div class="input-field col s3">
								<input type="text" id="qtyToOrder" name="qtyToOrder"
									value="${reorderLevelData.qtyToOrder}"> <label
									for="qtyToOrder"><strong>Qty to Order is
										Greater than or equal to</strong> </label>
							</div>
							<div class="col s3">
								<div class="col">
									<label
										style="color: #00b0ff; font-size: 1rem; cursor: text; -webkit-transition: 0.2s ease-out; -moz-transition: 0.2s ease-out; -o-transition: 0.2s ease-out; -ms-transition: 0.2s ease-out; transition: 0.2s ease-out;"><strong>Order
											Type&nbsp;&nbsp;&nbsp;</strong> </label>
								</div>
								<div class="col">
									<p>
										<input name="orderType" class="with-gap" id="orderTypeT"
											type="radio" value="T"
											${reorderLevelData.orderType=='T'?'checked':''} /> <label
											for="orderTypeT"
											style="color: #00b0ff; font-size: 1rem; cursor: text; -webkit-transition: 0.2s ease-out; -moz-transition: 0.2s ease-out; -o-transition: 0.2s ease-out; -ms-transition: 0.2s ease-out; transition: 0.2s ease-out;"><strong>T&nbsp;&nbsp;&nbsp;</strong>
										</label>
									</p>
									<p>
										<input name="orderType" class="with-gap" id="orderTypeE"
											type="radio" value="E"
											${reorderLevelData.orderType=='E'?'checked':''} /> <label
											for="orderTypeE"
											style="color: #00b0ff; font-size: 1rem; cursor: text; -webkit-transition: 0.2s ease-out; -moz-transition: 0.2s ease-out; -o-transition: 0.2s ease-out; -ms-transition: 0.2s ease-out; transition: 0.2s ease-out;"><strong>E</strong>
										</label>
									</p>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col s3">
								<input type="checkbox" id="includCAPA" name="includCAPA"
									value="Y" ${reorderLevelData.CAPAIncluded?'checked':''}>
								<label for="includCAPA"
									style="color: #00b0ff; font-size: 1rem; cursor: text; -webkit-transition: 0.2s ease-out; -moz-transition: 0.2s ease-out; -o-transition: 0.2s ease-out; -ms-transition: 0.2s ease-out; transition: 0.2s ease-out;"><strong>Include
										CAPA</strong> </label>
							</div>
							<div class="col s3">
								<span>
									<button
										class="btn-floating btn-large waves-effect waves-light blue"
										type="submit" id="getQtyToOrderBtn" name="getQtyToOrderBtn"
										value="getQtyToOrderBtn">
										<i class="mdi-action-assignment-turned-in right"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</div>
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

                        $('.collapsible').collapsible({
                            accordion: true
                                    // A setting that changes the collapsible behavior to expandable instead of the default accordion style
                        });

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
