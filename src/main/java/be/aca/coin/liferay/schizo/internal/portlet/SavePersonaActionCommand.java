package be.aca.coin.liferay.schizo.internal.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.domain.PersonaProfile;
import be.aca.coin.liferay.schizo.api.exception.CannotSavePersonaException;
import be.aca.coin.liferay.schizo.api.service.SchizoService;
import be.aca.coin.liferay.schizo.internal.autologin.SchizoAutoLogin;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + SchizoPortletConstants.PORTLET_NAME,
				"mvc.command.name=/schizo/save_persona"
		},
		service = MVCActionCommand.class
)
public class SavePersonaActionCommand implements MVCActionCommand {

	private static final Log LOGGER = LogFactoryUtil.getLog(SchizoAutoLogin.class);

	@Reference private SchizoService schizoService;

	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) {
		String oldScreenName = actionRequest.getParameter("oldScreenName");

		String screenName = actionRequest.getParameter("screenName");
		String emailAddress = actionRequest.getParameter("emailAddress");
		String firstName = actionRequest.getParameter("firstName");
		String lastName = actionRequest.getParameter("lastName");

		String dataContext = actionRequest.getParameter("dataContext");

		PersonaProfile profile = new PersonaProfile(screenName, emailAddress, firstName, lastName, null);
		Persona persona = new Persona(profile, new Gson().fromJson(dataContext, JsonObject.class));

		try {
			schizoService.savePersona(oldScreenName, persona);
			return false;
		} catch (CannotSavePersonaException e) {
			LOGGER.error(e);
			return true;
		}
	}
}