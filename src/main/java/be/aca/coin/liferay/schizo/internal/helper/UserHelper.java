package be.aca.coin.liferay.schizo.internal.helper;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Portal;

import be.aca.coin.liferay.schizo.internal.domain.PersonaDefinition;

@Component(immediate = true, service = UserHelper.class)
public class UserHelper {

	@Reference private Portal portal;
	@Reference private UserLocalService userLocalService;

	public User getOrCreateUser(HttpServletRequest request, PersonaDefinition persona) throws PortalException {
		long companyId = portal.getCompanyId(request);

		try {
			return userLocalService.getUserByScreenName(companyId, persona.getScreenName());
		} catch (NoSuchUserException e) {
			User user = userLocalService.addUser(
					userLocalService.getDefaultUserId(companyId),
					companyId,
					true,
					null,
					null,
					false,
					persona.getScreenName(),
					persona.getEmailAddress(),
					0,
					null,
					portal.getCompany(request).getLocale(),
					persona.getFirstName(),
					null,
					persona.getLastName(),
					0,
					0,
					true,
					0,
					1,
					1970,
					null,
					new long[0],
					new long[0],
					new long[0],
					new long[0],
					false,
					new ServiceContext()
			);

			if (persona.getBio() != null) {
				user.setComments(persona.getBio());
				userLocalService.updateUser(user);
			}

			if (persona.getPortraitWithoutMime() != null) {
				userLocalService.updatePortrait(user.getUserId(), Base64.decode(persona.getPortraitWithoutMime()));
			}

			return user;
		}
	}
}