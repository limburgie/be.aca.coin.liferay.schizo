package be.aca.coin.liferay.schizo.internal.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.domain.PersonaProfile;
import be.aca.coin.liferay.schizo.api.exception.CannotSavePersonaException;
import be.aca.coin.liferay.schizo.api.service.SchizoService;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + SchizoPortletConstants.PORTLET_NAME,
				"mvc.command.name=/schizo/save_persona"
		},
		service = MVCActionCommand.class
)
public class SavePersonaActionCommand extends BaseMVCActionCommand {

	private static final Log LOGGER = LogFactoryUtil.getLog(SavePersonaActionCommand.class);

	@Reference private SchizoService schizoService;
	@Reference private Portal portal;

	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) {
		String oldScreenName = actionRequest.getParameter("oldScreenName");

		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(actionRequest, "emailAddress");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		String portrait = ParamUtil.getString(actionRequest, "portrait");
		String bio = ParamUtil.getString(actionRequest, "bio");

		String dataContext = ParamUtil.getString(actionRequest,"dataContext", "{}");

		if (screenName.isEmpty() || emailAddress.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
			error(actionRequest, actionResponse, "profileFieldsEmpty");
			return;
		}

		try {
			PersonaProfile profile = new PersonaProfile(screenName, emailAddress, firstName, lastName, portrait, bio);
			Persona persona = new Persona(profile, new Gson().fromJson(dataContext, JsonObject.class));

			schizoService.savePersona(oldScreenName, persona);
			SessionMessages.add(actionRequest, "personaSaved");
		} catch (JsonSyntaxException e) {
			error(actionRequest, actionResponse, "invalidJson");
		} catch (CannotSavePersonaException e) {
			LOGGER.error(e);
			error(actionRequest, actionResponse, "personaNotSaved");
		}
	}

	private void error(ActionRequest actionRequest, ActionResponse actionResponse, String key) {
		SessionErrors.add(actionRequest, key);

		actionResponse.setRenderParameter("mvcRenderCommandName", "/schizo/edit_persona");
		actionResponse.setRenderParameter("schizo", ParamUtil.getString(actionRequest, "oldScreenName"));
		actionResponse.setRenderParameter("error", Boolean.TRUE.toString());
	}
}