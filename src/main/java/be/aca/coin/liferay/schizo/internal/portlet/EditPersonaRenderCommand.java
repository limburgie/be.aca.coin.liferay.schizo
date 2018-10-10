package be.aca.coin.liferay.schizo.internal.portlet;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.exception.NoSuchPersonaException;
import be.aca.coin.liferay.schizo.api.service.SchizoService;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + SchizoPortletConstants.PORTLET_NAME,
				"mvc.command.name=/schizo/edit_persona"
		},
		service = MVCRenderCommand.class
)
public class EditPersonaRenderCommand implements MVCRenderCommand {

	@Reference private SchizoService schizoService;

	public String render(RenderRequest renderRequest, RenderResponse renderResponse) {
		enableBackButton(renderRequest, renderResponse);

		String screenName = renderRequest.getParameter("schizo");

		try {
			Persona persona = schizoService.getPersona(screenName);

			if (ParamUtil.getBoolean(renderRequest, "error")) {
				persona.getProfile().setScreenName(renderRequest.getParameter("screenName"));
				persona.getProfile().setEmailAddress(renderRequest.getParameter("emailAddress"));
				persona.getProfile().setFirstName(renderRequest.getParameter("firstName"));
				persona.getProfile().setLastName(renderRequest.getParameter("lastName"));
				persona.getProfile().setPortrait(renderRequest.getParameter("portrait"));
			}

			renderRequest.setAttribute("persona", persona);
			renderRequest.setAttribute("title", "Edit persona " + persona.getProfile().getFullName());
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