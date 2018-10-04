package be.aca.coin.liferay.schizo.api.service;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.exception.NoSuchPersonaException;

public interface SchizoService {

	/**
	 * Retrieves the persona definition for the given screen name.
	 * If no persona is found with that screen name, a NoSuchPersonaException is thrown.
	 */
	Persona getPersona(String screenName) throws NoSuchPersonaException;
}