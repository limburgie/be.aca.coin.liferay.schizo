<%@ include file="/control_panel/init.jsp" %>

<portlet:actionURL name="/schizo/save_persona" var="savePersonaUrl"/>


<form class="container-fluid-1280" method="post" action="${savePersonaUrl}">
	<c:if test="${editMode}">
		<input type="hidden" name="<portlet:namespace/>oldScreenName" value="${renderRequest.getParameter("schizo")}"/>
	</c:if>
	<div class="card-horizontal main-content-card">
		<div class="panel-group">
			<div class="sheet">
				<h2 class="sheet-title">${title}</h2>

				<liferay-ui:error key="profileFieldsEmpty" message="Screen name, email address, first and last name should not be empty."/>
				<liferay-ui:error key="invalidJson" message="Data context does not have a valid JSON syntax."/>
				<liferay-ui:error key="personaNotSaved" message="Persona was not saved due to an unexpected error. Please check the logs."/>
				<liferay-ui:error key="personaNotDeleted" message="Persona was not deleted due to an unexpected error. Please check the logs."/>

				<h3 class="sheet-subtitle">Profile information</h3>
				<div class="row">
					<fieldset class="fieldset col-md-9">
						<div class="row">
							<fieldset class="fieldset col-md-6">
								<div class="form-group">
									<label class="control-label" for="<portlet:namespace/>firstName">First name</label>
									<input class="form-control" id="<portlet:namespace/>firstName" name="<portlet:namespace/>firstName" value="${editMode ? persona.firstName : ''}"/>
								</div>
								<div class="form-group">
									<label class="control-label" for="<portlet:namespace/>lastName">Last name</label>
									<input class="form-control" id="<portlet:namespace/>lastName" name="<portlet:namespace/>lastName" value="${editMode ? persona.lastName : ''}"/>
								</div>
							</fieldset>
							<fieldset class="fieldset col-md-6">
								<div class="form-group">
									<label class="control-label" for="<portlet:namespace/>screenName">Screen name</label>
									<input class="form-control" id="<portlet:namespace/>screenName" name="<portlet:namespace/>screenName" value="${editMode ? persona.screenName : ''}"/>
								</div>
								<div class="form-group">
									<label class="control-label" for="<portlet:namespace/>emailAddress">Email address</label>
									<input class="form-control" id="<portlet:namespace/>emailAddress" name="<portlet:namespace/>emailAddress" value="${editMode ? persona.emailAddress : ''}"/>
								</div>
							</fieldset>
						</div>
						<div class="row">
							<fieldset class="fieldset col-md-6">
								<div class="form-group">
									<label class="control-label" for="<portlet:namespace/>bio">Bio</label>
									<textarea class="form-control" id="<portlet:namespace/>bio" name="<portlet:namespace/>bio">${editMode ? persona.bio : ''}</textarea>
								</div>
							</fieldset>
							<fieldset class="fieldset col-md-3">
								<div class="form-group">
									<label class="control-label" for="<portlet:namespace/>sites">Site memberships</label>
									<textarea class="form-control" id="<portlet:namespace/>sites" name="<portlet:namespace/>sites">${editMode ? persona.concatenatedSites : ''}</textarea>
								</div>
							</fieldset>
							<fieldset class="fieldset col-md-3">
								<div class="form-group">
									<label class="control-label" for="<portlet:namespace/>roles">Role assignments</label>
									<textarea class="form-control" id="<portlet:namespace/>roles" name="<portlet:namespace/>roles">${editMode ? persona.concatenatedRoles : ''}</textarea>
								</div>
							</fieldset>
						</div>
					</fieldset>
					<fieldset class="fieldset col-md-3">
						<div class="form-group">
							<label class="control-label">Portrait</label>
							<div>
								<c:choose>
									<c:when test="${not empty persona.portrait}">
										<img id="<portlet:namespace/>portraitImage" src="${persona.portrait}" width="100%"/>
									</c:when>
									<c:otherwise>
										<img id="<portlet:namespace/>portraitImage" src="/image/user_male_portrait" width="100%"/>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="btn-group button-holder">
								<button id="<portlet:namespace/>changePortraitButton" class="btn btn-default">Change</button>
								<button id="<portlet:namespace/>deletePortraitButton" class="btn btn-default">Delete</button>
							</div>
							<input type="file" style="display: none" id="<portlet:namespace/>portraitFile" accept=".jpg, .jpeg, .png"/>
							<input type="hidden" id="<portlet:namespace/>portrait" name="<portlet:namespace/>portrait" value="${editMode ? persona.portrait : ''}"/>
						</div>
					</fieldset>
				</div>

				<h3 class="sheet-subtitle">Persona data context</h3>
				<div class="form-group">
					<textarea style="display:none" id="<portlet:namespace/>dataContext" name="<portlet:namespace/>dataContext">${editMode ? persona.prettyPrintedDataContext : "{}"}</textarea>
					<div class="lfr-editor-container" id="<portlet:namespace />dataContextEditorContainer">
						<div class="lfr-rich-editor" id="<portlet:namespace />dataContextRichEditor"></div>
					</div>
				</div>

				<div class="sheet-footer">
					<button class="btn btn-primary btn-default" type="submit">Save</button>
					<a class="btn btn-cancel btn-default btn-link" href="<portlet:renderURL/>">Cancel</a>
					<c:if test="${editMode}">
						<portlet:actionURL name="/schizo/delete_persona" var="deletePersonaUrl">
							<portlet:param name="schizo" value='${renderRequest.getParameter("schizo")}'/>
						</portlet:actionURL>
						<a class="btn btn-danger" href="${deletePersonaUrl}">Delete</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</form>

<aui:script use="aui-ace-editor,node-event-simulate">
	A.on("domready", function(event) {
		var dataContext = A.one("#<portlet:namespace />dataContext");
		var editorNode = A.one("#<portlet:namespace />dataContextRichEditor");

		var editor = new A.AceEditor({
			boundingBox: editorNode,
			height: 400,
			mode: "json",
			width: "100%",
			tabSize: 2,
			value: dataContext.val()
		}).render();

		editor.getSession().on("change", function() {
			dataContext.val(editor.getSession().getValue());
		});

		document.getElementById("<portlet:namespace />portraitFile").addEventListener("change", function() {
			if (this.files && this.files[0]) {
				var fileReader = new FileReader();
				fileReader.addEventListener("load", function(e) {
					document.getElementById("<portlet:namespace />portraitImage").src = e.target.result;
					document.getElementById("<portlet:namespace />portrait").value = e.target.result;
				});
				fileReader.readAsDataURL(this.files[0]);
			}
		});

		A.one("#<portlet:namespace/>changePortraitButton").on("click", function(e) {
			A.one("#<portlet:namespace/>portraitFile").simulate("click");
			e.preventDefault();
		});

		A.one("#<portlet:namespace/>deletePortraitButton").on("click", function(e) {
			document.getElementById("<portlet:namespace />portraitImage").src = "/image/user_male_portrait";
			document.getElementById("<portlet:namespace />portrait").value = "";
			e.preventDefault();
		});
	});
</aui:script>