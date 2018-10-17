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

	<c:choose>
		<c:when test="${!empty personaStore.personas}">
			<div class="row">
				<c:forEach items="${personaStore.personas}" var="persona">
					<portlet:renderURL var="editPersonaUrl">
						<portlet:param name="schizo" value="${persona.screenName}"/>
						<portlet:param name="mvcRenderCommandName" value="/schizo/edit_persona"/>
					</portlet:renderURL>

					<div class="col-lg-3 col-md-4 col-sm-6">
						<div class="card">
							<a href="${editPersonaUrl}">
								<c:choose>
									<c:when test="${not empty persona.portrait}">
										<img src="${persona.portrait}" width="100%"/>
									</c:when>
									<c:otherwise>
										<img src="/image/user_male_portrait" width="100%"/>
									</c:otherwise>
								</c:choose>
							</a>
							<div class="card-row card-row-padded card-row-valign-top">
								<div class="card-col-content">
									<h2 class="text-center">
										<a href="${editPersonaUrl}" style="color: #272833">${persona.firstName}</a>
									</h2>
									<p class="lfr-card-subtitle-text">${persona.bio}</p>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
			<div class="main-content-body">
				<div class="sheet taglib-empty-result-message">
					<div class="taglib-empty-result-message-header"></div>
					<div class="sheet-text text-center text-muted">
						No personas defined yet. <a href="${addPersonaUrl}">Create your first!</a>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>