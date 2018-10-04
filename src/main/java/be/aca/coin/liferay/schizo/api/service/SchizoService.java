package be.aca.coin.liferay.schizo.api.service;

import be.aca.coin.liferay.schizo.api.domain.PersonaDefinition;

public interface SchizoService {

	PersonaDefinition getPersonaForScreenName(String screenName) throws NoSuchPersonaException;
}
