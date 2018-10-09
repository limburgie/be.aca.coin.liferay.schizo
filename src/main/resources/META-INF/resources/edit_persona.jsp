<%@ include file="/init.jsp" %>

<portlet:actionURL name="/schizo/save_persona" var="savePersonaUrl"/>

<form class="container-fluid-1280" method="post" action="${savePersonaUrl}">
	<c:if test="${editMode}">
		<input type="hidden" name="<portlet:namespace/>oldScreenName" value="${renderRequest.getParameter("schizo")}"/>
	</c:if>
	<textarea style="display:none" id="<portlet:namespace/>dataContext" name="<portlet:namespace/>dataContext">${editMode ? persona.getPrettyPrintedDataContext() : "{}"}</textarea>
	<div class="card-horizontal main-content-card">
		<div class="panel-group">
			<div class="sheet">
				<h2 class="sheet-title">${title}</h2>

				<h3 class="sheet-subtitle">Profile information</h3>
				<div class="row">
					<fieldset class="fieldset col-md-6">
						<div class="form-group">
							<label class="control-label" for="<portlet:namespace/>screenName">Screen name</label>
							<input class="form-control" id="<portlet:namespace/>screenName" name="<portlet:namespace/>screenName" value="${editMode ? persona.getProfile().getScreenName() : ''}"/>
						</div>
						<div class="form-group">
							<label class="control-label" for="<portlet:namespace/>emailAddress">Email address</label>
							<input class="form-control" id="<portlet:namespace/>emailAddress" name="<portlet:namespace/>emailAddress" value="${editMode ? persona.getProfile().getEmailAddress() : ''}"/>
						</div>
						<div class="form-group">
							<label class="control-label" for="<portlet:namespace/>firstName">First name</label>
							<input class="form-control" id="<portlet:namespace/>firstName" name="<portlet:namespace/>firstName" value="${editMode ? persona.getProfile().getFirstName() : ''}"/>
						</div>
						<div class="form-group">
							<label class="control-label" for="<portlet:namespace/>lastName">Last name</label>
							<input class="form-control" id="<portlet:namespace/>lastName" name="<portlet:namespace/>lastName" value="${editMode ? persona.getProfile().getLastName() : ''}"/>
						</div>
					</fieldset>
				</div>

				<h3 class="sheet-subtitle">Persona data context</h3>
				<div class="form-group">
					<label class="control-label" for="<portlet:namespace/>dataContext">Data context</label>
					<div class="lfr-editor-container" id="<portlet:namespace />dataContextEditorContainer">
						<div class="lfr-rich-editor" id="<portlet:namespace />dataContextRichEditor"></div>
					</div>
				</div>

				<div class="sheet-footer">
					<button class="btn btn-primary btn-default" type="submit">Save</button>
					<a class="btn btn-cancel btn-default btn-link" href="<portlet:renderURL/>">Cancel</a>
				</div>
			</div>
		</div>
	</div>
</form>

<aui:script use="aui-ace-editor">
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
	});
</aui:script>