<#list schizoPersonas>
	<div class="row">
        <#items as persona>
			 <div class="col-md-2 text-center">
				<a href="${persona.loginUrl}">
					<#if persona.portrait??>
						<p>
							<img src="${persona.portrait}" width="100%"/>
						</p>
					</#if>
				</a>
				<h3>${persona.firstName}</h3>
				<small>${persona.bio}</small>
			 </div>
		</#items>
	</div>
</#list>
