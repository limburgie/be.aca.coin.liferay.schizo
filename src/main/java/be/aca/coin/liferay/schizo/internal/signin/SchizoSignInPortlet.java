package be.aca.coin.liferay.schizo.internal.signin;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

@Component(
		immediate = true,
		property = {
				"com.liferay.portlet.display-category=category.tools",
				"javax.portlet.display-name=Persona Sign In",
				"javax.portlet.init-param.view-template=/sign_in/view.jsp",
				"javax.portlet.name=" + SchizoSignInPortletConstants.PORTLET_NAME,
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.supports.mime-type=text/html"
		},
		service = Portlet.class
)
public class SchizoSignInPortlet extends MVCPortlet {

}