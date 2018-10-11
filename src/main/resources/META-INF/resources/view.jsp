<%@ include file="/init.jsp" %>

<clay:navigation-bar inverted="${true}" navigationItems="${navItems}"/>

<clay:management-toolbar
	selectable="${false}"
	showSearch="${false}"
	showCreationMenu="${true}"
	creationMenu="${creationMenu}"
/>

<div class="container">
	<liferay-ui:success key="personaSaved" message="Persona was successfully saved."/>

	<c:if test="${schizoService.personasCount > 0}">
		<div class="row">
			<c:forEach items="${schizoService.personas}" var="persona">
				<portlet:renderURL var="editPersonaUrl">
					<portlet:param name="schizo" value="${persona.profile.screenName}"/>
					<portlet:param name="mvcRenderCommandName" value="/schizo/edit_persona"/>
				</portlet:renderURL>

				<div class="col-lg-3 col-md-4 col-sm-6">
					<div class="card">
						<a href="${editPersonaUrl}">
							<c:choose>
								<c:when test="${not empty persona.profile.portrait}">
									<img src="${persona.profile.portrait}" width="100%"/>
								</c:when>
								<c:otherwise>
									<img src="/image/user_male_portrait" width="100%"/>
								</c:otherwise>
							</c:choose>
						</a>
						<div class="card-row card-row-padded card-row-valign-top">
							<div class="card-col-content">
								<h2 class="text-center">
									<a href="${editPersonaUrl}" style="color: #272833">
										${persona.profile.firstName}
									</a>
								</h2>
								<p class="lfr-card-subtitle-text">
									${persona.profile.bio}
								</p>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:if>
</div>