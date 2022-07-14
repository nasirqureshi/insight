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
				id="partlinkoemsearchform" action="/insight/parts/partlinkoemsearch">
				<div class="row">
					<input type="hidden" id="partlinkoemsearchmode"
						name="partlinkoemsearchmode" value="">
				</div>
				<div class="row">
					<h5 class="light col s12">PartLink Search</h5>
				</div>
				<div class="card  blue-grey lighten-4">
					<div class="card-content">
						<div class="row">
							<div class="input-field col s2">
								<input name="partlinksearchtxt" id="partlinksearchtxt" value=""
									type="text" required> <label for="partlinksearchtxt"><strong>Search
										PartLink #</strong> </label>
							</div>
							<div class="col s2">
								<button
									class="btn-floating btn-large waves-effect waves-light blue"
									type="button" id="partlinksearchbtn" name="partlinksearchbtn"
									value="partlinksearch">
									<i class="mdi-action-view-week right"></i>
								</button>
							</div>
							<c:if test="${not empty partslinklist}">
								<div class="col s3">
									<button
										class="btn-floating btn-large waves-effect waves-light blue"
										type="button" id="getnewpartnobtn" name="getnewpartnobtn"
										value="getnewpartno">
										<i class="mdi-image-loupe  right">generate new partnumber</i>
									</button>
								</div>
							</c:if>
						</div>
					</div>
				</div>
				<div class="divider"></div>
				<c:if test="${not empty partslinklist}">
					<div class="card	cyan lighten-5">
						<div class="card-content">
							<div class="row">
								<div class="col s12">
									<div class="row">
										<p class="caption">Parts Link</p>
									</div>
									<table class="responsive-table bordered">
										<thead>
											<tr>
												<th><strong>Partslink #</strong></th>
												<th><strong>Make</strong></th>
												<th><strong>Model</strong></th>
												<th><strong>Yearfrom</strong></th>
												<th><strong>Yearto</strong></th>
												<th><strong>Desc</strong></th>
												<th><strong>Desc2</strong></th>
												<th><strong>OEM</strong></th>
												<th><strong>Notes</strong></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="pl" items="${partslinklist}">
												<tr>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${pl.plink}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${pl.make}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${pl.model}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${pl.y1}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${pl.y2}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${pl.pname}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${pl.mvariables}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${pl.oem}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${pl.notes}" /></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<div class="divider"></div>
				<c:if test="${not empty bvpartslinklist}">
					<div class="card	teal lighten-5">
						<div class="card-content">
							<div class="row">
								<div class="col s12">
									<div class="row">
										<p class="caption">Parts Info</p>
									</div>
									<table class="responsive-table bordered">
										<thead>
											<tr>
												<th><strong>Part #</strong></th>
												<th><strong>Inter #</strong></th>
												<th><strong>PartLink</strong></th>
												<th><strong>Make</strong></th>
												<th><strong>Model</strong></th>
												<th><strong>Desc</strong></th>
												<th><strong>Yearfrom</strong></th>
												<th><strong>Yearto</strong></th>
												<th><strong>St</strong></th>
												<th><strong>Or</strong></th>
												<th><strong>Re</strong></th>
												<th><strong>Location</strong></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="bv" items="${bvpartslinklist}">
												<tr>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.partno}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.interchangeno}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.plink}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.make}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.model}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.partdescription}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.yearfrom}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.yearto}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.unitsinstock}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.unitsonorder}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.reorderlevel}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${bv.location}" /></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<div class="divider"></div>
			</form>
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
			

			$("#navlogout").click(function() {
				$('input[name=navmode]').val('logout');
				$("#navform").submit();
			});

			$("#navhome").click(function() {
				$('input[name=navmode]').val('home');
				$("#navform").submit();
			});
			
			$("#partlinksearchtxt").keypress(function(event) {
				if (event.which == 13) {
					event.preventDefault();
					$("#partlinksearchbtn").click();
				}
			});
			
			$("#partlinksearchbtn").click(function() {

				$('input[name=partlinkoemsearchmode]').val('partlinksearch');
				$("#partlinkoemsearchform").submit();

			});
			
			$("#getnewpartnobtn").click(function() {

				$('input[name=partlinkoemsearchmode]').val('generatenewnumber');
				$("#partlinkoemsearchform").submit();

			});

			$("#partlinkoemsearchform").submit(function() {
				var isFormValid = true;
				if ($('#partlinkoemsearchmode').val() == 'partlinksearch') {
					if ($('#partlinksearchtxt').val() == '') {
						alert('container no cannot be blank');
						isFormValid = false;
					}

				}
				return isFormValid;
			});

		});
	</script>
</body>
</html>
