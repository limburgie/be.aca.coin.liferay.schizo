package be.aca.coin.liferay.schizo.internal.contributor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.template.TemplateContextContributor;

import be.aca.coin.liferay.schizo.api.service.SchizoService;

@Component(
		immediate = true,
		property = { "type=" + TemplateContextContributor.TYPE_GLOBAL },
		service = TemplateContextContributor.class
)
public class SchizoTemplateContextContributor implements TemplateContextContributor {

	@Reference private SchizoService schizoService;

	public void prepare(Map<String, Object> contextObjects, HttpServletRequest request) {
		contextObjects.put("personas", schizoService.getPersonas());
	}
}