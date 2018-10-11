package be.aca.coin.liferay.schizo.internal.store.impl;

import java.util.*;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.ArrayUtil;

import be.aca.coin.liferay.schizo.internal.configuration.SchizoConfiguration;
import be.aca.coin.liferay.schizo.internal.domain.PersonaDefinition;
import be.aca.coin.liferay.schizo.internal.store.PersonaStore;
import be.aca.coin.liferay.schizo.internal.store.exception.CannotSavePersonaException;
import be.aca.coin.liferay.schizo.internal.store.exception.NoSuchPersonaException;

@Component(
		immediate = true,
		service = PersonaStore.class,
		configurationPid = "be.aca.coin.liferay.schizo.internal.configuration.SchizoConfiguration"
)
public class PersonaConfigurationStore implements PersonaStore {

	private static final Log LOGGER = LogFactoryUtil.getLog(PersonaConfigurationStore.class);

	@Reference private ConfigurationProvider configurationProvider;

	private Map<String, PersonaDefinition> personaMap;
	private boolean dirty;

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		personaMap = new LinkedHashMap<>();

		SchizoConfiguration schizoConfiguration = ConfigurableUtil.createConfigurable(SchizoConfiguration.class, properties);

		if (schizoConfiguration != null) {
			for (String definitionJson : schizoConfiguration.personaDefinitions()) {
				if (definitionJson != null && !definitionJson.isEmpty()) {
					PersonaDefinition personaDefinition = new Gson().fromJson(definitionJson, PersonaDefinition.class);
					personaMap.put(personaDefinition.getScreenName(), personaDefinition);
				}
			}

			dirty = false;
		}
	}

	public List<PersonaDefinition> getPersonas() {
		return new ArrayList<>(personaMap.values());
	}

	public PersonaDefinition getPersona(String screenName) throws NoSuchPersonaException {
		PersonaDefinition persona = personaMap.get(screenName);

		if (persona == null) {
			throw new NoSuchPersonaException();
		}

		return persona;
	}

	public void savePersona(String screenName, PersonaDefinition persona) throws CannotSavePersonaException {
		Map<String, PersonaDefinition> personaMap = new LinkedHashMap<>(this.personaMap);

		personaMap.put(screenName, persona);

		savePersonaMap(personaMap);

		dirty = true;

		waitForConfigurationUpdate();
	}

	private void waitForConfigurationUpdate() {
		while(dirty) {
			try {
				Thread.sleep(100);
				LOGGER.debug("Waiting for configuration to be updated");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void savePersonaMap(Map<String, PersonaDefinition> personaMap) throws CannotSavePersonaException {
		String[] personaDefinitions = new String[0];

		Gson gson = new Gson();

		for (PersonaDefinition persona : personaMap.values()) {
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
}