package be.aca.coin.liferay.schizo.internal.signin.portlet.configaction;

import javax.portlet.*;

import org.osgi.service.component.annotations.*;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;

import be.aca.coin.liferay.schizo.internal.signin.portlet.SchizoSignInPortletConstants;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + SchizoSignInPortletConstants.PORTLET_NAME
		},
		service = ConfigurationAction.class
)
public class SchizoSignInConfigurationAction extends DefaultConfigurationAction {

	private static final String PREFERENCE_FORMAT = "preferences--%s--";

	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		String displayStyle = ParamUtil.getString(actionRequest, String.format(PREFERENCE_FORMAT, "displayStyle"));

		PortletPreferences preferences = actionRequest.getPreferences();
		preferences.setValue("displayStyle", displayStyle);
		preferences.store();

		super.processAction(portletConfig, actionRequest, actionResponse);
	}
}