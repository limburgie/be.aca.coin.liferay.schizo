<%@ include file="/init.jsp" %>

<div class="container-fluid-1280">
	<div class="lfr-search-container-wrapper main-content-body">
		<c:if test="${schizoService.personasCount > 0}">
			<ul class="list-group">
				<c:forEach items="${schizoService.personas}" var="persona">
					<li class="list-group-item list-group-item-flex">
						<div class="autofit-col">
							<div class="user-icon">
								<c:choose>
									<c:when test="${not empty persona.profile.portrait}">
										<img src="data:image/jpeg;base64,${persona.profile.portrait}" width="32" height="32"/>
									</c:when>
									<c:otherwise>
										<img src="/image/user_male_portrait" width="32" height="32"/>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="autofit-col">
							<h5>
								<a href="#">
									${persona.profile.fullName}
								</a>
							</h5>
							<h6 class="text-default">
								${persona.profile.screenName}
							</h6>
						</div>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	</div>
</div>