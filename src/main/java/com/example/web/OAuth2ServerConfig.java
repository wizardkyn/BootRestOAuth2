package com.example.web;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.example.web.login.LoginService;

//@Configuration
public class OAuth2ServerConfig {

	private static final String RESOURCE_ID = "REST_SERVICE";

//	@Configuration
//	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
		@Autowired
		private TokenStore tokenStore;

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources
				.tokenStore(tokenStore)
				.resourceId(RESOURCE_ID);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().authenticated();
		}
	}

//	@Configuration
//	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private DataSource dataSource;

		@Bean
		public JdbcTokenStore tokenStore() {
			return new JdbcTokenStore(dataSource);
		}
		
		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Autowired
		@Qualifier("loginService")
		private LoginService loginService;
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.jdbc(dataSource)
				.withClient("my-trusted-client")
					.authorizedGrantTypes("password", "authorization_code","refresh_token", "implicit")
					.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
					.scopes("read", "write", "trust")
					.resourceIds(RESOURCE_ID)
					.accessTokenValiditySeconds(60).and()
				.withClient("my-client-with-registered-redirect")
					.authorizedGrantTypes("authorization_code")
					.authorities("ROLE_CLIENT")
					.scopes("read", "trust")
					.resourceIds(RESOURCE_ID)
					.redirectUris("http://anywhere?key=value").and()
				.withClient("restClient")
					.authorizedGrantTypes("client_credentials", "password")
					.authorities("ROLE_CLIENT")
					.scopes("read")
					.resourceIds(RESOURCE_ID)
					.secret("restSecret");
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints
				.tokenStore(tokenStore())
				.authenticationManager(this.authenticationManager)
				.userDetailsService(loginService);
		}
	}
}
