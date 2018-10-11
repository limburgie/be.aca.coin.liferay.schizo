package be.aca.coin.liferay.schizo.internal.rest;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.service.Schizo;

@Component(
		immediate = true,
		property = {
				JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/schizo",
				JaxrsWhiteboardConstants.JAX_RS_NAME + "=Schizo"
		},
		service = Application.class
)
@ApplicationPath("/schizo")
public class SchizoRestService extends Application {

	@Reference private Schizo schizo;

	@GET
	@Path("/personas")
	@Produces("application/json")
	public List<Persona> getPersonas() {
		return schizo.getPersonas();
	}

	@GET
	@Path("/data-context")
	@Produces("application/json")
	public String getDataContext() {
		return schizo.getDataContext();
	}

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}
}
