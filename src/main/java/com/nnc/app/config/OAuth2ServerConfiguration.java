package com.nnc.app.config;

import com.nnc.app.config.oauth2.MongoDBApprovalStore;
import com.nnc.app.config.oauth2.MongoDBAuthorizationCodeServices;
import com.nnc.app.config.oauth2.MongoDBClientDetailsService;
import com.nnc.app.config.oauth2.MongoDBTokenStore;
import com.nnc.app.repository.*;
import com.nnc.app.security.AuthoritiesConstants;

import io.github.jhipster.security.Http401UnauthorizedEntryPoint;
import io.github.jhipster.security.AjaxLogoutSuccessHandler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class OAuth2ServerConfiguration {

    @Bean
    public TokenStore tokenStore(OAuth2AccessTokenRepository oAuth2AccessTokenRepository, OAuth2RefreshTokenRepository oAuth2RefreshTokenRepository) {
        return new MongoDBTokenStore(oAuth2AccessTokenRepository, oAuth2RefreshTokenRepository);
    }

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        private final TokenStore tokenStore;

        private final Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint;

        private final AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

        private final CorsFilter corsFilter;

        public ResourceServerConfiguration(TokenStore tokenStore, Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint,
            AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler, CorsFilter corsFilter) {

            this.tokenStore = tokenStore;
            this.http401UnauthorizedEntryPoint = http401UnauthorizedEntryPoint;
            this.ajaxLogoutSuccessHandler = ajaxLogoutSuccessHandler;
            this.corsFilter = corsFilter;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .exceptionHandling()
                .authenticationEntryPoint(http401UnauthorizedEntryPoint)
            .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(ajaxLogoutSuccessHandler)
            .and()
                .csrf()
                .disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .headers()
                .frameOptions().disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/profile-info").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/configuration/ui").permitAll()
                .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN);
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("res_NgxNosqlCrm").tokenStore(tokenStore);
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        private final AuthenticationManager authenticationManager;

        private final TokenStore tokenStore;

        private final OAuth2ApprovalRepository oAuth2ApprovalRepository;

        private final OAuth2CodeRepository oAuth2CodeRepository;

        private final OAuth2ClientDetailsRepository oAuth2ClientDetailsRepository;

        public AuthorizationServerConfiguration(@Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
                TokenStore tokenStore, OAuth2ApprovalRepository oAuth2ApprovalRepository, OAuth2CodeRepository oAuth2CodeRepository,
                OAuth2ClientDetailsRepository oAuth2ClientDetailsRepository) {

            this.authenticationManager = authenticationManager;
            this.tokenStore = tokenStore;
            this.oAuth2ApprovalRepository = oAuth2ApprovalRepository;
            this.oAuth2CodeRepository = oAuth2CodeRepository;
            this.oAuth2ClientDetailsRepository = oAuth2ClientDetailsRepository;
        }

        @Bean
        public ApprovalStore approvalStore() {
            return new MongoDBApprovalStore(oAuth2ApprovalRepository);
        }

        @Bean
        protected AuthorizationCodeServices authorizationCodeServices() {
            return new MongoDBAuthorizationCodeServices(oAuth2CodeRepository);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                .authorizationCodeServices(authorizationCodeServices())
                .approvalStore(approvalStore())
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.allowFormAuthenticationForClients();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(new MongoDBClientDetailsService(oAuth2ClientDetailsRepository));
        }
    }
}
