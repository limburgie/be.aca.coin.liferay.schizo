<%@ include file="/sign_in/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="${true}" var="saveConfigurationUrl"/>
<liferay-portlet:renderURL portletConfiguration="${true}" var="showConfigurationUrl"/>

<form class="container container-view form" method="post" action="${saveConfigurationUrl}">
	<div class="sheet sheet-lg">
		<fieldset>
			<liferay-ddm:template-selector
					className="be.aca.coin.liferay.schizo.api.service.Schizo"
					displayStyle='${portletPreferences.getValue("displayStyle", "")}'
					displayStyleGroupId="${portletGroupId}"
					refreshURL="${showConfigurationUrl}"
					showEmptyOption="${true}"/>
		</fieldset>
	</div>
	<div class="sheet-footer dialog-footer">
		<button type="submit" class="btn btn-primary btn-default">
			Save
		</button>
		<button type="button" class="btn btn-cancel btn-default btn-link">
			Cancel
		</button>
	</div>
</form>