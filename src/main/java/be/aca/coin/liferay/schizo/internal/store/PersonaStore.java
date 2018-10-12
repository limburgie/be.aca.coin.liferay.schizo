package be.aca.coin.liferay.schizo.internal.store;

import java.util.List;

import be.aca.coin.liferay.schizo.internal.domain.PersonaDefinition;
import be.aca.coin.liferay.schizo.internal.store.exception.CannotSavePersonaException;
import be.aca.coin.liferay.schizo.internal.store.exception.NoSuchPersonaException;

/**
 * Interface for storing and retrieving persona definitions.
 */
public interface PersonaStore {

	/**
	 * Returns all defined personas in the system.
	 */
	List<PersonaDefinition> getPersonas();

	/**
	 * Returns the persona definition with the given screen name.
	 * @throws NoSuchPersonaException if no such persona is found.
	 */
	PersonaDefinition getPersona(String screenName) throws NoSuchPersonaException;

	/**
	 * Returns true if there is a persona defined with the given screen name.
	 */
	boolean hasPersona(String screenName);

	/**
	 * Stores the persona definition under the given screen name.
	 * @throws CannotSavePersonaException if a problem occurs while saving the persona.
	 */
	void savePersona(String screenName, PersonaDefinition persona) throws CannotSavePersonaException;
}