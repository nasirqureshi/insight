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
<script type="text/javascript">
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
</script>
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
				<input style="font-weight: bold;" type="hidden" id="navmode"
					name="navmode" value="logout">
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
		<form class="col s12" role="form" method="post"
			id="partmaintenanceform" name="partmaintenanceform"
			action="/insight/parts/partmaintenancesearch">
			<div class="row">
				<h5 class="light col s12">Parts Maintenance</h5>
			</div>
			<div class="row">
				<div class="input-field col s2">
					<input style="font-weight: bold;" id="searchpartno"
						name="searchpartno" value="${searchpartno}" type="text"><label
						for="searchpartno"><strong>Search Part #</strong></label>
				</div>
				<div class="col s2">
					<button
						class="btn-floating btn-large waves-effect waves-light  blue"
						type="submit" id="partmaintenancesearchBtn"
						name="partmaintenancesearchBtn" value="partmaintenancesearch">
						<i class="mdi-action-search right"></i>
					</button>
				</div>
				<div class="col s2">
					<input type="hidden" id="partmaintenancemode"
						name="partmaintenancemode" value="">
				</div>
			</div>
			<div class="card light-blue lighten-5">
				<div class="card-content">
					<div class="row">
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="partno" name="partno"
								value="${parts.partno}" type="text"><label for="partno"><strong>Part
									#</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="interchangeno"
								name="interchangeno" value="${parts.interchangeno}" type="text"><label
								for="interchangeno"><strong>Inter #</strong></label>
						</div>
						<c:choose>
							<c:when test="${partmaintenancemode eq 'create'}">
								<div class="col s2">
									<button
										class="btn-floating btn-large waves-effect waves-light  blue"
										type="submit" id="partmaintenancecreateBtn"
										name="partmaintenancecreateBtn" value="partmaintenancecreate">
										<i class="mdi-content-add-circle-outline right"></i>
									</button>
								</div>
							</c:when>
							<c:otherwise>
								<div class="col s2">
									<button
										class="btn-floating btn-large waves-effect waves-light  blue"
										type="submit" id="partmaintenanceupdateBtn"
										name="partmaintenanceupdateBtn" value="partmaintenanceupdate">
										<i class="mdi-content-save right"></i>
									</button>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="divider"></div>
					<div class="row"></div>
					<div class="row">
						<div class="col s3">
							<label><strong>MakeModel</strong></label> <select
								id="makemodelcodeselected" name="makemodelcodeselected"
								class="browser-default">
								<option value="${makemodelcodeselected}" selected>${makemodelcodeselected}</option>
								<c:forEach var="makemodel" items="${makemodellistdd}">
									<option value="${makemodel.key}">${makemodel.key}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col s3">
							<label><strong>Subcategory</strong></label> <select
								id="subcategoryselected" name="subcategoryselected"
								class="browser-default">
								<option value="${subcategoryselected}" selected>${subcategoryselected}</option>
								<c:forEach var="subcategory" items="${subcategorylistdd}">
									<option value="${subcategory.key}">${subcategory.key}</option>
								</c:forEach>
							</select>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="year" name="year"
								value="${parts.year}" type="text"><label for="year"><strong>Year</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="yearfrom" name="yearfrom"
								value="${parts.yearfrom}" type="text"><label
								for="yearfrom"><strong>YearFrom</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="yearto" name="yearto"
								value="${parts.yearto}" type="text"><label for="yearto"><strong>YearTo</strong></label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<textarea id="partdescription" name="partdescription"
								class="materialize-textarea" maxlength="250">${parts.partdescription}</textarea>
							<label for="partdescription"><strong>Description</strong></label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="actualprice"
								name="actualprice" value="${parts.actualprice}" type="text"><label
								for="actualprice"><strong>Buy Price</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="costprice" name="costprice"
								value="${parts.costprice}" type="text"><label
								for="costprice"><strong>Sell Price</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="listprice" name="listprice"
								value="${parts.listprice}" type="text"><label
								for="listprice"><strong>List Price</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="wholesaleprice"
								name="wholesaleprice" value="${parts.wholesaleprice}"
								type="text"><label for="wholesaleprice"><strong>Wholesale</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="percent" name="percent"
								value="${percent}" type="text"> <label for="percent"><strong>Percent</strong></label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="unitsinstock"
								name="unitsinstock" value="${parts.unitsinstock}" type="text"><label
								for="unitsinstock"><strong>Stock</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="unitsonorder"
								name="unitsonorder" value="${parts.unitsonorder}" type="text"><label
								for="unitsonorder"><strong>Order</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="reorderlevel"
								name="reorderlevel" value="${parts.reorderlevel}" type="text"><label
								for="reorderlevel"><strong>Reorder</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="safetyquantity"
								name="safetyquantity" value="${parts.safetyquantity}"
								type="text"><label for="safetyquantity"><strong>Safety</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="sortcode" name="sortcode"
								value="${parts.sortcode}" type="text"><label
								for="sortcode"><strong>SortCode</strong></label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="keystonenumber"
								name="keystonenumber" value="${parts.keystonenumber}"
								type="text"><label for="keystonenumber"><strong>PartsLink</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="oemnumber" name="oemnumber"
								value="${parts.oemnumber}" type="text"><label
								for="oemnumber"><strong>OEM</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="dpinumber" name="dpinumber"
								value="${parts.dpinumber}" type="text"><label
								for="dpinumber"><strong>DPI</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="location" name="location"
								value="${parts.location}" type="text"><label
								for="location"><strong>Location</strong></label>
						</div>
						<div class="input-field col s2">
							<input style="font-weight: bold;" id="capa" name="capa"
								value="${parts.capa}" type="text"><label for="capa"><strong>Capa</strong></label>
						</div>
						<div class="col s2">
							<label><strong>OrderType</strong></label> <select
								id="ordertypeselected" name="ordertypeselected"
								class="browser-default">
								<option style="font-weight: bold;" value="${ordertypeselected}"
									selected>${ordertypeselected}</option>
								<c:forEach var="ordertype" items="${ordertypelistdd}">
									<option value="${ordertype}">${ordertype}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<script>
		$(document)
				.ready(
						function() {

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
							
							
							
							$("#actualprice, #costprice").change(function() { // input on change
								ap = $("#actualprice").val();
							    cp = $("#costprice").val();
							    
							    if( (ap <= 0.00 ) || (cp <= 0.00) ){
							    	 $('input[name=percent]').val('0.00');
							    }else{
							    	 df = ( (cp - ap) * 100 / cp );
							    	 lp = (cp / 0.7 );
							    	 	$('input[name=listprice]').val(lp.toFixed(2) || '').focus();
									    $('input[name=percent]').val(df.toFixed(2) || '').focus();
							    }
							    
							    
							   
							   // $("#percent").html(df.toFixed(2));
								//alert(parseFloat($("#actualprice").val(), 2) );
								//var result =( ( parseFloat($("#costprice").val() - $("#actualprice").val() ) * 100 ) / parseFloat($("#costprice").val(); 
							   // var result = parseFloat(parseFloat( $("#actualprice").val(), 2) * 100) / parseFloat($("#costprice").val(), 2).toFixed(2);
							   // $('#percent').val(result||''); //shows value in "#rate"
							  })
							
							
							
							
							$("#partmaintenancecreateBtn")
							.click(
									function() {
										$("#partmaintenanceform")
												.attr("action",
														"/insight/parts/partmaintenancecreate");
										$("#partmaintenanceform")
												.submit();
									});
							
							$("#partmaintenanceupdateBtn")
							.click(
									function() {
										$("#partmaintenanceform")
												.attr("action",
														"/insight/parts/partmaintenanceupdate");
										$("#partmaintenanceform")
												.submit();
									});

							$("#partmaintenancesearchBtn")
									.click(
											function() {
												$("#partmaintenanceform")
														.attr("action",
																"/insight/parts/partmaintenancesearch");
												$("#partmaintenanceform")
														.submit();
											});

							$("#partmaintenanceform").submit(function() {
								var isFormValid = true;
								return isFormValid;
							});
						});
	</script>
</body>
</html>
