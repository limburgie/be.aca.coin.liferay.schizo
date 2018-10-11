package be.aca.coin.liferay.schizo.internal.portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;

@Component(
		immediate = true,
		property = {
				"panel.category.key=" + PanelCategoryKeys.CONTROL_PANEL_USERS,
				"store.ranking:Integer=100"
		},
		service = PanelApp.class
)
public class SchizoPanelApp extends BasePanelApp {

	public String getPortletId() {
		return SchizoPortletConstants.PORTLET_NAME;
	}

	@Reference(
			target = "(javax.portlet.name=" + SchizoPortletConstants.PORTLET_NAME + ")",
			unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}
}