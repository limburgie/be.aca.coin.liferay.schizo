package be.aca.coin.liferay.schizo.internal.signin.portlet;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.*;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import be.aca.coin.liferay.schizo.api.service.Schizo;

@Component(
		immediate = true,
		property = {
				"com.liferay.portlet.add-default-resource=true",
				"com.liferay.portlet.display-category=category.tools",
				"javax.portlet.display-name=Persona Sign In",
				"javax.portlet.init-param.view-template=/sign_in/view.jsp",
				"javax.portlet.init-param.config-template=/sign_in/configuration.jsp",
				"javax.portlet.name=" + SchizoSignInPortletConstants.PORTLET_NAME,
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.supports.mime-type=text/html"
		},
		service = Portlet.class
)
public class SchizoSignInPortlet extends MVCPortlet {

	@Reference private Schizo schizo;

	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		renderRequest.setAttribute("displayStyle", getDisplayStyle(renderRequest));
		renderRequest.setAttribute("contextObjects", getContextObjects());
		renderRequest.setAttribute("personas", schizo.getPersonas());

		super.render(renderRequest, renderResponse);
	}

	private Map<String, Object> getContextObjects() {
		Map<String, Object> result = new HashMap<>();
		result.put("personas", schizo.getPersonas());

		return result;
	}

	private String getDisplayStyle(RenderRequest renderRequest) {
		PortletPreferences preferences = renderRequest.getPreferences();

		return preferences.getValue("displayStyle", "");
	}
}