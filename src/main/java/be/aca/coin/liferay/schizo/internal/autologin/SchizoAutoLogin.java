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
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

@Component(immediate = true, service = AutoLogin.class)
public class SchizoAutoLogin extends BaseAutoLogin {

	private static final String SCHIZO_PARAMETER_NAME = "schizo";
	private static final Log LOGGER = LogFactoryUtil.getLog(SchizoAutoLogin.class);

	@Reference private Portal portal;
	@Reference private UserLocalService userLocalService;

	protected String[] doLogin(HttpServletRequest request, HttpServletResponse response) {
		long companyId = portal.getCompanyId(request);
		String screenName = request.getParameter(SCHIZO_PARAMETER_NAME);

		try {
			User user = userLocalService.getUserByScreenName(companyId, screenName);

			String userId = String.valueOf(user.getUserId());
			String password = user.getPassword();

			LOGGER.info(String.format("Schizo signed in with persona <%s>", screenName));

			return new String[] { userId, password, Boolean.toString(true) };
		} catch (PortalException e) {
			return new String[0];
		}
	}
}