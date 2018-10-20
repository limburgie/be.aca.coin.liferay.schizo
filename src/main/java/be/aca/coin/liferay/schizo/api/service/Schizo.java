package be.aca.coin.liferay.schizo.api.service;

import java.util.List;

import be.aca.coin.liferay.schizo.api.domain.Persona;

public interface Schizo {

	String DEFAULT_DATA_CONTEXT = "{}";

	/**
	 * Retrieves a list of all personas. Personas are ordered as configured.
	 */
	List<Persona> getPersonas();

	/**
	 * Retrieves the data context of the currently signed in persona as a JSON string.
	 * If no user is signed in or if the currently signed in user is not a persona, null is returned.
	 */
	String getDataContext();

	/**
	 * Retrieves true if the currently logged in user is a persona.
	 */
	boolean isPersona();
}