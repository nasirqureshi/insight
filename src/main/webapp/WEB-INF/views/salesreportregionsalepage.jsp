<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c'%>
<div class="row">
	<div class="col s12">
		<div class="col s6">
			<div class="card	white">
				<div class="card-content">
					<c:if
						test="${not empty regionSales and not empty regionSales.regionSalesReport1}">
						<c:set var="report_1" value="${regionSales.regionSalesReport1}" />
						<div class="row" style="font-weight: bold;">${report_1.header}</div>
						<div class="row">
							<div class="col s12">
								<ul class="tabs"
									style="background-color: lightgray; color: black;">
									<li class="tab col s3"><a class="active" href="#chRpt">CH</a></li>
									<li class="tab col s3"><a href="#amRpt">AM</a></li>
									<li class="tab col s3"><a href="#grRpt">GR</a></li>

								</ul>
							</div>
							<c:forEach begin="0" end="3" step="1" varStatus="loop">
								<c:set var="report1" />
								<c:set var="divContainer" />
								<c:choose>
									<c:when test="${loop.index eq 0}">
										<c:set var="report1" value="${report_1.ch}" />
										<c:set var="divContainer" value="chRpt" />
									</c:when>
									<c:when test="${loop.index eq 1}">
										<c:set var="report1" value="${report_1.am}" />
										<c:set var="divContainer" value="amRpt" />
									</c:when>
									<c:when test="${loop.index eq 2}">
										<c:set var="report1" value="${report_1.gr}" />
										<c:set var="divContainer" value="grRpt" />
									</c:when>

								</c:choose>
								<div id="${divContainer}" class="col s12">
									<c:if test="${not empty report1}">
										<table class="borderedAll centered">
											<thead>
												<tr>
													<th style="padding: 0px 0px;"><strong>${report1.date0.header}</strong></th>
													<th style="padding: 0px 0px;"><strong>${report1.date1.header}</strong></th>
													<th style="padding: 0px 0px;"><strong>${report1.date2.header}</strong></th>
													<th style="padding: 0px 0px;"><strong>${report1.date3.header}</strong></th>
													<th style="padding: 0px 0px;"><strong>${report1.date4.header}</strong></th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td style="vertical-align: top; padding: 0px 0px;">
														<table class="borderedAll">
															<colgroup>
																<col class="greyCol" />
																<col class="greyCol" />
															</colgroup>
															<c:forEach items="${report1.date0.regionSalesList}"
																var="regSales">
																<tr>
																	<td style=""><c:out value="${regSales.region}" /></td>
																	<td style=""><c:out value="${regSales.sale}" /></td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td style="vertical-align: top; padding: 0px 0px;">
														<table class="borderedAll">
															<colgroup>
																<col class="chCol" />
																<col class="chCol" />
															</colgroup>
															<c:forEach items="${report1.date1.regionSalesList}"
																var="regSales">
																<tr>
																	<td style=""><c:out value="${regSales.region}" /></td>
																	<td style=""><c:out value="${regSales.sale}" /></td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td style="vertical-align: top; padding: 0px 0px;">
														<table class="borderedAll">
															<colgroup>
																<col class="amCol" />
																<col class="amCol" />
															</colgroup>
															<c:forEach items="${report1.date2.regionSalesList}"
																var="regSales">
																<tr>
																	<td style=""><c:out value="${regSales.region}" /></td>
																	<td style=""><c:out value="${regSales.sale}" /></td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td style="vertical-align: top; padding: 0px 0px;">
														<table class="borderedAll">
															<colgroup>
																<col class="grCol" />
																<col class="grCol" />
															</colgroup>
															<c:forEach items="${report1.date3.regionSalesList}"
																var="regSales">
																<tr>
																	<td style=""><c:out value="${regSales.region}" /></td>
																	<td style=""><c:out value="${regSales.sale}" /></td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td style="vertical-align: top; padding: 0px 0px;">
														<table class="borderedAll">
															<colgroup>
																<col class="nyCol" />
																<col class="nyCol" />
															</colgroup>
															<c:forEach items="${report1.date4.regionSalesList}"
																var="regSales">
																<tr>
																	<td style=""><c:out value="${regSales.region}" /></td>
																	<td style=""><c:out value="${regSales.sale}" /></td>
																</tr>
															</c:forEach>
														</table>
													</td>
												</tr>
											</tbody>
										</table>
									</c:if>
								</div>
							</c:forEach>
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="col s6">
			<div class="card	white">
				<div class="card-content">
					<c:if
						test="${not empty regionSales and not empty regionSales.regionSalesReport2}">
						<c:set var="report_1" value="${regionSales.regionSalesReport2}" />
						<div class="row" style="font-weight: bold;">${report_1.header}</div>
						<div class="row">
							<div class="col s12">
								<ul class="tabs"
									style="background-color: lightgray; color: black;">
									<li class="tab col s3"><a class="active" href="#chRpt2">CH</a></li>
									<li class="tab col s3"><a href="#amRpt2">AM</a></li>
									<li class="tab col s3"><a href="#grRpt2">GR</a></li>

								</ul>
							</div>
							<c:forEach begin="0" end="3" step="1" varStatus="loop">
								<c:set var="report1" />
								<c:set var="divContainer" />
								<c:choose>
									<c:when test="${loop.index eq 0}">
										<c:set var="report1" value="${report_1.ch}" />
										<c:set var="divContainer" value="chRpt2" />
									</c:when>
									<c:when test="${loop.index eq 1}">
										<c:set var="report1" value="${report_1.am}" />
										<c:set var="divContainer" value="amRpt2" />
									</c:when>
									<c:when test="${loop.index eq 2}">
										<c:set var="report1" value="${report_1.gr}" />
										<c:set var="divContainer" value="grRpt2" />
									</c:when>

								</c:choose>
								<div id="${divContainer}" class="col s12">
									<c:if test="${not empty report1}">
										<table class="borderedAll centered">
											<thead>
												<tr>
													<th style="padding: 0px 0px;"><strong>${report1.date0.header}</strong></th>
													<th style="padding: 0px 0px;"><strong>${report1.date1.header}</strong></th>
													<th style="padding: 0px 0px;"><strong>${report1.date2.header}</strong></th>
													<th style="padding: 0px 0px;"><strong>${report1.date3.header}</strong></th>
													<th style="padding: 0px 0px;"><strong>${report1.date4.header}</strong></th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td style="vertical-align: top; padding: 0px 0px;">
														<table class="borderedAll">
															<colgroup>
																<col class="greyCol" />
																<col class="greyCol" />
															</colgroup>
															<c:forEach items="${report1.date0.regionSalesList}"
																var="regSales">
																<tr>
																	<td style=""><c:out value="${regSales.region}" /></td>
																	<td style=""><c:out value="${regSales.sale}" /></td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td style="vertical-align: top; padding: 0px 0px;">
														<table class="borderedAll">
															<colgroup>
																<col class="chCol" />
																<col class="chCol" />
															</colgroup>
															<c:forEach items="${report1.date1.regionSalesList}"
																var="regSales">
																<tr>
																	<td style=""><c:out value="${regSales.region}" /></td>
																	<td style=""><c:out value="${regSales.sale}" /></td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td style="vertical-align: top; padding: 0px 0px;">
														<table class="borderedAll">
															<colgroup>
																<col class="amCol" />
																<col class="amCol" />
															</colgroup>
															<c:forEach items="${report1.date2.regionSalesList}"
																var="regSales">
																<tr>
																	<td style=""><c:out value="${regSales.region}" /></td>
																	<td style=""><c:out value="${regSales.sale}" /></td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td style="vertical-align: top; padding: 0px 0px;">
														<table class="borderedAll">
															<colgroup>
																<col class="grCol" />
																<col class="grCol" />
															</colgroup>
															<c:forEach items="${report1.date3.regionSalesList}"
																var="regSales">
																<tr>
																	<td style=""><c:out value="${regSales.region}" /></td>
																	<td style=""><c:out value="${regSales.sale}" /></td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td style="vertical-align: top; padding: 0px 0px;">
														<table class="borderedAll">
															<colgroup>
																<col class="nyCol" />
																<col class="nyCol" />
															</colgroup>
															<c:forEach items="${report1.date4.regionSalesList}"
																var="regSales">
																<tr>
																	<td style=""><c:out value="${regSales.region}" /></td>
																	<td style=""><c:out value="${regSales.sale}" /></td>
																</tr>
															</c:forEach>
														</table>
													</td>
												</tr>
											</tbody>
										</table>
									</c:if>
								</div>
							</c:forEach>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>