package be.aca.coin.liferay.schizo.internal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.service.Schizo;
import be.aca.coin.liferay.schizo.internal.autologin.SchizoAutoLogin;
import be.aca.coin.liferay.schizo.internal.store.PersonaStore;
import be.aca.coin.liferay.schizo.internal.store.exception.NoSuchPersonaException;

@Component(
		immediate = true,
		service = Schizo.class
)
public class DefaultSchizo implements Schizo {

	private static final String LOGIN_URL_FORMAT = "/c/portal/login?" + SchizoAutoLogin.SCHIZO_PARAMETER_NAME + "=%s";

	@Reference private PersonaStore personaStore;

	public List<Persona> getPersonas() {
		return personaStore.getPersonas().stream().map(personaDefinition -> {
			String firstName = personaDefinition.getFirstName();
			String portrait = personaDefinition.getPortrait();
			String bio = personaDefinition.getBio();
			String loginUrl = String.format(LOGIN_URL_FORMAT, personaDefinition.getScreenName());

			return new Persona(firstName, portrait, bio, loginUrl);
		}).collect(Collectors.toList());
	}

	public String getDataContext() {
		String screenName = getCurrentUserScreenName();

		if (screenName == null) {
			return null;
		}

		try {
			return personaStore.getPersona(screenName).getPrettyPrintedDataContext();
		} catch (NoSuchPersonaException e) {
			return null;
		}
	}

	public boolean isPersona() {
		String screenName = getCurrentUserScreenName();

		if (screenName == null) {
			return false;
		}

		return personaStore.hasPersona(screenName);
	}

	private String getCurrentUserScreenName() {
		PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isSignedIn()) {
			return null;
		}

		return permissionChecker.getUser().getScreenName();
	}
}