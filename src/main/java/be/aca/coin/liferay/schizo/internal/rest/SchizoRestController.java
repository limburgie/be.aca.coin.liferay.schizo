package be.aca.coin.liferay.schizo.internal.rest;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import be.aca.coin.liferay.schizo.api.service.Schizo;

@Component(
		immediate = true,
		property = {
				"jaxrs.application=true"
		},
		service = Application.class
)
@ApplicationPath("/api")
public class SchizoRestController extends Application {

	private static final Log LOGGER = LogFactoryUtil.getLog(SchizoRestController.class);

	@Reference private Schizo schizo;
	@Reference private PermissionCheckerFactory permissionCheckerFactory;
	@Reference private Portal portal;

	@GET
	@Path("/data-context")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDataContext(@Context HttpServletRequest request) {
		return runPermissioned(request, t -> schizo.getDataContext(), Schizo.DEFAULT_DATA_CONTEXT);
	}

	@GET
	@Path("/is-persona")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean isPersona(@Context HttpServletRequest request) {
		return runPermissioned(request, t -> schizo.isPersona(), false);
	}

	@FunctionalInterface
	private interface PermissionThread<T> {

		T execute(HttpServletRequest request);
	}

	private <T> T runPermissioned(HttpServletRequest request, PermissionThread<T> permissionThread, T defaultValue) {
		try {
			User user = portal.getUser(request);
			PermissionChecker oldPermissionChecker = PermissionThreadLocal.getPermissionChecker();
			PermissionThreadLocal.setPermissionChecker(permissionCheckerFactory.create(user));

			T result = permissionThread.execute(request);

			PermissionThreadLocal.setPermissionChecker(oldPermissionChecker);

			return result;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(new JacksonJsonProvider());
		singletons.add(new PersonasResource(schizo));

		return singletons;
	}
}