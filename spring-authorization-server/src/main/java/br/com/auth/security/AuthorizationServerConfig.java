package br.com.auth.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Value("${access_token_validity_seconds}")
	private int accessTokenValidityInSeconds;
	@Value("${refresh_token_validity_seconds}")
	private int refreshTokenValidityInSeconds;

    /**
     * Key to generate JWT Token
     */
    private static final String SIGNING_KEY = "ACa2bsDX4a";

    /**
     * ClientId, ClientSecret and ResourceIds (client can access)
     */
    private static final String CLIENT_ID = "FRONT_END_APP";
    private static final String CLIENT_SECRET = "passw@rd123";
    private static final String[] RESOURCE_IDS = { "spring-authorization-server",  "spring-resource-server" };

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        	.tokenServices(tokenServices())
        	.tokenStore(tokenStore())
        	.accessTokenConverter(accessTokenConverter())
        	.authenticationManager(this.authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(CLIENT_ID)
                // For oauth access client using "client_credentials"
				//.authorizedGrantTypes("client_credentials")
                .authorizedGrantTypes("password")
				.scopes("read", "write")
				.resourceIds(RESOURCE_IDS)
				.secret("{noop}" + CLIENT_SECRET);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setReuseRefreshToken(false);
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        defaultTokenServices.setAccessTokenValiditySeconds(accessTokenValidityInSeconds);
        defaultTokenServices.setRefreshTokenValiditySeconds(refreshTokenValidityInSeconds);
        return defaultTokenServices;
    }
}
