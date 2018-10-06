package be.aca.coin.liferay.schizo.internal.portlet;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.ServletContextPool;

@Component(
		immediate = true,
		property = {
				"com.liferay.portlet.add-default-resource=true",
				"com.liferay.portlet.display-category=category.hidden",
				"javax.portlet.display-name=Schizo",
				"javax.portlet.init-param.view-template=/view.jsp",
				"javax.portlet.name=" + SchizoPortletConstants.PORTLET_NAME,
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.security-role-ref=administrator",
				"javax.portlet.supports.mime-type=text/html"
		},
		service = Portlet.class
)
public class SchizoPortlet extends MVCPortlet {

	public void init(PortletConfig config) throws PortletException {
		super.init(config);

		LiferayPortletConfig liferayPortletConfig = (LiferayPortletConfig) config;
		PortletApp portletApp = liferayPortletConfig.getPortlet().getPortletApp();

		ServletContextPool.put(portletApp.getServletContextName(), portletApp.getServletContext());
	}

	public void destroy() {
		ServletContextPool.remove(getPortletContext().getPortletContextName());

		super.destroy();
	}
}