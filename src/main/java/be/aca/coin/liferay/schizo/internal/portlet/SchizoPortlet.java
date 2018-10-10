package be.aca.coin.liferay.schizo.internal.portlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.portlet.*;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.ServletContextPool;

import be.aca.coin.liferay.schizo.api.service.SchizoService;

@Component(
		immediate = true,
		property = {
				"com.liferay.portlet.add-default-resource=true",
				"com.liferay.portlet.display-category=category.hidden",
				"javax.portlet.display-name=Schizo",
				"javax.portlet.init-param.view-template=/view.jsp",
				"javax.portlet.init-param.copy-request-parameters=true",
				"javax.portlet.name=" + SchizoPortletConstants.PORTLET_NAME,
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.security-role-ref=administrator",
				"javax.portlet.supports.mime-type=text/html"
		},
		service = Portlet.class
)
public class SchizoPortlet extends MVCPortlet {

	@Reference private SchizoService schizoService;

	private List<NavigationItem> navItems;

	public void init(PortletConfig config) throws PortletException {
		super.init(config);

		LiferayPortletConfig liferayPortletConfig = (LiferayPortletConfig) config;
		PortletApp portletApp = liferayPortletConfig.getPortlet().getPortletApp();
		ServletContextPool.put(portletApp.getServletContextName(), portletApp.getServletContext());

		initializeNavItems();
	}

	private void initializeNavItems() {
		NavigationItem navItem = new NavigationItem();
		navItem.setActive(true);
		navItem.setHref("");
		navItem.setLabel("Personas");

		navItems = Collections.singletonList(navItem);
	}

	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		renderRequest.setAttribute("schizoService", schizoService);
		renderRequest.setAttribute("navItems", navItems);
		renderRequest.setAttribute("creationMenu", createCreationMenu(renderResponse));

		super.doView(renderRequest, renderResponse);
	}

	private CreationMenu createCreationMenu(RenderResponse renderResponse) {
		CreationMenu creationMenu = new CreationMenu();

		creationMenu.addPrimaryDropdownItem(dropdownItem -> {
			dropdownItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/schizo/edit_persona");
			dropdownItem.setLabel("Add persona");
		});

		return creationMenu;
	}

	public void destroy() {
		ServletContextPool.remove(getPortletContext().getPortletContextName());

		super.destroy();
	}
}