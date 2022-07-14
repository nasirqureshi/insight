<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c'%>
<div class="row">
	<div class="col s12">
		<div class="col s6">
			<div class="card	white">
				<div class="card-content">
					<c:if
						test="${not empty regionReturn and not empty regionReturn.regionReturnReport1}">
						<c:set var="report1" value="${regionReturn.regionReturnReport1}" />
						<div class="row" style="font-weight: bold;">${report1.header}</div>
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
									<td style="vertical-align: top;">
										<table class="borderedAll">
											<colgroup>
												<col class="greyCol" />
												<col class="greyCol" />
											</colgroup>
											<c:forEach items="${report1.date0.regionReturnList}"
												var="regReturn">
												<tr>
													<td style=""><c:out value="${regReturn.region}" /></td>
													<td style=""><c:out value="${regReturn.returned}" /></td>
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
											<c:forEach items="${report1.date1.regionReturnList}"
												var="regReturn">
												<tr>
													<td style=""><c:out value="${regReturn.region}" /></td>
													<td style=""><c:out value="${regReturn.returned}" /></td>
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
											<c:forEach items="${report1.date2.regionReturnList}"
												var="regReturn">
												<tr>
													<td style=""><c:out value="${regReturn.region}" /></td>
													<td style=""><c:out value="${regReturn.returned}" /></td>
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
											<c:forEach items="${report1.date3.regionReturnList}"
												var="regReturn">
												<tr>
													<td style=""><c:out value="${regReturn.region}" /></td>
													<td style=""><c:out value="${regReturn.returned}" /></td>
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
											<c:forEach items="${report1.date4.regionReturnList}"
												var="regReturn">
												<tr>
													<td style=""><c:out value="${regReturn.region}" /></td>
													<td style=""><c:out value="${regReturn.returned}" /></td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>
		</div>
		<div class="col s6">
			<div class="card	white">
				<div class="card-content">
					<c:if
						test="${not empty regionReturn and not empty regionReturn.regionReturnReport2}">
						<c:set var="report1" value="${regionReturn.regionReturnReport2}" />
						<div class="row" style="font-weight: bold;">${report1.header}</div>
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
											<c:forEach items="${report1.date0.regionReturnList}"
												var="regReturn">
												<tr>
													<td style=""><c:out value="${regReturn.region}" /></td>
													<td style=""><c:out value="${regReturn.returned}" /></td>
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
											<c:forEach items="${report1.date1.regionReturnList}"
												var="regReturn">
												<tr>
													<td style=""><c:out value="${regReturn.region}" /></td>
													<td style=""><c:out value="${regReturn.returned}" /></td>
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
											<c:forEach items="${report1.date2.regionReturnList}"
												var="regReturn">
												<tr>
													<td style=""><c:out value="${regReturn.region}" /></td>
													<td style=""><c:out value="${regReturn.returned}" /></td>
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
											<c:forEach items="${report1.date3.regionReturnList}"
												var="regReturn">
												<tr>
													<td style=""><c:out value="${regReturn.region}" /></td>
													<td style=""><c:out value="${regReturn.returned}" /></td>
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
											<c:forEach items="${report1.date4.regionReturnList}"
												var="regReturn">
												<tr>
													<td style=""><c:out value="${regReturn.region}" /></td>
													<td style=""><c:out value="${regReturn.returned}" /></td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>