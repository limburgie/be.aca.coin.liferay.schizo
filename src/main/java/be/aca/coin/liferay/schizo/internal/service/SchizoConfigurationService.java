package be.aca.coin.liferay.schizo.internal.service;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import be.aca.coin.liferay.schizo.api.domain.PersonaDefinition;
import be.aca.coin.liferay.schizo.api.domain.PersonaProfile;
import be.aca.coin.liferay.schizo.api.service.NoSuchPersonaException;
import be.aca.coin.liferay.schizo.api.service.SchizoService;
import be.aca.coin.liferay.schizo.internal.configuration.SchizoConfiguration;

@Component(
		immediate = true,
		service = SchizoService.class,
		configurationPid = "be.aca.coin.liferay.schizo.internal.configuration.SchizoConfiguration"
)
public class SchizoConfigurationService implements SchizoService {

	private Map<String, PersonaDefinition> personaDefinitions;

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		personaDefinitions = new HashMap<>();

		for (String definitionJson : ConfigurableUtil.createConfigurable(SchizoConfiguration.class, properties).personaDefinitions()) {
			PersonaDefinition persona = fromJson(new JsonParser().parse(definitionJson).getAsJsonObject());
			personaDefinitions.put(persona.getProfile().getScreenName(), persona);
		}
	}

	private PersonaDefinition fromJson(JsonObject definitionJO) {
		JsonObject profileJO = definitionJO.getAsJsonObject("profile");

		String screenName = profileJO.get("screenName").getAsString();
		String firstName = profileJO.get("firstName").getAsString();
		String lastName = profileJO.get("lastName").getAsString();

		PersonaProfile profile = new PersonaProfile(screenName, firstName, lastName);

		return new PersonaDefinition(profile);
	}

	public PersonaDefinition getPersonaForScreenName(String screenName) throws NoSuchPersonaException {
		PersonaDefinition definition = personaDefinitions.get(screenName);

		if (definition == null) {
			throw new NoSuchPersonaException();
		}

		return definition;
	}
}