<#list schizoPersonas>
	<div class="row">
        <#items as persona>
			<div class="col-md-2 text-center">
				<div style="background: #F0F2F5; padding: 15px; border-radius: 5px">
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
			</div>
		</#items>
	</div>
</#list>
