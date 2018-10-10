<h3>Sign in as</h3>
<#list schizoPersonas>
    <ul>
        <#items as persona>
			<li>
				<a href="/c/portal/login?schizo=${persona.profile.screenName}">
					<#if persona.profile.portrait??>
						<img src="${persona.profile.portrait}" width="20" height="20"/>
					</#if>
					${persona.profile.firstName}
				</a>
			</li>
		</#items>
	</ul>
</#list>

<p>or <a href="/c/portal/logout">sign out</a>.</p>

<h3>Current persona's data context</h3>
<pre>
	${schizoDataContext}
</pre>