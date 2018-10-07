package be.aca.coin.liferay.schizo.internal.service;

import java.util.*;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.domain.PersonaProfile;
import be.aca.coin.liferay.schizo.api.exception.NoSuchPersonaException;
import be.aca.coin.liferay.schizo.api.service.SchizoService;
import be.aca.coin.liferay.schizo.internal.configuration.SchizoConfiguration;

@Component(
		immediate = true,
		service = SchizoService.class,
		configurationPid = "be.aca.coin.liferay.schizo.internal.configuration.SchizoConfiguration"
)
public class SchizoConfigurationService implements SchizoService {

	private Map<String, Persona> personaMap;

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		personaMap = new LinkedHashMap<>();

		for (String definitionJson : ConfigurableUtil.createConfigurable(SchizoConfiguration.class, properties).personaDefinitions()) {
			Persona persona = fromJson(new JsonParser().parse(definitionJson).getAsJsonObject());
			personaMap.put(persona.getProfile().getScreenName(), persona);
		}
	}

	private Persona fromJson(JsonObject definitionJO) {
		JsonObject profileJO = definitionJO.getAsJsonObject("profile");

		String screenName = profileJO.get("screenName").getAsString();
		String emailAddress = profileJO.get("emailAddress").getAsString();
		String firstName = profileJO.get("firstName").getAsString();
		String lastName = profileJO.get("lastName").getAsString();
		String portrait = profileJO.has("portrait") ? profileJO.get("portrait").getAsString() : null;

		PersonaProfile profile = new PersonaProfile(screenName, emailAddress, firstName, lastName, portrait);

		JsonObject dataContext = definitionJO.getAsJsonObject("dataContext");

		return new Persona(profile, dataContext);
	}

	public Persona getPersona(String screenName) throws NoSuchPersonaException {
		Persona persona = personaMap.get(screenName);

		if (persona == null) {
			throw new NoSuchPersonaException();
		}

		return persona;
	}

	public List<Persona> getPersonas() {
		return new ArrayList<>(personaMap.values());
	}

	public int getPersonasCount() {
		return personaMap.size();
	}

	public JsonObject getDataContext() {
		PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isSignedIn()) {
			return new JsonObject();
		}

		String screenName = permissionChecker.getUser().getScreenName();

		try {
			return getPersona(screenName).getDataContext();
		} catch (NoSuchPersonaException e) {
			return new JsonObject();
		}
	}
}