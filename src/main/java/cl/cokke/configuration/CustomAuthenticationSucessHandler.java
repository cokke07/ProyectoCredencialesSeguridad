package cl.cokke.configuration;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class CustomAuthenticationSucessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//se convierte la lista que se recibe de la autentificacion (Authentication) a un set de datos
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		
		if (roles.contains("ADMIN")) {//si el rol contenido en el set de datos es ADMIN
			response.sendRedirect("/admin"); //se redirecciona hacia el controller de admin
		}else {
			response.sendRedirect("/client");
		}
		
	}

}
