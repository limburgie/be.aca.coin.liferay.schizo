package be.aca.coin.liferay.schizo.internal.portlet.command;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import be.aca.coin.liferay.schizo.internal.portlet.SchizoPortletConstants;
import be.aca.coin.liferay.schizo.internal.store.PersonaStore;
import be.aca.coin.liferay.schizo.internal.store.exception.PersonaStorageException;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + SchizoPortletConstants.PORTLET_NAME,
				"mvc.command.name=/schizo/delete_persona"
		},
		service = MVCActionCommand.class
)
public class DeletePersonaActionCommand extends BaseMVCActionCommand {

	private static final Log LOGGER = LogFactoryUtil.getLog(DeletePersonaActionCommand.class);

	@Reference private PersonaStore personaStore;

	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) {
		String screenName = actionRequest.getParameter("schizo");

		try {
			personaStore.deletePersona(screenName);
			SessionMessages.add(actionRequest, "personaDeleted");
		} catch (PersonaStorageException e) {
			LOGGER.error(e);
			error(actionRequest, actionResponse, "personaNotDeleted");
		}
	}

	private void error(ActionRequest actionRequest, ActionResponse actionResponse, String key) {
		SessionErrors.add(actionRequest, key);

		actionResponse.setRenderParameter("mvcRenderCommandName", "/schizo/edit_persona");
		actionResponse.setRenderParameter("schizo", ParamUtil.getString(actionRequest, "schizo"));
		actionResponse.setRenderParameter("error", Boolean.TRUE.toString());
	}
}
