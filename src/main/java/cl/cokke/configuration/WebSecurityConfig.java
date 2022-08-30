package cl.cokke.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig {
	//inyectando el manejo de autentificacion para redirigir al usuario si los datos de ingreso son correctos
		@Autowired
		private AuthenticationSuccessHandler authenticationSuccessHandler;
		
		@Bean
		AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
			return authenticationConfiguration.getAuthenticationManager();
		}
		
		//filtros para comparar la peticion (request) que llega a la aplicacion desde la vista
		@Bean
		SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			
			//configuraciones para deshabilitar seguridad y trabajar con la base de datos H2
			http.csrf().disable();
			http.headers().frameOptions().disable();
			
			http
			.authorizeRequests()
			.antMatchers("/admin/**","/h2-console/**").hasAuthority("ADMIN") //.hasRole("ADMIN") //definiendo rutas para las diferentes autoridades existentes
			.antMatchers("/client/**").hasAuthority("CLIENT")   //.hasRole("USER")
			.antMatchers("/login","/")//estableciendo el ingreso sin seguridad a /login para desplegar la pagina de inicio de sesion
			.permitAll()
			.anyRequest()//algun request diferente sera autenticado, el usuario debe estar logueado
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login")//estableciendo la pagina de login
			.successHandler(authenticationSuccessHandler)//manejando el inicio de sesion exitoso mediante el AuthenticacionSuccessHandler (CustomAuthenticationSucessHandler)
			.failureUrl("/login?error=true")//pagina si sucede un error en el login
			.usernameParameter("email")//name en el input de ingreso
			.passwordParameter("password")
//			.defaultSuccessUrl("/default", true)
	        .and()
			.exceptionHandling()
			.accessDeniedPage("/recurso-prohibido");
			
			http
			.logout()
	        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	        .logoutSuccessUrl("/login")
	        .deleteCookies("JSESSIONID")
	        .invalidateHttpSession(true);
					
			http.sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	        .invalidSessionUrl("/login");
				
			return http.build();
		}
		
		//configurando rutas de recursos, permitiendo el ingreso hacia los diferentes antMatchers (path o rutas)
		@Bean 
		@Order(0)
		SecurityFilterChain resources(HttpSecurity http) throws Exception {
		    http
		        .requestMatchers((matchers) -> matchers.antMatchers("/images/**", "/js/**", "/webjars/**","/css/**"))
		        .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
		        .requestCache().disable()
		        .securityContext().disable()
		        .sessionManagement().disable();

		    return http.build();
		}
		
		//encriptacion de la password mediante BCrypt
		@Bean
		BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
	}