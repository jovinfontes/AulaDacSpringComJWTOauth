package com.dacproject.dacproject.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.dacproject.dacproject.components.JwtTokenEnhancer;

/*Implementação do Oauth2
 * do servidor de autorização AuthorizationServerConfig
 */
@Configuration
@EnableAuthorizationServer /*Essa anotatio vai configurar esta classe como servidor de autorização */
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	
	@Value("${jwt.duration}")
	private Integer jwtDuration;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
     /*
     * Aqui é o bean de métodos da classe WebSecuriryConfig,
     * utilizados aqui neste servidor de autorização
     */
	@Autowired
	private AuthenticationManager authenticationManager;

     /*
     * Aqui são os beans de métodos da classe AppConfig,
     * utilizados aqui neste servidor de autorização
     */
    @Autowired
	private JwtTokenStore tokenStore;
	
	@Autowired
	private JwtTokenEnhancer tokenEnhancer;
	
    /*Este método permite configurarmos as permissões se o usuário estiver autenticado
     * permitAll() e isAuthenticated() são métodos pré-definidos do framework Spring
     */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

    /*Este método de configuração tem a função de tratar as requisições vindas do
     *cliente. Assim, uma aplicação Web quiser acessar algum recurso da API ela
     * precisará informar o nome da aplicação (clienteId) no caso seria o nme de "dacproject"
     * e a senha de deste usuário (clientSecret).
     * Ainda neste método é permitido fazer leitura e escrita de acesso "read", "write".
     * O authorizedGrantTypes é o tipo de token utilizado para a autorização.
     * E o accessTokenValiditySeconds é o tempo de expiração desse token gerado,
     * definimos a variável de ambiente jwtDuration = 86400 minutos
     */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient(clientId)
		.secret(passwordEncoder.encode(clientSecret))
		.scopes("read", "write")
		.authorizedGrantTypes("password")
		.accessTokenValiditySeconds(jwtDuration);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain chain = new TokenEnhancerChain();
		chain.setTokenEnhancers(Arrays.asList(accessTokenConverter, tokenEnhancer));
		
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore)
		.accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(chain);
	}
}
