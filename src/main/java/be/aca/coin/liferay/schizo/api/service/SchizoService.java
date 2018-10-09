package be.aca.coin.liferay.schizo.api.service;

import java.util.List;

import com.google.gson.JsonObject;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.exception.CannotSavePersonaException;
import be.aca.coin.liferay.schizo.api.exception.NoSuchPersonaException;

public interface SchizoService {

	/**
	 * Retrieves the persona definition for the given screen name.
	 * If no persona is found with that screen name, a NoSuchPersonaException is thrown.
	 */
	Persona getPersona(String screenName) throws NoSuchPersonaException;

	/**
	 * Retrieves a list of all personas. Personas are ordered as configured.
	 */
	List<Persona> getPersonas();

	/**
	 * Retrieves the amount of personas defined.
	 */
	int getPersonasCount();

	/**
	 * Updates the persona with the given screen name using the given information.
	 */
	void savePersona(String screenName, Persona persona) throws CannotSavePersonaException;

	/**
	 * Retrieves the data context of the currently signed in persona.
	 * If no user is signed in or if the currently signed in user is not a persona, a null object is returned.
	 */
	JsonObject getDataContext();
}