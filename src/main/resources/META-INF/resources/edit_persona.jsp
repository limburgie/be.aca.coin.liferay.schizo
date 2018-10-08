<%@ include file="/init.jsp" %>

<portlet:actionURL var="savePersonaUrl"/>

<form class="container-fluid-1280" method="post" action="${savePersonaUrl}">
	<c:if test="${editMode}">
		<input type="hidden" name="<portlet:namespace/>oldScreenName" value="${renderRequest.getParameter("schizo")}"/>
	</c:if>
	<div class="card-horizontal main-content-card">
		<div class="panel-group">
			<div class="sheet">
				<h2 class="sheet-title">${editMode ? persona.getProfile().getFullName() : 'New persona'}</h2>
				<div class="sheet-section">
					<h3 class="sheet-subtitle">Profile information</h3>
					<div class="row">
						<fieldset class="fieldset col-md-6">
							<div class="form-group">
								<label class="control-label" for="<portlet:namespace/>screenName">Screen name</label>
								<input class="form-control" id="<portlet:namespace/>screenName" name="<portlet:namespace/>screenName" value="${editMode ? persona.getProfile().getScreenName() : ''}"/>
							</div>
							<div class="form-group">
								<label class="control-label" for="<portlet:namespace/>emailAddress">Email address</label>
								<input class="form-control" id="<portlet:namespace/>emailAddress" name="<portlet:namespace/>emailAddress" value="${editMode ? persona.getProfile().getEmailAddress() : ''}/>
							</div>
							<div class="form-group">
								<label class="control-label" for="<portlet:namespace/>firstName">Email address</label>
								<input class="form-control" id="<portlet:namespace/>firstName" name="<portlet:namespace/>firstName" value="${editMode ? persona.getProfile().getFirstName() : ''}/>
							</div>
							<div class="form-group">
								<label class="control-label" for="<portlet:namespace/>lastName">Last name</label>
								<input class="form-control" id="<portlet:namespace/>lastName" name="<portlet:namespace/>lastName" value="${editMode ? persona.getProfile().getLastName() : ''}/>
							</div>
						</fieldset>
					</div>
				</div>
				<div class="sheet-section">
					<h3 class="sheet-subtitle">Persona data context</h3>
				</div>
				<div class="sheet-footer">
					<button class="btn btn-primary btn-default" type="submit">Save</button>
					<a class="btn btn-cancel btn-default btn-link" href="<portlet:renderURL/>">Cancel</a>
				</div>
			</div>
		</div>
	</div>
</form>

<c:choose>
	<c:when test='${empty renderRequest.getParameter("schizo")}'>
		Creating a new persona.
	</c:when>
	<c:otherwise>
		Editing persona ${renderRequest.getParameter("schizo")}.
	</c:otherwise>
</c:choose>