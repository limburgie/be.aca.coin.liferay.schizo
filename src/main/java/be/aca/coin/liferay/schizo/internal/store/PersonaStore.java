package be.aca.coin.liferay.schizo.internal.store;

import java.util.List;

import be.aca.coin.liferay.schizo.internal.domain.PersonaDefinition;
import be.aca.coin.liferay.schizo.internal.store.exception.CannotSavePersonaException;
import be.aca.coin.liferay.schizo.internal.store.exception.NoSuchPersonaException;

/**
 * Interface for storing and retrieving persona definitions.
 */
public interface PersonaStore {

	List<PersonaDefinition> getPersonas();

	PersonaDefinition getPersona(String screenName) throws NoSuchPersonaException;

	void savePersona(String screenName, PersonaDefinition persona) throws CannotSavePersonaException;
}