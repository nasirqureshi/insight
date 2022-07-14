<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c'%>
<div class="row">
	<div class="col s12">
		<div class="col s4">
			<div class="card white">
				<div class="card-content">
					<c:if
						test="${not empty salesDetails and not empty salesDetails.saleReport1}">
						<c:set var="report1" value="${salesDetails.saleReport1}" />
						<div class="row" style="font-weight: bold;">${report1.reportHeader}</div>
						<table class="borderedAll">
							<colgroup>
								<col class="greyCol" />
								<col class="chCol" />
								<col class="amCol" />
								<col class="grCol" />
								<col class="greyCol" />
							</colgroup>
							<thead>
								<tr>
									<th></th>
									<th><strong>CH</strong></th>
									<th><strong>AM</strong></th>
									<th><strong>GR</strong></th>
									<th><strong>TOTAL</strong></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Cnt (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].count}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].count}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out value="${report1.mpSalesDetails[loop.index].count}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].count}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out value="${report1.grSalesDetails[loop.index].count}" />
												<c:set var="total"
													value="${total + report1.grSalesDetails[loop.index].count}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Sales (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].sale}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].sale}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out value="${report1.mpSalesDetails[loop.index].sale}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].sale}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out value="${report1.grSalesDetails[loop.index].sale}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].sale}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Dics (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out
													value="${report1.chSalesDetails[loop.index].discount}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].discount}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out
													value="${report1.mpSalesDetails[loop.index].discount}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].discount}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out
													value="${report1.grSalesDetails[loop.index].discount}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].discount}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Tax (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].tax}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].tax}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out value="${report1.mpSalesDetails[loop.index].tax}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].tax}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out value="${report1.grSalesDetails[loop.index].tax}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].tax}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Gross (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].gross}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].gross}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out value="${report1.mpSalesDetails[loop.index].gross}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].gross}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out value="${report1.grSalesDetails[loop.index].gross}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].gross}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Net (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].net}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].net}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out value="${report1.mpSalesDetails[loop.index].net}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].net}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out value="${report1.grSalesDetails[loop.index].net}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].net}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Rtns (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out
													value="${report1.chSalesDetails[loop.index].returned}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].returned}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out
													value="${report1.mpSalesDetails[loop.index].returned}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].returned}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out
													value="${report1.grSalesDetails[loop.index].returned}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].returned}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="row" style="font-weight: bold;">${report1.reportFooter}</div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="col s4">
			<div class="card	white">
				<div class="card-content">
					<c:if
						test="${not empty salesDetails and not empty salesDetails.saleReport2}">
						<c:set var="report1" value="${salesDetails.saleReport2}" />
						<div class="row" style="font-weight: bold;">${report1.reportHeader}</div>
						<table class="borderedAll">
							<colgroup>
								<col class="greyCol" />
								<col class="chCol" />
								<col class="amCol" />
								<col class="grCol" />
								<col class="greyCol" />
							</colgroup>
							<thead>
								<tr>
									<th></th>
									<th><strong>CH</strong></th>
									<th><strong>AM</strong></th>
									<th><strong>GR</strong></th>
									<th><strong>TOTAL</strong></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Cnt (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].count}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].count}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out value="${report1.mpSalesDetails[loop.index].count}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].count}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out value="${report1.grSalesDetails[loop.index].count}" />
												<c:set var="total"
													value="${total + report1.grSalesDetails[loop.index].count}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Sales (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].sale}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].sale}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out value="${report1.mpSalesDetails[loop.index].sale}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].sale}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out value="${report1.grSalesDetails[loop.index].sale}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].sale}" />
											</c:if></td>
										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Dics (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out
													value="${report1.chSalesDetails[loop.index].discount}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].discount}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out
													value="${report1.mpSalesDetails[loop.index].discount}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].discount}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out
													value="${report1.grSalesDetails[loop.index].discount}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].discount}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Tax (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].tax}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].tax}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out value="${report1.mpSalesDetails[loop.index].tax}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].tax}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out value="${report1.grSalesDetails[loop.index].tax}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].tax}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Gross (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].gross}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].gross}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out value="${report1.mpSalesDetails[loop.index].gross}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].gross}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out value="${report1.grSalesDetails[loop.index].gross}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].gross}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Net (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].net}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].net}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out value="${report1.mpSalesDetails[loop.index].net}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].net}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out value="${report1.grSalesDetails[loop.index].net}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].net}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="5" style="background-color: antiquewhite;"></td>
								</tr>
								<c:forEach begin="0" end="4" step="1" varStatus="loop">
									<c:set var="total" value="${0.00}" />
									<tr>
										<td>Rtns (<c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out value="${report1.chSalesDetails[loop.index].year}" />
											</c:if>)
										</td>
										<td><c:if
												test="${not empty report1.chSalesDetails and not empty report1.chSalesDetails[loop.index]}">
												<c:out
													value="${report1.chSalesDetails[loop.index].returned}" />
												<c:set var="total"
													value="${total+report1.chSalesDetails[loop.index].returned}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.mpSalesDetails and not empty report1.mpSalesDetails[loop.index]}">
												<c:out
													value="${report1.mpSalesDetails[loop.index].returned}" />
												<c:set var="total"
													value="${total+report1.mpSalesDetails[loop.index].returned}" />
											</c:if></td>
										<td><c:if
												test="${not empty report1.grSalesDetails and not empty report1.grSalesDetails[loop.index]}">
												<c:out
													value="${report1.grSalesDetails[loop.index].returned}" />
												<c:set var="total"
													value="${total+report1.grSalesDetails[loop.index].returned}" />
											</c:if></td>

										<td><c:out value="${total}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="row" style="font-weight: bold;">${report1.reportFooter}</div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="col s4">
			<div class="card white">
				<div class="card-content">
					<div class="row">
						<c:if
							test="${not empty salesDetails and not empty salesDetails.saleReport3}">
							<div class="col s12">
								<ul class="tabs"
									style="background-color: lightgray; color: black;">
									<li class="tab col s3"><a class="active" href="#chSpRpt">CH</a></li>
									<li class="tab col s3"><a href="#amSpRpt">AM</a></li>
									<li class="tab col s3"><a href="#grSpRpt">GR</a></li>

								</ul>
							</div>
							<c:set var="report_3" value="${salesDetails.saleReport3}" />
							<c:forEach begin="0" end="3" step="1" varStatus="loop">
								<c:set var="report3" />
								<c:set var="divContainer" />
								<c:set var="chartContainer" />
								<c:choose>
									<c:when test="${loop.index eq 0}">
										<c:set var="report3" value="${report_3.ch}" />
										<c:set var="divContainer" value="chSpRpt" />
										<c:set var="chartContainer" value="chSpChart" />
									</c:when>
									<c:when test="${loop.index eq 1}">
										<c:set var="report3" value="${report_3.am}" />
										<c:set var="divContainer" value="amSpRpt" />
										<c:set var="chartContainer" value="amSpChart" />
									</c:when>
									<c:when test="${loop.index eq 2}">
										<c:set var="report3" value="${report_3.gr}" />
										<c:set var="divContainer" value="grSpRpt" />
										<c:set var="chartContainer" value="grSpChart" />
									</c:when>

								</c:choose>
								<div id="${divContainer}" class="col s12">
									<div class="row" style="font-weight: bold;">${report_3.reportHeader}</div>
									<table class="borderedAll">
										<colgroup>
											<col class="greyCol" />
											<col class="chCol" />
											<col class="amCol" />
											<col class="grCol" />
											<col class="" />
										</colgroup>
										<thead>
											<tr>
												<th><strong>Person</strong></th>
												<th><strong>Orders:Mean Time </strong></th>
												<th><strong>Sale</strong></th>
												<th><strong>Returns</strong></th>
												<th><strong>##</strong></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${report3}" var="spSaleDetails">
												<tr>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${spSaleDetails.salesPerson}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${spSaleDetails.returnInvCount}" />:</td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${spSaleDetails.sale}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"><c:out
															value="${spSaleDetails.returns}" /></td>
													<td style="word-wrap: break-word; white-space: normal;"></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<div id="${chartContainer}" style="height: 500px; width: 100%;"></div>
								</div>
							</c:forEach>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>