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
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/jquery/jquery.jqplot.css" />" />
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

.activeTab {
	/*background-color: darkgray;*/
	
}

a.active {
	background-color: darkgray;
}

.tabs .tab a {
	color: black !important;
	font-size: large;
}

table.borderedAll>tbody>tr {
	/*border-bottom: 1px solid #d0d0d0;*/
	
}

table.borderedAll>tbody>tr>td {
	border: 1px solid black;
	padding: 2px 2px;
	font-weight: bold;
	font-size: 14px;
}

table.borderedAll>thead>tr>th {
	border: 1px solid black;
}

.chCol {
	background-color: #dfeaf1;
}

.amCol {
	background-color: #e5f7f5;
}

.grCol {
	background-color: #f5f5fd;
}

.nyCol {
	background-color: rgba(0, 0, 255, .25);
}

.greyCol {
	background-color: #fffde8;
}

.row {
	margin-bottom: auto;
}

html {
	line-height: normal;
}

.card .card-content {
	padding-top: 2px;
}

.card-panel {
	padding: 0.5px;
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
			<div class="col s12">
				<ul class="tabs" style="background-color: lightgray; color: black;">
					<li class="tab col s4"><a
						<c:if test="${tab eq 'salesdetails'}">class="active"</c:if>
						href="#" id="saleDetailsBtn">Sale Details</a></li>
					<li class="tab col s4"><a
						<c:if test="${tab eq 'regionsale'}">class="active"</c:if> href="#"
						id="regionSaleBtn">Region Sale</a></li>
					<li class="tab col s4"><a
						<c:if test="${tab eq 'regionreturn'}">class="active"</c:if>
						href="#" id="regionReturnBtn">Region Return</a></li>
				</ul>
			</div>
			<!--div id="saleDetails" class="col s12">
                        <div class="row"></div>
                        <div class="divider"></div>
                        sale Details
                    </div>
                    <div id="regionSale" class="col s12">
                        <div class="row"></div>
                        <div class="divider"></div>
                        region Sale
                    </div>
                    <div id="regionReturn" class="col s12">
                        <div class="row"></div>
                        <div class="divider"></div>
                        region Return
                    </div-->
		</div>
		<div class="row">
			<form class="col s12" role="form" method="post" id="dateForm"
				name="dateForm">
				<div class="row">
					<div class="col s12">
						<div class="col s7">&nbsp;</div>
						<div class="col s2 input-field">
							<input type="date" value="${startDate}" name="fromDate"
								id="fromDate" /> <label for="fromDate" class="active"><strong>From
									Date</strong> </label>
						</div>
						<div class="col s2 input-field">
							<input type="date" value="${endDate}" name="toDate" id="toDate" />
							<label for="toDate" class="active"><strong>To
									Date</strong></label>
						</div>
						<div class="col s1">
							<span>
								<button
									class="btn-floating btn-large waves-effect waves-light blue"
									type="button" id="dateSubmitBtn" name="dateSubmitBtn"
									onclick="submitTabClick('${tab}')">
									<i class="mdi-action-assignment-turned-in right"></i>
								</button>
							</span>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="divider"></div>
		<c:choose>
			<c:when test="${tab eq 'salesdetails'}">
				<jsp:include page="salesreportsalesdetailspage.jsp"></jsp:include>
				<script>
                            setTimeout(function() {
                                //$("#saleDetailsBtn").click(); 
                                location.reload(true);
                            }, 60000);
                        </script>
			</c:when>
			<c:when test="${tab eq 'regionsale'}">
				<jsp:include page="salesreportregionsalepage.jsp"></jsp:include>
				<script>
                            setTimeout(function() {
                                //$("#regionSaleBtn").click(); 
                                location.reload(true);
                            }, 60000);
                        </script>
			</c:when>
			<c:when test="${tab eq 'regionreturn'}">
				<jsp:include page="salesreportregionreturnpage.jsp"></jsp:include>
				<script>
                            setTimeout(function() {
                                //$("#regionReturnBtn").click();
                                location.reload(true);
                            }, 60000);
                        </script>
			</c:when>
		</c:choose>
	</div>
	<div style="display: none;">
		<form method="post" id="tabClickForm" action="">
			<input type="date" value="" name="startDate" id="startDate" /> <input
				type="date" value="" name="endDate" id="endDate" />
		</form>
	</div>
	<script src="<c:url value="/resources/jquery/jquery-2.1.3.js" />"></script>
	<script src="<c:url value="/resources/jquery/materialize.js" />"></script>
	<!--script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script-->
	<script type="text/javascript"
		src="<c:url value="/resources/jquery/jquery.jqplot.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/jquery/jqplot.pieRenderer.js" />"></script>
	<script>
                            function submitTabClick(tab) {
                                if (tab === 'salesdetails') {
                                    $("#tabClickForm").attr('action', '/insight/report/saleReportSaleDetails');
                                } else if (tab === 'regionsale') {
                                    $("#tabClickForm").attr('action', '/insight/report/saleReportRegionSale');
                                } else if (tab === 'regionreturn') {
                                    $("#tabClickForm").attr('action', '/insight/report/saleReportRegionReturn');
                                }
                                $("#startDate").val($("#fromDate").val());
                                $("#endDate").val($("#toDate").val());
                                $("#tabClickForm").submit();
                            }
                            $(document).ready(
                                    function() {
                                        //$('ul.tabs').tabs();
                                        var cb = '<c:out value="${bg}"/>';
                                        $('nav').css('background-color', cb);

                                        $(".button-collapse").sideNav();
                                        $(".dropdown-button").dropdown();
                                        $('select').material_select();

                                        $("#saleDetailsBtn").click(function() {
                                            submitTabClick('salesdetails');

                                        });
                                        $("#regionSaleBtn").click(function() {
                                            submitTabClick('regionsale');
                                        });
                                        $("#regionReturnBtn").click(function() {
                                            submitTabClick('regionreturn');
                                        });

                                        $("#navlogout").click(function() {
                                            $('input[name=navmode]').val('logout');
                                            $("#navform").submit();
                                        });

                                        $("#navhome").click(function() {
                                            $('input[name=navmode]').val('home');
                                            $("#navform").submit();
                                        });

                                    });
        </script>
	<script>
            $(document).ready(function() {
                var data = [];
            <c:if test="${not empty salesDetails and not empty salesDetails.saleReport3}">
                <c:set var="report_1" value="${salesDetails.saleReport3}"/>
                <c:forEach begin="0" end="3" step="1" varStatus="loop">
                    <c:set var="report3"/>
                    <c:set var="chartContainer"/>

                    <c:choose>
                        <c:when  test="${loop.index eq 0}">
                            <c:set var="report3" value="${report_1.ch}"/>
                            <c:set var="chartContainer" value="chSpChart"/>
                        </c:when>
                        <c:when  test="${loop.index eq 1}">
                            <c:set var="report3" value="${report_1.am}"/>
                            <c:set var="chartContainer" value="amSpChart"/>
                        </c:when>
                        <c:when  test="${loop.index eq 2}">
                            <c:set var="report3" value="${report_1.gr}"/>
                            <c:set var="chartContainer" value="grSpChart"/>
                        </c:when>
                        <c:when  test="${loop.index eq 3}">
                            <c:set var="report3" value="${report_1.ny}"/>
                            <c:set var="chartContainer" value="nySpChart"/>
                        </c:when>
                    </c:choose>
                    
                var data = new Array(${fn:length(report3)});
                    <c:forEach items="${report3}" var="spSaleDetails" varStatus="loopIdx">
                data[${loopIdx.index}] = new Array(2);
                data[${loopIdx.index}][0] = '${spSaleDetails.salesPerson}';
                data[${loopIdx.index}][1] = ${spSaleDetails.sale};
                    </c:forEach>

                var plot1 = jQuery.jqplot('${chartContainer}', [data], {
                    //gridPadding: {top: 70, bottom: 0, left: 0, right: 0},
                    grid: {
                        drawBorder: false,
                        drawGridlines: false,
                        background: '#ffffff',
                        shadow: false
                    },
                    axesDefaults: {
                    },
                    seriesDefaults: {
                        // Make this a pie chart.
                        renderer: jQuery.jqplot.PieRenderer,
                        rendererOptions: {
                            // Put data labels on the pie slices.
                            // By default, labels show the percentage of the slice.
                            showDataLabels: true,
                            padding: 5
                                    //diameter: 400
                                    // By default, data labels show the percentage of the donut/pie.
                                    // You can show the data 'value' or data 'label' instead.
                                    //dataLabels: 'value'
                        }
                    },
                    legend: {
                        show: true,
                        location: 's',
                        //marginLeft: '60px',
                        rendererOptions: {
                            //numberRows: 8
                            numberColumns: 9
                        }
                        /*placement: "outside"*/
                    }
                }
                );
                $("#${chartContainer}").bind('jqplotDataHighlight', function(ev, seriesIndex, pointIndex, data) {
                    var $this = $(this);
                    $this.attr('title', data[0] + ": " + data[1]);
                });
                </c:forEach>
            </c:if>



            });
        </script>
</body>
</html>
