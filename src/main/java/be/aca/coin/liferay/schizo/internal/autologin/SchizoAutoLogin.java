package be.aca.coin.liferay.schizo.internal.autologin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.exception.NoSuchPersonaException;
import be.aca.coin.liferay.schizo.api.service.SchizoService;
import be.aca.coin.liferay.schizo.internal.helper.UserHelper;

@Component(
		immediate = true,
		service = AutoLogin.class
)
public class SchizoAutoLogin extends BaseAutoLogin {

	private static final String SCHIZO_PARAMETER_NAME = "schizo";
	private static final Log LOGGER = LogFactoryUtil.getLog(SchizoAutoLogin.class);

	@Reference private UserHelper userHelper;
	@Reference private SchizoService schizoService;

	protected String[] doLogin(HttpServletRequest request, HttpServletResponse response) {
		String screenName = request.getParameter(SCHIZO_PARAMETER_NAME);

		try {
			Persona persona = schizoService.getPersona(screenName);

			User user = userHelper.getOrCreateUser(request, persona);

			String userId = String.valueOf(user.getUserId());
			String password = user.getPassword();

			LOGGER.info(String.format("Schizo signed in with persona <%s>", persona.getProfile().getFirstName()));

			return new String[] { userId, password, Boolean.toString(true) };
		} catch (NoSuchPersonaException e) {
			LOGGER.info(String.format("No persona exists with screen name <%s>", screenName));
		} catch (PortalException e) {
			LOGGER.error(e);
		}

		return new String[0];
	}
}