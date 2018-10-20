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

		if (screenName == null) {
			if (ParamUtil.getBoolean(renderRequest, "error")) {
				PersonaDefinition persona = new PersonaDefinition();
				enrich(persona, renderRequest);
				setAttributes(renderRequest, persona, "Add persona", true);
			} else {
				setAttributes(renderRequest, null, "Add persona", false);
			}
		} else {
			PersonaDefinition persona;

			try {
				persona = personaStore.getPersona(screenName);
			} catch (NoSuchPersonaException e) {
				persona = new PersonaDefinition();
			}

			if (ParamUtil.getBoolean(renderRequest, "error")) {
				enrich(persona, renderRequest);
			}

			setAttributes(renderRequest, persona, "Edit persona " + persona.getFirstName(), true);
		}

		return "/control_panel/edit_persona.jsp";
	}

	private void enrich(PersonaDefinition persona, RenderRequest renderRequest) {
		persona.setScreenName(renderRequest.getParameter("screenName"));
		persona.setEmailAddress(renderRequest.getParameter("emailAddress"));
		persona.setFirstName(renderRequest.getParameter("firstName"));
		persona.setLastName(renderRequest.getParameter("lastName"));
		persona.setPortrait(renderRequest.getParameter("portrait"));
		persona.setBio(renderRequest.getParameter("bio"));
		persona.setSites(Arrays.asList(renderRequest.getParameter("sites").split("\\r?\\n")));
		persona.setRoles(Arrays.asList(renderRequest.getParameter("roles").split("\\r?\\n")));
	}

	private void enableBackButton(RenderRequest renderRequest, RenderResponse renderResponse) {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(renderResponse.createRenderURL().toString());
	}

	private void setAttributes(RenderRequest renderRequest, PersonaDefinition persona, String title, boolean editMode) {
		renderRequest.setAttribute("persona", persona);
		renderRequest.setAttribute("title", title);
		renderRequest.setAttribute("editMode", editMode);
	}
}