package be.aca.coin.liferay.schizo.internal.signin.adt;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.service.Schizo;
import be.aca.coin.liferay.schizo.internal.signin.portlet.SchizoSignInPortletConstants;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + SchizoSignInPortletConstants.PORTLET_NAME
		},
		service = TemplateHandler.class
)
public class SchizoSignInPortletDisplayTemplateHandler extends BasePortletDisplayTemplateHandler {

	@Reference private Schizo schizo;

	public String getClassName() {
		return Schizo.class.getName();
	}

	public String getName(Locale locale) {
		return "Persona Sign In Template";
	}

	public String getResourceName() {
		return SchizoSignInPortletConstants.PORTLET_NAME;
	}

	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(long classPK, String language, Locale locale) throws Exception {
		Map<String, TemplateVariableGroup> templateVariableGroups = super.getTemplateVariableGroups(classPK, language, locale);

		TemplateVariableGroup fields = templateVariableGroups.get("fields");
		fields.empty();

		fields.addCollectionVariable("Personas", List.class, "personas", "Persona", Persona.class, "persona", "firstName");

		return templateVariableGroups;
	}

	public String getTemplatesHelpPath(String language) {
		return "be/aca/coin/liferay/schizo/internal/signin/adt/default." + language;
	}
}