<#--
Uncomment and edit the skeleton template below for a
custom representation of your persona sign in portlet.

${schizo.personas} contains the list of Persona objects.
Each Persona object has the following properties:
- firstName
- portrait (base64 representation)
- bio
- loginUrl
-->

<#--
<#list schizo.personas>
	<div class="row">
        <#items as persona>
			<div class="col-md-2 text-center">
				<div class="card">
					<a href="${persona.loginUrl}">
						<#if persona.portrait??>
							<img src="${persona.portrait}" width="100%"/>
						<#else>
							<img src="/image/user_male_portrait" width="100%"/>
						</#if>
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
		</#items>
	</div>
<#else>
	<p>No personas were defined.</p>
</#list>
-->