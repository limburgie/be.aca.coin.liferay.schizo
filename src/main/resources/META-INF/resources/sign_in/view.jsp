<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/sign_in/init.jsp" %>

<liferay-ddm:template-renderer
		className="be.aca.coin.liferay.schizo.api.service.Schizo"
		contextObjects="${contextObjects}"
		displayStyle="${displayStyle}"
		displayStyleGroupId="${portletGroupId}"
		entries="${personas}">

	<c:choose>
		<c:when test="${themeDisplay.signedIn}">
			<p>You are signed in as <c:choose><c:when test="${schizo.persona}">persona</c:when><c:otherwise>real user</c:otherwise></c:choose> <a href="${themeDisplay.URLMyAccount}">${themeDisplay.user.fullName}</a>.</p>
			<a class="btn btn-default" href="${themeDisplay.URLSignOut}">Sign out</a>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${!empty personas}">
					<div class="row">
						<c:forEach items="${personas}" var="persona">
							<div class="col-md-2 text-center">
								<div class="card">
									<a href="${persona.loginUrl}">
										<c:choose>
											<c:when test="${!empty persona.portrait}">
												<img src="${persona.portrait}" width="100%"/>
											</c:when>
											<c:otherwise>
												<img src="/image/user_male_portrait" width="100%"/>
											</c:otherwise>
										</c:choose>
									</a>
									<div class="card-row card-row-padded card-row-valign-top">
										<div class="card-col-content">
											<h3 class="text-center">
												<a href="${persona.loginUrl}">${persona.firstName}</a>
											</h3>
											<p class="lfr-card-subtitle-text">${persona.bio}</p>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:when>
				<c:otherwise>
					No personas were defined.
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>

</liferay-ddm:template-renderer>