package be.aca.coin.liferay.schizo.internal.portlet.command;

import java.util.Arrays;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import be.aca.coin.liferay.schizo.internal.domain.PersonaDefinition;
import be.aca.coin.liferay.schizo.internal.portlet.SchizoPortletConstants;
import be.aca.coin.liferay.schizo.internal.store.PersonaStore;
import be.aca.coin.liferay.schizo.internal.store.exception.NoSuchPersonaException;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + SchizoPortletConstants.PORTLET_NAME,
				"mvc.command.name=/schizo/edit_persona"
		},
		service = MVCRenderCommand.class
)
public class EditPersonaRenderCommand implements MVCRenderCommand {

	@Reference private PersonaStore personaStore;

	public String render(RenderRequest renderRequest, RenderResponse renderResponse) {
		enableBackButton(renderRequest, renderResponse);

		String screenName = renderRequest.getParameter("schizo");

		try {
			PersonaDefinition persona = personaStore.getPersona(screenName);

			if (ParamUtil.getBoolean(renderRequest, "error")) {
				persona.setScreenName(renderRequest.getParameter("screenName"));
				persona.setEmailAddress(renderRequest.getParameter("emailAddress"));
				persona.setFirstName(renderRequest.getParameter("firstName"));
				persona.setLastName(renderRequest.getParameter("lastName"));
				persona.setPortrait(renderRequest.getParameter("portrait"));
				persona.setBio(renderRequest.getParameter("bio"));
				persona.setSites(Arrays.asList(renderRequest.getParameter("sites").split(",")));
			}

			renderRequest.setAttribute("persona", persona);
			renderRequest.setAttribute("title", "Edit persona " + persona.getFirstName());
			renderRequest.setAttribute("editMode", true);
		} catch (NoSuchPersonaException e) {
			renderRequest.setAttribute("persona", null);
			renderRequest.setAttribute("title", "Add persona");
			renderRequest.setAttribute("editMode", false);
		}

		return "/edit_persona.jsp";
	}

	private void enableBackButton(RenderRequest renderRequest, RenderResponse renderResponse) {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(renderResponse.createRenderURL().toString());
	}
}