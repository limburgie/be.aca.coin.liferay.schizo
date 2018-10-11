<h3>Sign in as</h3>
<#list schizoPersonas>
    <ul>
        <#items as persona>
			<li>
				<a href="${persona.loginUrl}">
					<#if persona.portrait??>
						<img src="${persona.portrait}" width="20" height="20"/>
					</#if>
					${persona.firstName}
				</a>
			</li>
		</#items>
	</ul>
</#list>

<p>or <a href="/c/portal/logout">sign out</a>.</p>

<h3>Current persona's data context</h3>
<pre>
	${schizoDataContext!""}
</pre>