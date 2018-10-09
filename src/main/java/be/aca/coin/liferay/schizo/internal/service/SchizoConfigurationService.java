package be.aca.coin.liferay.schizo.internal.service;

import java.util.*;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.exception.CannotSavePersonaException;
import be.aca.coin.liferay.schizo.api.exception.NoSuchPersonaException;
import be.aca.coin.liferay.schizo.api.service.SchizoService;
import be.aca.coin.liferay.schizo.internal.configuration.SchizoConfiguration;

@Component(
		immediate = true,
		service = SchizoService.class,
		configurationPid = "be.aca.coin.liferay.schizo.internal.configuration.SchizoConfiguration"
)
public class SchizoConfigurationService implements SchizoService {

	@Reference private ConfigurationProvider configurationProvider;

	private Map<String, Persona> personaMap;

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		personaMap = new LinkedHashMap<>();

		for (String definitionJson : ConfigurableUtil.createConfigurable(SchizoConfiguration.class, properties).personaDefinitions()) {
			Persona persona = new Gson().fromJson(definitionJson, Persona.class);
			personaMap.put(persona.getProfile().getScreenName(), persona);
		}
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

	public void savePersona(String screenName, Persona persona) throws CannotSavePersonaException {
		Map<String, Persona> personaMap = new LinkedHashMap<>(this.personaMap);

		personaMap.put(screenName, persona);

		savePersonaMap(personaMap);
	}

	private void savePersonaMap(Map<String, Persona> personaMap) throws CannotSavePersonaException {
		String[] personaDefinitions = new String[0];

		Gson gson = new Gson();

		for (Persona persona : personaMap.values()) {
			personaDefinitions = ArrayUtil.append(personaDefinitions, gson.toJson(persona));
		}

		Dictionary<String, Object> properties = new Hashtable<>();
		properties.put("personaDefinitions", personaDefinitions);

		try {
			configurationProvider.saveSystemConfiguration(SchizoConfiguration.class, properties);
		} catch (ConfigurationException e) {
			throw new CannotSavePersonaException(e);
		}
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